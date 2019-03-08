import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
      if (elements == null) {
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
      int delete = new Random().nextInt(size - 1);
      T removed = elements[delete];
      if (size == 0) {
         return null;
      }
      if (size > 0 && size < elements.length / 4) {
         resize(elements.length / 2);
      }
      else {
        elements[delete] = elements[size - 1];
        elements[size] = null;
      }
      return removed;
   }

   public T sample() {
      int sample = new Random().nextInt(size);
      if (size == 0) {
         return null;
      }
      else {
         return elements[sample];
      }
   }

   public boolean isEmpty() {
       return (size == 0);
   }
   public int size() {
       return size;
   }

   public Iterator<T> iterator() {
       return new ArrayIterator(elements, size);
   }

   public class ArrayIterator<T> implements Iterator<T> {
      private T[] items;
      private int count;
      private int current;

      public ArrayIterator(T[] elements,int size) {
         for (int i = 0; i < size; i++) {
            items[i] = elements[i];
         }
         count = size;
         current = 0;
      }

      public boolean hasNext() {
         return (current < count);
      }

      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException("does not have next");
         }
            return items[current + 1];
      }
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}