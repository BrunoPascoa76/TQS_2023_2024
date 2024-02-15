package ua.bp;

public class TqsBoundedStack<T> extends TqsStack<T> {
    private int limit;

    public TqsBoundedStack(int limit){
        super();
        this.limit=limit;
    }

    @Override
    public void push(T item) throws IllegalStateException{}
}
