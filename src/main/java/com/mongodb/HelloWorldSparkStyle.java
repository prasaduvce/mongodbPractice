package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by renuka.prasad on 8/16/2016.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) throws Exception {
				System.out.println("request.ip() ===> "+request.ip());
				System.out.println("request.getClass().getName() ===> "+request.getClass().getName());
				return "Hello World from Spark";
            }
        });

    }
}
