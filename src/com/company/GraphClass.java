package com.company;

import java.util.LinkedList;

class GraphClass extends ReadFiles {
    public GraphClass(int size) {
        super();
    }

    static class Edge {

        String initialVertex;
        String secondVertex;
        int miles;

        public Edge(String initialVertex, String secondVertex, int miles) {
            this.initialVertex = initialVertex;
            this.secondVertex = secondVertex;
            this.miles = miles;
        }
    }

    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencyList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new LinkedList[vertices];

            for (int i=0; i<vertices; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
        }

        public void addEdge(String initialVertex, String secondVertex, int miles) {
            Edge edge = new Edge(initialVertex, secondVertex, miles);
            adjacencyList[vertices].add(edge);

        }

        public void printGraph(){
            for (int i = 0; i <vertices ; i++) {
                LinkedList<Edge> list = adjacencyList[i];
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println("vertex-" + i + " is connected to " +
                            list.get(j).secondVertex + " with weight " +  list.get(j).miles);
                }
            }
        }
    }

    /*
    void addEdge(int v1, int v2) {
        // directed. v1->v2             // array of edges on the column in Adjacency List. each has a LL. get the correct index to get the correct LL
        Edge newEdge = new Edge();      // create a new box. neighbor on left, next on right
        newEdge.neighbor = v2;          // node that will get linked to
        newEdge.next = edges[v1];
        edges[v1] = newEdge;

        // undirected. v1->v2, v2->v1
        Edge newEdge1 = new Edge();      // create a new box. neighbor on left, next on right
        newEdge1.neighbor = v1;          // node that will get linked to
        newEdge1.next = edges[v2];
        edges[v2] = newEdge1;
    }

     */

}
