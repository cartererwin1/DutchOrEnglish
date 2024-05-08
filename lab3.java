import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class lab3 {
    public static void decisionTreeTrain(String exampleFileName, String outputFileName) {
        ArrayList<Pair<String, Boolean[]>> tuples = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(exampleFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] words = line.split("\\|");
                Boolean[] engBooleans = new Boolean[6];
        
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
                if(words[1].contains("het")) {
                    engBooleans[3] = false;
                } else {
                    engBooleans[3] = true;
                }
                if(words[1].contains("ion")) {
                    engBooleans[4] = true;
                } else {
                    engBooleans[4] = false;
                }
                if(words[1].contains("and")) {
                    engBooleans[5] = true;
                } else {
                    engBooleans[5] = false;
                }
                
                Pair<String, Boolean[]> tuple = new Pair<String, Boolean[]>(words[0], engBooleans);
                tuples.add(tuple);
            }
            //build tree 
            DecisionTree tree = new DecisionTree(tuples);
            tree.train();
            try {
                FileOutputStream fileOut = new FileOutputStream(outputFileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(tree);
                out.close();
                fileOut.close();
            } catch(IOException e) {
                System.err.println("couldnt save tree");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void predict(String treeFile, String inputFile) {
        DecisionTree tree = null;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(treeFile));
            tree = (DecisionTree) input.readObject();
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(tree != null) {
            tree.predict(inputFile);
        } else {
            System.out.println("not work");
        }
    }
    
    

    public static void main(String[] args) {
        if(args[0].equals("train")) {
          if(args.length == 4) {
            if(args[3].equals("dt")) {
              decisionTreeTrain(args[1], args[2]);
            } else {
              System.err.println("usage: train <examples> <hypothesisOut> <learning-type (dt)>");
            }
          } else {
            System.err.println("usage: train <examples> <hypothesisOut> <learning-type>");
          }
        } else if(args[0].equals("predict")) {
          if(args.length == 3) {
            predict(args[1], args[2]);
          } else {
            System.err.println("usage: predict <hypothesis> <file>");
          }
        } else {
            System.err.println("usage: use train or predict");
        }
    }
}