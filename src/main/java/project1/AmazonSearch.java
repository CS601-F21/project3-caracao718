package project1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


public class AmazonSearch {



    private CopyOnWriteArrayList<String> results;

    public AmazonSearch(String searchType, String input) {

        System.out.println("reached amazonsearch");

        String review_file_name = "Cell_Phones_and_Accessories_5.json";
        FileReader reviewReader = new FileReader(review_file_name);
        System.out.println("finished reading in review");
        DataStructures reviewStructures = reviewReader.readReviewFile();
        System.out.println("created the review data structure");

        String qa_file_name = "qa_Cell_Phones_and_Accessories.json";
        FileReader qaReader = new FileReader(qa_file_name);
        System.out.println("finish reading in qa");
        DataStructures qaStructures = qaReader.readQaFile();
        System.out.println("created the qa data structure");

        executeCommand(reviewStructures, qaStructures, searchType, input);

        System.out.println("finished reading in data");
    }

    public CopyOnWriteArrayList<String> getResults() {
        return results;
    }


    /**
     * A method that takes input commands from the command line, and execute the program accordingly.
     * @param reviewStructures
     */
    private void executeCommand(DataStructures reviewStructures, DataStructures qaStructures, String searchType, String input) {
        switch (searchType) {
            case "find" -> findAsin(input, reviewStructures, qaStructures);
            case "reviewsearch" -> reviewSearch(input, reviewStructures);
            default -> System.out.println("something wrong with input, please try again");
        }
    }

    /**
     * Given the ASIN number of a specific product, display a list of all reviews for that product and display a list of all questions and answers about that product.
     * @param asin
     */
    private void findAsin(String asin, DataStructures reviewStructure, DataStructures qaStructure) {
        int reviewCount = 0;
        int qaCount = 0;
        HashMap<String, ArrayList<Item>> reviewMap = reviewStructure.getAsinMap();
        ArrayList<Item> reviews = reviewMap.get(asin);
        printFindAsin(reviews, reviewCount, qaCount);

        HashMap<String, ArrayList<Item>> qaMap = qaStructure.getAsinMap();
        ArrayList<Item> qas = qaMap.get(asin);
        printFindAsin(qas, reviewCount, qaCount);

    }

    /**
     * A method that prints out the review text and QA text.
     * @param items
     * @param reviewCount
     */
    private List<String> printFindAsin(ArrayList<Item> items, int reviewCount, int qaCount) {
        results = new CopyOnWriteArrayList<>();
        if (items == null) {
            System.out.println("no results for this part");
        } else {
            for (Item item : items) {
                if (item instanceof Review review) {
                    reviewCount++;
                    results.add("review number: " + reviewCount);
                    results.add("Review: " + review.getReviewText() + '\n');
                } else if (item instanceof QuestionAndAnswer qa) {
                    qaCount++;
                    results.add("QA number: " + qaCount);
                    results.add("Question: " + qa.getQuestion());
                    results.add("Answer: " + qa.getAnswer() +'\n');

                }
            }
        }
        return results;
    }

    /**
     * Given a one-word term, display a list of all reviews that contain the exact term. Results are sorted by term frequency
     * @param term
     */
    private void reviewSearch(String term, DataStructures reviewStructures) {
        results = new CopyOnWriteArrayList<>();
        int count = 0;
        List<Integer> list = reviewStructures.getIndex().exactSearch(term);
        for (int id : list) {
            Review review = (Review)reviewStructures.getDictionary().get(id);
            count++;
            results.add(count + ". " +review.getReviewText() + '\n');
        }
    }






}
