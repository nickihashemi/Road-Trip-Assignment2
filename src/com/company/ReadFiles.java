package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class ReadFiles {

    ArrayList<String> verticesList = new ArrayList<>(10);
    LinkedList<Edge> adjacencyList = new LinkedList<Edge>();


    // roads.txt
    String initialVertex;
    String secondVertex;
    int miles;

    // attractions.txt
    String attraction;
    String location;


    static class Edge {

        String initialVertex;
        String secondVertex;
        int miles;

        public Edge(String initialVertex, String secondVertex, int miles) {
            this.initialVertex = initialVertex;
            this.secondVertex = secondVertex;
            this.miles = miles;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "initialVertex='" + initialVertex + '\'' +
                    ", secondVertex='" + secondVertex + '\'' +
                    ", miles=" + miles +
                    '}';
        }
    }

    public void addEdge(String initialVertex, String secondVertex, int miles) {
        Edge edge = new Edge(initialVertex, secondVertex, miles);
        adjacencyList.add(edge);

//        Edge edge1 = new Edge(secondVertex, initialVertex, miles);
//        adjacencyList.add(edge1);

    }

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

            addEdge(initialVertex, secondVertex, miles);
        }
        System.out.println(adjacencyList);

    }

    public LinkedList<Edge> getAdjacencyList() {
        return adjacencyList;
    }

    public static void main(String[] args) throws IOException {
        ReadFiles readFiles = new ReadFiles();
        readFiles.attractions();
        readFiles.roads();


        Dijkstra dijkstra = new Dijkstra();
        //dijkstra.

        // create a new list by calling the dijkstras
        // call GraphClass(adjacencylist)
        // List cities = new GraphClass(adjacencyList).apply(initial, second);


    }
}
