/*
 * Created on May 10, 2005 by Adam. 
 * Test case for LinkedList methods.
 *
 */
package net.crystalrudiments.util;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import junit.framework.TestCase;

/**
 * LinkedListTestCase - Test case for LinkedList methods.
 */
public class LinkedListTest extends TestCase {

	CList list = null;
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		list = new LinkedList();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		list = null;
		System.gc();
	}

	/*
	 * Class under test for void LinkedList()
	 */
	public void testLinkedList() {
		assertEquals( 0, list.size() );
		assertNotNull( list.iterator() );
		assertNull( list.get(0) );
	}

	/*
	 * Class under test for void add(Object, Comparator)
	 */
	public void testAddObjectComparator() {
		Comparator c = new Comparator() {

			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo( arg1.toString() );
			}
			
		};
		list.add( "z", c );
		list.add( "x", c );
		list.add( "a", c );
		list.add( "c", c );
		list.add( "b", c );
		assertEquals( 5, list.size() );
		assertEquals( "[a,b,c,x,z]", list.toString() );
		list.add( "a" );
		assertEquals( "a", list.get( list.size() - 1 ) );
		list.clear();
		assertEquals( 0, list.size());
		assertNull(list.get(0));
		list.add("a", c);
		assertEquals( "a", list.get( list.size() - 1 ) );
		list.remove("a");
		assertEquals( 0, list.size());
		assertNull(list.get(0));
	}

	public void testIterator() {

		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		int n=0;
		for  (Iterator iter = list.iterator(); iter.hasNext(); n++)
		{
			assertEquals( "_" + n, (String) iter.next());
		}
		assertEquals( 9, n );
		
		Iterator iter = list.iterator();
		for  (; iter.hasNext(); n--)
		{
			assertEquals(n + 1, list.size());
			iter.remove();
		}
		assertEquals(1, list.size());
		iter.remove();
		assertEquals(0, list.size());
		assertNull(list.get(0));
	}


	public void testSize() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		assertEquals( 10, list.size() );
	}

	public void testClear() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		list.clear();
		assertEquals( 0, list.size() );
		list.add("a");
		assertEquals( "[a]", list.toString() );
	}

	public void testIsEmpty() {
		assertTrue( list.isEmpty() );
		list.add(0, "aa");
		assertFalse( list.isEmpty());
	}

	/*
	 * Class under test for Object[] toArray()
	 */
	public void testToArray() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		Object[] arr = list.toArray();
		assertEquals( 10, arr.length );
		for  (int i=0; i < 10; i++)
		{
			assertEquals( "_" + i, arr[i] );
		}
	}

	public void testGet() {
		assertNull(list.get(0));
		list.add("a");
		assertNotNull(list.get(0));
		assertEquals( list.get(0), "a");
	}

	/*
	 * Class under test for Object remove(int)
	 */
	public void testRemoveint() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		list.remove( 4 );
		assertFalse( list.contains( "_4"));
		list.remove( 6 - 1 );
		list.remove( 8 - 2 );
		assertEquals("[_0,_1,_2,_3,_5,_7,_9]", list.toString());
	}

	/*
	 * Class under test for void add(int, Object)
	 */
	public void testAddintObject() {
		for  (int i=0; i < 5; i++)
		{
			list.add( "_" + i );
		}
		list.add( 0, "a");
		assertEquals("[a,_0,_1,_2,_3,_4]", list.toString());
		list.add( 0, "b");
		assertEquals("[b,a,_0,_1,_2,_3,_4]", list.toString());
		list.add( 0, "c");
		assertEquals("[c,b,a,_0,_1,_2,_3,_4]", list.toString());
	}

	public void testIndexOf() {
		for  (int i=0; i < 5; i++)
		{
			list.add( "_" + i );
		}
		assertEquals( 4, list.indexOf("_4"));
	}

	public void testLastIndexOf() {
		for  (int i=0; i < 5; i++)
		{
			list.add( "_4" );
		}
		list.add( "zzz");
		assertEquals( 4, list.lastIndexOf("_4"));
	}

	/*
	 * Class under test for boolean add(Object)
	 */
	public void testAddObject() {
		for  (int i=0; i < 5; i++)
		{
			list.add( "_" + i );
		}
		list.clear();
		for  (int i=0; i < 5; i++)
		{
			list.add( "-" + i );
		}
		assertEquals( "[-0,-1,-2,-3,-4]", list.toString());
	}

	public void testContains() {
		for  (int i=0; i < 5; i++)
		{
			list.add( "-" + i );
		}
		assertTrue( list.contains("-1"));
		assertTrue( list.contains("-0"));
		list.clear();
		for  (int i=0; i < 5; i++)
		{
			list.add( "-" + i );
			list.add( "-" + i );
		}
		assertTrue( list.contains("-1"));
		assertTrue( list.contains("-0"));
	}

	/*
	 * Class under test for boolean remove(Object)
	 */
	public void testRemoveObject() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		list.remove( "_4" );
		assertFalse( list.contains( "_4"));
		list.remove( "_6" );
		list.remove( "_8" );
		assertEquals("[_0,_1,_2,_3,_5,_7,_9]", list.toString());
	}

	/*
	 * Class under test for boolean addAll(int, Collection)
	 */
	public void testAddAllintCollection() {
		Set set = new HashSet();
		for  (int i=0; i < 5; i++)
		{
			set.add( "-" + i );
		}
		list.add( "zzz" );
		list.addAll(0, set);
		assertEquals( 6, list.size());
		set.add( "zzz" );
		assertTrue( set.containsAll(list));
		assertTrue( list.containsAll(set));
		assertNotSame( "zzz", list.get(0));
	}

	/*
	 * Class under test for boolean addAll(Collection)
	 */
	public void testAddAllCollection() {
		Set set = new HashSet();
		for  (int i=0; i < 5; i++)
		{
			set.add( "-" + i );
		}
		list.addAll(set);
		assertEquals( 5, list.size());
		
		assertTrue( set.containsAll(list));
		assertTrue( list.containsAll(set));
	}

	public void testContainsAll() {
		Set set = new HashSet();
		for  (int i=0; i < 5; i++)
		{
			set.add( "-" + i );
		}
		for  (int i=0; i < 5; i++)
		{
			list.add( "-" + i );
		}
		assertEquals( 5, list.size());
		
		assertTrue( set.containsAll(list));
		assertTrue( list.containsAll(set));
	}

	public void testRemoveAll() {
		Set set = new HashSet();
		set.add("a");
		for  (int i=0; i < 5; i++)
		{
			set.add( "-" + i );
		}
		for  (int i=0; i < 5; i++)
		{
			list.add( "-" + i );
		}
		list.removeAll( set );
		set.clear();
		
	}

	public void testRetainAll() {
		Set set = new HashSet();
		set.add("a");
		for  (int i=0; i < 5; i++)
		{
			set.add( "-" + i );
		}
		for  (int i=0; i < 10; i++)
		{
			list.add( "-" + i );
		}
		list.retainAll(set);
		assertEquals( 5, list.size() );
		assertEquals("[-0,-1,-2,-3,-4]", list.toString());
		set.clear();
	}

	public void testSubList() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "-" + i );
		}
		List list2 = list.subList(1, 5);
		assertEquals( 4, list2.size() );
		assertEquals("[-1,-2,-3,-4]", list2.toString());
		
	}

	public void testSubCList() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "-" + i );
		}
		CList list2 = list.subCList(1, 5);
		assertEquals( 4, list2.size() );
		assertEquals("[-1,-2,-3,-4]", list2.toString());
		assertTrue( list.compareTo( list2 ) > 0 );
	}

	/*
	 * Class under test for ListIterator listIterator()
	 */
	public void testListIterator() {

		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		int n=0;
		for  (ListIterator iter = list.listIterator(); iter.hasNext(); n++)
		{
			assertEquals( "_" + n, (String) iter.next());
		}
		assertEquals( 9, n );
		
		ListIterator iter = list.listIterator();
		for  (; iter.hasNext(); n--)
		{
			assertEquals(n + 1, list.size());
			iter.remove();
		}
		assertEquals(1, list.size());
		iter.remove();
		assertEquals(0, list.size());
		assertNull(list.get(0));
	}

	/*
	 * Class under test for ListIterator listIterator(int)
	 */
	public void testListIteratorint() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		int n=1;
		for  (ListIterator iter = list.listIterator(1); iter.hasNext(); n++)
		{
			assertEquals( "_" + n, (String) iter.next());
		}
		assertEquals( 9, n );
		
		ListIterator iter = list.listIterator();
		for  (; iter.hasNext(); n--)
		{
			assertEquals(n + 1, list.size());
			iter.remove();
		}
		assertEquals(1, list.size());
		iter.remove();
		assertEquals(0, list.size());
		assertNull(list.get(0));
	}

	public void testSet() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "_" + i );
		}
		list.set(0, "a");
		assertEquals("a", list.get(0));
		list.set(9, "z");
		assertEquals("z", list.get(9));
		list.set(4, "c");
		assertEquals("c", list.get(4));
	}

	/*
	 * Class under test for Object[] toArray(Object[])
	 */
	public void testToArrayObjectArray() {
		for  (int i=0; i < 10; i++)
		{
			list.add( "" + i );
		}
		String[] array = new String[list.size()];
		list.toArray(array);
		for  (int i=0; i < 10; i++)
		{
			assertEquals( "" + i , array[i] );
		}
	}

	public void testCompareTo() {
		CList list2 = new LinkedList();
		
		for  (int i=0; i < 10; i++)
		{
			list.add( "" + i );
			list2.add( "" + i );
		}
		assertEquals( list, list2 );
		assertEquals(0, list.compareTo( list2 ));
		list2.add("z");
		assertTrue( list.compareTo(list2) < 0 );
		assertTrue( list2.compareTo(list) > 0 );
	}

	/*
	 * Class under test for boolean equals(Object)
	 */
	public void testEqualsObject() {
		CList list2 = new LinkedList();
		
		for  (int i=0; i < 10; i++)
		{
			list.add( "" + i );
			list2.add( "" + i );
		}
		assertEquals( list, list2 );
		assertEquals(0, list.compareTo( list2 ));
		list2.add("z");
		list2.remove(0);
		assertNotSame( list, list2 );
	}

}
