package com.mongodb.ch2;

import static com.mongodb.ch2.Helpers.printJson;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;

public class SortSkipLimitTest {
	public static void main(String[] args) {
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> collection = mongoDatabase.getCollection("sortSkipLimitTest");
		collection.drop();

		for (int i=1;i<=10;i++) {
			for ( int j=1;j<=10;j++) {
				collection.insertOne(new Document().append("i", i)
						.append("j", j));
			}
		}

		Bson projection = fields(include("i", "j"), excludeId());
		//Bson sort = new Document("i", -1).append("j", -1);
		Bson sort = Sorts.orderBy(Sorts.ascending("i"), Sorts.descending("j"));


		//Find all into
		ArrayList<Document> documents = collection.find().projection(projection).sort(sort)
				.limit(50).skip(20).into(new ArrayList<Document>());
		//documents.forEach((document) -> printJson(document));
		for (Document doc:documents) {
			printJson(doc, false);
		}

		System.out.println("collection.count() ===> "+collection.count());
	}
}
