package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;

import static java.lang.System.exit;

public class Dijkstra {

    ReadFiles readFiles = new ReadFiles();

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
            int theDist = distance.get(location);
            attractionDistances.add(theDist);
            distanceToLocation.put(theDist, location);
        }

        Collections.sort(attractionDistances);

        for (int theDist : attractionDistances) {
            visitOrder.add(distanceToLocation.get(theDist));
        }

        visitOrder.add(0, starting_city);
        visitOrder.add(ending_city);

        Stack stack = new Stack();

        int milesTotal = 0;
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

        System.out.println("Total Miles: " + milesTotal);
        return cities;
    }
}