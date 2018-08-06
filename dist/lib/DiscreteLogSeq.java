/**
 * Class DiscreteLogSeq computes solutions to the modular power operation,
 * a^n (mod p) = b, where a, p, and b are given as arguments. It computes
 * these solutions using a sequential brute force algorithm. If multiple
 * solutions are found it prints the solutions for the smallest and largest
 * values of i.
 *
 * Usage: java DiscreteLog <p> <a> <b>
 *
 * @author  Peter Hogya
 * @version 07-Oct-2017
 */
import java.math.BigInteger;

public class DiscreteLogSeq  {

	public static void main(String[] args) throws Exception {
		
		// To measure total execution time
		long startTime = System.nanoTime();
		
		// Variables given in arguments
		BigInteger p = null;
		BigInteger a = null;
		BigInteger b = null;
		
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
		
		// Check if p is prime
		if(!p.isProbablePrime(64)) {
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
			System.err.println("Argument <b> must be an integer in the range 1 <= a <= p-1.");
			usage();
		}
		
		// Solutions
		BigInteger s1 = null;
		BigInteger s2 = null;
		
		// To measure main loop time
		long startLoop = System.nanoTime();
		
		// If a solution to a^i (mod p) = b is found it is stored in s1.
		// Consecutive solutions are stored in s2.
		// Thus s1 is the smallest solution and s2 is the largest.
		for(BigInteger i = BigInteger.ZERO; i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) {
			if( a.modPow(i, p).equals(b) && s1 == null) {
				s1 = i;
			}
			else if( a.modPow(i, p).equals(b) && s1 != null) {
				s2 = i;
			}
		}
		
		long endLoop = System.nanoTime();
		
		// No solutions are found
		if( s1 == null && s2 == null ) {
			System.out.println("Undefined");
		}
		// One solution is found
		else if( s1 != null && s2 == null ) {
			System.out.println(a.toString() + "^" + s1 + " (mod " + p.toString() + ") = " + b.toString());
		}
		// More than one solution is found
		else {
			System.out.println(a.toString() + "^" + s1 + " (mod " + p.toString() + ") = " + b.toString());
			System.out.println(a.toString() + "^" + s2 + " (mod " + p.toString() + ") = " + b.toString());
		}
		
		long endTime = System.nanoTime();
		System.out.println("Main loop execution time is " + (endLoop - startLoop));
		System.out.println("Total execution time is " + (endTime - startTime));
	}
    
	/**
     * Prints a usage message and exits.
     */
    private static void usage()
    {
        System.err.println ("Usage: DiscreteLog <p> <a> <b>");
        System.exit(1);
    }
}
