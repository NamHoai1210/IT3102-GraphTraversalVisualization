package project.graph.model.algorithm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import project.graph.model.Graph;
import project.graph.model.algorithm.command.Step;
import project.graph.model.algorithm.command.CompositeStep;
import project.graph.model.algorithm.command.PseudoCode;
public abstract class Algorithm {
	private Graph graph;
	private CompositeStep compositeStep; 
	private List<PseudoCode> listPseudoCodes = new ArrayList<PseudoCode>();
	private List<Step> listSteps = new ArrayList<Step>();
	private AnchorPane root;
	private PseudoCode currentCode;
	public Algorithm() {}
	public Algorithm(Graph graph) {
		this.graph = graph;
	}
	public Algorithm(Graph graph, String filename) {
		this.graph = graph;
		this.loadListPseudoCodes(filename);
	}
	public Algorithm(Graph graph, String filename,AnchorPane root) {
		compositeStep= new CompositeStep("");
		this.graph = graph;
		this.loadListPseudoCodes(filename);
		this.root = root;
	}
	public Algorithm(Graph graph,AnchorPane root) {
		compositeStep= new CompositeStep("");
		this.graph = graph;
		this.root = root;
	}
	public Graph getGraph() {
		return graph;
	}
	public PseudoCode getPseudoCode(int index) {
		return listPseudoCodes.get(index);
	}
	public PseudoCode getPseudoCode(String content) {
		for(int i=0;i<listPseudoCodes.size();i++) {
			if(listPseudoCodes.get(i).getContent().equals(content)) return listPseudoCodes.get(i);
		}
		return null;
	}
	
	public PseudoCode getCurrentCode() {
		return currentCode;
	}
	
	public void setCurrentCode(PseudoCode currentCode) {
		this.currentCode = currentCode;
	}
	public void loadListPseudoCodes(String filename) {
		try {
		      File myObj = new File(filename);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        listPseudoCodes.add(new PseudoCode(data));
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public int getIndexOfPseudoCode(PseudoCode pc){
		return listPseudoCodes.indexOf(pc);
	}
	public void addCommand(Step st) {
		compositeStep.addStep(st);
	}
	public CompositeStep getCompositeStep() {
		return compositeStep;
	}
	public Step getStep(int index) {
		return listSteps.get(index);
	}
	public int getListStepSize() {
		return listSteps.size();
	}
	
	public List<Step> getListSteps() {
		return listSteps;
	}
	
	public AnchorPane getRoot() {
		return root;
	}
	public int getListPseudoCodeSize() {
		return listPseudoCodes.size();
	}
	public void initCompositeStep() {
		compositeStep.clear();
		listSteps.clear();
	}
	public abstract void execute();
	public abstract void doDisplay(int index,Label label,boolean isPlaying);
	public abstract void undoDisplay(int index,Label label);
	
}
