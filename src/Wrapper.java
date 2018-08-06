import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Wrapper {

    public static void main(String[] args) {
    	
    	// Command line arguments
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
    	
        try {
            System.out.println("Running Sequential DiscreteLog program:");
            runProcess("javac DiscreteLogSeq.java", new File(".\\lib"), 'n');
            runProcess("java DiscreteLogSeq " + p.toString() + " " + a.toString() + " " + b.toString(), new File(".\\lib"), 'p');
            
            System.out.println("\nRunning Parallel DiscreteLog program:");
            runProcess("javac -cp .\\pj2.jar;. DiscreteLogPar.java", new File(".\\lib"), 'n');
            runProcess("java -cp .\\pj2.jar;. pj2 DiscreteLogPar " + p.toString() + " " + a.toString() + " " + b.toString(), new File(".\\lib"), 'p');
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
    	
        String line = null;
        BufferedReader in = new BufferedReader (
            new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
      }

      private static void runProcess(String command, File file, char Mode) throws Exception {
    	  
        Process pro = Runtime.getRuntime().exec(command, null, file);
        if( Mode == 'p' ) {
        	printLines(command + " stdout:", pro.getInputStream());
            printLines(command + " stderr:", pro.getErrorStream());
        }
      }
      
      /**
  	* Prints a usage message and exits.
  	*/
  	private static void usage() {
  		System.err.println ("Usage: java -jar DL.jar <p> <a> <b>");
  		System.exit(1);
  	}
}