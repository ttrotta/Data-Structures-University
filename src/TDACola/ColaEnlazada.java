package TDACola;

import TDANodo.Nodo;
import Interfaces.Queue;
import Exceptions.EmptyQueueException;

public class ColaEnlazada<E> implements Queue<E> {
    protected Nodo<E> head, tail;
    protected int size;

    public ColaEnlazada() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size; 
    }

    public boolean isEmpty() {
        return size == 0; // ó head == null
    }

    public E front() throws EmptyQueueException {
        if(isEmpty())
            throw new EmptyQueueException("La cola está vacía.");
        return head.getElement();
    }

    public void enqueue(E element) {
        Nodo<E> nodo = new Nodo<E>(element);
        nodo.setNext(null);
        if(isEmpty())
            head = nodo;
        else
            tail.setNext(nodo);
        tail = nodo;
        size++;
    }

    public E dequeue() throws EmptyQueueException {
        if(isEmpty())
            throw new EmptyQueueException("La cola está vacía.");
        E tmp = head.getElement();
        head = head.getNext();
        size--;
        if(size == 0)
            tail = null;
        return tmp;
    }
}