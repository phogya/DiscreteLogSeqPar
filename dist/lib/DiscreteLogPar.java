/**
 * Class DiscreteLogPar computes solutions to the modular power operation,
 * a^n (mod p) = b, where a, p, and b are given as arguments. It computes
 * these solutions using a parallel brute force algorithm. If multiple
 * solutions are found it prints the solutions for the smallest and largest
 * values of i.
 *
 * Usage: java pj2 DiscreteLog <p> <a> <b>
 *
 * @author  Peter Hogya
 * @version 09-Oct-2017
 */

import java.math.BigInteger;

import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.LongVbl;


public class DiscreteLogPar 
	extends Task {
	
	// Command line arguments
	BigInteger p;
	BigInteger a;
	BigInteger b;

	// Solutions
	LongVbl sMin;
	LongVbl sMax;

	/**
	* Main Program
	*/
	public void main(String[] args) throws Exception {
		
		long startTime = System.nanoTime();
		
		// Check number of arguments
		if ( args.length != 3 ){
			System.err.println("DiscreteLog takes 3 arguments.");
			usage();
		}
		
		// Check if arguments are integers and convert to BigInts
		try {
			p = new BigInteger(args[0]);
			a = new BigInteger(args[1]);
			b = new BigInteger(args[2]);
			
		} catch ( NumberFormatException e) {
			System.err.println("All arguments must be integers.");
			usage();
		}
		
		// Check if p >= 2
		if( p.compareTo(BigInteger.ONE) != 1) {
			System.err.println("Argument <p> must be greater than or equal to 2.");
			usage();
		}

		// Check if p is prime
		if( !p.isProbablePrime(64)) {
			System.err.println("Argument <p> must be a prime number.");
			usage();
		}
		
		// Check range of a
		if( a.compareTo(BigInteger.ONE) == -1 || a.compareTo(p.subtract(BigInteger.ONE)) == 1) {
			System.err.println("Argument <a> must be an integer in the range 1 <= a <= p-1.");
			usage();
		}
		
		// Check range of b
		if( b.compareTo(BigInteger.ONE) == -1 || b.compareTo(p.subtract(BigInteger.ONE)) == 1) {
			System.err.println("Argument <b> must be an integer in the range 1 <= b <= p-1.");
			usage();
		}
		
		// Solutions
		sMin = new LongVbl.Min(p.longValue());
		sMax = new LongVbl.Max(0L);
		
		long startLoop = System.nanoTime();
		
		// Parallel loop to check for solutions to a^n (mod p) = b
		parallelFor(0L, p.longValue() - 1L).exec( new LongLoop() {
			
			LongVbl thrMin;
			LongVbl thrMax;

			public void start() {
				thrMin = threadLocal (sMin);
				thrMax = threadLocal (sMax);
			}

			public void run (long i) {
				
				if( a.modPow(BigInteger.valueOf(i), p).equals(b)) {
					if( i < thrMin.item) {
						thrMin.item = i;
					}
					if( i > thrMax.item) {
						thrMax.item = i;
					}
				}
			}
		});
		
		long endLoop = System.nanoTime();
			
		// No solutions are found
		if( sMin.longValue() == p.longValue() && sMax.longValue() == 0L ) {
			System.out.println("Undefined");
		}
		// One solution is found
		else if( sMin.longValue() == sMax.longValue() ) {
			System.out.println(a.toString() + "^" + sMin + " (mod " + p.toString() + ") = " + b.toString());
		}
		// More than one solution is found
		else {
			System.out.println(a.toString() + "^" + sMin + " (mod " + p.toString() + ") = " + b.toString());
			System.out.println(a.toString() + "^" + sMax + " (mod " + p.toString() + ") = " + b.toString());
		}
		
		long endTime = System.nanoTime();
		System.out.println("Main loop execution time is " + (endLoop - startLoop));
		System.out.println("Total execution time is " + (endTime - startTime));
	}
	
	/**
	* Prints a usage message and exits.
	*/
	private static void usage() {
		System.err.println ("Usage: DiscreteLog <p> <a> <b>");
		System.exit(1);
	}
}
