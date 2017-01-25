package com.mongodb.ch2;

import static com.mongodb.ch2.Helpers.printJson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;

/**
 * Created by renuka.prasad on 1/25/2017.
 */
public class FindTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> collection = mongoDatabase.getCollection("findTest");
		collection.drop();

		for (int i=0;i<10;i++) {
			collection.insertOne(new Document("x", i));
		}

		//Find one
		Document first = collection.find().first();
		printJson(first);

		//Find all into
		ArrayList<Document> documents = collection.find().into(new ArrayList<Document>());
		//documents.forEach((document) -> printJson(document));
		for (Document doc:documents) {
			printJson(doc);
		}

		//Iterator
		MongoCursor<Document> cursor = collection.find().iterator();

		try {
			System.out.println("Iterating cursors ===> ");
			while (cursor.hasNext()) {
				printJson(cursor.next());
			}
		} finally {
			cursor.close();
		}

		System.out.println("Total count of records ==> "+collection.count());

	}
}
