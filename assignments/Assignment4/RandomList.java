import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * RandomList.java.
 * Implements the RandomizedList interface.
 *
 * @author Dillon Thompson (djt0026@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 */
public class RandomList<T> implements DoubleEndedList<T> {
   private Node front;
   private Node rear;
   private int size;

   public RandomList() {
    front = null;
    rear = null;
    size = 0;
}

   private class Node {
    private T element;
    private Node next;
    private Node prev;

    public Node(T e) {
        element = e;
    }

    public Node(T e, Node n, Node p) {
        element = e;
        next = n;
        prev = p;
    }
}

   public int size() {
       return size;
   }

   public boolean isEmpty() {
       return size == 0;
   }

   public void addFirst(T element) {
       if (element == null) {
           throw new IllegalArgumentException();
       }
       Node n = new Node(element);
       if (isEmpty()) {
           front = n;
           rear = n;
       }
       else {
        n.next = front;
        front.prev = n;
        front = n;
       }
       size++;
   }

   public void addLast(T element) {
       if (element == null) {
           throw new IllegalArgumentException();
       }
       Node n = new Node(element);
       if (isEmpty()) {
           front = n;
           rear = n;
       }
       else {
           n.next = null;
           rear.next = n;
           n.prev = rear;
           rear = n;
       }
       size++;
   }

   public T removeFirst() {
       if (isEmpty()) {
           return null;
       }
       T removed = front.element;
       if (size() == 1) {
           front = null;
           rear = null;
           size--;
           return removed;
       }
       else {
        front = front.next;
        front.prev = null;
        size--;
        return removed;
       }
   }

   public T removeLast() {
       if (isEmpty()) {
           return null;
       }
       T removed = rear.element;
       if (size() == 1) {
           rear = null;
           front = null;
           size--;
           return removed;
       }
       else {
        rear = rear.prev;
        rear.next = null;
        size--;
        return removed;
       }
   }
 
   public Iterator<T> iterator() {
       return new ListIterator();
   }

   public class ListIterator implements Iterator<T> {
      private Node current = front;

      public boolean hasNext() {
          return current != null;
      }

      public T next() {
          if (!hasNext()) {
              throw new NoSuchElementException();
          }
          T result = current.element;
          current = current.next;
          return result;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}