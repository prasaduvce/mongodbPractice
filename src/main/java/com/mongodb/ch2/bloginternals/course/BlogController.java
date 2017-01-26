package com.mongodb.ch2.bloginternals.course;

import freemarker.template.Configuration;
import java.io.StringWriter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class BlogController {
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(BlogController.class, "/freemarker");
		Spark.get("/welcome", new Route() {
			StringWriter writer = new StringWriter();
			public Object handle(Request request, Response response) throws Exception {

				return writer;
			}
		});
	}
}
