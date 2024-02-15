package ua.bp;

public class TqsBoundedStack<T> extends TqsStack<T> {
    private int limit;

    public TqsBoundedStack(int limit){
        super();
        this.limit=limit;
    }

    @Override
    public void push(T item) throws IllegalStateException{
        if(list.size()>=limit){
            throw new IllegalStateException("size must not be greater than "+limit);
        }
        super.push(item);
    }
}
