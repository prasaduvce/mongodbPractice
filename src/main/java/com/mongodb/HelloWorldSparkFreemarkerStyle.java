package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renuka.prasad on 8/16/2016.
 */
public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Spark.get("/hello", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Map<String, Object> helloMap = new HashMap<String, Object>();
                    helloMap.put("name", "Hello World By Free marker and Spark");
                    helloTemplate.process(helloMap, writer);
                    System.out.println(writer);
                } catch (IOException e) {
                    Spark.halt(500);
                    e.printStackTrace();
                } catch (TemplateException e) {
                    Spark.halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });

    }
}
