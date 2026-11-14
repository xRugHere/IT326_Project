package com.bitfit.datastore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

/**
 * Firestore implementation with abstract functions. processes data as maps and uses Firestore's API to store 
 * and retrieve documents. It provides methods for saving, creating, finding, listing, and deleting documents in a Firestore database.
 *  The class also includes a Filter interface for querying documents based on specific conditions.
 */
@Component
@ConditionalOnProperty(name = "bitfit.datastore", havingValue = "firestore")
public class FirestoreDataStore extends DataStore {

    private final Firestore firestore;

    public FirestoreDataStore(ObjectMapper mapper, Firestore firestore) {
        super(mapper);
        this.firestore = firestore;
    }

    @Override
    protected void putDocument(String collection, String id, Map<String, Object> document) throws Exception {
        // set() with a map replaces the whole document, which is what save() promises.
        documentRef(collection, id).set(document).get();
    }

    @Override
    protected boolean createDocument(String collection, String id, Map<String, Object> document) throws Exception {
        DocumentReference ref = documentRef(collection, id);
        // The transaction makes "check, then create" atomic. If two requests race for the same ID,
        // Firestore retries the loser, which then sees the document and returns false.
        return firestore.runTransaction(transaction -> {
            if (transaction.get(ref).get().exists()) {
                return false;
            }
            transaction.create(ref, document);
            return true;
        }).get();
    }

    @Override
    protected Optional<Map<String, Object>> getDocument(String collection, String id) throws Exception {
        DocumentSnapshot snapshot = documentRef(collection, id).get().get();
        return snapshot.exists() ? Optional.ofNullable(snapshot.getData()) : Optional.empty();
    }

    @Override
    protected List<Map<String, Object>> findDocuments(String collection, Filter filter) throws Exception {
        return dataOf(toQuery(collectionRef(collection), filter).get().get());
    }

    @Override
    protected List<Map<String, Object>> listDocuments(String collection) throws Exception {
        return dataOf(collectionRef(collection).get().get());
    }

    @Override
    protected void deleteDocument(String collection, String id) throws Exception {
        // Firestore doesn't treat deleting a missing document as an error.
        documentRef(collection, id).delete().get();
    }

    private static Query toQuery(CollectionReference collection, Filter filter) {
        if (filter instanceof Filter.Equals equals) {
            return collection.whereEqualTo(equals.field(), equals.value());
        }
        if (filter instanceof Filter.ArrayContains contains) {
            return collection.whereArrayContains(contains.field(), contains.value());
        }
        throw new IllegalArgumentException("Unsupported filter: " + filter);
    }

    private static List<Map<String, Object>> dataOf(QuerySnapshot snapshot) {
        return snapshot.getDocuments().stream()
                .map(QueryDocumentSnapshot::getData)
                .toList();
    }

    private CollectionReference collectionRef(String collection) {
        checkNoSlash(collection, "Collection name");
        return firestore.collection(collection);
    }

    private DocumentReference documentRef(String collection, String id) {
        checkNoSlash(id, "Document ID");
        return collectionRef(collection).document(id);
    }

    /**
     * Firestore reads "/" as a path separator, so an ID like "a1/logs/x" would quietly write into a
     * subcollection somewhere else instead of failing. Reject it instead.
     */
    private static void checkNoSlash(String value, String label) {
        if (value.contains("/")) {
            throw new IllegalArgumentException(label + " can't contain '/': " + value);
        }
    }
}
