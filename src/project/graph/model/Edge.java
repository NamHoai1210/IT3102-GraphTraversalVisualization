package project.graph.model;

import project.graph.model.ui.Arrow;

public class Edge {
	private boolean isDirected;
	private Vertex from;
	private Vertex to;
	private Arrow graphic;
	//Constructor
	public Edge() {
	}
	
	public Edge(boolean isDirected, Vertex from, Vertex to) {
		super();
		this.isDirected = isDirected;
		this.from = from;
		this.to = to;
	}

	public Edge(boolean isDirected, Vertex from, Vertex to,Arrow graphic) {
		this.isDirected = isDirected;
		this.from = from;
		this.to = to;
		this.graphic = graphic;
	}
	
	public Arrow getGraphic() {
		return graphic;
	}

	public Vertex getFrom() {
		return from;
	}

	public Vertex getTo() {
		return to;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Edge) {
			Edge edge = (Edge)obj;
			if(edge.getFrom().equals(this.from) && edge.getTo().equals(this.to) && edge.isDirected==this.isDirected) return true;
		}
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(isDirected)
		return ""+from.getId()+" -> "+to.getId();
		else return ""+from.getId()+" -- "+to.getId();
	}
}
