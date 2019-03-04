import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PointTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void slopeToTest() {
      Point pts1 = new Point(9,9);
      Point pts2 = new Point(12,11);
      double expected = 0.66666666;
      double actual = pts1.slopeTo(pts2);
      assertEquals(expected, actual, 0.001);
   }
}
