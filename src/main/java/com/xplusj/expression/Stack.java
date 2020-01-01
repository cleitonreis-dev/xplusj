package com.xplusj.expression;

public class Stack<T>{

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

    public static<T> Stack<T> instance(){
        return new Stack<>();
    }

    private static class Node<T>{
        private final T value;
        private final Node<T> next;

        private Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
