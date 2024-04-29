import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class DTNode implements Serializable {
    private ArrayList<Pair<String, Boolean[]>> set;
    private DTNode leftChild;
    private DTNode rightChild;
    private DTNode parent;
    private int split;
    private Double gain;

    public DTNode(ArrayList<Pair<String, Boolean[]>> tuples, DTNode parent) {
        this.set = tuples;
        this.parent = parent;
        this.leftChild = null;
        this.rightChild = null;
        this.split = -1;
        this.gain = -1.0;
    }
    public ArrayList<Pair<String, Boolean[]>> getSet() {
        return this.set;
    }
    public void addLeftChild(DTNode node) {
        this.leftChild = node;
    }
    public void addRightChild(DTNode node) {
        this.rightChild = node;
    }
    public DTNode getParent() {
        return this.parent;
    }
    public void setGain(Double gain) {
        this.gain = gain;
    }
    public void setParent(DTNode node) {
        this.parent = node;
    }
    public void setSplit(int i) {
        this.split = i;
    }
    public int getSplit() {
        return this.split;
    }
    public DTNode getLeftChild() {
        return this.leftChild;
    }
    public DTNode getRightChild() {
        return this.rightChild;
    }


    public void adaboostTrain(ArrayList<Integer> usedAttributes) {
        if(this.getSet().size() == 0) {
            return;
        } else if(isAllSame(this.getSet())) {
            return;
        } else if(usedAttributes.size() == this.getSet().get(0).getSecond().length) {
            return;
        }
        else {
            //do all splits and get the remainders
            HashMap<Integer, Double> remainders = getRemainders(this, usedAttributes);
            //pick lowest remainder        
            double lowestRemainder = 1.1;
            int attributeWithLowest = -1;
            for(int i : remainders.keySet()) {
                if (remainders.get(i) < lowestRemainder && !usedAttributes.contains(i)) {
                    lowestRemainder = remainders.get(i);
                    attributeWithLowest = i;
                }
            }
           
            Double gain = calculateEntropy(this) - lowestRemainder;
            this.setGain(gain);
            //split on lowest remainder
            Pair<DTNode, DTNode> nodes = this.attributeSplit(attributeWithLowest);
            
            ArrayList<Integer> usedAttributesCopy = new ArrayList<>();
            usedAttributesCopy.addAll(usedAttributes);
            usedAttributesCopy.add(attributeWithLowest);
            this.setSplit(attributeWithLowest);
            nodes.getFirst().train(usedAttributesCopy);
            nodes.getSecond().train(usedAttributesCopy);
            this.addLeftChild(nodes.getFirst());
            this.addRightChild(nodes.getSecond());
        }
    }
    public void train(ArrayList<Integer> usedAttributes) {
        if(this.getSet().size() == 0) {
            return;
        } else if(isAllSame(this.getSet())) {
            return;
        } else if(usedAttributes.size() == this.getSet().get(0).getSecond().length) {
            return;
        }
        else {
            //do all splits and get the remainders
            HashMap<Integer, Double> remainders = getRemainders(this, usedAttributes);
            //pick lowest remainder        
            double lowestRemainder = 1.1;
            int attributeWithLowest = -1;
            for(int i : remainders.keySet()) {
                if (remainders.get(i) < lowestRemainder && !usedAttributes.contains(i)) {
                    lowestRemainder = remainders.get(i);
                    attributeWithLowest = i;
                }
            }
            
            Double gain = calculateEntropy(this) - lowestRemainder;
            this.setGain(gain);
            //split on lowest remainder
            Pair<DTNode, DTNode> nodes = this.attributeSplit(attributeWithLowest);
            
            ArrayList<Integer> usedAttributesCopy = new ArrayList<>();
            usedAttributesCopy.addAll(usedAttributes);
            usedAttributesCopy.add(attributeWithLowest);
            this.setSplit(attributeWithLowest);
            
            nodes.getFirst().train(usedAttributesCopy);
            nodes.getSecond().train(usedAttributesCopy);
            this.addLeftChild(nodes.getFirst());
            this.addRightChild(nodes.getSecond());
            
            

        //recurse split on each child
        }
    }
    //set1 : number in set1/num in total * entropy() +  
    public static HashMap<Integer, Double> getRemainders(DTNode node, ArrayList<Integer> usedAttributes) {
        HashMap<Integer, Double> remainders = new HashMap<>();
            for(int attribute = 0; attribute < node.getSet().get(0).getSecond().length; attribute++) {
                DTNode split = new DTNode(node.getSet(), null);
                Pair<DTNode, DTNode> nodes = split.attributeSplit(attribute);
                double node1Entropy = calculateEntropy(nodes.getFirst());
                double node2Entropy = calculateEntropy(nodes.getSecond());
                double node1Size = nodes.getFirst().getSet().size();
                double node2Size = nodes.getSecond().getSet().size();
                double node1Fraction = (double) (node1Size / split.getSet().size());
                double node2Fraction = (double) (node2Size / split.getSet().size());
                double remainder =  node1Fraction* node1Entropy + node2Fraction * node2Entropy;
                remainders.put(attribute, remainder);
            }
        return remainders;
    }    

    public static boolean isAllSame(ArrayList<Pair<String, Boolean[]>> set) {
        String firstElement = set.get(0).getFirst();
        for(int i =1; i < set.size(); i++) {
            if(!set.get(i).getFirst().equals(firstElement)) {
                return false;
            }
        }
        return true;
    }
    //entropies needs to be updated for each set(node), calculate it based on correct and incorrect not true and false for the attribute
    public Pair<DTNode, DTNode> attributeSplit(int attributeIndex) {
        //if ran out of attributes or set is all same value or set is empty
        
        ArrayList<Pair<String, Boolean[]>> data = this.getSet();
        ArrayList<Pair<String, Boolean[]>> trueSet = new ArrayList<Pair<String, Boolean[]>>();
        ArrayList<Pair<String, Boolean[]>> falseSet = new ArrayList<Pair<String, Boolean[]>>();
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getSecond()[attributeIndex]) {
                trueSet.add(data.get(i));
            } else {
                falseSet.add(data.get(i));
            }
        }
        return new Pair<DTNode, DTNode> (new DTNode(trueSet, this), new DTNode(falseSet, this));
    }


    public static Double calculateAdaEntropy(DTNode node) {
        ArrayList<Pair<String, Boolean[]>> data = node.getSet();
        Double positives = 0.0;
        Double negatives = 0.0;
        //sum over the weiug
        return 0.0;
    }
    public static Double calculateEntropy(DTNode node) {
        ArrayList<Pair<String, Boolean[]>> data = node.getSet();
        int positives = 0;
        int negatives = 0;
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getFirst().equals("A")) {
                positives++;
            } else {
                negatives++;                    
            }
        } 
        if(negatives == node.getSet().size() || positives == node.getSet().size()) {
            return 0.0;
        }
        double probOfPositive = (double) positives/(positives+negatives);
        double probOfNegative = (double) negatives/(positives+negatives);
        //return (probOfPositive * (Math.log(1/probOfPositive) / Math.log(2))) + ( probOfNegative * (Math.log(1/probOfNegative) / Math.log(2)));    
        //System.out.println(probOfPositive * (Math.log(1/probOfPositive)/Math.log(2)) + probOfNegative * (Math.log(1/probOfNegative)/Math.log(2)));
        return (probOfPositive * (Math.log(1/probOfPositive)/Math.log(2)) + probOfNegative * (Math.log(1/probOfNegative)/Math.log(2)));
    }
}
