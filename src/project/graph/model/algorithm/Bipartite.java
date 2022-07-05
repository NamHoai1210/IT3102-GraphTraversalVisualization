package project.graph.model.algorithm;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import project.graph.model.Edge;
import project.graph.model.Graph;
import project.graph.model.Vertex;
import project.graph.model.algorithm.command.CompositeStep;
import project.graph.model.algorithm.command.Detail;
import project.graph.model.algorithm.command.EdgeDetail;
import project.graph.model.algorithm.command.Step;
import project.graph.model.algorithm.command.VertexDetail;
public class Bipartite extends Algorithm{
	public Bipartite(Graph graph){
        super(graph);
        this.loadListPseudoCodes("src/project/graph/model/algorithm/command/BipartiteCheck.txt");
    }
	
	public Bipartite(Graph g, String filename) {
		super(g,filename);
	}
	
	public Bipartite(Graph graph, String filename, AnchorPane root) {
		super(graph, filename, root);
	}
	public Bipartite(Graph graph, AnchorPane root) {
		super(graph, root);
		String filename = "src/project/graph/model/algorithm/command/BipartiteCheck.txt";
		this.loadListPseudoCodes(filename);
		setCurrentCode(getPseudoCode(0));
	}

	boolean isBipartiteUtil(int src, int colorArr[],CompositeStep step) {
		CompositeStep subStep;
		Iterator<Vertex> iterator;
		int v,u;
        // Create a color array to store
        // colors assigned to all vertices.
        // Vertex number is used as index
        // in this array. The value '-1'
        // of colorArr[i] is used to indicate
        // that no color is assigned
        // to vertex 'i'. The value 1 is
        // used to indicate first color
        // is assigned and value 0 indicates
        // second color is assigned.
        // Assign first color to source
        colorArr[src] = 1;
        step.addStep(new VertexDetail("Assign",getGraph().getVertex(src)));
        // Create a queue (FIFO) of vertex numbers
        // and enqueue source vertex for BFS traversal
        LinkedList<Vertex>q = new LinkedList<Vertex>();
        subStep= new CompositeStep(getPseudoCode(1).getContent());
        q.add(getGraph().getVertex(src));
        subStep.addStep(new Detail("<- Queue = "+q));
        step.addStep(subStep);
        // Run while there are vertices in queue (Similar to BFS)
        while (q.size() != 0)
        {
        	subStep = new CompositeStep(getPseudoCode(2).getContent());
            // Dequeue a vertex from queue
            Vertex currentVertex = q.poll();
            subStep.addStep(new Detail("<- Dequeue vertex "+currentVertex.getId()+" from queue: "+q));
            u=getGraph().getVertexIndex(currentVertex);
            // Return false if there is a self-loop
            if (getGraph().getEdge(currentVertex, currentVertex)!=null) {
            	subStep.addStep(new Detail("<- There is a self-loop, exit the algorithm"));
            	step.addStep(subStep);
                return false;
             }
            CompositeStep smallStep,microStep;
            // Find all non-colored adjacent vertices
            iterator = getGraph().getAdjList(u).iterator();
            Vertex n;
            while(iterator.hasNext()) {
            	smallStep= new CompositeStep(getPseudoCode(3).getContent());
            	n=iterator.next();
            	smallStep.addStep(new EdgeDetail("Try", getGraph().getEdge(currentVertex, n)));
            	v= getGraph().getVertexIndex(n);
            	if(colorArr[v]==-1) {
            		microStep = new CompositeStep(getPseudoCode(4).getContent());
            		colorArr[v] = 1-colorArr[u];
            		if(colorArr[v]==1) microStep.addStep(new VertexDetail("Assign",n));
            		else microStep.addStep(new VertexDetail("Assign another",n));
            		q.add(n);
            		microStep.addStep(new Detail("<- Enqueue vertex "+n.getId()+" to queue: "+q));
            		smallStep.addStep(microStep);
            	}else if(colorArr[v]==colorArr[u]) {
            		microStep = new CompositeStep(getPseudoCode(5).getContent());
            		microStep.addStep(new Detail("<- Vertex " +currentVertex.getId()+" and " +n.getId()+ " v have the same color, exit"));
            		smallStep.addStep(microStep);
            		subStep.addStep(smallStep);
            		step.addStep(subStep);
            		return false;
            	}else {
            		smallStep.addStep(new Detail("<- Vertex "+currentVertex.getId() +" and vertex "+n.getId()+ " (already visited) have different color, continue."));
            	}
            	subStep.addStep(smallStep);
            }
            step.addStep(subStep);
        }
        
        // If we reach here, then all adjacent vertices can
        // be colored with alternate color
        return true;
	}
    @Override
    public void execute(){
    	initCompositeStep();
		int V = getGraph().getVertexsSize();
		CompositeStep step = new CompositeStep("");
		 int colorArr[] = new int[V];
	        for (int i=0; i<V; i++)
	            colorArr[i] = -1;
		this.addCommand(new Detail("<- Mark all the vertices as colorless"));
		for (int i = 0; i < V; i++) {
	        if (colorArr[i] == -1) {
	        	step = new CompositeStep(getPseudoCode(0).getContent());
	            if (isBipartiteUtil(i,colorArr,step) == false) {
	            	this.addCommand(step);
	            	this.addCommand(new Detail("This isn't a bipartite graph!"));
	            	this.getCompositeStep().presentList(this.getListSteps());
	                return;
	            }
	        this.addCommand(step);
		}
     }
		this.addCommand(new Detail("This is a bipartite graph!"));
		this.getCompositeStep().presentList(this.getListSteps());
    }
    @Override
    public void doDisplay(int index,Label label, boolean isPlaying) {
    	if(index>=getListStepSize()) return;
    	Step step = getStep(index);
		if(step instanceof CompositeStep) {
			getCurrentCode().changeTo(0);
			setCurrentCode(getPseudoCode(step.getContent()));
			if(getCurrentCode()!=null) {
			getCurrentCode().changeTo(1);
			}
		}else if(step instanceof Detail) {
			if(step instanceof EdgeDetail) {
				Edge edge = ((EdgeDetail)step).getEdge();
				getGraph().getEdge(edge.getTo(), edge.getFrom()).getGraphic().setVisible(false);;
				edge.getGraphic().setVisible(true);
			}
			if(isPlaying) label.setText(((Detail)step).presentUI(1));
			else label.setText(((Detail)step).presentUI(0));
		}
    }
    @Override
    public void undoDisplay(int index,Label label) {
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
			if(step instanceof EdgeDetail) {
				Edge edge = ((EdgeDetail)step).getEdge();
				getGraph().getEdge(edge.getTo(), edge.getFrom()).getGraphic().setVisible(true);
				edge.getGraphic().setVisible(false);
			}
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
