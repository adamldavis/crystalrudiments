package net.crystalrudiments.util;
/**
 *
 * Implentation of a Queue with head and tail.
 *
 * @author Adam Davis
 * @version 2.0, May 7, 2005
 */
public class LinkedQueue implements Queue {

    private RealNode head;

    private RealNode tail;

    //Purpose: Constructor
    //PostCon: head and tail set to null
    public LinkedQueue() {
        this(null, null);
    }

    //Purpose: Constructor
    public LinkedQueue(RealNode h, RealNode t) {
        setHead(h);
        setTail(t);
    }

    //Purpose: accessors
    RealNode getHead() {
        return head;
    }

    RealNode getTail() {
        return tail;
    }

    //Purpose: modifiers
    void setHead(RealNode h) {
        head = h;
    }

    void setTail(RealNode t) {
        tail = t;
    }

    //Pre: nothin
    //Purpose: returns true if queue is empty
    //Postcon: no change to q
    public boolean isEmpty() {
        return (getHead() == null);
    }
    
    public boolean empty()
    {
    	return isEmpty();
    }

    //Pre: none
    //Purpose: add a new Object to the tail of queue
    //Post: Object added to queue
    public void enqueue(Object newData) {
        RealNode temp = new RealNode();
        temp.setData(newData);
        if (isEmpty()) {
            setHead(temp);
            setTail(temp);
        } else {
            getTail().setNext(temp);
            setTail(temp);
        }
    }

    //Pre: instantiation
    //Purpose: remove object from head o' Queue
    //Post: Returns removed element or zero if none
    public Object dequeue() {
        Object retVal;
        if (isEmpty()) {
            retVal = null;
        } else {
            retVal = getHead().getData();
            setHead(getHead().getNext());
            if (getHead() == null) setTail(null);
        }
        return retVal;
    }
}//class
