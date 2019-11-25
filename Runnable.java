
public final class Runnable {
	public static void main(String args[]){
		if(args.length < 4){
			System.out.println("need three arguments *.tsp  seed shift TempMultiplier");
			System.exit(1);
		}
		
		String name = args[0];
		final int seed = Integer.parseInt(args[1]);  
		final int shift = Integer.parseInt(args[2]);  
		final double tempMultiplier = Double.parseDouble(args[3]);  
		  
		System.out.println("Start reading file");
		  
		Instance inst = new Instance(name);
		System.out.println("File read");
		
		System.out.println("Annealing");
		Annealing AN = new Annealing(inst,seed,tempMultiplier,shift);
		AN.annealing();
		System.out.println("completed Annealing");
		System.out.println("Total score " + AN.best.score);
		System.out.println("Error " + AN.compute_error());
		System.out.println("Valid "+AN.best.valid());
		AN.best.write(seed,tempMultiplier,shift);
	}
}
