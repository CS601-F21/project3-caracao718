package project1;

/**
 * A class that stores useful information into data structures.
 * @author caracao718
 */

import java.util.ArrayList;
import java.util.HashMap;

public class DataStructures {
    private HashMap<Integer, Item> dictionary = new HashMap<>(); // stores docID as the key, Review object as the value
    private HashMap<String, ArrayList<Item>> asinMap = new HashMap<>(); // stores asin number as the key, a list of Review objects as the value
    private InvertedIndex index = new InvertedIndex(); // stores the inverted index of the review dataset


    /**
     * A method that adds a pair of docID and an item to a HashMap
     * @param docID
     * @param item
     */
    public void addDictionary(int docID, Item item) {
        dictionary.put(docID, item);
    }


    /**
     * A method that adds a pair of asin and Item to the asinMap
     * @param asin
     * @param item
     */
    public void addAsinMap(String asin, Item item) {
        if (asinMap.containsKey(asin)) {
            ArrayList<Item> list = asinMap.get(asin);
            list.add(item);
            asinMap.replace(asin, list);
        } else {
            ArrayList<Item> newList = new ArrayList<>();
            newList.add(item);
            asinMap.put(asin, newList);
        }
    }


    /**
     * A getter to retrieve the dictionary HashMap. Key is the docID, and value is an Item object
     * @return HashMap
     */
    public HashMap<Integer, Item> getDictionary() {
        return dictionary;
    }

    /**
     * A getter to retrieve the invertedIndex.
     * @return HashMap
     */
    public InvertedIndex getIndex() { return index; }

    /**
     * A getter to retrieve the asinMap.
     * @return HashMap
     */
    public HashMap<String, ArrayList<Item>> getAsinMap() { return asinMap; }


    /**
     * A method that split the string by white space, then remove all punctuations in each string, convert each token to lowercase. Then store each token into the index.
     * @param input
     * @param id
     */
    public void tokenizeString(String input, int id) {
        String[] output = input.split("\\s");
        for (int i = 0; i < output.length; i++) {
            output[i] = output[i].replaceAll("\\p{Punct}", "");
            output[i] = output[i].toLowerCase();
            index.add(output[i], id);
        }
    }

}
