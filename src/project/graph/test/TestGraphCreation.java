package project.graph.test;
import project.graph.model.Graph;
import project.graph.model.factory.DirectedGraphCreation;
public class TestGraphCreation {
	public static void main(String[] args) {
		Graph graph = DirectedGraphCreation.createGraphFromConsole();
		graph.initGraph();
		System.out.println("List of Vertex: "+graph.getListVertexs());
		System.out.println("List of Adj: "+graph.getListAdj());
		System.out.println("List of Edge: "+graph.getListEdges());
		Graph reverseGraph = graph.getReverse();
		System.out.println("\nList of Reverse Adj: "+reverseGraph.getListAdj());
		System.out.println("List of Reverse Edge: "+reverseGraph.getListEdges());
		Graph undirectedGraph = graph.convertToUndirectedGraph();
		System.out.println("\nList of Undirected Adj: "+undirectedGraph.getListAdj());
		System.out.print("List of Undirected Edge: "+undirectedGraph.getListEdges());
		System.out.println("\nList of Adj: "+graph.getListAdj());
	}
}
