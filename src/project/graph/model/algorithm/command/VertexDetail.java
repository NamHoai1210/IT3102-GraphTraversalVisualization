package project.graph.model.algorithm.command;

import java.util.Random;

import javafx.scene.paint.Color;
import project.graph.model.Vertex;

public class VertexDetail extends Detail{
	private Vertex vertex;
	public static int red =102, green=205,blue=170;
	public VertexDetail(String content, Vertex vertex) {
		super(content);
		this.vertex = vertex;
	}
	public Vertex getVertex() {
		return vertex;
	}
	public static void setAnotherColor() {
		Random generator=new Random();
		red = generator.nextInt(200);
		blue = generator.nextInt(200);
		green = generator.nextInt(200);
	}
	@Override
	public String toString() {
		if(super.toString().equals("Visit")) return " * "+super.toString()+" vertex: "+vertex;
		else if(super.toString().equals("Get")) return " * " + super.toString() +" a vertex of current SCC: "+vertex;
		else if(super.toString().equals("Assign")) return " * "+ super.toString() + " color for vertex: "+vertex;
		else if(super.toString().equals("Assign another")) return " * "+ super.toString() + " color for vertex: "+vertex;
		else return "";
		
	}
	@Override
	public void presentConsole() {
		System.out.println(this);
	}
	@Override
	public String presentUI(int time) {
		if(super.toString().equals("Visit")) vertex.getGraphic().changeTo(3);
		else if(super.toString().equals("Get")) vertex.getGraphic().changeToCustom(Color.rgb(red, green, blue));
		else if(super.toString().equals("Assign")) vertex.getGraphic().changeTo(3);
		else if(super.toString().equals("Assign another")) vertex.getGraphic().changeTo(4);
		return this.toString();
	}
	@Override
	public void unPresentUI() {
		vertex.getGraphic().changeTo(0);
	}
}
