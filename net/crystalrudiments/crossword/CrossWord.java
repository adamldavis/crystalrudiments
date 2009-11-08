/**
 * CrossWord.java
 * 
 * Created: June 6, 2001. 7:30pm--8:40pm
 *
 */

package net.crystalrudiments.crossword;
import java.util.Vector;
/**
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, June 6, 2001
 */
public class CrossWord
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = true;
	
	private static final int MAX_REP_WORD = 40;
	private static final int MAX_REP_CROSS = 10;
	
	private char[][] matrix;
	
	private Vector letters;
	private int num_letters=0;
	
	private Vector words;
	
	int x;
	int y;
	
	/**
	* Default Constructor
	*/
	public CrossWord(int x, int y, Vector words)
	{
		this.x=x;
		this.y=y;
		matrix = new char[x][y];
		clearMatrix();
		this.words = words;
		letters = new Vector((words.size()*14),10);
	}//CrossWord
	
	private void clearMatrix()
	{
		for (int i=0; i<x; i++) for (int j=0;j<y;j++)
		matrix[i][j] = ' ';
	}
	public void makeCrossWord()
	{
		int i;
		if (isProblem()) {
			if (bDEBUG) System.out.println("isProblem");
			return;
		}
		for (i=0; i < MAX_REP_CROSS; i++) {
			if (attemptCrossWord()) break;
			else clearMatrix();
		}
		if (bDEBUG) System.out.println("attempts="+(i+1));
	}
	/**
	* Attempts to make Cross Word puzzle in matrix.
	* @return If it succeeded.
	*/
	private boolean attemptCrossWord()
	{
		int num=0;
		int i,j;
		for (i=0; i<words.size();i++)
		{
			String str = (String) words.elementAt(i);
			str = str.toUpperCase();
			
			for (j=0; j<str.length(); j++) {
				letters.add(str.substring(j,j+1));
				num_letters++;
			}
			for (j=0; j < MAX_REP_WORD; j++) {
				if (placeWord(str)) { num++; break; }
			}
			//if (bDEBUG) System.out.println("tries:" + (j+1));
		}//i
		if (num==words.size()) return true;
		else return false;
	}
	private boolean placeWord(String str)
	{
		char[] old = new char[str.length()];
		boolean worked =true;
		int ran = randomInt(4);
		int dx=0;
		int dy=0;
		int xx,yy;
		int i;
		switch (ran) {
			case 0: dx = -1; break;
			case 1: dx = 1; break;
			case 2: dy = -1; break;
			case 3: dy = 1; break;
		}
		xx = randomInt(x);
		yy = randomInt(y);
		for (i=0; i<str.length(); i++) 
		{
			old[i] = matrix[xx][yy];
			if (isOkay(xx,yy,str.charAt(i))) matrix[xx][yy] = str.charAt(i);
			else { worked=false; break; }
			xx += dx;
			yy += dy;
			if (xx<0 || yy<0) { worked=false; xx -= dx; yy -= dy; break; }
			if (xx>=x|| yy>=y) { worked=false; xx -= dx; yy -= dy; break; }
		}
		if (!worked) for ( ;i>=0; i--) 
		{
			char k = old[i];
			matrix[xx][yy] = k;
			xx -= dx;
			yy -= dy;
		}
		return worked;
	}
	private int randomInt(int range)
	{
		double raw = Math.random();
		return (int) (raw *(double)range);
	}
	private boolean isOkay(int i, int j, char b) 
	{
		char a = matrix[i][j];
		if ((a==b)||(a==' ')) return true;
		else return false;
	}
	private boolean isProblem()
	{
		int i;
		if (words==null) return true;
		if (words.isEmpty()) return true;
		for (i=0; i<words.size();i++)
		{
			if (!(words.elementAt(i) instanceof String)) return true;
			String str = (String) words.elementAt(i);
			if (str.length()>x && str.length()>y) return true;
		}
		return false;
	}
	public void fillInSpace()
	{
		int i,j;
		for (i=0; i<x; i++) for (j=0; j<y; j++) {
			if (matrix[i][j]==' ')
			{
				int ran = randomInt(letters.size());
				String str = (String) letters.elementAt(ran);
				matrix[i][j] = str.charAt(0);
			}
		}
	}//fillInSpace
	
	public char[][] getMatrix() { return matrix;}
	
	public String toString()
	{
		String ret="";
		int i,j;
		for (j=0; j<y; j++) {
			ret+= "{";
			for (i=0;i<x;i++) ret += matrix[i][j];
			ret+= "}\n";
		}
		return ret;
	}//toString
	
	/**
	* Main method. Run by java compiler.
	*/
	public static void main(String args[])
	{
		Vector words = new Vector(40);
		words.add("amusing");
		words.add("fun");
		words.add("elated");
		words.add("humorous");
		words.add("hilarious"); //5
		words.add("gorgious");
		words.add("joking");
		words.add("playful");
		words.add("excited");
		words.add("ugly"); 		//10
		words.add("stupid");
		words.add("outrageous");
		words.add("child");
		words.add("young");
		words.add("contrived");	//15
		words.add("vodka");
		words.add("borish");
		words.add("star");
		words.add("fish");
		words.add("moon");		//20
		words.add("beam");
		words.add("corny");
		words.add("little");
		words.add("brat");
		words.add("water");		//25
		words.add("gigantic");
		words.add("alien");
		words.add("foreign");
		words.add("planet");
		words.add("doctor");	//30
		CrossWord cw = new CrossWord(20,20,words);
		
		cw.makeCrossWord();
		System.out.println(cw);
		cw.fillInSpace();
		//System.out.println(cw);
	}
	
}
