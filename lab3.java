import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.util.Pair;

public class lab3 {
    public static void adaboostTrain(String exampleFileName, String outputFileName) {
        /**\
         * procedure ADABOOST(Ex,L,K)
         * Set example weights to 1/|Ex|
         *  H ← list()
         * for k = 1 to K do
            * hk ← L(Ex)
            * err ← 0
            * for exj ∈ Ex do
            *      if hk (exj.x) != exj.y then 
            *         err = err + exj.w
            * ∆W ← err/1−err
            * for exj ∈ Ex do
            *      if hk (exj.x)= exj.y then
            *          exj.w = exj.w ×∆W
            * Normalize_Weights(ex)
            * hk.w ← .5×ln(1−err/err)
            * H.add(hk)
        return H
         */
        System.out.println("training with ada");
    }
    public static void decisionTreeTrain(String exampleFileName, String outputFileName) {
        /*procedure DTL(ex,attributes,parent_examples)
            if Every example has the same classification then
                return Majority_Answer(ex) . Nothing left to separate
            if attributes.empty() then
                return Majority_Answer(ex) . Nothing else to ask.
            if ex.empty() then
                return Majority_Answer(parent_ex) . We do not have training data for this case.
            A ← argemaxa∈attributesImportance(a,ex) . Find the question that best splits the data.
            tree ← Node(question = A) . Get a new Node that asks about A
            for vk ∈ A do . Loop through the values for A
                exk ← {e | e ∈ ex ∧e.A = vk } . Gather all of the examples with this value
                tree.addChild(DTL(exk,attributes −A,ex)) . Recursively build the child nodes
            return tree 
        */
        String fileName = "examples.txt";
        ArrayList<Pair<String, Boolean[]>> tuples = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\|");
                Boolean[] engBooleans = new Boolean[3];

                if(words[1].contains("the")) {
                    engBooleans[0] = true;
                } else {
                    engBooleans[0] = false;
                }
                if(words[1].contains("uw")) {
                    engBooleans[1] = false;
                } else {
                    engBooleans[1] = true;
                }
                if(words[1].contains("aa")) {
                    engBooleans[2] = false;
                } else {
                    engBooleans[2] = true;
                }
                Pair<String, Boolean[]> tuple = new Pair<String, Boolean[]>(words[0], engBooleans);
                tuples.add(tuple);
            }
            //calculate entropy of each test
            for(Pair<String, Boolean[]> pair : tuples) {
                Boolean[] bools = pair.getValue();
                
            }
            Boolean[] bools = tuples.get(0).getValue();
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        if(args[0].equals("train")) {
            if(args.length == 4) {
                System.out.println("train invoked");
                if(args[3].equals("dt")) {
                    decisionTreeTrain(args[1], args[2]);
                } else if(args[3].equals("ada")) {
                    adaboostTrain(args[1], args[2]);
                } else {
                    System.err.println("usage: train <examples> <hypothesisOut> <learning-type (dt or ada)>");
                }
            } else {
                System.err.println("usage: train <examples> <hypothesisOut> <learning-type>");
            }



        } else if(args[0].equals("predict")) {
            if(args.length == 3) {
                System.out.println("predict invoked");
            } else {
                System.err.println("usage: predict <hypothesis> <file>");
            }
        } else {
            System.err.println("usage: use train or predict");
        }
    }
}