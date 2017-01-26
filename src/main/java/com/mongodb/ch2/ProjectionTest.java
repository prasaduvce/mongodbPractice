package com.mongodb.ch2;

import static com.mongodb.ch2.Helpers.printJson;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import java.util.ArrayList;
import java.util.Random;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ProjectionTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> collection = mongoDatabase.getCollection("projectionTest");
		collection.drop();

		for (int i=0;i<10;i++) {
			collection.insertOne(new Document().append("x", new Random().nextInt(2))
			.append("y", new Random().nextInt(100)).append("i", i));
		}

		//Bson filter = new Document("x", 0).append("y", new Document("$gt", 30)).append("y", new Document("$lt", 50));

		Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

		//Bson projection = new Document("x", 1).append("i", 1).append("_id", 0);
		Bson projection = Projections.fields(Projections.exclude("_id"), Projections.include("x", "i"));
		//Find all into
		ArrayList<Document> documents = collection.find(filter).projection(projection).into(new ArrayList<Document>());
		//documents.forEach((document) -> printJson(document));
		for (Document doc:documents) {
			printJson(doc);
		}


	}
}
