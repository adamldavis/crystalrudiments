/**
*
*/
package net.crystalrudiments.hem;
import java.util.*;
public class Template {
    String category;
    Vector sentences;
    boolean used[];
    Template() { 
		category = new String(); 
		sentences = new Vector(); 
	}
    void initUsed() {
        used = new boolean[ sentences.size() ];
        Arrays.fill(used, false);
    }
	String getCategory() {
		return category;
	}
	String getSentence() {
        for (int i=0; i<used.length; i++) {
            if (!used[i]) {
                used[i] = true;
                return (String) sentences.get(i);
            }
        }
        return null;
    }
}
