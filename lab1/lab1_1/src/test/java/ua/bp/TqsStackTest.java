package ua.bp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class TqsStackTest{
    private TqsStack<Integer> stack;

    @BeforeEach
    void initialize(){
        stack=new TqsStack<>();
    }
    
    @Test
    void checkEmpty(){
        assertTrue(stack.isEmpty());
    }

    @Test
    void checkInitialSize(){
        assertEquals(0,stack.size());
    }

    @Test
    void checkSizeChanges(){
        for(int i=1;i<=10;i++){
            stack.push(i);
        }
        assertFalse(stack.isEmpty());
        assertEquals(10, stack.size());
    }

    @Test
    void checkPopValue(){
        for(int i=1;i<=10;i++){
            stack.push(i);
        }
        assertEquals(10, stack.pop());
    }

    @Test
    void checkPeekSameSize(){
        for(int i=1;i<=10;i++){
            stack.push(i);
        }
        int oldSize=stack.size();
        assertEquals(10, stack.peek());
        assertEquals(oldSize, stack.size());
    }

    @Test
    void checkPopChangeSize(){
        for(int i=1;i<=10;i++){
            stack.push(i);
        }
        for(int i=1;i<=10;i++){
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void checkPopEmptyException(){
        assertThrows(NoSuchElementException.class,()->{stack.pop();});
    }

    @Test
    void checkPeekEmptyException(){
        assertThrows(NoSuchElementException.class, ()->{stack.peek();});
    }

    @Test
    void checkBounded(){
        TqsBoundedStack<Integer> boundedStack=new TqsBoundedStack<>(10);
        for(int i=1;i<=10;i++){
            boundedStack.push(i);
        }

        assertThrows(IllegalStateException.class,()->{boundedStack.push(11);});
    }
}
