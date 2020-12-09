// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph

package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;

import static java.lang.System.exit;

public class Dijkstra {

    ReadFiles readFiles = new ReadFiles();
    int V = readFiles.getAdjacencyList().size();

    Hashtable<String, Boolean> visited = new Hashtable<>();
    Hashtable<String, String> path = new Hashtable<>();         // initial city, previous city
    Hashtable<String, Integer> distance = new Hashtable<>();    // city, cost

    Hashtable<String, String> attractionsList = new Hashtable<String, String>();

    ArrayList<String> verticesList = new ArrayList<>();
    LinkedList<ReadFiles.Edge> edgeList = new LinkedList<ReadFiles.Edge>();
    Hashtable<String, List<String>> adjacencyList = new Hashtable<String, List<String>>();

    public Dijkstra(ArrayList<String> verticesList, LinkedList<ReadFiles.Edge> edgeList, Hashtable<String, List<String>> adjacencyList, Hashtable<String, String> attractionsList) {
        this.verticesList = verticesList;
        this.edgeList = edgeList;
        this.adjacencyList = adjacencyList;
        this.attractionsList = attractionsList;
    }

    public String minDistance() {
        int min = Integer.MAX_VALUE;
        String min_index = "";

        for (String location : verticesList) {
            if (!visited.get(location) && distance.get(location) <= min) {
                min = distance.get(location);
                min_index = location;
            }
        }

        return min_index;
    }

    public void known(String location) {
        visited.put(location, true);
    }


    List<String> route(String starting_city, String ending_city, List<String> attractions) {

        ArrayList<String> cities = new ArrayList<String>();

        for (String location : verticesList) {
            visited.put(location, false);
            distance.put(location, Integer.MAX_VALUE);
        }

        distance.put(starting_city, 0);

        for (String location : verticesList) {
            while (!visited.get(location)) {

                String city = minDistance();
                known(city);

                for (String vertex : adjacencyList.get(city)) {
                    if (distance.get(vertex) > distance.get(city) + readFiles.weight(city, vertex)) {
                        distance.put(vertex, distance.get(city) + readFiles.weight(city, vertex));
                        path.put(vertex, city);
                    }
                }
            }
        }

        ArrayList<Integer> attractionDistances = new ArrayList<Integer>();
        Hashtable<Integer, String> distanceToLocation = new Hashtable<Integer, String>();
        ArrayList<String> visitOrder = new ArrayList<String>();

        for (String a : attractions) {
            String location = attractionsList.get(a);
            int dist = distance.get(location);
            attractionDistances.add(dist);
            distanceToLocation.put(dist, location);
        }

        Collections.sort(attractionDistances);

        for (int dist : attractionDistances) {
            visitOrder.add(distanceToLocation.get(dist));
        }

        visitOrder.add(0, starting_city);
        visitOrder.add(ending_city);

        int milesTotal = 0;
        Stack stack = new Stack();

        for (int i=0; i<visitOrder.size()-1; i++) {
            String current = visitOrder.get(i);
            String nextLocation = visitOrder.get(i+1);
            String temp = visitOrder.get(i+1);
            stack.push(nextLocation);

            while (!current.equals(nextLocation)) {
                String prevLocation = path.get(nextLocation);
                milesTotal += readFiles.weight(nextLocation, prevLocation);
                stack.push(prevLocation);
                nextLocation = prevLocation;
            }

            while (!stack.isEmpty()) {
                cities.add((String) stack.pop());
            }

            visited.clear();
            path.clear();
            distance.clear();

            for (String location : verticesList) {
                visited.put(location, false);
                distance.put(location, Integer.MAX_VALUE);
            }

            distance.put(temp, 0);

            for (String location : verticesList) {
                while (!visited.get(location)) {

                    String city = minDistance();
                    known(city);

                    for (String vertex : adjacencyList.get(city)) {
                        if (distance.get(vertex) > distance.get(city) + readFiles.weight(city, vertex)) {
                            distance.put(vertex, distance.get(city) + readFiles.weight(city, vertex));
                            path.put(vertex, city);
                        }
                    }
                }
            }
        }

        // print the constructed distance array
//        printSolution(dist);
        return cities;
    }

    public static void main(String[] args) {



        //d.dijkstra(readFiles.getAdjacencyList(), 0);
    }
}