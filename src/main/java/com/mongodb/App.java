package com.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("m101");
		MongoCollection<BsonDocument> collection = mongoDatabase.getCollection("movieDetails", BsonDocument.class);

		FindIterable<BsonDocument> bsonDocuments = collection.find();
		//bsonDocuments.forEach((bsonDocument)->System.out.printf("bsonDocument.values() ===> "+bsonDocument.values()));
		mongoClient.close();
	}
}
