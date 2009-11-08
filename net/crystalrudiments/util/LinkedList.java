package net.crystalrudiments.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A Serializable, Cloneable, and Comparable implementation of LinkedList
 * with a small foot-print.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class LinkedList implements CList
{
    private static final long serialVersionUID = 42L;
    
    /**
     * Head of the list.
     */    
    protected RealNode head = null;

    /**
     * Last node in the list.
     */    
    protected RealNode tail = null;

    /**
     * Size of the current list.
     */    
    protected int size = 0;

    /**
     * Initiales the list with given head, tail, and size.
     * Should only be used internally or by sub-classes.
     * @param head Head of list.
     * @param tail Tail of list.
     * @param size Size of list.
     */
    protected LinkedList( RealNode head, RealNode tail, int size )
    {
        super();
        this.head = head;
        this.tail = tail;
        this.size = size;
    }
    /*
     * addInOrder, Traverse and Delete.
     */

    /**
     * Creates an empty LinkedList.
     */    
    public LinkedList()
    {
        this(null,null, 0);
    }
    
    public LinkedList(Collection coll)
    {
        this(null,null, 0);
        this.addAll( coll );
    }

    /**
     * Adds the object before the first object greater than itself according to
     * the comparator, or at the end of the list, which ever comes first.
     * @param newObject Object to add.
     * @param comp Used to compare objects.
     */ 
    public void add( Object newObject, Comparator comp )
    {
        addInOrder( newObject, comp );
        size++;
        if ( tail.getNext() != null ) tail = tail.getNext();
    }

    /**
     * Adds the object before the first object greater than itself according to
     * the comparator, or at the end of the list, which ever comes first.
     * 
     * @param newObject Object to add.
     * @param comp comparator to use.
     * @return Node just added.
     */
    protected RealNode addInOrder( Object newObject, Comparator comp )
    {
    	if ( head == null || comp.compare( head.getData(), newObject ) > 0 )
        {
    		RealNode node = new RealNode( newObject, head );
    		if (tail == null) tail = (head==null) ? node : head;
            head = node;
            return head;
        }
        for ( RealNode curr = head; curr.getNext() != null; curr = curr.getNext() )
        {
            if ( ( curr.getNext() == null ) || comp.compare( curr.getNext().getData(), newObject ) > 0 )
            {
                RealNode temp = new RealNode( newObject, curr.getNext() );
                curr.setNext( temp );
                return curr;
            }
        }
        return null;
    }

    /**
     * Returns an iterator for this list.
     * @return An internal iterator on this list starting at index 0.
     */    
    public Iterator iterator()
    {
        return new LinkedListIterator();
    }

    class LinkedListIterator implements Iterator, ListIterator
    {

        int i = 0;

        RealNode node = null;

        RealNode prev = null;

        /**
         *  
         */
        public LinkedListIterator()
        {
            super();
            node = head;
            prev = null;
        }

        /**
         * Removes current node from the list. Moves on to the next node.
         * 
         * @see java.util.Iterator#remove()
         */
        public void remove()
        {
        	if (node == null) return;
        		
            if (prev == null) {
            	head = head.getNext();
            	node = head;
            	if (head == null) tail = null;
            	size--;
			} else {
				prev.setNext(node.getNext());
				node = prev.getNext();
				size--;
			}
        }

        /**
		 * @see java.util.Iterator#hasNext()
		 */
        public boolean hasNext()
        {
            return ( node != null && node.getNext() != null );
        }

        /**
         * @see java.util.Iterator#next()
         */
        public Object next()
        {
            Object o = ( node == null ? null : node.getData() );
            if ( node != null )
            {
                prev = node;
                node = node.getNext();
                i++;
            }
            return o;
        }

        /**
         * @see java.util.ListIterator#nextIndex()
         */
        public int nextIndex()
        {
            return i + 1;
        }

        /**
         * @see java.util.ListIterator#previousIndex()
         */
        public int previousIndex()
        {
            return i - 1;
        }

        /**
         * @see java.util.ListIterator#hasPrevious()
         */
        public boolean hasPrevious()
        {
            return prev != null;
        }

        /**
         * @see java.util.ListIterator#previous()
         */
        public Object previous()
        {
            return ( prev == null ) ? null : prev.getData();
        }

        /**
         * @see java.util.ListIterator#add(java.lang.Object)
         */
        public void add( Object obj )
        {
            RealNode n = new RealNode( obj, node );
            if ( prev == null ) {
            	head = n;
            }
            else {
            	prev.setNext( n );
            	if (prev == tail) tail = n;
            }
            size++;
            node = n;
        }

        /**
         * @see java.util.ListIterator#set(java.lang.Object)
         */
        public void set( Object data )
        {
            node.setData( data );
        }

    }

    //Pre: helper module
    //Purpose:
    //Post:
    /**
     * Deletes the first encounter of the given object.
     * @param target Object to delete.
     * @return True only if successful.
     */    
    protected boolean deleteFirst( Object target )
    {
        if ( head == null ) return false;
        if ( areEqual( head.getData(), target ) )
        {
            if ( tail == head )
            {
                tail = null;
                head = null;
            } else
            {
                head = head.getNext();
            }
            return true;
        }
        for ( RealNode curr = head; curr.getNext() != null; curr = curr.getNext() )
        {
            if ( curr.getNext().getData().equals( target ) )
            {
                curr.setNext( curr.getNext().getNext() );
                if ( tail == curr.getNext() )
                {
                    tail = curr;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Current size of the list.
     * @see java.util.List#size()
     * @return Current size of the list.
     */
    public int size()
    {
        return size;
    }

    /**
     * Clears the list. Removes all elements.
     * @see java.util.List#clear()
     */
    public void clear()
    {
        size = 0;
        head = null;
        tail = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#isEmpty()
     */
    /**
     * If list has no elements, returns true.
     * @return True if empty.
     */    
    public boolean isEmpty()
    {
        return ( size == 0 );
    }

    /**
     * Converts this list into an array.
     * @see java.util.List#toArray()
     * @return An array containing all this list's elements in their current ordering.
     */
    public Object[] toArray()
    {
        Object[] array = new Object[size];
        int i = 0;
        for ( RealNode curr = head; true; i++, curr = curr.getNext() )
        {
            Object element = curr.getData();
            array[i] = element;
            if (curr.getNext() == null) break;
        }
        return array;
    }

    /**
     * Gets the element at the given index.
     * @see java.util.List#get(int)
     * @param i Index.
     * @return Element at index.
     */
    public Object get( int i )
    {
        RealNode node = getNode( i );

        return ( node == null ) ? null : node.getData();
    }

    /**
     * Removes the element at the given index, if any.
     * @see java.util.List#remove(int)
     * @param index Index of object to remove from list.
     * @return Objectx removed if any, otherwise null.
     */
    public Object remove( int index )
    {
        RealNode curr = head;
        RealNode prev = null;
        int i = 0;
        if ( index >= size || index < 0 ) return null;
        for ( i = 0; i < index; i++ )
        {
            prev = curr;
            curr = curr.getNext();
        }
        if (prev == null)
        {
        	head = head.getNext();
        }
        else {
        	prev.setNext( curr.getNext() );
        }
        size--;
        return curr.getData();
    }

    /**
     * Adds an object at the given index.
     * @param index Index where to add object.
     * @param obj Object to add to the list.
     *
     * @see java.util.List#add(int, java.lang.Object)
     */    
    public void add( int index, Object obj )
    {
        RealNode node = new RealNode( obj, null );
        if ( head == null )
        {
            head = node;
            tail = node;
        }
        else if ( index == 0 )
        {
        	node.setNext( head );
        	head = node;
        }
        else
        {
	        RealNode curr = getNode( index - 1 );
	        node.setNext( curr.getNext() );
	        curr.setNext( node );
	        if ( curr == tail ) tail = node;
        }
        size++;
    }

    /**
     * Gets the index of the first object in the list equal to the given object using
     * {@link Object#equals(Object)}.
     * @param data Element to compare to list objects and find index.
     * @return First index of an element in list equal to given object.
     * @see java.util.List#indexOf(java.lang.Object)
     */    
    public int indexOf( Object data )
    {
        RealNode curr = head;
        int i = 0;
        for ( i = 0; i < size; i++ )
        {
            if ( areEqual( curr.getData(), data ) )
            {
                break;
            }
            curr = curr.getNext();
        }
        if ( i == size ) return -1;
        return i;
    }

    /**
     * Gets the index of the <I>last</I> object in the list equal to the given object
     * using {@link Object#equals(Object)}.
     * @param data Element to compare to list objects and find index.
     * @return Last index of an element in list equal to given object.
     *
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */    
    public int lastIndexOf( Object data )
    {
        RealNode curr = head;
        int i = 0;
        int n = -1;
        for ( i = 0; i < size; i++ )
        {
            if ( curr.getData().equals( data ) )
            {
                n = i;
            }
            curr = curr.getNext();
        }
        return n;
    }

    /**
     * Adds the object to the list at the end of the list.
     * @param obj Object to add.
     * @return True only if successful.
     *
     * @see java.util.List#add(java.lang.Object)
     */    
    public boolean add( Object obj )
    {
        RealNode node = new RealNode( obj, null );
        if ( head == null )
        {
            head = node;
            tail = node;
            size++;
            return true;
        }
        tail.setNext( node );
        tail = tail.getNext();
        size++;
        return true;
    }

    /**
     * Returns true only if the list contains an object equal to the given object.
     * @see java.util.List#contains(java.lang.Object)
     * @param target Object to decide if the list contains.
     * @return True only if the list contains the given object.
     */
    public boolean contains( Object target )
    {
        return hasData( target );
    }

    /**
     * Returns true only if the list contains an object equal to the given object.
     * @see java.util.List#contains(java.lang.Object)
     * @return True only if the list contains the given object.
     * @param data Object to decide if the list contains.
     */
    protected boolean hasData( Object data )
    {
        if ( head == null ) return false;
        if ( areEqual( head.getData(), data ) ) { return true; }
        for ( RealNode curr = head; curr.getNext() != null; curr = curr.getNext() )
        {
            if ( areEqual( curr.getNext().getData(), data ) ) { return true; }
        }
        return false;
    }
    
    public static boolean areEqual(Object o1, Object o2){
    	if (o1 == null && o2 == null ) return true;
    	if (o1 != null && o2 != null )
    	{
    		return o1.equals(o2);
    	}
    	return false;
    }

    /**
     *
     * @see java.util.List#remove(java.lang.Object)
     * @param target
     * @return
     */
    public boolean remove( Object target )
    {
        boolean okay = deleteFirst( target );
        if ( okay ) size--;
        return okay;
    }

    /**
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    public boolean addAll( int index, Collection list )
    {
        if ( index > size || index < 0 ) return false;
        
        RealNode curr = index == 0 ? null : getNode( index - 1 );
        for ( Iterator iter = list.iterator(); iter.hasNext(); )
        {
            Object element = ( Object ) iter.next();
            add( index, element );
        }
        return true;
    }

    /**
     * @param index
     * @return
     */
    protected RealNode getNode( int index )
    {
        RealNode curr = head;
        if ( index == size ) { return tail; }
        for ( int i = 0; i < Math.min( index, size ); i++ )
        {
            curr = curr.getNext();
        }
        return curr;
    }

    /**
     * @see java.util.List#addAll(java.util.Collection)
     */
    public boolean addAll( Collection list )
    {
    	Iterator iter = list.iterator();
    	if (head == null)
    	{
    		Object element = ( Object ) iter.next();
            RealNode node = new RealNode( element, null );
            head = node;
            tail = node;
            size++;
    	}
        while ( iter.hasNext() )
        {
            Object element = ( Object ) iter.next();
            RealNode node = new RealNode( element, null );

            tail.setNext( node );
            tail = node;
            size++;
        }
        return true;
    }

    /**
     * @see java.util.List#containsAll(java.util.Collection)
     */
    public boolean containsAll( Collection list )
    {
        for ( Iterator iter = list.iterator(); iter.hasNext(); )
        {
            Object element = ( Object ) iter.next();
            if ( !hasData( element ) ) return false;
        }
        return true;
    }

    /**
     * @see java.util.List#removeAll(java.util.Collection)
     */
    public boolean removeAll( Collection arg0 )
    {
        boolean okay = true;
        for ( Iterator iter = arg0.iterator(); iter.hasNext(); )
        {
            Object element = ( Object ) iter.next();
            boolean d = deleteFirst( element );
            if ( d ) size--;
            if ( !d ) okay = false;
        }
        return okay;
    }

    /**
     * @see java.util.List#retainAll(java.util.Collection)
     */
    public boolean retainAll( Collection list )
    {
        int c = 0;

        if ( !list.contains( head.getData() ) )
        {
            if ( tail == head )
            {
                tail = null;
                head = null;
                size = 0;
            } else
            {
                head = head.getNext();
                size--;
            }
        }
        boolean stayPut = false;
        for ( RealNode curr = head; curr.getNext() != null; )
        {
            if ( !list.contains( curr.getNext().getData() ) )
            {
            	if ( tail == curr.getNext() )
                {
                    tail = curr;
                }
            	size --;
                curr.setNext( curr.getNext().getNext() );
                //you just removed next, so stay put.
            } else
            {
                c++;
                curr = curr.getNext();
            }
        }
        return ( c >= list.size() );
    }

    /**
     * Returns a list from start, inclusive, to ending, exclusive.
     * 
     * @see java.util.List#subList(int, int)
     * @param i1 Start inclusive.
     * @param i2 Ending, exclusive.
     * @return A LinkedList from i1 to i2.
     */
    public List subList( int i1, int i2 )
    {
        RealNode hd = new RealNode();
        RealNode end = new RealNode();
        int count = 1;
        if (i1 == i2 || i2 < i1) {
            return new LinkedList();
        }
        hd = (RealNode) getNode(i1).clone();
        end = hd;
        for (int i = i1 + 1; i < i2; i++)
        {
            RealNode n = new RealNode(getNode(i).getData(), null);
            end.setNext(n);
            end = n;
            count++;
        }
        return new LinkedList(hd, end, count);
    }
    
    /**
     * Returns a list from start, inclusive, to ending, exclusive.
     * 
     * @see java.util.List#subList(int, int)
     * @param i1 Start inclusive.
     * @param i2 Ending, exclusive.
     * @return A LinkedList from i1 to i2.
     */
    public CList subCList( int i1, int i2 )
    {
    	return (CList) subList(i1, i2);
    }

    /**
     * Returns ListIterator on this list.
     * @see java.util.List#listIterator()
     * @return ListIterator on this list.
     */
    public ListIterator listIterator()
    {
        return new LinkedListIterator();
    }

    /**
     * Returns ListIterator on this list, starting at given index.
     * 
     * @see java.util.List#listIterator(int)
     */
    public ListIterator listIterator( int i )
    {
        ListIterator iter = new LinkedListIterator();
        for ( ; iter.previousIndex() + 1 < i; iter.next() )
            ;
        return iter;
    }

    /**
     * 
     * @see java.util.List#set(int, java.lang.Object)
     */
    public Object set( int index, Object data )
    {
        RealNode node = getNode( index );
        if ( node == null ) { return null; }
        node.setData( data );
        return data;
    }

    /**
     * 
     * @see java.util.List#toArray(java.lang.Object[])
     */
    public Object[] toArray( Object[] array )
    {
        int i = 0;
        for ( RealNode curr = head; true; i++, curr = curr.getNext() )
        {
            Object element = curr.getData();
            if (i < array.length) array[i] = element;
            if (curr.getNext() == null) break;
        }
        return array;
    }
    /**
     * Compares the sizes of the given lists.
     * If those are equal, compares the toStrings of this
     * list and the given list.
     * If input is not list, -1 is returned.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object obj )
    {
        if ( obj instanceof Collection) {
            Collection list = (Collection)obj;
            if ( size == list.size() )
            {
            	return this.toString().compareTo( list.toString() );
            }
            return size - list.size();
        }
        return -1;
    }
    
    /**
     * Returns true if sizes are equals and toStrings are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
    	return (0 == compareTo(obj));
    }
    
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        return new LinkedList(this);
    }
    
    public String toString()
    {
    	StringBuffer sb = new StringBuffer("[");
    	for ( RealNode curr = head; true; curr = curr.getNext() )
        {
            sb.append( curr.toString() );
            if ( curr.getNext() != null ) sb.append(',');
            else break;
        }
    	sb.append(']');
    	return sb.toString();
    }

} //LinkedList
