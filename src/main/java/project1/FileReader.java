package project1;

/**
 * A class that read in files from given datasets, and store useful information for the project.
 * @author caracao718
 */

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    private final String fileName;

    /**
     * Constructor that create a FileReader, and input the fileName
     * @param fileName
     */
    public FileReader(String fileName) {
        this.fileName = fileName;
    }


    /**
     * A method that reads in the review file, and stores the JSON values in asinReviewMap, reviewDictionary, and the reviewIndex.
     * @return InvertedIndex
     */
    public synchronized DataStructures readReviewFile() {
        DataStructures reviewStructure = new DataStructures();
        int docId = 0;
        String line;
        try (BufferedReader reviewReader = new BufferedReader(new java.io.FileReader(fileName))) {
            Gson gson = new Gson();
            while ((line = reviewReader.readLine()) != null) {
                docId++;
                try {
                    Review review  = gson.fromJson(line, Review.class);
                    reviewStructure.addAsinMap(review.getAsin(), review);
                    reviewStructure.tokenizeString(line, docId);
                    reviewStructure.addDictionary(docId,review);
                } catch(com.google.gson.JsonSyntaxException ignored) {}
            }
        } catch (IOException e) {
            System.out.println("Error reading file, please try again");
            System.exit(0);
        }
        return reviewStructure;
    }

    /**
     * A method that reads in the QA file, and stores the JSON values in asinQaMap, qaDictionary, and the qaIndex.
     * @return InvertedIndex
     */
    public synchronized DataStructures readQaFile() {
        DataStructures qaStructure = new DataStructures();
        int docId = 0;
        String line;
        try (BufferedReader qaReader = new BufferedReader(new java.io.FileReader(fileName))) {
            Gson gson = new Gson();
            while ((line = qaReader.readLine()) != null) {
                docId++;
                try {
                    QuestionAndAnswer qa = gson.fromJson(line, QuestionAndAnswer.class);
                    qaStructure.addAsinMap(qa.getAsin(), qa);
                    qaStructure.tokenizeString(line, docId);
                    qaStructure.addDictionary(docId, qa);
                } catch (com.google.gson.JsonSyntaxException ignored) {}
            }
        } catch (IOException e) {
            System.out.println("Error reading file, please try again");
            System.exit(0);
        }
        return qaStructure;
    }


}