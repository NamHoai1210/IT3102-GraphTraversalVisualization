package project.graph.test;
import project.graph.model.Graph;
import project.graph.model.algorithm.*;
import project.graph.model.factory.DirectedGraphCreation;
public class TestBFS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph graph = DirectedGraphCreation.createGraphFromConsole();
		BFS bfs = new BFS(graph);
		bfs.execute();
	}

}
