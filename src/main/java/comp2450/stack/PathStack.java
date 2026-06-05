package comp2450.stack;

public interface PathStack<T> {
    void push(T data);
    T pop();
    T peek();
    int size();
    boolean isEmpty();
}
