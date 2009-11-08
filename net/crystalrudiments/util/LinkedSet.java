/*
 * Created on May 9, 2005 by Adam. 
 * LinkedList which only contains unique members.
 *
 */
package net.crystalrudiments.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;


/**
 * LinkedList which only contains unique members.
 */
public class LinkedSet extends LinkedList implements Set
{

    /**
     * @see java.util.List#add(int, java.lang.Object)
     */
    public void add( int index, Object obj )
    {
        if (!hasData(obj))        super.add( index, obj );
    }
    /**
     * @see net.crystalrudiments.util.LinkedList#add(java.lang.Object, java.util.Comparator)
     */
    public void add( Object newObject, Comparator comp )
    {
        if (!hasData(newObject)) super.add( newObject, comp );
    }
    /**
     * @see java.util.Collection#add(java.lang.Object)
     */
    public boolean add( Object obj )
    {
        if (!hasData(obj)) return super.add( obj );
        return false;
    }
    /**
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    public boolean addAll( Collection list )
    {
        boolean okay = true;
        for ( Iterator iter = list.iterator(); iter.hasNext(); )
        {
            Object element = ( Object ) iter.next();
            if ( !hasData(element) )
            {
                RealNode node = new RealNode( element, null );

                tail.setNext( node );
                tail = node;
            }
            else
            {
                okay = false;
            }
        }
        return okay;
    }
    /**
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    public boolean addAll( int index, Collection list )
    {
        boolean okay = true;
        if ( index > size || index < 0 ) return false;
        RealNode curr = getNode( index );
        for ( Iterator iter = list.iterator(); iter.hasNext(); )
        {
            Object element = ( Object ) iter.next();
            if ( !hasData(element) )
            {
                RealNode node = new RealNode( element, null );

                curr.setNext( node );
                curr = node;
            }
            else okay = false;
        }
        return okay;
    }
    /**
     * Creates an empty LinkedSet.
     */
    public LinkedSet()
    {
        super();
    }
    
    

}
