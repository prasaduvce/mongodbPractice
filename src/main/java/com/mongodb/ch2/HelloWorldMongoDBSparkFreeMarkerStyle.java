package com.mongodb.ch2;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldMongoDBSparkFreeMarkerStyle {
	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreeMarkerStyle.class, "/");

		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectionsPerHost(10).build();
		MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), mongoClientOptions);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("course");
		final MongoCollection<Document> collection = mongoDatabase.getCollection("hello");
		collection.drop();

		collection.insertOne(new Document("name", "MongoDB"));
		Spark.get("/", new Route() {
			public Object handle(Request request, Response response) throws Exception {
				StringWriter stringWriter = new StringWriter();
				try {
					Template template = configuration.getTemplate("/hello.ftl");
					Document document = collection.find().first();
					template.process(document, stringWriter);
				} catch (Exception e) {
					Spark.halt(500);
					e.printStackTrace();
				}
				return stringWriter;
			}
		});

	}
}
