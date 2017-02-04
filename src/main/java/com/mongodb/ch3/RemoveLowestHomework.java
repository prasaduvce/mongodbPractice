package com.mongodb.ch3;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
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

				List<Document> scores = (List<Document>) doc.get("scores");
				// Now find the lowest homework score.
				Document minScoreObj = null;
				double minScore = Double.MAX_VALUE;  // Minimum score value.

				for (Document scoreDocument : scores) {
					// The array contains documents with "type" and "score".
					double score = scoreDocument.getDouble("score");
					String type = scoreDocument.getString("type");
					if (type.equals("homework") && score < minScore) {
						minScore = score;
						minScoreObj = scoreDocument; // this is the lowest score obj
					}
				}
				// Remove the lowest score.
				if (minScoreObj != null) {
					scores.remove(minScoreObj);   // remove the lowest
				}

				// replace the scores array for the student
				mongoCollection.updateOne(eq("_id", doc.get("_id")),
						new Document("$set", new Document("scores", scores)));
				/*Object id = doc.get("_id");
				Document match = mongoCollection.find(Filters.eq("_id", id)).first();
				//match.put( "_id", id );

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
					Document docToDelete = homeworkIterator.next();
					System.out.println("Removing  ===> " + docToDelete);
					mongoCollection.updateOne(match, new Document("$pull", docToDelete));
					while (homeworkIterator.hasNext()) {
						Document docToKeep = homeworkIterator.next();
						//listToUpdate.add(homeworkIterator.next());
						System.out.println("Keeping  ===> " + docToKeep);
						mongoCollection.updateOne(match, new Document("$push", docToKeep));
					}



				}*/

				System.out.println("=========================================================");
			}
		}
		finally {
			mongoCursor.close();
		}
		mongoClient.close();
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
