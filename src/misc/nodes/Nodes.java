package misc.nodes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tlanders on 6/4/2016.
 */
public class Nodes {
    public static void main(String [] args) throws Exception {
        System.out.println("Nodes starting...");

        Files.lines(Paths.get(args[0])).map(str -> {
//            System.out.println("line: " + str);
            String [] lineSplit = str.split(" ");
            List<Map<Integer, String>> nodes = new ArrayList<Map<Integer, String>>();
            if(lineSplit.length > 1) {
                Map<Integer, String> n1 = new HashMap<>();
                n1.put(Integer.parseInt(lineSplit[0]), lineSplit[1]);
                Map<Integer, String> n2 = new HashMap<>();
                n2.put(Integer.parseInt(lineSplit[1]), lineSplit[0]);
                nodes.add(n1);
                nodes.add(n2);
            }
            return nodes;
        }).distinct().flatMap(List::stream)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting())).entrySet().stream()
                .forEach(entry -> System.out.println("Node " + entry.getKey() + " has a degree of " + entry.getValue()));

        //System.out.println("res=" + res + ", class=" + res.getClass().getName());
        /*
        Files.lines(Paths.get(args[0])).forEach(line -> {
            String [] lineSplit = line.split(" ");
            System.out.println(line + " - " + lineSplit.length);
            if(lineSplit.length == 1) {
                // initialize nodes
                int totalNodes = Integer.parseInt(lineSplit[0]);
                nodes = (Set<String> []) new Object[totalNodes];
                for(int i = 0; i < totalNodes; i++) {
                    nodes[i] = new HashSet<String>();
                }
            }
        });
*/
        System.out.println("Nodes done.");
    }
}
