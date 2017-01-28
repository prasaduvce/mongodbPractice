package com.mongodb.ch2;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;

public class HomeWork23 {
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase mongoDatabase = mongoClient.getDatabase("students");
		MongoCollection<Document> documentMongoCollection = mongoDatabase.getCollection("grades");

		Bson sortBy = Sorts.ascending(Arrays.asList("student_id", "score"));
		ArrayList <Document > documents = documentMongoCollection.find().sort(sortBy).into(new ArrayList<Document>());

		int studentId = -1;
		for (Document document:documents) {
			Document entry = document;
			if (!document.get("student_id").equals(studentId)) {
				Object ObjectId = document.get("_id");
				System.out.println("Removing student Document with _id " + ObjectId + ", student_id " + document.get("student_id"));
				documentMongoCollection.deleteOne(document);
			}
			studentId = entry.getInteger("student_id");
		}
		for (Document document:documents) {
			Helpers.printJson(document);
		}

	}
}
