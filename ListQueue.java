/*
 * Brian Vu
 * CSc 210
 * 
 * Class represents a generic ListQueue object which implements
 * the QueueInterface interface. This data structure can store
 * any object of type T; what T is is determined by the user.
 * A ListQueue is just a queue that is backed using a LinkedList. 
 * 
 * Fields: 
 * private int size which represents how many elements are
 * in the queue (LinkedList) 
 *  
 * private Node head which is a Node object representing the
 * start (first element/Node) of the queue (LinkedList)
 * 
 * Inner Classes:
 * private class Node<E> (description above the Node class below)
 * 
 * Methods (descriptions of each method is on top of the 
 * method below):
 * ListQueue() (Constructor)
 * ListQueue(ListQueue<T> queue) (Copy Constructor)
 * enqueue(T value)
 * dequeue()
 * peek()
 * isEmpty()
 * size()
 * clear()
 * toString()
 * equals(Object obj)
 */

public class ListQueue<T> implements QueueInterface<T> {

    /*
     * Class represents generic Node objects for a linked list.
     * Its fields include data which is the data that a
     * Node holds and next, which is the reference to the
     * next Node object or null.
     * 
     * The constructor initializes a Node to have its data
     * value as null and its next pointer pointing to null.
     * 
     * There is another variant of the constructor that
     * allows users to initialize the data and pointer
     * to An object of type E and another node. Parameters:
     * (E data, Node next).
     */
    private class Node<E> {
        private E data;
        private Node<E> next;

        public Node() {
            this(null, null);
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    private int size = 0;

    /*
     * Constructor
     * 
     * Initializes a ListQueue with a size of 0
     * and a head Node with an object of type T
     * as its value and its next pointer pointing
     * to null.
     */
    public ListQueue() {
        this.head = new Node<T>();
        this.size = 0;
    }

    /*
     * Copy Constructor
     * 
     * Takes in a ListQueue<T> to copy its fields
     * and use them to create a new ListQueue object
     */
    public ListQueue(ListQueue<T> queue) {
        this.head = queue.head;
        this.size = queue.size;
    }

    /*
     * Add an element (Object of type T) to the back of
     * the queue.
     */
    @Override
    public void enqueue(T value) {
        Node<T> cur = this.head;
        Node<T> newNode = new Node<T>(value, null);
        this.size++;
        if (this.size <= 1) {
            this.head = newNode;
        } else {
            while (cur != null) {
                if (cur.next == null) {
                    cur.next = newNode;
                    cur = cur.next;
                }
                cur = cur.next;
            }
        }
    }

    /*
     * Remove and return the front element in the queue.
     * 
     * If the user attempts to dequeue from an empty queue, ignore the
     * request (i.e. make no changes to your queue) and return null.
     */
    @Override
    public T dequeue() {
        if (this.head == null) {
            return null;
        }
        T removed = this.head.data;
        Node<T> newNode = this.head.next;
        this.head = newNode;
        this.size--;
        return removed;
    }

    /*
     * Returns (but do NOT remove) the front element of the queue.
     * 
     * If the user tries to peek on an empty queue, ignore the
     * request (i.e. make no changes to your queue) and return null.
     */
    @Override
    public T peek() {
        return this.head.data;
    }

    /*
     * Returns true if the queue has no elements, else false.
     */
    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /*
     * Returns the number of elements in the queue.
     */
    @Override
    public int size() {
        return this.size;
    }

    /*
     * Removes all elements from the queue.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.head = null;
    }

    /*
     * Returns a string of all of the elements in the queue
     * in order in the form of {element, element... }. Method
     * loops through the LinkedList in order to grab every element.
     */
    @Override
    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        String retVal = "{";
        Node<T> cur = this.head;
        while (cur != null) {
            if (cur.next == null) {
                retVal = retVal.concat(cur.data + "}");
            } else {
                retVal = retVal.concat(cur.data + ",");
            }
            cur = cur.next;
        }
        return retVal;
    }

    /*
     * Compares the ListQueue with an Object and checks
     * if the object is an instance of ListQueue and if
     * the object has the exact same fields as the ListQueue
     * being compared to; size, elements.
     * 
     * Takes in an Object o to compare
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListQueue) {
            ListQueue<T> other = (ListQueue<T>) obj;
            Node<T> stackCur = this.head;
            Node<T> otherCur = other.head;
            if (other.size == this.size) {
                while (stackCur != null) {
                    if (stackCur.data.equals(otherCur.data)) {
                        stackCur = stackCur.next;
                        otherCur = otherCur.next;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
