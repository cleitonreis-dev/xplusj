package com.xplusj.interpreter.stack;

public interface Stack<T> {

    void push(T value);

    T pull();

    T peek();

    boolean isEmpty();

    static<T> Stack<T> defaultStack(){
        return new LinkedStack<>();
    }
}
