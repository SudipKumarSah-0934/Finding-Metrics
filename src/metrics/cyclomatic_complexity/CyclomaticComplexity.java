package metrics.cyclomatic_complexity;

import java.util.ArrayList;;

public class CyclomaticComplexity {
	public Node calculateCyclomaticComplexity(ArrayList<String> cyclomaticFilesRead) {
		System.out.print("\n");
		Graph2 graph = new Graph2(cyclomaticFilesRead);
		System.out.println();
		System.out.println("The graph:");
		return graph.buildGraph();
	}
}