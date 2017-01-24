package com.mongodb.ch2;

import java.util.Arrays;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DocumentTest {
	public static void main(String[] args) {
		Document document = new Document().append("str", "MongoDB, Hello")
				.append("int", 123)
				.append("float", 123.5)
				.append("long", 1234567890123456789L)
				.append("boolean", false)
				.append("date", new Date())
				.append("objectId", new ObjectId())
				.append("null", null)
				.append("embeddedDoc", new Document("x", 0))
				.append("list", Arrays.asList(1, 2, 3));

		String str = document.getString("str");
		int i = document.getInteger("int");
		double f = document.getDouble("float");
		long l = document.getLong("long");
		boolean b = document.getBoolean("boolean");
		Date d = document.getDate("date");
		ObjectId objectId = document.getObjectId("objectId");
		Object nulllObject = document.get("null");

		//document.
		Helpers.printJson(document);
	}
}
