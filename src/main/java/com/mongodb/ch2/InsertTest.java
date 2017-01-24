package com.mongodb.ch2;

import static com.mongodb.ch2.Helpers.printJson;
import static java.util.Arrays.asList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Hello world!
 *
 */
public class InsertTest
{
    public static void main( String[] args )
    {
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		MongoCollection<Document> coll = mongoDatabase.getCollection("insertTest");

		coll.drop();

		Document document = new Document("name", "Smith").append("age", 30).append("profession", "programmer");
		Document document1 = new Document("name", "Jack").append("age", 25).append("profession", "hacker");
		Document document2 = new Document("name", "Jones").append("age", 25).append("profession", "deployer");

		/*printJson(document);
		coll.insertOne(document);
		printJson(document);*/

		printJson(document1);
		printJson(document2);
		coll.insertMany(asList(document1, document2));
		printJson(document1);
		printJson(document2);
	}
}
