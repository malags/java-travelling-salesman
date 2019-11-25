import java.util.Random;

public final class Annealing {

	final int n;
	final Instance inst;
	final Random random;
	final double constTemp;
	final int shift;
	Solution best;
	
	Annealing(final Instance inst,final int seed, double constTemp, final int shift){
		this.n = inst.n;
		this.inst = inst;
		this.constTemp = constTemp;
		if(seed < 0)
			random = new Random();
		else
			random = new Random(seed);
		NearestNeighbour NN = new NearestNeighbour(inst,random.nextInt(Integer.MAX_VALUE));
		this.best = NN.sol;
		this.shift = shift;
		
	}
	
	
	final Solution randomSelec(final Solution current){
		Solution s = new Solution();
		s.score = current.score;
		s.inds = current.inds.clone();
		s.inst = inst;
		int r1 = random.nextInt(n);
		int r2 = random.nextInt(n);
		while(r1 ==r2){
			r2 = random.nextInt(n);
		}
		int r3 = random.nextInt(n);
		while(r1 == r3 || r3 == r2){
			r3 = random.nextInt(n);
		}
		
		
		int c1, c2,c3;
		//sort
		//r1 is min
		if(r1 < r2 && r1 < r3){
			c1 = r1;
			// r1 < r2 <r3
			if(r2 < r3){
				c2 = r2;
				c3 = r3;
			}
			// r1 < r3 < r2
			else{
				c2 = r3;
				c3 = r2;
			}
		}
		//r2 is min
		else if(r2 <r1 && r2 < r3){
			c1 = r2;
			// r2 < r1 <r3
			if(r1 < r3){
				c2 = r1;
				c3 = r3;
			}
			// r2 < r3 < r1
			else{
				c2 = r3;
				c3 = r1;
			}
		}
		//r3 is min
		else{
			c1 = r3;
			// r3 < r1 <r2
			if(r1 < r2){
				c2 = r1;
				c3 = r2;
			}
			// r3 < r2 < r1
			else{
				c2 = r2;
				c3 = r1;
			}
		}
		
		//add distance that will be removed twice (two numbers are successors)
		if(c1 == c2-1){
			s.score += inst.getDistanceR(s.inds[c1], s.inds[c2]);
		}
		if(c2 == c3-1){
			s.score += inst.getDistanceR(s.inds[c3], s.inds[c2]);
		}
		if(c3 == inst.n-1 && c1 == 0){
			s.score += inst.getDistanceR(s.inds[0], s.inds[inst.n-1]);
		}
		
		//update distance
		//remove edges
		//predecessor of c1
		if(c1 == 0){
			s.score += -inst.getDistanceR(s.inds[0], s.inds[inst.n-1]); 
		}
		else{
			s.score += -inst.getDistanceR(s.inds[c1],s.inds[c1-1]); 
		}
		//successor of c3
		if(c3 == inst.n-1){
			s.score += -inst.getDistanceR(s.inds[0], s.inds[inst.n-1]); 
		}
		else{
			s.score += -inst.getDistanceR(s.inds[c3], s.inds[c3+1]);
		}
		
		s.score +=  -inst.getDistanceR(s.inds[c1], s.inds[c1+1]) 
					- inst.getDistanceR(s.inds[c2-1], s.inds[c2]) 
					- inst.getDistanceR(s.inds[c2+1], s.inds[c2]) 
				    -inst.getDistanceR(s.inds[c3], s.inds[c3-1]);
		//removed all edges
		
		
		
		
		int temp = s.inds[r1];
		s.inds[r1] = s.inds[r2];
		s.inds[r2] = s.inds[r3];
		s.inds[r3] = temp;
		
		//add edges
		//remove distance that will be added twice (two numbers are successors)
		if(c1 == c2-1){
			s.score -= inst.getDistanceR(s.inds[c1], s.inds[c2]);
		}
		if(c2 == c3-1){
			s.score -= inst.getDistanceR(s.inds[c3], s.inds[c2]);
		}
		if(c3 == inst.n-1 && c1 == 0){
			s.score -= inst.getDistanceR(s.inds[0], s.inds[inst.n-1]);
		}
		
		//add edges
		//predecessor of c1
		if(c1 == 0){
			s.score += inst.getDistanceR(s.inds[0], s.inds[inst.n-1]); 
		}
		else{
			s.score += inst.getDistanceR(s.inds[c1],s.inds[c1-1]); 
		}
		//successor of c3
		if(c3 == inst.n-1){
			s.score += inst.getDistanceR(s.inds[0], s.inds[inst.n-1]); 
		}
		else{
			s.score += inst.getDistanceR(s.inds[c3], s.inds[c3+1]);
		}
		
		s.score += inst.getDistanceR(s.inds[c1], s.inds[c1+1]) 
				+ inst.getDistanceR(s.inds[c2-1], s.inds[c2]) 
				+ inst.getDistanceR(s.inds[c2+1], s.inds[c2]) 
				+inst.getDistanceR(s.inds[c3], s.inds[c3-1]);
		return s;
	}
	
	double compute_error(){
		return inst.compute_error(best.evaluate());
	}
	
	void annealing(){
		final long tim = System.currentTimeMillis();
		double Temp = n;
		Solution current = best;
		best.evaluate();
		final Opt2 opt2 = new Opt2(inst,current,shift);
		opt2.opt(tim);
		Solution neccxt;
		
		double E = 1;
		while(Temp > 0){
			while(E > 0){
				if(System.currentTimeMillis() - tim > 179998) {
					System.out.println("time over");
					return;
				}
				neccxt = randomSelec(current);
				opt2.switchSol(neccxt);
				opt2.opt(tim);
				if(best.score == inst.best_known) return;
				
				E = neccxt.score - current.score;
				if(E < 0){
					current = neccxt;
					if (neccxt.score < best.score){
                        best = neccxt;
                    }
				}
				else{
					if(random.nextDouble() < Math.pow(1.61803398875, -E/Temp)){
						current = neccxt;
					}
				}
				
			}// end inner while
			Temp *= this.constTemp/* 0.98923;/**/ /*0.78923;/**/ /* 0.68923/**/;
		    E = 1;
		}
		System.out.println("time taken "+ (System.currentTimeMillis() - tim));
		
	}
	
}
