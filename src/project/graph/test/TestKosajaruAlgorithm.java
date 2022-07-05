package project.graph.test;
import project.graph.model.Graph;
import project.graph.model.algorithm.*;
import project.graph.model.factory.DirectedGraphCreation;
public class TestKosajaruAlgorithm {
	public static void main(String[] args) {
		Graph graph = DirectedGraphCreation.createGraphFromConsole();
		Kosajaru kosajaru = new Kosajaru(graph,"/home/namhoai/Documents/OOP_MiniProject/GraphTraversal/src/project/graph/model/algorithm/command/Kosajaru.txt");
		kosajaru.execute();
	}
}
