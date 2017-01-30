package com.mongodb.ch3;

import java.util.Comparator;
import org.bson.Document;

public class ScoreComparator implements Comparator<Document> {
	public int compare(Document doc1, Document doc2) {
		if (doc1.get("score").equals(doc2.get("score"))) {
			return 0;
		}
		if (Double.valueOf(doc1.get("score").toString()) > Double.valueOf(doc2.get("score").toString())) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
