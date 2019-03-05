import java.rmi.server.ObjID;

import sun.security.util.Length;

/**
 * ListFactory.java.
 * Implements the factory method pattern (https://en.wikipedia.org/wiki/Factory_method_pattern)
 * for lists in this assignment.
 *
 * @author Dillon Thompson (djt0026@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 */
public class RandomArray<T> implements RandomizedList<T> {
   private static final int DEFAULT_CAPACITY = 5;
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

   public T remove(T element) {
      if (elements.length == 0) {
         return null;
      }
      if (size > 0 && size < elements.length / 4) {
         resize(elements.length / 2);
      }
      else {
         elements[element] = elements[size - 1];
         elements[size - 1] = null;
      }
   }
}
public class DoublyLinkedList<T> implements DoubleEndedList<T> {

}

public class ListFactory {

   /**
    * Return an instance of a class that implements RandomizedList.
    */
   public static <T> RandomizedList<T> makeRandomizedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // RandomizedList interface.
      return null;
   }

   /**
    * Return an instance of a class that implements DoubleEndedList.
    */
   public static <T> DoubleEndedList<T> makeDoubleEndedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // DoubleEndedList interface.
      return null;
   }

}
