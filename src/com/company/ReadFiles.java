package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * ReadFiles Class:
 */
public class ReadFiles {

    ArrayList<String> verticesList = new ArrayList<>();
    LinkedList<Edge> edgeList = new LinkedList<Edge>();
    Hashtable<String, String> attractionsHashTable = new Hashtable<String, String>();
    Hashtable<String, List<String>> adjacencyList = new Hashtable<String, List<String>>();

    // roads.txt
    String initialVertex;
    String secondVertex;
    int miles;

    // attractions.txt
    String attraction;
    String location;

    /**
     * Edge Class
     */
    static class Edge {

        String initialVertex;
        String secondVertex;
        int miles;

        /**
         * Constructor to assign values
         * @param initialVertex
         * @param secondVertex
         * @param miles
         */
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

    /**
     * Adds edges between starting, ending vertices + weight
     * @param initialVertex
     * @param secondVertex
     * @param miles
     */
    public void addEdge(String initialVertex, String secondVertex, int miles) {
        Edge edge = new Edge(initialVertex, secondVertex, miles);
        edgeList.add(edge);

        Edge edge1 = new Edge(secondVertex, initialVertex, miles);
        edgeList.add(edge1);
    }

    /**
     * Gets the weight of two vertices
     * @param first
     * @param second
     * @return
     */
    public int weight(String first, String second) {
        for (Edge edge : edgeList) {
            if (edge.initialVertex.equals(first) && edge.secondVertex.equals(second)) {
                return edge.miles;
            }
            if (edge.initialVertex.equals(second) && edge.secondVertex.equals(first)) {
                return edge.miles;
            }
        }
        return 0;
    }

    /**
     * Reads the attractions.txt file and adds it to a Hashtable
     * @throws IOException
     */
    public void attractions() throws IOException {

        File attractionsFile = new File("/Users/nickihashemi/IdeaProjects/assignment2/src/com/company/attractions.txt");
        BufferedReader attractionsReader = new BufferedReader(new FileReader(attractionsFile));

        String lineReader;
        while ((lineReader = attractionsReader.readLine()) != null) {
            String[] temp = lineReader.split(",");
            attraction = temp[0];
            location = temp[1];
            attractionsHashTable.put(attraction, location);
        }

        System.out.println("Attractions: " + attractionsHashTable.toString());
    }

    /**
     * Reads the roads.txt file and adds it to a Hashtable
     * @throws IOException
     */
    public void roads() throws IOException {

        File roadsFile = new File("/Users/nickihashemi/IdeaProjects/assignment2/src/com/company/roads.txt");
        BufferedReader roadsReader = new BufferedReader(new FileReader(roadsFile));

        String lineReader = roadsReader.readLine();
        while (lineReader != null) {
            String[] temp = lineReader.split(",", 4);
            initialVertex = temp[0];
            secondVertex = temp[1];
            miles = Integer.parseInt(temp[2]);

            adjacencyList.putIfAbsent(initialVertex, new LinkedList<>());
            adjacencyList.putIfAbsent(secondVertex, new LinkedList<>());
            adjacencyList.get(initialVertex).add(secondVertex);
            adjacencyList.get(secondVertex).add(initialVertex);


            if (!verticesList.contains(initialVertex)) {
                verticesList.add(initialVertex);
            }
            if (!verticesList.contains(secondVertex)) {
                verticesList.add(secondVertex);
            }

            lineReader = roadsReader.readLine();

            addEdge(initialVertex, secondVertex, miles);
        }

        System.out.println("Vertices: " + verticesList);
        System.out.println("Edges: " + edgeList);

    }

    /**
     * Calls the above methods, add attraction to ArrayList and pass it in
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ReadFiles readFiles = new ReadFiles();
        readFiles.attractions();
        readFiles.roads();

        ArrayList attractions = new ArrayList();
        attractions.add("Disney World");
        attractions.add("Statue of Liberty");

        Dijkstra dijkstra = new Dijkstra(readFiles.verticesList, readFiles.edgeList, readFiles.adjacencyList, readFiles.attractionsHashTable);
        System.out.println();
        System.out.println("Path: " + dijkstra.route("Abilene TX", "Portland OR", attractions).toString());

    }
}
