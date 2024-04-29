import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class DecisionTree implements Serializable{
    private DTNode root;

    //data
    //get entropies for all splits of data based on each attribute
    //get remainders for all splits
    public DecisionTree(ArrayList<Pair<String, Boolean[]>> tuples) {
        this.root = new DTNode(tuples, null);
    }

    public void train() {
        ArrayList<Integer> blankUsedAttributes = new ArrayList<>();
        DTNode root = getRoot();
        root.train(blankUsedAttributes);
    }

    public void adaboostTrain() {
        ArrayList<Integer> blankUsedAttributes = new ArrayList<>();
        DTNode root = getRoot();
        root.adaboostTrain(blankUsedAttributes);
    }
    public String insert(Boolean[] bools) {
        DTNode node = this.getRoot();
        while(node.getSplit() != -1) {
            if(bools[node.getSplit()]) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        ArrayList<Pair<String, Boolean[]>> set = node.getSet();
        int dutchCounter = 0;
        int englishCounter = 0;
        for(int i = 0; i < set.size(); i++) {
            if(set.get(i).getFirst().equals("nl")) { // SWAPPED
                dutchCounter++;
            } else if (set.get(i).getFirst().equals("en")) {
                englishCounter++;
            }
        }
        if(englishCounter > dutchCounter) {
            return "en";
        } else {
            return "nl";
        }
    }

    public void predict(String fileName) {
        File inputFile = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String input;
            while ((input = br.readLine()) != null) {
                input.strip();
                Boolean[] engBooleans = new Boolean[6];
                if(input.contains("the")) {
                    engBooleans[0] = true;
                } else {
                    engBooleans[0] = false;
                }
                if(input.contains("uw")) {
                    engBooleans[1] = false;
                } else {
                    engBooleans[1] = true;
                }
                if(input.contains("aa")) {
                    engBooleans[2] = false;
                } else {
                    engBooleans[2] = true;
                }
                if(input.contains("het")) {
                    engBooleans[3] = false;
                } else {
                    engBooleans[3] = true;
                }
                if(input.contains("ion")) {
                    engBooleans[4] = true;
                } else {
                    engBooleans[4] = false;
                }
                if(input.contains("and")) {
                engBooleans[5] = true;
                } else {
                    engBooleans[5] = false;
                }
                System.out.println(this.insert(engBooleans));
            }
        } catch (IOException e) { }
    }
    public DTNode getRoot() {
        return this.root;
    }
}