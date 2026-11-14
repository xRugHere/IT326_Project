package com.bitfit.datastore;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * abstract DataStore that defines the interface for storing and retrieving documents in a database.
 * Each document is represented as a Map<String, Object>, and the DataStore provides methods for saving, 
 * creating, finding, listing, and deleting documents. The DataStore also includes a Filter interface for 
 * querying documents based on specific conditions.
 */
public abstract class DataStore {

    private static final TypeReference<Map<String, Object>> DOCUMENT = new TypeReference<>() { };

    private final ObjectMapper mapper;

    /**
     * @param mapper the app's Jackson mapper. A copy is used that skips stored fields the model no
     *               longer has, so old documents can still be read after a field is removed.
     */
    protected DataStore(ObjectMapper mapper) {
        this.mapper = mapper.copy().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // what each database implements to store and retrieve the documents/maps. the public methods above convert to/from objects and handle errors.

    /** Inserts the document, or replaces the whole document if the ID is already taken. */
    protected abstract void putDocument(String collection, String id, Map<String, Object> document)
            throws Exception;


    protected abstract boolean createDocument(String collection, String id, Map<String, Object> document)
            throws Exception;

    /** The document with this ID, or empty if there isn't one. */
    protected abstract Optional<Map<String, Object>> getDocument(String collection, String id)
            throws Exception;

    protected abstract List<Map<String, Object>> findDocuments(String collection, Filter filter)
            throws Exception;

    /** Every document in the collection, in any order. */
    protected abstract List<Map<String, Object>> listDocuments(String collection) throws Exception;

    /** Deletes the document. Deleting an ID that doesn't exist is not an error. */
    protected abstract void deleteDocument(String collection, String id) throws Exception;

    // abstract DataStore that defines the interface for storing and retrieving documents in a database.

    /** Inserts or replaces the object under this ID and returns it. */
    public <T> T save(String collection, String id, T object) {
        checkKey(collection, id);
        Map<String, Object> document = toDocument(object);
        run("save to", collection, () -> {
            putDocument(collection, id, document);
            return null;
        });
        return object;
    }

    /**
     * Inserts the object only if nothing is stored under this ID yet. Returns false, and changes
     * nothing, if the ID is taken. Use it to claim something unique, for example a document in
     * "usernames" whose ID is the lowercase username.
     */
    public <T> boolean create(String collection, String id, T object) {
        checkKey(collection, id);
        Map<String, Object> document = toDocument(object);
        return run("create in", collection, () -> createDocument(collection, id, document));
    }

    /** The object stored under this ID, or empty if there isn't one. */
    public <T> Optional<T> findById(String collection, String id, Class<T> type) {
        checkKey(collection, id);
        return run("read from", collection, () -> getDocument(collection, id))
                .map(document -> fromDocument(document, type));
    }

    /** Every object in the collection that matches the filter, in no particular order. */
    public <T> List<T> find(String collection, Filter filter, Class<T> type) {
        checkCollection(collection);
        Filter stored = storedForm(filter);
        return run("search", collection, () -> findDocuments(collection, stored)).stream()
                .map(document -> fromDocument(document, type))
                .toList();
    }

    /**
     * A matching object, or empty if none match. Only for fields that are unique, such as a friend
     * code, since which one comes back is not defined when several match.
     */
    public <T> Optional<T> findOne(String collection, Filter filter, Class<T> type) {
        return find(collection, filter, type).stream().findFirst();
    }

    /** Every object in the collection, in no particular order. */
    public <T> List<T> findAll(String collection, Class<T> type) {
        checkCollection(collection);
        return run("list", collection, () -> listDocuments(collection)).stream()
                .map(document -> fromDocument(document, type))
                .toList();
    }

    public boolean exists(String collection, String id) {
        checkKey(collection, id);
        return run("read from", collection, () -> getDocument(collection, id)).isPresent();
    }

    /** Deletes the object if it exists. */
    public void delete(String collection, String id) {
        checkKey(collection, id);
        run("delete from", collection, () -> {
            deleteDocument(collection, id);
            return null;
        });
    }

    /**
     * Whether a document matches a filter. This defines what each filter means, so every database
     * must give the same results. A database that can't run a filter as a query can load the
     * collection and keep the documents this accepts.
     */
    protected static boolean matches(Map<String, Object> document, Filter filter) {
        Object stored = readField(document, filter.field());
        if (filter instanceof Filter.Equals equals) {
            return sameValue(stored, equals.value());
        }
        if (filter instanceof Filter.ArrayContains contains) {
            return stored instanceof Collection<?> items
                    && items.stream().anyMatch(item -> sameValue(item, contains.value()));
        }
        throw new IllegalArgumentException("Unsupported filter: " + filter);
    }

    /** The value at a field path such as "profile.pendingRequestIds", or null if it isn't there. */
    protected static Object readField(Map<String, Object> document, String path) {
        Object current = document;
        for (String part : path.split("\\.")) {
            if (!(current instanceof Map<?, ?> map)) {
                return null;
            }
            current = map.get(part);
        }
        return current;
    }

    private Map<String, Object> toDocument(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Can't store null");
        }
        try {
            return mapper.convertValue(object, DOCUMENT);
        } catch (IllegalArgumentException e) {
            throw new DataStoreException("Can't store a " + object.getClass().getSimpleName() + " as a document", e);
        }
    }

    private <T> T fromDocument(Map<String, Object> document, Class<T> type) {
        try {
            return mapper.convertValue(document, type);
        } catch (IllegalArgumentException e) {
            throw new DataStoreException("A stored document can't be read as a " + type.getSimpleName(), e);
        }
    }

    /** The filter with its value in stored form (an enum becomes its name), so it compares like the field. */
    private Filter storedForm(Filter filter) {
        if (filter instanceof Filter.Equals equals) {
            return new Filter.Equals(equals.field(), mapper.convertValue(equals.value(), Object.class));
        }
        if (filter instanceof Filter.ArrayContains contains) {
            return new Filter.ArrayContains(contains.field(), mapper.convertValue(contains.value(), Object.class));
        }
        throw new IllegalArgumentException(filter == null
                ? "A filter is required (use findAll to get everything)"
                : "Unsupported filter: " + filter);
    }

    /** Equal, comparing numbers by value so 5 matches 5L and 5.0. */
    private static boolean sameValue(Object a, Object b) {
        if (a instanceof Number x && b instanceof Number y) {
            return isWhole(x) && isWhole(y) ? x.longValue() == y.longValue() : x.doubleValue() == y.doubleValue();
        }
        return Objects.equals(a, b);
    }

    private static boolean isWhole(Number n) {
        return n instanceof Integer || n instanceof Long || n instanceof Short || n instanceof Byte;
    }

    private static void checkCollection(String collection) {
        if (collection == null || collection.isBlank()) {
            throw new IllegalArgumentException("A collection name is required");
        }
    }

    private static void checkKey(String collection, String id) {
        checkCollection(collection);
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("A document ID is required");
        }
    }

    @FunctionalInterface
    private interface StoreCall<R> {
        R call() throws Exception;
    }

    private static <R> R run(String action, String collection, StoreCall<R> call) {
        try {
            return call.call();
        } catch (DataStoreException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DataStoreException("Interrupted while trying to " + action + " '" + collection + "'", e);
        } catch (Exception e) {
            throw new DataStoreException("Couldn't " + action + " '" + collection + "'", e);
        }
    }

    /**
     * A condition on one stored field. Field names are the model's property names, with dots for
     * nested fields ("profile.pendingRequestIds"). Kept small on purpose: every kind of filter here
     * has to work on every database, so only add one when a feature needs it.
     */
    public sealed interface Filter {

        String field();

        /** The field equals the value. */
        static Filter eq(String field, Object value) {
            return new Equals(field, value);
        }

        /** The field is a list that contains the value. */
        static Filter arrayContains(String field, Object value) {
            return new ArrayContains(field, value);
        }

        record Equals(String field, Object value) implements Filter {
            public Equals {
                check(field, value);
            }
        }

        record ArrayContains(String field, Object value) implements Filter {
            public ArrayContains {
                check(field, value);
            }
        }

        /**
         * Matching on null isn't supported, because databases disagree on whether a missing field
         * counts as null.
         */
        private static void check(String field, Object value) {
            if (field == null || field.isBlank()) {
                throw new IllegalArgumentException("A filter needs a field name");
            }
            if (value == null) {
                throw new IllegalArgumentException("Can't filter '" + field + "' on null");
            }
        }
    }

    /** Thrown when the database fails or a stored document can't be turned back into an object. */
    public static class DataStoreException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public DataStoreException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
