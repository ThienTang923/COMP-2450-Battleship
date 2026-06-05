package comp2450.stack;

import com.google.common.base.Preconditions;

public class Node<T> {
    private final T data;
    private Node<T> next;

    public Node(T data) {
        Preconditions.checkNotNull(data, "data cannot be null");

        this.data = data;
        this.next = null;

        checkInvariant();
    }

    public T getData() {
        checkInvariant();

        return this.data;
    }

    public Node<T> getNext() {
        checkInvariant();

        return this.next;
    }

    public void setNext(Node<T> next) {
        checkInvariant();

        this.next = next;

        checkInvariant();
    }

    private void checkInvariant() {
        Preconditions.checkNotNull(this.data, "data cannot be null");
    }
}
