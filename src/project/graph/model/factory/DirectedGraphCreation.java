package project.graph.model.factory;

import java.util.Scanner;

import project.graph.model.Graph;
import project.graph.model.Vertex;

public class DirectedGraphCreation {
	public static Graph createGraphFromConsole() {
		Graph graph = new Graph();
		char c;
		Scanner scanner = new Scanner(System.in);
		int i=0,id; 
		Vertex from, to;
		String[] strings;
		String input;
		System.out.println("Press V to add vertex,E to add edge,D to delete vertex,C to delete edge, X to exit: ");
		do {
			c=scanner.next().toLowerCase().charAt(0);
			switch (c) {
			case 'v':
				graph.addVertex(new Vertex(i));
				System.out.println("-> Added vertex "+i);
				i++;
				break;
			case 'e':
				System.out.print("Please enter 2 vertex in format [from,to]: ");
				input = scanner.next();
				strings= input.split(",");
				from = graph.getVertex(Integer.parseInt(strings[0]));
				if(from==null) { System.out.println("Error! Vertex does not exist!");break;}
				to = graph.getVertex(Integer.parseInt(strings[1]));
				if(to==null) { System.out.println("Error! Vertex does not exist!");break;}
				graph.addEdge(from, to,true);
				System.out.println("-> Added edge ("+from.getId()+","+to.getId()+")");
				break;
			case 'x':
				System.out.println("-> EXIT!\n");
				break;
			case 'd':
				System.out.print("Enter id of vertex: ");
				id = scanner.nextInt();
				graph.delVertex(graph.getAVertex(id));
				System.out.println("-> Deleted vertex "+id);
				break;
			case 'c':
				System.out.print("Please enter 2 vertex in format [from,to]: ");
				input = scanner.next();
				strings= input.split(",");
				from = graph.getAVertex(Integer.parseInt(strings[0]));
				if(from==null) { System.out.println("Error! Vertex does not exist!");break;}
				to = graph.getAVertex(Integer.parseInt(strings[1]));
				if(to==null) { System.out.println("Error! Vertex does not exist!");break;}
				graph.delEdge(from, to);
				System.out.println("-> Added edge ("+from.getId()+","+to.getId()+")");
				break;
			default:
				System.out.println("Error!");
				break;
			}
		}while(c!='x');
		return graph;
	} 
}
