This project is an homework for the artificial intelligence course, and it tries to find solutions to the travelling salesman problem by utilizing machine learning algorithms.

The system:

the result of java --version is:
java 10.0.2 2018-07-17
Java(TM) SE Runtime Environment 18.3 (build 10.0.2+13)
Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.2+13, mixed mode)

-----------------------------------------------------

the result of uname -a is:
Darwin Hydraboros 17.7.0 Darwin Kernel Version 17.7.0: Wed Oct 10 23:06:14 PDT 2018; root:xnu-4570.71.13~1/RELEASE_X86_64 x86_64

-----------------------------------------------------

The OS was macOS High Sierra Version 10.13.6 (17G3025)
The machine was:
 MacBook Pro (Retina, 15-inch, Mid 2015)
 Processor : 2.5 GHz Intel Core i7
 RAM       : 16 GB 1600 MHz DDR3

-----------------------------------------------------

Preparing the jar:

-cd into the folder
Run:
javac -O Runnable.java


-----------------------------------------------------

Execute the program (copy-paste to obtain my results):
---------------
---READ NOTE:--
---------------

java Runnable eil76.tsp 2 2 0.98923

java Runnable d198.tsp 123 3 0.989

java Runnable ch130.tsp 42 2 0.98923

java Runnable fl1577.tsp 42 3 0.68923

java Runnable kroa100.tsp 2 2 0.089

java Runnable lin318.tsp 0 2 0.61803398875

java Runnable pcb442.tsp 0 2 0.61803398875

java Runnable pr439.tsp 42 2 0.68923

java Runnable rat783.tsp 2 3 0.58923

java Runnable u1060.tsp 42 2 0.58923

-----------------------------------------------------
NOTE:
The calls will result in files problemname.sol containing the solution (in the same folder), the parameters and the total cost and error.
The first line will be written as a space separated sequence of indexes (1 to N)
the following lines will contain extra information.
If you need to change the output format to file it is possible by modifying the method write in file Solution.java (first 6 lines of the method).

The standard output of the calls above doesn't contain the sequence of indexes, the file generated automatically does, therefore the call with > problemname.sol WILL generate WRONG results.
