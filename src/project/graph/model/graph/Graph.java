package project.graph.model.graph;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import project.graph.model.ui.Arrow;

import java.util.ArrayList;
import java.util.Iterator;
public class Graph {
	private List<Vertex> listVertexs = new ArrayList<Vertex>();
	private List<List<Vertex>> listAdj = new ArrayList<>();
	private List<Edge> listEdges = new ArrayList<Edge>();
	
	//Constructor
	public Graph() {
	}
	//get Graph infor for test
	public List<Vertex> getListVertexs() {
		return listVertexs;
	}
	public List<List<Vertex>> getListAdj() {
		return listAdj;
	}
	
	public List<Edge> getListEdges() {
		return listEdges;
	}
	//main Method
	public boolean isDirected() {
		if(listEdges.size()==0) return false;
		return listEdges.get(0).isDirected();
	}
	public void changeToUnDirected() {
		if(!isDirected()) return;
		for(int i=0;i<listEdges.size();i++) {
			listEdges.get(i).setDirected(false);
		}
	}
	//Vertex
	public void addVertex(Vertex v) {
		listVertexs.add(v);
		listAdj.add(new ArrayList<>());
	}
	public boolean isVertex(Vertex v) {
		return listVertexs.contains(v);
	}
	public Vertex getVertex(int index) {
		if(index<0 || index >=this.getVertexsSize()) return null;
        return listVertexs.get(index);
	}
	public int getVertexIndex(Vertex v) {
		return listVertexs.indexOf(v);
	}
	public int getVertexIndex(int v) {
		return listVertexs.indexOf(new Vertex(v));
	}
	public List<Edge> delVertex(Vertex v) {
		List<Edge> delEdges = new ArrayList<Edge>();
		int i;
		for(i=0;i<listEdges.size();i++) {
			if(listEdges.get(i).getFrom().equals(v)||listEdges.get(i).getTo().equals(v)) {
				delEdges.add(listEdges.get(i));
				listEdges.remove(i);
				if(getEdgeSize()==0) break;
				}
		}
		listAdj.remove(listVertexs.indexOf(v));
		for(i=0;i<listAdj.size();i++) {
			listAdj.get(i).remove(v);
		}
		listVertexs.remove(v);
		return delEdges;
		
	}
	public List<Edge> delVertex(int index) {
		Vertex v = listVertexs.get(index);
		List<Edge> delEdges = new ArrayList<Edge>();
		int i;
		for(i=0;i<listEdges.size();i++) {
			if(listEdges.get(i).getFrom().equals(v)||listEdges.get(i).getTo().equals(v)) {
				delEdges.add(listEdges.get(i));
				listEdges.remove(i);
				if(getEdgeSize()==0) break;
				i--;
				}
		}
		listAdj.remove(listVertexs.indexOf(v));
		for(i=0;i<listAdj.size();i++) {
			listAdj.get(i).remove(v);
		}
		listVertexs.remove(v);
		return delEdges;
	}
	public Vertex getAVertex(int id) {
		for(int i=0;i<listVertexs.size();i++) {
			if(listVertexs.get(i).getId()==id) return listVertexs.get(i);
		}
		return null;
	}
	public int getVertexsSize() {
		return listVertexs.size();
	}
	//Adjacent List
	public List<Vertex> getAdjList(Vertex v){
		return listAdj.get(this.getVertexIndex(v));
	}
	public List<Vertex> getAdjList(int index){
		return listAdj.get(index);
	}
	public void addAdj(Vertex from, Vertex to) {
		List<Vertex> adjList = this.getAdjList(from);
		if(adjList.contains(to)) return;
		int i=0;
		while(i<adjList.size() && to.getId()> adjList.get(i).getId()) i++;
		listAdj.get(getVertexIndex(from)).add(i,to);
	}
	
	//Edge
	public int getEdgeSize() {
		return listEdges.size();
	}
	public int getEdgeIndex(Edge edge) {
		return listEdges.indexOf(edge);
	}
	public int getEdgeIndex(Vertex from, Vertex to) {
		Edge n;
		for(int i=0;i<listEdges.size();i++) {
			n=listEdges.get(i);
			if(n.getFrom().equals(from) &&n.getTo().equals(to)) return i;
		}
		return -1;
	}
	public Edge getEdge(Vertex from, Vertex to) {
		Edge n;
		Iterator<Edge> i = listEdges.iterator();
        while (i.hasNext())
        {
             n = i.next();
            if(n.getFrom().equals(from) &&n.getTo().equals(to)) return n;
        }
        return null;
	}
	public Edge getEdge(int index) {
		return listEdges.get(index);
	}
	public void addEdge(Edge edge) {
		addAdj(edge.getFrom(), edge.getTo());
		listEdges.add(edge);
	}
	public void addEdge(Vertex from,Vertex to,boolean isDirected) {
		addAdj(from, to);
		if(isDirected==false) {
			addAdj(to, from);
		}
		listEdges.add(new Edge(isDirected, from, to));
	}
	public void delEdge(Vertex from, Vertex to) {
		listEdges.remove(getEdge(from,to));
		int i=listVertexs.indexOf(from);
		getAdjList(i).remove(to);
	}
	public void delEdge(int index) {
		int i=listVertexs.indexOf(listEdges.get(index).getFrom());
		getAdjList(i).remove(listEdges.get(index).getTo());
		listEdges.remove(index);
	}
	//Get reverse Graph
	public Graph getReverse(){
		if(!isDirected()) return this;
		Graph g = new Graph();
		int i; 
		List<Vertex> adjList;
		Iterator<Vertex> it = listVertexs.iterator();
        Vertex n;
        Arrow arrow;
        Double x,y,xy;
        while (it.hasNext())
        {
             n = it.next();
             g.addVertex(n);
        }
		for(i=0;i<this.getVertexsSize();i++) {
			adjList=listAdj.get(i);
			it=adjList.iterator();
			while (it.hasNext()){
				n = it.next();
				g.addAdj(n, listVertexs.get(i));
				getEdge(listVertexs.get(i),n);
				x = listVertexs.get(i).getGraphic().getX()-n.getGraphic().getX();
				y = listVertexs.get(i).getGraphic().getY()-n.getGraphic().getY();
				xy = Math.sqrt(x*x + y*y);
				x = (x/xy)*20.0f;
				y = (y/xy)*20.0f;
				arrow = new Arrow(n.getGraphic().getX(),n.getGraphic().getY(),listVertexs.get(i).getGraphic().getX()-x, listVertexs.get(i).getGraphic().getY()-y);
				g.addEdge(new Edge(true, n, listVertexs.get(i), arrow));
			}
		}
		return g;
	}
	//Covert to an undirected graph from a directed graph
	public Graph convertToUndirectedGraph() {
		Arrow arrow;
		if(!this.isDirected()) return this;
		Graph result = this.getReverse();
		Edge tmp;
		int i,j;
		for(i=0;i<this.listEdges.size();i++) {
			tmp = listEdges.get(i);
			if(result.getListEdges().contains(tmp)) continue;
			arrow = new Arrow(tmp.getGraphic().getStartX(), tmp.getGraphic().getStartY(), tmp.getGraphic().getEndX(), tmp.getGraphic().getEndY());
			result.addEdge(new Edge(false, tmp.getFrom(), tmp.getTo(), arrow));
			}
		result.changeToUnDirected();
		List<Vertex> adjList;
		for(i=0;i<result.getVertexsSize();i++) {
			adjList=result.getAdjList(i);
			for(j=0;j<adjList.size();j++) {
				result.addAdj(adjList.get(j), result.getVertex(i));
			}
		}
		for(i=0;i< result.getEdgeSize();i++) {
			if(result.getEdge(i).getGraphic()!=null)
			result.getEdge(i).getGraphic().hideArrow();
		}
		return result;
	}
	public void copyTo(Graph result) {
		result.listEdges.clear();
		result.listVertexs.clear();
		result.listAdj.clear();
		
		int i;
		for(i=0;i<listVertexs.size();i++) {
			result.addVertex(listVertexs.get(i));
		}
		for(i=0;i<listEdges.size();i++) {
			result.addEdge(listEdges.get(i));
		}
		
	}
	//InitGraph
	public void initGraph() {
		for(int i = 0;i<listVertexs.size();i++) {
			listVertexs.get(i).setId(i);
		}
	}
	
	public void initGraph(AnchorPane parent) {
		parent.getChildren().clear();
		int i;
		for(i = 0;i<listVertexs.size();i++) {
			listVertexs.get(i).setId(i);
			
		}
		for(i=0;i<listEdges.size();i++) {
			if(isDirected()) {
			listEdges.get(i).getGraphic().changeTo(0);
			}else {
				listEdges.get(i).getGraphic().changeTo(1);
			}
			parent.getChildren().add(listEdges.get(i).getGraphic());
		}
		for(i = 0;i<listVertexs.size();i++) {
			listVertexs.get(i).getGraphic().changeTo(0);
			parent.getChildren().add(listVertexs.get(i).getGraphic());
		}
	}
	public void loadGraph(AnchorPane parent) {
		parent.getChildren().clear();
		int i;
		for(i=0;i<listEdges.size();i++) {
			parent.getChildren().add(listEdges.get(i).getGraphic());
		}
		for(i = 0;i<listVertexs.size();i++) {
			parent.getChildren().add(listVertexs.get(i).getGraphic());
		}
	}
	
}
