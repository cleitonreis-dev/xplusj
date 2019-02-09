package com.xplusj.stack;

import lombok.AllArgsConstructor;

public class LinkedStack<T> implements Stack<T>{

    private Node<T> head;

    public void push(T value){
        head = new Node<>(value, head);
    }

    public T pull(){
        if(head == null)
            throw new IllegalStateException("Empty stack");

        T value = head.value;
        head = head.next;
        return value;
    }

    public T peek(){
        if(head == null)
            throw new IllegalStateException("Empty stack");

        return head.value;
    }

    public boolean isEmpty(){
        return head == null;
    }

    @AllArgsConstructor
    private static class Node<T>{
        private final T value;
        private final Node<T> next;
    }
}
