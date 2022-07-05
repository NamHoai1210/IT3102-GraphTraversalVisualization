package project.graph.model.algorithm.command;


import project.graph.model.Edge;

public class EdgeDetail extends Detail{
	private Edge edge;
	public EdgeDetail(String content,Edge edge) {
		super(content);
		this.edge = edge;
	}
	public Edge getEdge() {
		return edge;
	}
	@Override
	public String toString() {
		return " + "+super.toString()+" edge: "+edge;
	}
	@Override
	public void presentConsole() {
		System.out.println(this);
	}
	@Override
	public String presentUI(int time) {
		if(time ==0) {
			edge.getGraphic().changeTo(3);
		}else {
		edge.getGraphic().getAnimation().playFromStart();
		edge.getGraphic().effect(time);
		}
		return this.toString();
	}
	@Override
	public void unPresentUI() {
		if(edge.isDirected()) {
		edge.getGraphic().changeTo(0);}
		else {
			edge.getGraphic().changeTo(1);
		}
	}
}
