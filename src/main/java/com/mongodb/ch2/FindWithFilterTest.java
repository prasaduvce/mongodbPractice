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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Random;
import org.bson.Document;
import org.bson.conversions.Bson;

public class FindWithFilterTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> collection = mongoDatabase.getCollection("findWithFilterTest");
		collection.drop();

		for (int i=0;i<10;i++) {
			collection.insertOne(new Document().append("x", new Random().nextInt(2))
			.append("y", new Random().nextInt(100)));
		}

		//Bson filter = new Document("x", 0).append("y", new Document("$gt", 30)).append("y", new Document("$lt", 50));

		Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));
		//Find all into
		ArrayList<Document> documents = collection.find(filter).into(new ArrayList<Document>());
		//documents.forEach((document) -> printJson(document));
		for (Document doc:documents) {
			printJson(doc);
		}

		//Iterator
		MongoCursor<Document> cursor = collection.find(filter).iterator();

		try {
			System.out.println("Iterating cursors ===> ");
			while (cursor.hasNext()) {
				printJson(cursor.next());
			}
		} finally {
			cursor.close();
		}

		System.out.println("Total count of records ==> "+collection.count(filter));

	}
}
