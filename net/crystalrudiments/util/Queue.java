/*
 * Created on May 11, 2005 by Adam. 
 * TODO File in this comment.
 *
 */
package net.crystalrudiments.util;

import java.io.Serializable;

/**
 * Queue
 */
public interface Queue extends Serializable, Cloneable {

	
	public Object dequeue();
	
	public void enqueue(Object newData);
	
	public boolean isEmpty();
	
	public boolean empty();
}
