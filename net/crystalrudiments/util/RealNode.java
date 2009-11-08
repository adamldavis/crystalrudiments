package net.crystalrudiments.util;

import java.io.Serializable;

/**
 *
 * Implements a node for a list.
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 2.0, May 7, 2005
 */
final class RealNode implements Serializable, Cloneable {

    private static final long serialVersionUID = 4242L;
    private Object data;

    private RealNode next;

    
    public Object clone()
    {
        return new RealNode( data, next );
    }

    //Purpose: Constructor
    public RealNode(Object initData, RealNode next) {
        setData(initData);
        setNext(next);
    }

    public RealNode() {
        this(null, null);
    }

    public String toString() {
        if (data == null) {
            return "null";
        } else {
            try {
                return data.toString();
            } catch (Exception e) {
                return "";
            }
        } //endif
    } //toString

    //Purpose: accessors
    public Object getData() {
        return data;
    }

    public RealNode getNext() {
        return next;
    }

    //Purpose: modifiers
    public void setData(Object newdata) {
        data = newdata;
    }

    public void setNext(RealNode newnode) {
        next = newnode;
    }

} //Node
