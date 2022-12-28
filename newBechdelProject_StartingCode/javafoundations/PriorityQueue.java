package javafoundations;
import java.util.*;

/**
 * Works like a queue, but instead of FIF), always dequeues the item with the 
 * highest priority. Uses a LinkedMaxHeap to store and sort items.
 *
 * @author (Audrey Yip, Jasmine Le, Kim Hsueh)
 * @version (12 Dec 22)
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T>
{
    private LinkedMaxHeap<T> heap;
    /**
     * Constructor, for class PriorityQueue
     */
    public PriorityQueue()
    {
        heap = new LinkedMaxHeap<T>();
    }

    /** 
     * Returns a reference to the element at the front of the queue
     */
    public T first()
    {
        return heap.getMax();
    }

    /**
     * Adds the specified element to the rear of the queue. 
     */
    public void enqueue (T element){
        heap.add(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     */ 
    public T dequeue(){
        return heap.removeMax();
    }

    /** 
     * Returns true if the queue contains no elements and false
     */
    public boolean isEmpty(){
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements in the queue. 
     */
    public int size(){
        return heap.size();
    }

    /**
     * Returns a string representation of the queue.
     */
    public String toString(){
        //dequeue the heap
        String s = "<front of queue>\n";
        // create temporary queue
        LinkedMaxHeap<T> heap2 = new LinkedMaxHeap<T>();
        while (this.size() != 0){
            T element = this.dequeue();
            s += element + "\n";
            heap2.add(element);
        }
        // enqueue elements from temporary queue to original queue 
        while(heap2.size()!=0){
            T element = heap2.removeMax();
            this.enqueue(element);
        }
        return s + "<rear of queue>";
    }

    public static void main (String[] args){
        System.out.println("\n----Testing constructor and toString()----");
        PriorityQueue<Integer> test = new PriorityQueue<Integer>();
        System.out.println("Expected Output: empty queue");
        System.out.println("Actual Output: \n" + test);
        
        System.out.println("\n----Testing on empty queue----");
        System.out.println("isEmpty():");
        System.out.println("Expected Output: true, Actual Output: " + test.isEmpty());
        
        System.out.println("size():");
        System.out.println("Expected Output: 0, Actual Output: " + test.size());
        
        System.out.println("\n----Testing enqueue----");
        System.out.println("Enqueueing 3,4,5,1,2");
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(1);
        test.enqueue(2);
        System.out.println("Expecting 5,4,3,2,1 in order. Actual Output: \n" + test);
        
        System.out.println("\n----Testing first()----");
        System.out.println("Expecting 5. Actual Output: " + test.first());
        
        System.out.println("\n----Testing dequeue()----");
        System.out.println("Expected return: 5. Actual return: " + test.dequeue());
        
        System.out.println("Now, expecting queue with 4,3,2,1. Actual Output: \n" + test);
    }
}