// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph

package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Dijkstra {

    ReadFiles readFiles = new ReadFiles();
    int V = readFiles.getAdjacencyList().size();

    int minDistance(int dist[], Boolean visited[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (visited[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void printSolution(int dist[]) {
        System.out.println("Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }


    void dijkstra(int graph[][], int src) {
        int dist[] = new int[V];

        Boolean visited[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);

            visited[u] = true;

            for (int v = 0; v < V; v++)
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        // print the constructed distance array 
        printSolution(dist);
    }

    public static void main(String[] args) {
        Dijkstra d = new Dijkstra();

        ReadFiles readFiles = new ReadFiles();
        //d.dijkstra(readFiles.getAdjacencyList(), 0);
    }
}