import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods
 * on arrays of ints.
 *
 * @author   Dillon Thompson (djt0026@auburn.edu)
 * @author   Dean Hendrix (dh@auburn.edu)
 * @version  TODAY
 *
 */




public final class Selector {

    /**
     * Can't instantiate this class.
     *
     * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
     *
     */
   private Selector() {}

    /**
     * Selects the minimum value from the Collection c, as defined by
     * the supplied Comparator comp. This method throws an IllegalArgumentException
     * if either c or comp is null, and it throws a NoSuchElementException
     * if c is empty. The Collection c is not changed by this method.
     *
     * @param c    the Collection to be searched through
     * @param comp the Comparator that defines the ordering of the elements in c
     * @return minimum value in c
     * @throws IllegalArgumentException if either c or comp is null
     * @throws NoSuchElementException   if c is empty
     */
   public static <T> T min(Collection<T> c, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (c.size() == 1) {
         return c.iterator().next();
      }

      T min = c.iterator().next();
      for (T i : c) {
         if (comp.compare(i, min) < 0) {
            min = i;
         }
      }
      return min;
   }

    /**
     * Selects the maximum value from the Collection c, as defined by
     * the supplied Comparator comp. This method throws an IllegalArgumentException
     * if either c or comp is null, and it throws a NoSuchElementException
     * if c is empty. The Collection c is not changed by this method.
     *
     * @param c     the Collection to be searched through
     * @param comp  the Comparator that defines the ordering of the elements in c
     * @return      maximum value in c
     * @throws      IllegalArgumentException if either c or comp is null
     * @throws      NoSuchElementException if c is empty
     *
     */
   public static <T> T max(Collection<T> c, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException();
      }
      if (c.size() == 1) {
         return c.iterator().next();
      }
      T max = c.iterator().next();
      for (T i : c) {
         if (comp.compare(i, max) > 0) {
            max = i;
         }
      }
      return max;
   }
    /**
     * Returns a Collection containing all the values in the supplied
     * Collection c that are in the range [low..high]; that is, all the
     * values that are greater than or equal to low and less than or
     * equal to high, including duplicate values, as determined by the
     * supplied Comparator comp. The returned Collection contains only
     * values in the range [low..high], and no others. Note that low and
     * high do not have to be actual values in c. If there are no
     * qualifying values, including the case where c is empty, this method
     * throws a NoSuchElementException. This method throws an
     * IllegalArgumentException if either c or comp is null. The Collection c
     * is not changed by this method.
     *
     * @param c     the Collection to be searched through
     * @param low   the lower bound value of the range
     * @param high  the upper bound value of the range
     * @param comp  the Comparator that defines the ordering of the elements in c
     * @return      a Collection of elements in the range [low..high]
     * @throws      IllegalArgumentException as specified above
     * @throws      NoSuchElementException as specified above
     *
     */
     
   public static <T> Collection<T> range(Collection<T> c, T low, T high, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException();
      }

      ArrayList<T> arr = new ArrayList<>(c);
      ArrayList<T> range = new ArrayList<>(0);
      if (comp.compare(low, high) <= 0) {
         for (T i : arr) {
            if (comp.compare(i, low) >= 0 && comp.compare(i, high) <= 0) {
               range.add(i);
            }
         }
      }
      if (range.size() == 0) {
         throw new NoSuchElementException();
      }
      return range;
   }

        /**
     * Selects the kth minimum value from the Collection c, as defined by
     * the supplied Comparator comp. This method throws an IllegalArgumentException
     * if either c or comp is null, and it throws a NoSuchElementException
     * if either c is empty or if there is no kth minimum value. Note that there
     * is no kth minimum value if k < 1, k > c.size(), or if k is larger than
     * the number of distinct values in c. The Collection c is not changed by
     * this method.
     *
     * @param c     the Collection to be searched through
     * @param k     the k-selection value
     * @param comp  the Comparator that defines the ordering of the elements in c
     * @return      kth minimum value in c
     * @throws      IllegalArgumentException as specified above
     * @throws      NoSuchElementException as specified above
     *
     */

   public static <T> T kmin(Collection<T> c, int k, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (c.isEmpty() || k < 1 || k > c.size() || k > uniqueSum(c, comp)) {
         throw new NoSuchElementException();
      }
      if (c.size() == 1 && k == 1 && uniqueSum(c, comp) == 1) {
         return c.iterator().next();
      }

      int count = 1;
      int newCount = 1;

      ArrayList<T> arr = new ArrayList<>(c);
      arr.sort(comp);
      ArrayList<T> newArr = new ArrayList<>(1);
      int end = arr.size();
      for (T i : arr) {
         if ( count < arr.size() && comp.compare(i, arr.get(count)) != 0 ) {
            if (k == newCount) {
               return i;
            }
            else {
               newArr.add(i);
               newCount++;
            }
         }
         else if (count == arr.size()) {
            newArr.add(i);
         }
         count++;
      }
      if (k > newArr.size()) {
         throw new IllegalArgumentException("invalid k");
      }
      return newArr.get(k - 1);
   }

    /**
     * Selects the kth maximum value from the Collection c, as defined by
     * the supplied Comparator comp. This method throws an IllegalArgumentException
     * if either c or comp is null, and it throws a NoSuchElementException
     * if either c is empty or if there is no kth maximum value. Note that there
     * is no kth maximum value if k < 1, k > c.size(), or if k is larger than
     * the number of distinct values in c. The Collection c is not changed by
     * this method.
     *
     * @param c     the Collection to be searched through
     * @param k     the k-selection value
     * @param comp  the Comparator that defines the ordering of the elements in c
     * @return      kth minimum value in c
     * @throws      IllegalArgumentException as specified above
     * @throws      NoSuchElementException as specified above
     *
     */
   public static <T> T kmax(Collection<T> c, int k, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException();
      }
      if (c.isEmpty() || k < 1 || k > c.size() || k > uniqueSum(c, comp)) {
         throw new NoSuchElementException();
      }
      if (c.size() == 1 && k == 1 && uniqueSum(c, comp) == 1) {
         return c.iterator().next();
      }

      int count = 1;
      int newCount = 1;

      ArrayList<T> arr = new ArrayList<>(c);
      java.util.Collections.sort(arr, java.util.Collections.reverseOrder(comp));
      ArrayList<T> newArr = new ArrayList<>(0);
      for (T i : arr) {
         if (count < arr.size() && comp.compare(i, arr.get(count)) != 0) {
            if (k == newCount) {
               return i;
            }
            else {
               newArr.add(i);
               newCount++;
            }
         }
         else if (count == arr.size()) {
            newArr.add(i);
         }
         count++;
      }
      if (k > newArr.size()) {
         throw new IllegalArgumentException();
      }
      return newArr.get(k - 1);
   }

    /**
     * Returns the largest value in the Collection c that is less than
     * or equal to the given key, as defined by the supplied Comparator
     * comp. This method throws an IllegalArgumentException if either c
     * or comp is null, and throws a NoSuchElementException if c is empty
     * or if there is no qualifying value. Note that key does not have to
     * be an actual value in c.
     *
     * @param c     the Collection to be searched through
     * @param key   the reference value
     * @param comp  the Comparator that defines the ordering of the elements in c
     * @return      the next smaller value in c
     * @throws      IllegalArgumentException as specified above
     * @throws      NoSuchElementException as specified above
     *
     */

   public static <T> T floor(Collection<T> c, T key, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException("Collection is null");
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      Collection<T> range = range(c, (min(c, comp)), key, comp);
      if (range.isEmpty()) {
         throw new NoSuchElementException("invalid key");
      }
      T floor = max(range, comp);
      return floor;
   }
   
   /**
 * Returns the smallest value in the Collection c that is greater than
 * or equal to the given key, as defined by the supplied Comparator
 * comp. This method throws an IllegalArgumentException if either c
 * or comp is null, and throws a NoSuchElementException if c is empty
 * or if there is no qualifying value. Note that key does not have to 
 * be an actual value in c.
 *
 * @param c     the Collection to be searched through
 * @param key   the reference value
 * @param comp  the Comparator that defines the ordering of the elements in c
 * @return      the next greater value in c
 * @throws      IllegalArgumentException as specified above
 * @throws      NoSuchElementException as specified above
 *
 */
   
   public static <T> T ceiling(Collection<T> c, T key, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException("Collection is null");
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      Collection<T> range = range(c, key, max(c, comp), comp);
      T ceiling = min(range, comp);
      return ceiling;
   }
   
   /**
 * Returns the total amount of unique elements from the Collection c, as defined by
 * the supplied Comparator comp. This method throws an IllegalArgumentException
 * if either c or comp is null, and it throws a NoSuchElementException 
 * if c is empty. The Collection c is not changed by this method.
 *
 * @param c     the Collection to be searched through
 * @param comp  the Comparator that defines the ordering of the elements in c
 * @return      Total amount of distinct elements in c
 * @throws      IllegalArgumentException if either c or comp is null
 * @throws      NoSuchElementException if c is empty
 *
 */
   
   private static <T> int uniqueSum(Collection<T> c, Comparator<T> comp) {
      if (c == null || comp == null) {
         throw new IllegalArgumentException("Collection is null");
      }
      if (c.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      Collection<T> arr = range(c, min(c, comp), max(c, comp), comp);
      int count = 0;
      for (T i : c) {
         arr.remove(i);
         if (arr.contains(i)) {
            count++;
         }
      }
      return (c.size() - count);
   }
}
