package project1;

import java.util.*;

/**
 * A class that implements inverted index
 * @author caracao718
 */

public class InvertedIndex {

    private Map<String, Map<Integer, Integer>> invertedIndex = new HashMap<>(); // word -> {docID -> frequency}

    /**
     * A method that add a new map to the invertedIndex
     * @param input
     * @param id
     */
    public synchronized void add(String input, int id) {
        if (invertedIndex.containsKey(input)) {
            // map = get the value associated with word
            var map = invertedIndex.get(input);
            if (map.containsKey(id)) {
                map.replace(id, map.get(id) + 1);
            } else {
                map.put(id, 1);
            }
        } else {
            Map<Integer, Integer> newMap = new HashMap<>();
            newMap.put(id, 1);
            invertedIndex.put(input, newMap);
        }
    }

    /**
     * Given a String term, return a list of docIDs that contains the exact term in the invertedIndex key.
     * @param term
     * @return
     */
    public synchronized List<Integer> exactSearch(String term) {
        term = term.toLowerCase();
        Map<Integer, Integer> unSortedMap = invertedIndex.get(term);
        if (unSortedMap == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> sorted = new ArrayList<>(); //list of docIds
        unSortedMap.entrySet()  //reference:https://howtodoinjava.com/java/sort/java-sort-map-by-values/
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //sort by value
                .forEachOrdered(x -> sorted.add(x.getKey()));
        return sorted;
    }


}
