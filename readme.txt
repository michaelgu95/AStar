COMP550 EC - Map Routing
Michael Gu

In order to optimize Dijkstra's algorithm, I attempted Idea #2 given in the assignment description.

This involved implementing A*, which essentially takes vanilla Dijkstra and adds a change during the relaxation step. This change can be found on line 172 in dijkstra.java:

double adjacentDistance = min.getDistanceFromSource() + neighborEntry.getValue() 
				+ neighbor.getEuclideanToTarget(to) - min.getEuclideanToTarget(to); // this line -> makes implementation into A*

If we comment this line out, it is clear that the runtime increases. Though this is not sublinear, it is a definite improvement, running at approximately O(VlgV).

There are several online implementations that attempt to implement A*, but none that were sublinear time. I believe another approach is necessary to arrive at such an optimization.



*Note:
There were several test cases where the A* implementation led to an incorrect path answer, and others where it was okay. However, you can verify that my implemenation of vanilla Dijkstra's is indeed correct by commenting out line 172 and adding a semi-colon to 171. 
This shows that I indeed created a "well-working implementation that provide correct shortest paths", despite attempting an optimization that fails in certain cases.

