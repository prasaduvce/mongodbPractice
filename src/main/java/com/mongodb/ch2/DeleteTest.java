package com.mongodb.ch2;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class DeleteTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> coll = mongoDatabase.getCollection("deleteTest");
		coll.drop();

		for (int i=0;i<10;i++) {
			coll.insertOne(new Document().append("_id", i));
		}

		//coll.deleteMany(Filters.gte("_id", 4));
		coll.deleteOne(Filters.eq("_id", 4));
		List<Document> documents = coll.find().into(new ArrayList<Document>());
		for (Document doc : documents) {
			Helpers.printJson(doc);
		}
	}
}
