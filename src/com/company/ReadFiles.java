package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReadFiles {

    ArrayList<String> verticesList = new ArrayList<>(10);

    // roads.txt
    String initialVertex;
    String secondVertex;
    int miles;

    // attractions.txt
    String attraction;
    String location;

    public void attractions() throws IOException {
        Hashtable<String, String> attractionsHashTable = new Hashtable<String, String>();

        File attractionsFile = new File("/Users/nickihashemi/IdeaProjects/assignment2/src/com/company/attractions.txt");
        BufferedReader attractionsReader = new BufferedReader(new FileReader(attractionsFile));

        String lineReader;
        while ((lineReader = attractionsReader.readLine()) != null) {
            String[] temp = lineReader.split(",");
            attraction = temp[0];
            location = temp[1];
            attractionsHashTable.put(attraction, location);
        }

        System.out.println("Mappings of attractions: " + attractionsHashTable.toString());
    }

    public void roads() throws IOException {

        GraphClass graph = new GraphClass(verticesList.size());
//        GraphClass.Edge edge = new GraphClass.Edge(initialVertex, secondVertex, miles);

        File roadsFile = new File("/Users/nickihashemi/IdeaProjects/assignment2/src/com/company/roads.txt");
        BufferedReader roadsReader = new BufferedReader(new FileReader(roadsFile));

        String lineReader = roadsReader.readLine();
        while (lineReader != null) {
            String[] temp = lineReader.split(",", 4);
            initialVertex = temp[0];
            secondVertex = temp[1];
            miles = Integer.parseInt(temp[2]);

            if (!verticesList.contains(initialVertex)) {
                verticesList.add(initialVertex);
            }
            if (!verticesList.contains(secondVertex)) {
                verticesList.add(secondVertex);
            }

            lineReader = roadsReader.readLine();

        }
    }

    public static void main(String[] args) throws IOException {
        ReadFiles readFiles = new ReadFiles();
        readFiles.attractions();
        readFiles.roads();


    }
}
