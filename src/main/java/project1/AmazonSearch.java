package project1;


import framework.ServerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


public class AmazonSearch {
    private final String review_file_name = "Cell_Phones_and_Accessories_5.json";
    private final String qa_file_name = "qa_Cell_Phones_and_Accessories.json";
    private final FileReader reviewReader;
    private final FileReader qaReader;
    private final DataStructures reviewStructures;
    private final DataStructures qaStructures;

    public AmazonSearch() {
        reviewReader = new FileReader(review_file_name);
        reviewStructures = reviewReader.readReviewFile();

        qaReader = new FileReader(qa_file_name);
        qaStructures = qaReader.readQaFile();
    }


    public CopyOnWriteArrayList<String> getResults(String searchType, String input) {
        CopyOnWriteArrayList<String> results = new CopyOnWriteArrayList<>();
        executeCommand(reviewStructures, qaStructures, searchType, input, results);
        return results;
    }

    /**
     * A method that takes input commands from the command line, and execute the program accordingly.
     * @param reviewStructures
     */
    private synchronized void executeCommand(DataStructures reviewStructures, DataStructures qaStructures, String searchType, String input, CopyOnWriteArrayList<String> results) {
        switch (searchType) {
            case "find" -> findAsin(input, reviewStructures, qaStructures, results);
            case "reviewsearch" -> reviewSearch(input, reviewStructures, results);
            default -> System.out.println("something wrong with input, please try again");
        }
    }



    /**
     * Given the ASIN number of a specific product, display a list of all reviews for that product and display a list of all questions and answers about that product.
     * @param asin
     */
    private synchronized void findAsin(String asin, DataStructures reviewStructure, DataStructures qaStructure, CopyOnWriteArrayList<String> results) {
        int reviewCount = 0;
        int qaCount = 0;
        HashMap<String, ArrayList<Item>> reviewMap = reviewStructure.getAsinMap();
        ArrayList<Item> reviews = reviewMap.get(asin);
        printFindAsin(reviews, reviewCount, qaCount, results);

        HashMap<String, ArrayList<Item>> qaMap = qaStructure.getAsinMap();
        ArrayList<Item> qas = qaMap.get(asin);
        printFindAsin(qas, reviewCount, qaCount, results);
    }

    /**
     * A method that prints out the review text and QA text.
     * @param items
     * @param reviewCount
     * @param qaCount
     */
    private synchronized void printFindAsin(ArrayList<Item> items, int reviewCount, int qaCount, CopyOnWriteArrayList<String> results) {
        if (items != null) {
            for (Item item : items) {
                if (item instanceof Review review) {
                    reviewCount++;

                    results.add("review number: " + reviewCount);
                    results.add("Review: " + review.getReviewText());
                } else if (item instanceof QuestionAndAnswer qa) {
                    qaCount++;
                    results.add("QA number: " + qaCount);
                    results.add("Question: " + qa.getQuestion());
                    results.add("Answer: " + qa.getAnswer());
                }
            }
        }
    }

    /**
     * Given a one-word term, display a list of all reviews that contain the exact term. Results are sorted by term frequency
     * @param term
     */
    private synchronized void reviewSearch(String term, DataStructures reviewStructures, CopyOnWriteArrayList<String> results) {
        int count = 0;
        List<Integer> list = reviewStructures.getIndex().exactSearch(term);
        if (!list.isEmpty()) {
            for (int id : list) {
                Review review = (Review)reviewStructures.getDictionary().get(id);
                count++;
                results.add(count + ". " +review.getReviewText());
            }
        } else {
            results.add("No result for this search, please try again");
        }

    }



}
