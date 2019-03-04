import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void minTest() {
      int[] a = {2, 4, 6, 8};
      int expected = 2;
      int actual = Selector.min(a);
      assertEquals("The min is: ", expected, actual);
   }
   @Test public void maxTest() {
      int[] a = {2, 4, 6, 8};
      int expected = 8;
      int actual = Selector.max(a);
      assertEquals(expected, actual);
   }
   @Test public void rangeTest() {
      int[] a = {2, 4, 6, 8};
      int low = 2;
      int high = 8;
      int[] expected = {2, 4, 6, 8};
      int[] actual = Selector.range(a, low, high);
      assertEquals(expected, actual);
   }
}
