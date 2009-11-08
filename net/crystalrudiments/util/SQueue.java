/**
 * AQueue
 *
 * <PRE>
 * AQueue Class
 *
 * Revisions:  1.0  May 12, 2001
 *                  Created AQueue.java.
 *
 * </PRE>
 */

package net.crystalrudiments.util;

import java.util.*;

/**
 * 
 * This is a queue that is synchronized.
 * 
 * @author <A HREF="mailto:gte459u@prism.gatech.edu">Adam Davis </A>
 * @version Version 1.0, May 12, 2001
 */
public class SQueue extends java.util.LinkedList implements Queue {


	/**
	 * Creates a blank queue.
	 */
	public SQueue() {
		super();
	}

	/**
	 * Fills the queue with all items from the collection.
	 * 
	 * @param list
	 *            Items to add.
	 */
	public SQueue(Collection list) {
		super(list);
	}

	/**
	 * Adds the object to the end of the queue.
	 * 
	 * @param o
	 *            Object to add.
	 */
	public synchronized void enqueue(Object o) {
		super.add(o);
	}

	/**
	 * Removes and returns the head of the queue. First in, first out.
	 * 
	 * @return The least recently added object which has not yet been dequeued.
	 */
	public synchronized Object dequeue() {
		Object ret = null;
		try {
			ret = super.remove(0);
		} catch (NoSuchElementException nsee) {
			ret = null;
		} catch (Exception e) {
			System.err.println("Error in dequeue()" + e);
		}

		return ret;
	}

	/**
	 * Tells whether or not the queue is empty.
	 * 
	 * @return True only if the queue's size is zero.
	 */
	public synchronized boolean empty() {
		return super.isEmpty();
	}
	/**
	 * Tells whether or not the queue is empty.
	 * 
	 * @return True only if the queue's size is zero.
	 */
	public synchronized boolean isEmpty()
	{
		return super.isEmpty();
	}

	/**
	 * String representation of the queue.
	 * 
	 * @return Delegated to {@link Vector#toString()}
	 */
	public synchronized String toString() {
		return super.toString();
	}

	/**
	 * Debug test Main.
	 */
	public static void main(String[] args) {
		SQueue q = new SQueue();

		q.enqueue("one");
		q.enqueue("two");
		q.enqueue("three");
		Object o = q.dequeue();
		q.enqueue("four");
		q.enqueue("five");
		q.enqueue("six");
		q.enqueue(o);

		System.out.println(q);

		for (int i = 0; i < 8; i++) {
			System.out.println(q.dequeue());
		}

		System.out.println(q);
	}//main

}//AQueue
