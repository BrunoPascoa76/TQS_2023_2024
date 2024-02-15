package ua.bp;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T>
{
    protected LinkedList<T> list;
    
    public TqsStack(){
        list=new LinkedList<>();
    }

    public T pop() throws NoSuchElementException{
        if(list.isEmpty()){
            throw new NoSuchElementException("Cannot pop empty list");
        }
        return list.pop();
    }

    public int size(){
        return list.size();
    }
    
    public T peek() throws NoSuchElementException{
        if(list.isEmpty()){
            throw new NoSuchElementException("Cannot peek empty list");
        }
        return list.peek();
    }

    public void push(T item){
        list.addFirst(item);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }
}
