package com.mongodb.ch3;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;

public class RemoveLowestHomework {
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase mongoDatabase = mongoClient.getDatabase("school");
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("students");

		MongoCursor<Document> mongoCursor = mongoCollection.find().iterator();
		try {
			while (mongoCursor.hasNext()) {
				Document doc = mongoCursor.next();

				ArrayList<Document> scores = (ArrayList<Document>) doc.get("scores");
				Object id = doc.get("_id");
				BasicDBObject match = new BasicDBObject();
				match.put( "_id", id );

				System.out.println(" Id of the document " + id);

				Iterator<Document> scoresIterator = scores.iterator();
				List<Document> homeworkDocuments = getHomeworkDocuments(scoresIterator);
				Collections.sort(homeworkDocuments, new ScoreComparator());
				System.out.println("homeworkDocuments ===> " + homeworkDocuments);

				Iterator<Document> homeworkIterator = homeworkDocuments.iterator();
				List<Document> listToUpdate = new ArrayList<Document>();

				if (homeworkDocuments.size() == 1) {
					listToUpdate.add(homeworkIterator.next());
				} else {
					homeworkIterator.next();
					while (homeworkIterator.hasNext()) {
						listToUpdate.add(homeworkIterator.next());
					}
				}

				/*BasicDBObject update = new BasicDBObject();
				update.put( "$pushAll", listToUpdate );

				mongoCollection.updateOne(match, update);*/
				System.out.println("Filtered homework documents  ===> " + listToUpdate);
				System.out.println("=========================================================");
			}
		}
		finally {
			mongoCursor.close();
		}
	}

	private static List<Document> getHomeworkDocuments(Iterator<Document> scoresIterator) {
		List<Document> homeworkDocument = new ArrayList<Document>();
		while (scoresIterator.hasNext()) {
			Document score = scoresIterator.next();
			if ("homework".equalsIgnoreCase((String)score.get("type"))) {
				homeworkDocument.add(score);
			}
		}
		return homeworkDocument;
	}


}
