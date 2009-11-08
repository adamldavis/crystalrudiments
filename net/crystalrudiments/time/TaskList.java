package net.crystalrudiments.time;

import java.util.*;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, July 6, 2004
 */
public class TaskList extends LinkedList {

    public TaskList() {
        super();
    }

    public void add(Task task) {
        super.add(task);
    }

    public void add(String str) {
        super.add(new Task(str));
    }

    public boolean empty() {
        return (super.size() == 0);
    }

    public String toString() {
        String str = "";
        for (Iterator iter = this.iterator(); iter.hasNext();) {
            str += iter.next().toString();
            str += "\n";
        }
        return str;
    }
}//TaskList
