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

   @SuppressWarnings("unchecked")
   private void resize(int capacity) {
      T[] copy = (T[]) new Object[capacity];
      for (int i = 0; i < elements.length; i++) {
         copy[i] = elements[i];
      }
      elements = copy;
   }
   public Iterator<T> iterator() {
    
    return new ArrayIterator(elements, size());
}

   public void add(T element) {
    if (element == null) {
        throw new IllegalArgumentException("element cannot be null");
     }
    if (isFull()) {
         resize(elements.length * 2);
      }
      elements[size()] = element;
      size++;
   }

   public T remove() {
      if (isEmpty()) {
         return null;
      }
      int delete = new Random().nextInt(size());
      T removed = elements[delete];
      elements[delete] = elements[size() - 1];
      elements[size() - 1] = null;
      size--;
      if (size() > 0 && size < elements.length / 4) {
          resize(elements.length / 2);
        }
    return removed;
   }

   public T sample() {
    if (isEmpty()) {
        return null;
     }
      int sample = new Random().nextInt(size());
      return elements[sample];
   }

   public boolean isFull() {
       return size() == elements.length;
   }

   public boolean isEmpty() {
       return size() == 0;
   }

   public int size() {
       return size;
   }

   public class ArrayIterator<T> implements Iterator<T> {
      private T[] items;
      private int count;
      private int current;

      public ArrayIterator(T[] elements, int size) {
         count = size;
         items = elements;
         current = 0;
      }

      public boolean hasNext() {
         return (current < count);
      }

      public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int next = new Random().nextInt(count);
        T temp = items[next];
        if (hasNext()) {
            items[next] = items[count];
            items[count] = temp;
            count--;
        }
        return temp;
      }
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}