package com.bitfit.datastore;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Keeps every collection in memory, for local runs and tests. Active when
 * {@code bitfit.datastore=memory} or when the property isn't set. Everything is lost when the app
 * stops.
 *
 * The documents stored here are the fresh maps {@link DataStore} builds on every save, and every
 * read builds a fresh object from them, so changing an object after saving it never changes what
 * is stored. That is how a real database behaves, so a service that forgets to save fails its
 * tests here too.
 */
@Component
@ConditionalOnProperty(name = "bitfit.datastore", havingValue = "memory", matchIfMissing = true)
public class InMemoryDataStore extends DataStore {

    /** collection name -> (document ID -> document) */
    private final Map<String, Map<String, Map<String, Object>>> collections = new ConcurrentHashMap<>();

    /** For tests: {@code new InMemoryDataStore()} with a plain Jackson mapper. */
    public InMemoryDataStore() {
        this(new ObjectMapper());
    }

    /** Used by Spring, which passes in the app's Jackson mapper. */
    @Autowired
    public InMemoryDataStore(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    protected void putDocument(String collection, String id, Map<String, Object> document) {
        collectionForWrite(collection).put(id, document);
    }

    @Override
    protected boolean createDocument(String collection, String id, Map<String, Object> document) {
        // putIfAbsent is atomic, so when two requests race for the same ID only one gets true.
        return collectionForWrite(collection).putIfAbsent(id, document) == null;
    }

    @Override
    protected Optional<Map<String, Object>> getDocument(String collection, String id) {
        return Optional.ofNullable(collectionForRead(collection).get(id));
    }

    @Override
    protected List<Map<String, Object>> findDocuments(String collection, Filter filter) {
        return collectionForRead(collection).values().stream()
                .filter(document -> matches(document, filter))
                .toList();
    }

    @Override
    protected List<Map<String, Object>> listDocuments(String collection) {
        return List.copyOf(collectionForRead(collection).values());
    }

    @Override
    protected void deleteDocument(String collection, String id) {
        Map<String, Map<String, Object>> documents = collections.get(collection);
        if (documents != null) {
            documents.remove(id);
        }
    }

    /** The collection, created empty the first time something is written to it. */
    private Map<String, Map<String, Object>> collectionForWrite(String collection) {
        return collections.computeIfAbsent(collection, name -> new ConcurrentHashMap<>());
    }

    /** The collection, or an empty map if nothing has been written to it (reads don't create it). */
    private Map<String, Map<String, Object>> collectionForRead(String collection) {
        return collections.getOrDefault(collection, Map.of());
    }
}
