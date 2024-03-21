/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(2);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class,
                () -> setB.add(11));
                
        // non-natural
        assertThrows(IllegalArgumentException.class,
                () -> setA.add(-99));

        // repeated
        assertThrows(IllegalArgumentException.class,
                () -> setA.add(99));
    }
  
    @Test
    public void testAddFromBadArray() {
        int[] elems1 = new int[]{10, 20,30};
        int[] elems2 = new int[]{-10,-20};
        int[] elems3 = new int[]{10,10};


        // array too big
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems1));

        //array has non-natural numbers
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));

        //array has repeated values
        assertThrows(IllegalArgumentException.class, ()-> setA.add(elems3));
    }

    @Test
    public void testDifference(){
        assertNotEquals(setB,BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 70}));
        BoundedSetOfNaturals setC2=new BoundedSetOfNaturals(2);
        setC2.add(50);
        setC2.add(60);
        assertEquals(setC, setC2);
    }

    @Test
    public void testIntersect(){
        setA.add(new int[]{70,80});
        assertTrue(setB.intersects(setC));
        assertTrue(setC.intersects(setB));
        
        assertFalse(setA.intersects(setB));
        assertFalse(setB.intersects(setA));
    }


}
