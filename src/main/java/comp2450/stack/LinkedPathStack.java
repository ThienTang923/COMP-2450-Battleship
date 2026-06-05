package comp2450.stack;

import com.google.common.base.Preconditions;

public class LinkedPathStack<T> implements PathStack<T> {

    private Node<T> top;
    private int size;

    public LinkedPathStack() {
        this.top = null;
        this.size = 0;

        checkInvariant();
    }

    @Override
    public void push(T data) {
        checkInvariant();
        Preconditions.checkNotNull(data, "data cannot be null");

        int oldSize = this.size;

        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.top);
        this.top = newNode;
        this.size++;

        checkInvariant();
        Preconditions.checkState(this.size == oldSize + 1,
                "size should increase by 1 after push");
        Preconditions.checkState(this.top.getData().equals(data),
                "top should contain pushed data");
    }

    @Override
    public T pop() {
        checkInvariant();
        Preconditions.checkState(!isEmpty(), "cannot pop from an empty stack");

        int oldSize = this.size;

        T data = this.top.getData();
        this.top = this.top.getNext();
        this.size--;

        checkInvariant();
        Preconditions.checkState(this.size == oldSize - 1,
                "size should decrease by 1 after pop");

        return data;
    }

    @Override
    public T peek() {
        checkInvariant();
        Preconditions.checkState(!isEmpty(), "cannot peek from an empty stack");

        T data = this.top.getData();

        checkInvariant();

        return data;
    }

    @Override
    public int size() {
        checkInvariant();

        return this.size;
    }

    @Override
    public boolean isEmpty() {
        checkInvariant();

        return this.size == 0;
    }

    private void checkInvariant() {
        Preconditions.checkState(this.size >= 0, "size cannot be negative");

        if (this.size == 0) {
            Preconditions.checkState(this.top == null,
                    "top must be null when stack is empty");
        } else {
            Preconditions.checkNotNull(this.top,
                    "top cannot be null when stack is not empty");
        }
    }
}

