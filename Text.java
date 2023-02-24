import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        
        // Read in exclude words
        Set<String> excludeWords = new HashSet<>();
        try (Scanner scanner = new Scanner(new File("exclude-words.txt"))) {
            while (scanner.hasNext()) {
                excludeWords.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            System.err.println("exclude-words.txt not found.");
            System.exit(1);
        }
        
        // Read in pages
        Map<String, Set<Integer>> index = new TreeMap<>();
        for (int i = 1; i <= 3; i++) {
            try (Scanner scanner = new Scanner(new File("Page" + i + ".txt"))) {
                int page = i;
                while (scanner.hasNext()) {
                    String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Z}]", "");
                    if (!excludeWords.contains(word)) {
                        index.putIfAbsent(word, new HashSet<>());
                        index.get(word).add(page);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Page" + i + ".txt not found.");
                System.exit(1);
            }
        }
        
        // Write out index
        try (PrintWriter writer = new PrintWriter(new File("index.txt"))) {
            for (Map.Entry<String, Set<Integer>> entry : index.entrySet()) {
                writer.print(entry.getKey() + " : ");
                boolean first = true;
                for (Integer page : entry.getValue()) {
                    if (!first) {
                        writer.print(",");
                    } else {
                        first = false;
                    }
                    writer.print(page);
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not write index.txt.");
            System.exit(1);
        }
        
        System.out.println("Index written to index.txt.");
    }
    
}