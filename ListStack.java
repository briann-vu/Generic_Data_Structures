/*
 * Brian Vu
 * CSc 210
 * 
 * Class represents a ListStack object which implements
 * the StackInterface interface. This data structure can store
 * any object of type T; what T is is determined by the user 
 * A ListStack is just a stack that is backed using a LinkedList. 
 * 
 * Fields: 
 * private int size which represents how many elements are
 * in the stack (LinkedList) 
 *  
 * private Node head which is a Node object representing the
 * start (first element/Node) of the stack (LinkedList)
 * 
 * Inner Classes:
 * private class Node<E> (description above the Node class below)
 * 
 * Methods (descriptions of each method is on top of the 
 * method below):
 * ListStack() (Constructor)
 * ListStack(ListStack<T> stack) (Copy Constructor)
 * push(T value)
 * pop()
 * peek()
 * isEmpty()
 * size()
 * clear()
 * toString()
 * equals(Object o)
 */

public class ListStack<T> implements StackInterface<T> {

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
     * to an object of type E and another node. Parameters:
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
     * Initializes a ListStack with a size of 0
     * and a head Node with null as its value and
     * its next pointer pointing to null.
     */
    public ListStack() {
        this.head = new Node<T>();
        this.size = 0;
    }

    /*
     * Copy Constructor
     * 
     * Takes in a ListStack<T> to copy its fields
     * and use them to create a new ListStack object
     */
    public ListStack(ListStack<T> stack) {
        this.head = stack.head;
        this.size = stack.size;
    }

    /*
     * Add an element to the top of the stack.
     * 
     * Takes in an object of type T representing the element
     * to be pushed.
     */
    @Override
    public void push(T value) {
        if (this.size == 0) {
            Node<T> newNode = new Node<T>(value, null);
            this.head = newNode;
        } else {
            Node<T> newNode = new Node<T>(value, this.head);
            this.head = newNode;
        }
        this.size++;
    }

    /*
     * Remove and return the top element in the stack.
     * 
     * If the user attempts to pop an empty stack, ignore the
     * request (i.e. make no changes to the stack) and return null.
     */
    @Override
    public T pop() {
        if (this.head == null) {
            return null;
        }
        T popped = this.head.data;
        Node<T> newNode = this.head.next;
        this.head = newNode;
        this.size--;
        return popped;
    }

    /*
     * Return (but do NOT remove) the top element of the stack.
     * 
     * If the user attempts to peek on an empty stack, ignore the
     * request (i.e. make no changes to the stack) and return null.
     */
    @Override
    public T peek() {
        return this.head.data;
    }

    /*
     * Returns true if the stack has no elements, else false.
     */
    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /*
     * Returns the number of elements in the stack.
     */
    @Override
    public int size() {
        return this.size;
    }

    /*
     * Removes all elements from the stack.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.head = null;
    }

    /*
     * Returns a string of all of the elements in the stack
     * in order in the form of {element, element... }. Method
     * loops through the LinkList in order to grab every element.
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
     * Compares the ListStack with an Object and checks
     * if the object is an instance of ListStack and if
     * the object has the exact same fields as the ListStack
     * being compared to; size, elements.
     * 
     * Takes in an Object o to compare
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ListStack) {
            ListStack<T> other = (ListStack<T>) obj;
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
