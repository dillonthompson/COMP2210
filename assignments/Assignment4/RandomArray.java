import java.rmi.server.ObjID;

import sun.security.util.Length;

/**
 * RandomArray.java.
 * Implements the RandomizedList interface.
 *
 * @author Dillon Thompson (djt0026@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 */
public class RandomArray<T> implements RandomizedList<T> {
   private static final int DEFAULT_CAPACITY = 1;
   private T[] elements;
   private int size;

   public RandomArray() {
      this(DEFAULT_CAPACITY);
   }

   @SuppressWarnings("unchecked")
   public RandomArray(int capacity) {
      elements = (T[]) new Object[capacity];
      size = 0;
   }

   public void add(T element) {
      if (size == elements.length) {
         resize(elements.length * 2);
      }
      if (element == null) {
         throw new IllegalArgumentException("element cannot be null");
      }
      elements[size] = element;
      size++;
   }

   private void resize(int capacity) {
      T[] copy = (T[]) new Object[capacity];
      for (int i = 0; i < elements.length; i++) {
         copy[i] = elements[i];
      }
      elements = copy;
   }

   public T remove() {
      int remove = new Random().nextInt(elements.size);
      T removed = elements[remove];
      if (elements.size == 0) {
         return null;
      }
      if (size > 0 && size < elements.length / 4) {
         resize(elements.length / 2);
      }
      else {
         elements[remove] = null;
         return removed;
      }
   }

   public T sample() {
      int sample = new Random().nextInt(elements.size);
      if (elements.size == 0) {
         return null;
      }
      else {
         return elements[sample];
      }
   }

   public Iterator<T> iterator() {
      Iterate itr = new Iterate(elements);
      return itr;
   }

   private class Iterate<T> implements Iterator<T> {
      private T[] array;
      private int current;

      public Iterate(T[] a) {
         array = a;
         current = 0;
      }

      public boolean hasNext() {
         if (current < size) {
            return true;
         }
         else{
            return false;
         }
      }

      public T next() {
         if (!hasNext()) {
            return null;
         }
         else {
            return array[current + 1];
         }
      }
      public void remove() {
         throw new UnsupportedOperationException("this operation is not allowed");
      }
   }
}