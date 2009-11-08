/*
 * Created on May 10, 2005 by Adam. 
 *
 */
package net.crystalrudiments.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * CList - Crystal Rudiments List interface.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public interface CList extends List, Serializable, Comparable, Cloneable {

	public CList subCList( int i1, int i2 );
	
	public void add( Object newObject, Comparator comp );
}
