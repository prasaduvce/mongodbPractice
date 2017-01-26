package com.mongodb.ch2;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import org.bson.Document;

public class UpdateAndReplaceTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);
		MongoDatabase db = mongoClient.getDatabase("course");
		MongoCollection<Document> coll = db.getCollection("updateAndReplaceTest");

		coll.drop();

		for (int i=0; i< 8;i++) {
			coll.insertOne(new Document("_id", i).append("x", i).append("y", true));
		}

		/*coll.replaceOne(Filters.eq("x", 5), new Document("x", 20).append("updated", true));*/
		/*coll.updateOne(Filters.eq("x", 5), new Document("$set", new Document("x", 20).append("updated", true))) ;*/
		/*coll.updateOne(Filters.eq("_id", 9), Updates.combine(Updates.set("x", 20), Updates.set("updated", true)), new UpdateOptions().upsert(true)) ;*/
		coll.updateMany(Filters.gte("_id", 5), Updates.inc("x", 1)) ;
		for (Document cur : coll.find().into(new ArrayList<Document>())) {
			Helpers.printJson(cur, false);
		}
	}
}
