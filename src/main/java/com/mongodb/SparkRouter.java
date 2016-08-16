package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by renuka.prasad on 8/16/2016.
 */
public class SparkRouter {
    public static void main(String[] args) {
        Spark.get("/echo/:test", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return request.params(":test");
            }
        });
    }
}
