import java.io.Serializable;

public class Trio<T, U, V> implements Serializable {
    private T first;
    private U second;
    private V third;
    
    public Trio(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    public T getFirst() {
        return first;
    }
    
    public U getSecond() {
        return second;
    }
    public V getThird() {
        return third;
    }
}