package ua.bp;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T>
{
    protected LinkedList<T> list;
    
    public TqsStack(){
        list=new LinkedList<>();
    }

    public T pop() throws NoSuchElementException{}

    public int size(){}
    
    public T peek() throws NoSuchElementException{}

    public void push(T item){}

    public boolean isEmpty(){}
}
