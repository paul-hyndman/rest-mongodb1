package com.example.persistence;

import com.example.domain.Policy;
import com.example.exception.DuplicatePolicyException;
import com.example.exception.PolicyNotFoundException;
import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Component("mongoDbPolicyLookup")
public class MongoDbPolicyLookup implements PolicyLookup {
    @Value("${mongo.client}")
    private String mongoClient;
    @Value("${mongo.db}")
    private String mongoDb;

    public Policy getPolicy(String number) {
        MongoCollection mongoCollection = getPolicyCollection();
        // Use pattern of entities having platform-specific, but only exposing natural key
        // when entity supports a natural key
        Bson projectionFields = Projections.fields(
                Projections.excludeId());
        Document doc = (Document) mongoCollection.find(eq("policyNumber", number))
                .projection(projectionFields)
                .first();
        if (doc == null) {
            throw new PolicyNotFoundException(number);
        }
        String json = doc.toJson();
        // Gson is used for mapping to keep Mongo-specific annotations out of Domain class
        return new Gson().fromJson(json, Policy.class);
    }

    public Policy createPolicy(Policy policy) {
        MongoCollection mongoCollection = getPolicyCollection();
        try {
            Document document = new Document()
                    .append("policyNumber", policy.getPolicyNumber())
                    .append("faceAmount", policy.getFaceAmount())
                    .append("insured", policy.getInsured())
                    .append("age", policy.getAge())
                    .append("streetAddress", policy.getStreetAddress())
                    .append("city", policy.getCity())
                    .append("state", policy.getState())
                    .append("zipCode", policy.getZipCode());
            mongoCollection.insertOne(document);
        } catch (Exception ex) {
            if (ex.getMessage().contains("E11000 duplicate key")) {
                throw new DuplicatePolicyException(policy.getPolicyNumber());
            }
        }
        return getPolicy(policy.getPolicyNumber());
    }

    private MongoCollection getPolicyCollection() {
        MongoClient client = MongoClients.create(mongoClient);
        MongoDatabase database = client.getDatabase(mongoDb);
        MongoCollection mongoCollection = database.getCollection("policies");
        return mongoCollection;
    }
}
