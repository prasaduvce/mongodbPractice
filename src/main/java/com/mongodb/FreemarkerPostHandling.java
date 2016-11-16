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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renuka.prasad on 8/16/2016.
 */
public class FreemarkerPostHandling {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreemarkerPostHandling.class, "/");

        Spark.get("/fruits", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                StringWriter writer = new StringWriter();
                try {
                    Template fruitPickerTemplate = configuration.getTemplate("fruitPicket.ftl");
                    Map<String, Object> fruitsListMap = new HashMap<String, Object>();
                    fruitsListMap.put("fruits", Arrays.asList("Apple", "Banana", "Guava", "Watermelon"));
                    fruitPickerTemplate.process(fruitsListMap, writer);
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

        Spark.post("/favourite_fruit", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                final String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why don't u select a fruit?";
                }
                return "Your favourite fruit is "+fruit;
            }
        });
    }
}
