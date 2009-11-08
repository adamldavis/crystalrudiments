/**
* Words.
*/
package net.crystalrudiments.hem;
import java.util.*;

public class Words {
    String type;
    Vector words;
    public Words() { 
		type=""; 
		words = new Vector(); 
	}
    public void addWord(String w) {
         words.add(w);
    }
    public void setType(String t) {
        type = t;
    }
    public String getType() {
        return type;
    }
    public boolean contains(String word) {
        return words.contains(word);
    }
}
