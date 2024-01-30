import java.util.*;

public class Main {

    public static Map<String, Integer> dijkstra(Map<String, Map<String,Integer>> graph, String start){
        //Intialize distances with infinity for all nodes except start node
        Map<String, Integer> distances = new HashMap();

        for(String node: graph.keySet()){
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        // Use a Priority Queue (min heap) to keep track of nodes with their tentative distances
        PriorityQueue<NodeDistancePair> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.distance));
        priorityQueue.add(new NodeDistancePair(start, 0));

        while(!priorityQueue.isEmpty()){
            NodeDistancePair current = priorityQueue.poll();
            String currentNode = current.node;
            int currentDistance = current.distance;

            //If the current distance is grater than the stored distance skip
            if(currentDistance > distances.get(currentNode)){
                continue;
            }

            //Explore neighbours and update distances
            for(Map.Entry<String, Integer> neighbourEntry: graph.get(currentNode).entrySet()){
                String neighbor = neighbourEntry.getKey();
                int weight = neighbourEntry.getValue();
                int distance = currentDistance + weight;

                //If shorter path found, update the distance and add to priority queue
                if(distance < distances.get(neighbor)){
                    distances.put(neighbor,distance);
                    priorityQueue.add(new NodeDistancePair(neighbor, distance));
                }
            }
        }


        return distances;

    }




    public static void main(String args[]){
        Map<String, Map<String, Integer>> graph = new HashMap<>();
        graph.put("A", Map.of("B", 1, "C", 4));
        graph.put("B", Map.of("A", 1, "C", 2, "D", 5));
        graph.put("C", Map.of("A", 4, "B", 2, "D", 1));
        graph.put("D", Map.of("B",5,"C",1));

        String startNode = "A";
        Map<String, Integer> shortestDistances = dijkstra(graph, startNode);

        System.out.println("Shortest Distance from Node " + startNode + ": " + shortestDistances);
    }

    static class NodeDistancePair{
        String node;
        int distance;

        NodeDistancePair(String node, int distance){
            this.node = node;
            this.distance = distance;
        }
    }


}
