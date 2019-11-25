import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public final class Instance {
	public double[] dist_matrix;
	public int[] dist_matrix_rounded;
	public int n;
	public int best_known;
	public final String name;
	Instance(final String name){
		this.name = name;
		try (BufferedReader br = new BufferedReader(new FileReader(name))) {
		    String line;
		    int i = 0;
		    int count = 0;
		    node[] nodes = null;
		    while ((line = br.readLine()) != null) {
		    	++count;
		    	if(line.contains("DIMENSION")){
		    		n = Integer.parseInt(line.split(" ")[2]);
		    		System.out.println("n = "+n);
		    		nodes = new node[n];
		    		dist_matrix = new double[n*n];
		    		continue;
		    	}
		    	if(line.contains("BEST_KNOWN")){
		    		best_known = Integer.parseInt(line.split(" ")[2]);
		    		System.out.println("best_known = "+best_known);
		    		continue;
		    	}
		    	if(line.contains("EOF")){
		    		break;
		    	}
		    	if(count < 8) continue;
		    	String[] elem = line.split(" ");
		    	nodes[i] = new node(Double.parseDouble(elem[1]),Double.parseDouble(elem[2]));
		    	++i;
		    }
		    set_matrix(nodes);
		} catch (FileNotFoundException e) {
			System.out.println("file "+name+ " not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		
	}
	
	void set_matrix(final node[] nodes){
		dist_matrix_rounded = new int[n*n];
		for (int i = 0 ; i < this.n ; ++i){
			for(int j = 0 ; j < this.n ; ++j){
				dist_matrix[j*this.n + i] = dist(nodes,j,i);
				dist_matrix_rounded[j*this.n + i] = (int) Math.round(dist(nodes,j,i));
			}
		}
    }
	
	private double dist(final node[] nodes,final int i,final int j){
		double d = (nodes[i].x - nodes[j].x)*(nodes[i].x - nodes[j].x) +
				   (nodes[i].y - nodes[j].y)*(nodes[i].y - nodes[j].y);
		return Math.sqrt(d);
	}
	
	double[] getAllDist(final int i){
		return Arrays.copyOfRange(dist_matrix, i*n, i*n + n);
	}
	
	int getPathLength(final Solution sol){
		int d = 0;
		for (int i = 0 ;i < sol.inds.length-1 ; ++i){
			d += Math.round(getDistance(sol.inds[i+1],sol.inds[i]));
		}
		d += Math.round(getDistance(sol.inds[n-1],sol.inds[0]));
		return d;
	}
	
	double compute_error(final int value){
		return (((double)value)-((double)best_known))/((double)best_known);
	}
	
	final int getDistanceR(final int i ,final int j){
		return this.dist_matrix_rounded[j*this.n + i];
	}
	
	double getDistance(final int i ,final int j){
        return this.dist_matrix[j*this.n + i];
    }
}
