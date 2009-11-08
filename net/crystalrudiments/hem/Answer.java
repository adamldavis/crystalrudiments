/**
* This class holds the structure for answers.
* It is most efficient when there are far more keywords then there are words
* in a sentence.
* <BR> Keywords can actually be phrases.
*/
package net.crystalrudiments.hem;
import java.util.*;

public class Answer {
    Vector keywords;
    Vector responses;
    boolean[] used;
    TreeSet tree;
    StringTokenizer strtok;
    Answer() { 
		keywords = new Vector();  
		responses = new Vector(); 
        tree = new TreeSet();
	}
    public void addKeyword(String keyword) {
        keywords.add(keyword);
        strtok = new StringTokenizer(keyword);
        while(strtok.hasMoreTokens()) {
             tree.add(strtok.nextToken());
        }
    }//
    public void addResponse(String res) {
        responses.add(res);
    }//
    public String getResponse() {
        for (int i=0; i<used.length; i++) {
            if (!used[i]) {
                 used[i] = true;
                 return (String) responses.get(i);
            }
        }
        return null;
    }
    public void initUsed() {
        used = new boolean[ responses.size() ];
        Arrays.fill(used, false);
    }
    /* Returns null if no match, otherwise it returns an unused response.
    */
    public String match(String str) {
        boolean okay = false;
        strtok = new StringTokenizer(str);
        while(strtok.hasMoreTokens()) {
            if (tree.contains(strtok.nextToken())) { okay = true; break; }
        }
        if (okay) for (int i=0; i<keywords.size(); i++) {
            if (str.indexOf((String) keywords.get(i)) >= 0) {
                    return getResponse(); 
            }
        }
        return null;
    }//match
            
	
}//Answer
