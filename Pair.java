import java.io.Serializable;
/*A type ambiguous class that allows for two objects to be stored together */
public class Pair<T, U> implements Serializable {
    private T first;
    private U second;
    
    /*Constructor for Pair
     * @param first: the first object
     * @param second: the second object
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    
    
    /*
     * @return: the first object in the pair
     */
    public T getFirst() {
        return first;
    }


    /*
     * @return: the second object in the pair
     */
    public U getSecond() {
        return second;
    }
}
