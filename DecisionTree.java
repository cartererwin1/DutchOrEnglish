import java.util.ArrayList;

import javafx.util.Pair;

public class DecisionTree {
    private DTNode root;

    public DecisionTree(ArrayList<Pair<String, Boolean[]>> tuples) {
        this.root = new DTNode(tuples);
    }
    public void attributeSplit(int attributeNum) {
        ArrayList<Pair<String, Boolean[]>> set = this.root.getSet();
        ArrayList<Pair<String, Boolean[]>> trueSet = new ArrayList<Pair<String, Boolean[]>>();
        ArrayList<Pair<String, Boolean[]>> falseSet = new ArrayList<Pair<String, Boolean[]>>();
        for(int i = 0; i < set.size(); i++) {
            if(set.get(i).getValue()[attributeNum]) { // if tuples ith attribute is true
                trueSet.add(set.get(i));
            } else {
                falseSet.add(set.get(i));
            }
        }
        this.root.addLeftChild(trueSet);
        this.root.addRightChild(falseSet);
    }
        
}
