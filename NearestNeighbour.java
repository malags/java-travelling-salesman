import java.util.Random;

public final class NearestNeighbour {

	public final Instance inst;
	public final Solution sol;
	
	private int selectFirst(final int seed){
		Random rn = new Random(seed);
		int selec = rn.nextInt(inst.n);
		return selec;
	}
	
	private int selectClosest(){
		double[] dist = inst.getAllDist(sol.getLast());
		dist[sol.getLast()] = Double.MAX_VALUE;
		for(int i : sol.inds){
			if(i < 0)
				break;
			dist[i] = Double.MAX_VALUE;
		}
		double m  = Double.MAX_VALUE;
		int ind = -1;
		for(int i = 0 ; i < inst.n ; ++i)
			if(dist[i] <= m){
				m = dist[i];
				ind = i;
			}
				
		return ind;
	}
	
	NearestNeighbour(final Instance inst,final int seed){
		this.inst = inst;
		
		this.sol = new Solution(inst);
		
		
		int current = selectFirst(seed);
		sol.append(current);
		
		while(sol.canInsert()){
			current = selectClosest();
			sol.append(current);
		}
		
	}
}
