/*
 * Created on May 10, 2005 by Adam. 
 * TODO File in this comment.
 *
 */
package net.crystalrudiments.util;

import junit.framework.TestCase;

/**
 * QueueTest
 */
public class QueueTest extends TestCase {

	LinkedQueue q = null;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		q = new LinkedQueue();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		q = null;
	}

	/*
	 * Class under test for void Queue()
	 */
	public void testQueue() {
		assertTrue( q.isEmpty() );
	}

	public void testGetHead() {
		
		q.enqueue( "a");
		assertNotNull( q.getHead() );
		assertEquals( "a", q.getHead().getData() );
		q.enqueue("b");
		q.enqueue("c");
		assertEquals( "a", q.getHead().getData() );
	}

	public void testGetTail() {
		
		q.enqueue( "a");
		assertNotNull( q.getTail() );
		assertEquals( "a", q.getTail().getData() );
		
		q.enqueue("b");
		assertNotNull( q.getTail() );
		assertEquals( "b", q.getTail().getData() );
		q.enqueue("c");
		assertNotNull( q.getTail() );
		assertEquals( "c", q.getTail().getData() );
	}

	public void testIsEmpty() {
		for (int i=0; i < 10; i++)
		{
			q.enqueue( "_" + i);
		}
		assertFalse(q.isEmpty());
		int i =0;
		while (i < 11 && !q.isEmpty()) {
			i++;
			q.dequeue();
		}
		assertEquals( 10, i );
	}

	public void testEnqueue() {
		q.enqueue( "a");
		assertEquals( "a", q.dequeue() );
		for (int i=0; i < 10; i++)
		{
			q.enqueue( "_" + i);
		}
		for (int i=0; i < 10; i++)
		{
			assertEquals( "_" + i, q.dequeue() );
		}
	}

	public void testDequeue() {
		
		q.enqueue( "a");
		assertEquals( "a", q.dequeue() );
		for (int i=0; i < 10; i++)
		{
			q.enqueue( "_" + i);
		}
		for (int i=0; i < 10; i++)
		{
			assertEquals( "_" + i, q.dequeue() );
		}
	}

}
