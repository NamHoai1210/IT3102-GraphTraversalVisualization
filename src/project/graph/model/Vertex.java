package project.graph.model;

import project.graph.model.ui.VertexNode;

public class Vertex {
	public static final double RADIUS = 20.0f;
	private int id;
	private VertexNode graphic;
	// Constructor
	public Vertex(int id) {
		this.id = id;
		graphic = new VertexNode(RADIUS, id+"");
	}
	
	public Vertex(int id, VertexNode graphic) {
		super();
		this.id = id;
		this.graphic = graphic;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		graphic.setText(id+"");
	}
	public VertexNode getGraphic() {
		return graphic;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vertex) {
			if(((Vertex)o).getId()==id) return true;
		}
		return false;
	}
	 @Override
	public String toString() {
		return ""+id;
	}
}
