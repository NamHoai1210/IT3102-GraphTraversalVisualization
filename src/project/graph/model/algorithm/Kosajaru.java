package project.graph.model.algorithm;
import java.util.Iterator;
import java.util.Stack;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import project.graph.model.algorithm.command.*;
import project.graph.model.graph.Graph;
import project.graph.model.graph.Vertex;

public class Kosajaru extends Algorithm{
	Graph reverseGraph;
	public Kosajaru(Graph g) {
		super(g);
	}
	public Kosajaru(Graph g, String filename) {
		super(g,filename);
	}
	
	public Kosajaru(Graph graph, String filename, AnchorPane root) {
		super(graph, filename, root);
	}
	public Kosajaru(Graph graph, AnchorPane root) {
		super(graph, root);
		String filename = "src/project/graph/model/algorithm/command/Kosajaru.txt";
		this.loadListPseudoCodes(filename);
		setCurrentCode(getPseudoCode(0));
	}
	void DFSUtil(Vertex v,boolean visited[],Graph g,CompositeStep step)
    {
        visited[g.getVertexIndex(v)] = true;
        step.addStep(new VertexDetail("Get", v));
        	//System.out.println(" -> Print vertex ("+v.getId() + ")");
  
        Vertex n;
        CompositeStep subStep = new CompositeStep(getPseudoCode(5).getContent());
        CompositeStep smallStep;
        Iterator<Vertex> i =g.getAdjList(v).iterator();
        while (i.hasNext())
        {
            n = i.next();
            subStep.addStep(new EdgeDetail("Try", g.getEdge(v, n) ));
            if (!visited[g.getVertexIndex(n)]){
            	//System.out.println(" -> Call DFSUtil(vertex "+n.getId()+", visited,graph)");
            	smallStep = new CompositeStep(getPseudoCode(4).getContent());
            	smallStep.addStep(new Detail(" -> Call DFSUtil(vertex "+n.getId()+", visited)"));
            	DFSUtil(n,visited,g,smallStep);
            	subStep.addStep(smallStep);}
            else subStep.addStep(new Detail(" -> "+n.getId()+" is visited, continue"));
        }
        step.addStep(subStep);
    }
	void fillOrder(Vertex v, boolean visited[], Stack<Vertex> stack,Graph g,CompositeStep step)
    {
        visited[g.getVertexIndex(v)] = true;
        step.addStep(new VertexDetail("Visit", v));
        //System.out.println("* Visit ("+v.getId()+")");
        CompositeStep subStep = new CompositeStep(getPseudoCode(1).getContent());
        CompositeStep smallStep;
        Iterator<Vertex> i = g.getAdjList(v).iterator();
        Vertex n;
        while (i.hasNext())
        {
             n = i.next();
             subStep.addStep(new EdgeDetail("Try", g.getEdge(v, n)));
            if(!visited[g.getVertexIndex(n)]) {
            	//System.out.println(" -> Call fillOrder(vertex "+n.getId()+", visited,stack,graph)");
            	smallStep = new CompositeStep(getPseudoCode(0).getContent());
            	smallStep.addStep(new Detail(" -> Call fillOrder(vertex "+n.getId()+", visited,stack)"));
                fillOrder(n, visited, stack,g,smallStep);
                subStep.addStep(smallStep);
                }
            else subStep.addStep(new Detail(" -> "+n.getId()+" is visited, continue"));
        }
  
        stack.push(v);
        smallStep = new CompositeStep(getPseudoCode(2).getContent());
        smallStep.addStep(new Detail(" -> Put vertex "+v.getId()+" to stack ->"+stack));
        subStep.addStep(smallStep);
        step.addStep(subStep);
        //System.out.println(" -> Put vertex "+v.getId()+" to stack ->"+stack);
        
    }
	@Override
	public void execute() {
		int number=0;
		initCompositeStep();
		int V = this.getGraph().getVertexsSize();
		Stack<Vertex> stack = new Stack<Vertex>();
		CompositeStep step = new CompositeStep("");
		CompositeStep subStep;
		//System.out.println("\n-Mark all the vertices as not visited (For first DFS)\n");
		this.addCommand(new Detail("<- Mark all the vertices as not visited (For first DFS)"));
        boolean visited[] = new boolean[V];
        for(int i = 0; i < V; i++) {
            visited[i] = false;}
        //System.out.println("-Start fill vertices in stack");
        Vertex n;
        for (int i = 0; i < V; i++) {
        	n = this.getGraph().getVertex(i);
            if (visited[i] == false) {
            	step = new CompositeStep(getPseudoCode(0).getContent());
            	step.addStep(new Detail(" -> Call fillOrder(vertex "+n.getId()+", visited,stack)"));
                fillOrder(n, visited, stack,this.getGraph(),step);
            }
        }
        this.addCommand(step);
        //System.out.println("-End fill vertices in stack\n");
        this.addCommand(new CompositeStep(getPseudoCode(3).getContent()));
        //System.out.println("-Create a reversed graph\n");
       
        reverseGraph= this.getGraph().getReverse();
		//System.out.println("\n-Mark all the vertices as not visited (For second DFS)\n");
		this.addCommand(new Detail("<- Mark all the vertices as not visited (For second DFS)"));
        for (int i = 0; i < V; i++)
            visited[i] = false;
        //System.out.println("-Process all vertices in order defined by Stack");
        step = new CompositeStep("");
        while (stack.empty() == false)
        {
            Vertex v = (Vertex)stack.pop();
            step.addStep(new Detail("<- Pop vertex "+v.getId()+" from stack ->"+stack));
            if (visited[reverseGraph.getVertexIndex(v)] == false)
            {
            	subStep = new CompositeStep(getPseudoCode(4).getContent());
            	subStep.addStep(new Detail(" -> Call DFSUtil(vertex "+v.getId()+", visited)"));
                DFSUtil(v, visited,reverseGraph,subStep);
                step.addStep(subStep);
                step.addStep(new CompositeStep(getPseudoCode(6).getContent()));
                number++;
                //System.out.println("-Complete to print a SCC\n");
            }else step.addStep(new Detail(" -> "+v.getId()+" is visited, continue"));
        }
        this.addCommand(step);
        this.addCommand(new Detail("In total, we have " +number+ " Strongly Connected Component(s) as seen above."));
        this.getCompositeStep().presentList(this.getListSteps());
        //this.getCompositeStep().presentConsole();
	}
	@Override
	public void doDisplay(int index,Label label,boolean isPlaying) {
		if(index>=getListStepSize()) return;
    	Step step = getStep(index);
		if(step instanceof CompositeStep) {
			getCurrentCode().changeTo(0);
			setCurrentCode(getPseudoCode(step.getContent()));
			if(getCurrentCode()!=null) {
			getCurrentCode().changeTo(1);
			}
			if(step.getContent().equals(getPseudoCode(3).getContent())){
				reverseGraph.initGraph(getRoot());
				addCover();
			}else if(step.getContent().equals(getPseudoCode(6).getContent())) {
				VertexDetail.setAnotherColor();
			}
		}else if(step instanceof Detail) {
			if(isPlaying) label.setText(((Detail)step).presentUI(1));
			else label.setText(((Detail)step).presentUI(0));
		}
		if(index == getListStepSize()-1) {
			returnGraph();
			addCover();
		}
	}
	@Override
	public void undoDisplay(int index,Label label) {
		if(index<0) return;
		if(index == getListStepSize()-1) {
			reverseGraph.loadGraph(getRoot());
			addCover();
		}
		Step step = getStep(index);
		if(step instanceof CompositeStep) {
			getCurrentCode().changeTo(0);
			for(int j=index-1;j>=0;j--) {
				Step tmp = getStep(j);
				if(tmp instanceof CompositeStep) {
					setCurrentCode(getPseudoCode(tmp.getContent()));
					if(getCurrentCode()!=null) {
					getCurrentCode().changeTo(1);
					}
					break;
				}
			}
			if(step.getContent().equals(getPseudoCode(3).getContent())){
				loadGraph();
			}
		}else if(step instanceof Detail) {
			((Detail)step).unPresentUI();
			if(index>0) {
				for(int j=index-1;j>=0;j--) {
					if(getStep(j)instanceof Detail) {
						label.setText(getStep(j).toString());
						break;
					}
				}
			}else {
				label.setText("");
			}
		}
		
	}
	public void loadGraph() {
		getRoot().getChildren().clear();
		int i;
		for(i=0;i<getGraph().getEdgeSize();i++) {
			getGraph().getEdge(i).getGraphic().changeTo(2);
			getRoot().getChildren().add(getGraph().getEdge(i).getGraphic());
		}
		for(i = 0;i<getGraph().getVertexsSize();i++) {
			getGraph().getVertex(i).getGraphic().changeTo(3);
			getRoot().getChildren().add(getGraph().getVertex(i).getGraphic());
		}
		addCover();
	}
	
	public void returnGraph() {
		getRoot().getChildren().clear();
		int i;
		for(i=0;i<getGraph().getEdgeSize();i++) {
			getGraph().getEdge(i).getGraphic().changeTo(0);
			getRoot().getChildren().add(getGraph().getEdge(i).getGraphic());
		}
		for(i = 0;i<getGraph().getVertexsSize();i++) {
			getRoot().getChildren().add(getGraph().getVertex(i).getGraphic());
		}
	}
	void addCover() {
		Rectangle rec = new Rectangle(800,640, Color.TRANSPARENT);
		AnchorPane.setTopAnchor(rec, 0.0);
		AnchorPane.setLeftAnchor(rec, 0.0);
		getRoot().getChildren().add(rec);
	}
}
