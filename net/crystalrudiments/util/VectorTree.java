
package net.crystalrudiments.util;

import java.util.*;

import net.crystalrudiments.lang.AdamObject;

/**
 *
 * A tree of vectors pointing to other vectors
 * and eventually ending with the leaves holding
 * the actual data.
 * <P>
 * Started October 18, 2001
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class VectorTree extends Vector
{
	private static final int INIT_VECTOR_SIZE = 8;
	
	/** The second parameter of Vector(x,y). Zero tells the Vector to
	* double it's capacity whenever it overflows.*/
	private static final int INIT_VECTOR_INC = 0;

	/** The local variable saying what delimeter is used for postions.*/
	String dlim = ",";
	
	/**
         * Creates an empty VectorTree with given inital capacity.
         * @param size Initial capactiy.
         */
	public VectorTree(int size) {	super(size, INIT_VECTOR_INC); 	}
        /**
         * Creates an empty VectorTree.
         */        
	public VectorTree()
	{
		this(INIT_VECTOR_SIZE);
	}
        /**
         * Initializes this VectorTree using the given Vector's values.
         * @param v Another VectorTree to copy.
         */        
	public VectorTree(Vector v) { super(v); }
	/* -------------------------------------------------------------*/
	/** 
	 * Modifier for dlim, the delimeter for position.
	 */
	public void setDlim(String d) {		dlim=d;		}
	/** 
	 * Accessor for dlim, the delimeter for position.
	 */
	public String getDlim() {		return dlim;	}
	
	public void addLeaf(Object obj)
	{
		this.add(obj);
	}
	
	public void addLeaf(Object obj, String position)
	{
		Object test = findPosition(position);
		Vector ptr;
		if (test instanceof Vector) {
			ptr = (Vector)test;
			ptr.add(obj);
		}
		else {
			AdamObject.error("Tried to add leaf to a position of data.");
		}
	}
	
	/**
	 * Returns the leaf or branch at position.
	 */
	public Object getLeaf(String position)
	{
		return findPosition(position);
	}
	/**
	 * Returns the requested object if it exists in the VectorTree.
	 * Otherwise it returns null. Actually returns the address of the
	 * Object you get'ed.
	 */
	public Object get(Object obj)
	{
		Object ret = null;
		
		ret = get(this, obj);
		
		return ret;
	}
	public Object get(Object elem, Object obj)
	{
		Object ret = null;
		
		if (elem instanceof Vector)
		{
			Vector v = (Vector)elem;
			
			for (int i=0; i<v.size(); i++) {
				ret = get(v.elementAt(i), obj);
				if (ret!=null) break;
			}
		} else {
			if (elem.equals(obj)) return elem;
		}
		return ret;
	}
	/**
         * Deletes the leaf or branch at position.
         * @param position
         */
	public void removeLeaf(String position)
	{
		int pos=0;
		Object ref;
		
		/* let pos = the postion of the last number in position.*/
		for (String s=position; s.indexOf(dlim)>-1 && s.indexOf(dlim)!=s.length()-1; )
		{
			pos = s.indexOf(dlim)+1;
			s = s.substring(pos);
		}
		
		ref = findPosition(position.substring(0,pos));
		
		if (ref != null)
		{
			int d = Integer.parseInt(position.substring(pos));
			
			if ( ((Vector)ref).size() < d ) {
				AdamObject.error("Asked to remove a leaf at index-out-of-bounds.");
			}
			else {
				((Vector)ref).removeElementAt(d);
			}
		}
	}
	/**
         * Returns the head vector of the VectorTree.
         * @return Reference to this actually.
         */
	public Vector getHeadVector()
	{
		return (Vector) this;
	}
	
	/**
	 * position works like DOS foldes: /1/5/0 == this[1][5][0]
	 */
	private Object findPosition(String position)
	{
		StringTokenizer strtok;
		Object ptr = this;
		
		strtok = new StringTokenizer(position, dlim);
		
		while (strtok.hasMoreTokens())
		{
			try { 
				ptr = ((Vector)ptr).elementAt( Integer.parseInt(strtok.nextToken()) ); 
			}
			catch (Exception e) {
				AdamObject.error("Index out of bounds. position = " + position);
			}
		}
		return ptr;
	}
	/**
	 * Big print string method.
	 */
	public String toString()
	{
		String str = "Dir=" + "\n";
		
		for (int i=0; i<this.size(); i++)
		{
			str += toString(this.elementAt(i), String.valueOf(i) +dlim);
			str += "\n";
		}
		return str;
	}
	/**
	 * Helper to toString method.
	 */
	private String toString(Object elem, String position)
	{
		String str="";
		
		if (elem instanceof Vector)
		{
			Vector v = (Vector)elem;
			str += "  ";
			str += "Dir=" + position + "\n";
			
			for (int i=0; i<v.size(); i++)
			{
				for (int j=0; j<position.length(); j++) str += " ";
				str += toString(v.elementAt(i), position + i + dlim);
				str += "\n";
			}
		} else
		{
			//str += "\n";
			//for (int j=0; j<position.length(); j++) str += " ";
			str += "  ";
			str += elem.toString();
		}
		return str;
	}
	/**
	 * Big testing main.
	 */
	public static void main (String[] args)
	{
		VectorTree vt = new VectorTree();
		
		vt.addLeaf("hello");
		vt.addLeaf("world");
		vt.addLeaf("bye");
		
		AdamObject.print("test 1-->\n" + vt.toString());
		
		Vector branch = new Vector(6);
		branch.add("one");
		branch.add("two");
		branch.add("three");
		
		vt.addLeaf(branch);
		
		vt.addLeaf("four", "3,");
		
		AdamObject.print("test 2-->\n" + vt.toString());
		
		System.out.println( vt.getLeaf("3,0") );
		vt.removeLeaf("3,0");
		
		AdamObject.print("test 3-->\n" + vt.toString());
		
		AdamObject.print("hi");
	}
}//VectorTree
