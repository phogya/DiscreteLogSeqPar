# DiscreteLogSeqPar

A java program written for Parallel and Distributed Systems. Calculates the solution or solutions to the modular power operation a^n (mod p) = b, given p, a, and b. There are two versions, the sequential version and the parallel version. The sequential version uses a brute force algorithm to check for solutions to the modular power operation. The parallel version uses the same algorithm except that it checks for multiple solutions in parallel. Both programs print their execution times for comparison. The parallel version uses the [Parallel Java 2](https://www.cs.rit.edu/~ark/pj2.shtml) library. The overhead from using the Parallel Java 2 library is significant when solving smaller problems. To see a significant difference between the two use large values for p, a, and b. For example let p equal 194729, a equal 12345, and b equal 12345.

##Usage

Compile the sequential program from the command line using:

	javac DiscreteLogSeq.java
	
and run it with:
	
	java DiscreteLogSeq <p> <a> <b>
	
where p, a, and b are integers, p is a prime number, and a 
and b are in the range 1 <= a <= p-1.

Compile the parallel program from the command line using:

	javac -cp ./pj2.jar;. DiscreteLogPar.java
	
and run it with:

	java -cp ./pj2.jar;. pj2 DiscreteLogPar <p> <a> <b>
	
where p, a, and b are the defined the same as the sequential program.