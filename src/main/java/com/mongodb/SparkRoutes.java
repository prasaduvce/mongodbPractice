package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkRoutes {
	public static void main(String[] args) {
		Spark.get("/", new Route() {
			public Object handle(Request request, Response response) throws Exception {
				return "Hello World\n";
			}
		});

		Spark.get("/test", new Route() {
			public Object handle(Request request, Response response) throws Exception {
				return "This is a test page\n";
			}
		});

		Spark.get("/echo/:thing", new Route() {
			public Object handle(Request request, Response response) throws Exception {
				return "Path Parameter Value is " + request.params(":thing");
			}
		});
	}
}
