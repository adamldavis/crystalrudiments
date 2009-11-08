package net.crystalrudiments.yoda;

/**
 * Move.java
 * This represents a series of moves which is one unit. A unit is
 * a series of inputs (or no input) followed by a series of outputs
 * and is considered one chunk of data which receives a score.
 * Note: I also realize that this chunk itself may not deserve a
 * score or the score may be the result of only a section of this
 * chunk. Also, the variable "previous" tells whether or not this
 * score is reliant somehow on the input or not.
 * ex. 
 * output={1,3,4,5}, input={3}, score = 0.33333.
 */
public class Move implements YodaConstants
{
	public byte[] input;
	public byte[] output;
	public byte previous = 0; //how much does input matter? -127=no,127=yes.
	public float score = 0;
	
	public Move(byte[] in, byte[] out, float s)
	{
		input=in;
		output=out;
		score= s;
	}
	public boolean equals(Object obj)
	{
		if (obj instanceof Move) 
		{
			int i;
			Move m = (Move) obj;
			if (input.length!=m.input.length ||
				output.length!=m.output.length) return false;
			for (i=0; i<input.length; i++)	
				if (input[i]!=m.input[i]) return false;
			for (i=0; i<output.length; i++)
				if (output[i]!=m.output[i]) return false;
			return true;
		}
		return false; 
	}
	/**
	* toString method.
	*/
	public String toString()
	{
		String str="in: ";
		int i;
		for (i=0; i<input.length-1; i++) str += String.valueOf(input[i]) +",";
		str += String.valueOf(input[i]);
		str += "\nout: ";
		for (i=0; i<output.length-1; i++) str += String.valueOf(output[i]) +",";
		str += String.valueOf(output[i]);
		str += "\nprevious:\t" + String.valueOf(previous);
		str += "\nscore:\t\t" + String.valueOf(score);
		
		return str;
	}
	/**
	* Tester Main.
	*/
	public static void main(String[] args)
	{
		byte[] in1 = {1,2,3,4};
		byte[] out1 ={3,2,1};
		byte[] in2 = {2,3,4,1};
		byte[] out2 ={1,2,3};
		Move m1 = new Move(in1, out1, 12);
		Move m2 = new Move(in2, out2, 98);
		Move m3 = new Move(in1, out1, -32);
		
		System.out.println( "m1=\n" + m1 + "\nm2=\n"+m2+"\nm3=\n"+m3 +
		"\nm1=?=m2 " + m1.equals(m2) + "\nm2=?=m3 " + m2.equals(m3) +
		"\nm1=?=m3 " + m1.equals(m3) +"\nm2=?=m2 " + m2.equals(m2) );
	}
} //Move