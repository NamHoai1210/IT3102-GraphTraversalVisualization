package project.graph.model.algorithm;

import project.graph.model.algorithm.command.CompositeStep;
import project.graph.model.algorithm.command.Detail;
import project.graph.model.algorithm.command.EdgeDetail;
import project.graph.model.algorithm.command.Step;
import project.graph.model.algorithm.command.VertexDetail;
import project.graph.model.graph.Graph;
import project.graph.model.graph.Vertex;

import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class BFS extends Algorithm{
	int startPoint;
	public BFS(Graph graph) {
		super(graph);
		String filename = "src/project/graph/model/algorithm/command/BFS.txt";
		this.loadListPseudoCodes(filename);
	}
	public BFS() {
		super();
	}
	public BFS(Graph graph, AnchorPane root,int startPoint) {
		super(graph, root);
		String filename = "src/project/graph/model/algorithm/command/BFS.txt";
		this.loadListPseudoCodes(filename);
		if(startPoint<0) startPoint = 0;
		else if (startPoint> getGraph().getVertexsSize()-1) startPoint =getGraph().getVertexsSize()-1;
		this.startPoint = startPoint;
		setCurrentCode(getPseudoCode(0));
	}
	public BFS(Graph graph, String filename, AnchorPane root,int startPoint) {
		super(graph, filename, root);
		if(startPoint<0) startPoint = 0;
		else if (startPoint> getGraph().getVertexsSize()-1) startPoint =getGraph().getVertexsSize()-1;
		this.startPoint = startPoint;
	}
	public BFS(Graph graph, String filename,int startPoint) {
		super(graph, filename);
		if(startPoint<0) startPoint = 0;
		else if (startPoint> getGraph().getVertexsSize()-1) startPoint =getGraph().getVertexsSize()-1;
		this.startPoint = startPoint;
	}
	
	public int getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(int startPoint) {
		if(startPoint<0) startPoint = 0;
		else if (startPoint> getGraph().getVertexsSize()-1) startPoint =getGraph().getVertexsSize()-1;
		this.startPoint = startPoint;
	}
	@Override
	public void execute() {
		initCompositeStep();
		CompositeStep subStep,smallStep,microStep;
		int V = getGraph().getVertexsSize();
		// Mark all the vertices as not visited(By default
        // set as false)
        boolean visited[] = new boolean[V];
        this.addCommand(new Detail("<- Mark all the vertices as not visited"));
        // Create a queue for BFS
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        // Mark the current node as visited and enqueue it
        Vertex s,n;
        visited[startPoint]=true;
        s=getGraph().getVertex(startPoint);
        queue.add(s);
        
        subStep = new CompositeStep(getPseudoCode(0).getContent());
        subStep.addStep(new Detail("Call BFS("+s.getId()+"), set Queue: "+queue));
        this.addCommand(subStep);
        while (queue.size() != 0)
        {
        	subStep = new CompositeStep(getPseudoCode(1).getContent());
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            subStep.addStep(new Detail("<- Dequeue vetex "+s.getId()+" from queue: "+queue));
            subStep.addStep(new VertexDetail("Visit", s));
 
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Vertex> i = getGraph().getAdjList(getGraph().getVertexIndex(s)).iterator();
            while (i.hasNext())
            {
            	smallStep = new CompositeStep(getPseudoCode(2).getContent());
                n = i.next();
                smallStep.addStep(new EdgeDetail("Try", getGraph().getEdge(s, n)));
                int u = getGraph().getVertexIndex(n);
                if (!visited[u])
                {
                	microStep = new CompositeStep(getPseudoCode(3).getContent());
                	microStep.addStep(new Detail("Vertex "+n.getId()+" is free, push to queue: "+queue));
                    visited[u] = true;
                    queue.add(n);
                }else {
                	microStep = new CompositeStep(getPseudoCode(4).getContent());
                	microStep.addStep(new Detail("Vertex "+n.getId()+" is visited, continue"));
                }
                smallStep.addStep(microStep);
                subStep.addStep(smallStep);
            }
            this.addCommand(subStep);
        }
        this.addCommand(new Detail("BFS("+startPoint+") is completed"));
        this.getCompositeStep().presentList(getListSteps());
	}
    @Override
    public void doDisplay(int index, Label label, boolean isPlaying) {
    	if(index>=getListStepSize()) return;
    	Step step = getStep(index);
		if(step instanceof CompositeStep) {
			getCurrentCode().changeTo(0);
			setCurrentCode(getPseudoCode(step.getContent()));
			if(getCurrentCode()!=null) {
			getCurrentCode().changeTo(1);
			}
		}else if(step instanceof Detail) {
			if(isPlaying) label.setText(((Detail)step).presentUI(1));
			else label.setText(((Detail)step).presentUI(0));
		}
    }
    @Override
    public void undoDisplay(int index, Label label) {
    	if(index<0) return;
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
}
