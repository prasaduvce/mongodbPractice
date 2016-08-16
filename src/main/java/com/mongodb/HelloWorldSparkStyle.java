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
        Spark.get("/hello", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World from Spark";
            }
        });

    }
}
