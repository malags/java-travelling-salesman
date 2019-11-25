
public final class Opt2 {
	final Instance inst;
	Solution sol;
	final int n;
	final int shift;
	final int finalIter;


	Opt2(final Instance inst,final Solution sol,int shift){
		this.inst = inst;
		this.sol  = sol;
		this.n    = inst.n;
		this.shift = shift;
		if(shift >= 0)
			finalIter = inst.n >> shift;
		else
			finalIter = -shift;
	}

	void switchSol(Solution s){
		this.sol = s;
	}


	private final int compute_gain(final int i,final int j){
		int ai  = sol.inds[i];
		int ai1 = sol.inds[i+1];
		int aj  = sol.inds[j];
		int aj1;
		if((j+1) == inst.n)
			aj1 = sol.inds[0];
		else
			aj1 = sol.inds[j+1];
		return inst.getDistanceR(aj, ai) - inst.getDistanceR(ai1,ai) + inst.getDistanceR(ai1,aj1)  - inst.getDistanceR(aj,aj1);
	}

	private final void exchange(final int i,final int j,int gain){
		int temp;
		for(int ii = j, jj=i+1; ii > jj ; --ii, ++jj){
			temp = sol.inds[ii];
			sol.inds[ii] = sol.inds[jj];
			sol.inds[jj] = temp;
		}
		sol.score += gain;
	}

	public final void opt(long time){
		double best_gain = -1;
		int best_j, best_i;
		int gain = 0;
		int lim = finalIter;
		while(best_gain < 0){
			best_i = -1;
			best_j = -1;
			best_gain = 0;
			for(int i = 0 ; i < inst.n - 2 ; ++i){
				for(int j = i+2; j < inst.n ; ++j){
					gain = compute_gain(i,j);
					if(gain < best_gain){
						best_i = i;
						best_j = j;
						best_gain = gain;
						break;
					}
				}
				if (System.currentTimeMillis() - time > 179998){
		            break;
		        }
				if(best_gain < 0)
					break;
			}
			if(best_j == -1)
				return;
			exchange(best_i,best_j,gain);
			if(lim < 0) return;
			--lim;
		}
	}

}
