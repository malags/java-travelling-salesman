import java.io.FileWriter;
import java.io.IOException;

public final class Solution {
	int[] inds;
	Instance inst;
	int c;
	int score;

	Solution(){}

	Solution(final Instance inst){
		this.inst = inst;
		this.inds = new int[inst.n];
		for(int i = 0 ; i < inst.n ; ++i)
			inds[i] = -1;
		c = 0;
	}

	int evaluate(){
		score = inst.getPathLength(this);
		return score;
	}

	void append(int v){
		inds[c] = v;
		++c;
	}

	final boolean canInsert(){
		return c < inds.length;
	}



	int getLast(){
		return inds[c-1];
	}

	boolean valid(){
		boolean[] v = new boolean[inds.length];
		for(int i = 0 ; i < inds.length ; ++i)
			v[i] = false;
		for(int i : inds)
			v[i]= true;
		boolean vv = true;
		for(boolean b : v){
			vv = b && vv;
		}
		return vv;
	}

	public final void write(int seed, double tempMultiplier,int shift){
		String output = "";
		for(int i = 0 ; i < inds.length-1 ; ++i){
			output += (inds[i]+1) + " ";
		}
		output += (inds[inds.length-1]+1);
		output += "\n";
		int value = evaluate();
		output += "Seed " + seed + '\n';
		output += "Shift " + shift + '\n';
		output += "tempMultiplier " + tempMultiplier + '\n';
		output += "Total cost " + value +'\n';
		output += "Error " + inst.compute_error(value);
		try {
			String ss = inst.name.replaceAll(".tsp",".sol");
			System.out.println("writing solution to file "+ss);
			//overwrite
			FileWriter filew = new FileWriter( ss , false );
			filew.write(output);
			filew.close();
			System.out.println("wrote to file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
