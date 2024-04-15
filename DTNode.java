import java.util.ArrayList;

import javafx.util.Pair;

public class DTNode {
    private ArrayList<Pair<String, Boolean[]>> set;
    private DTNode leftChild;
    private DTNode rightChild;

    public DTNode(ArrayList<Pair<String, Boolean[]>> tuples) {
        this.set = tuples;
        this.leftChild = null;
        this.rightChild = null;
    }
    public ArrayList<Pair<String, Boolean[]>> getSet() {
        return this.set;
    }
    public void addLeftChild(ArrayList<Pair<String, Boolean[]>> set) {
        this.leftChild = new DTNode(set);
    }
    public void addRightChild(ArrayList<Pair<String, Boolean[]>> set) {
        this.rightChild = new DTNode(set);
    }
}
