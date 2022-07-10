package project.graph.control;

import javafx.animation.KeyFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.graph.model.algorithm.Algorithm;

public class Context {
	private Algorithm algorithm;
	private int status;
	private Slider slider;
	private VBox codeBox;
	private Label label;
	private boolean isPlaying;
	private ChangeListener<Number> sliderChangeListener= new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, //
				Number oldValue, Number newValue) {
			int newInt = newValue.intValue();
			int oldInt = oldValue.intValue();
			if(newInt>oldInt) {
				for(int i=oldInt+1;i<=newInt;i++) {
					algorithm.doDisplay(i,label,isPlaying);
				}
			}else if(newInt<oldInt) {
				for(int i=oldInt;i>=newInt;i--) {
					algorithm.undoDisplay(i,label);
				}	
			}
			status = newInt;
		}
	};
	public Context() {
	}
	public Context(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	public void setUpAlgorithm(Algorithm alt){
		status =0;
		algorithm= alt;
		/*if(alt instanceof BFS) {
			System.out.print("-> BFS Algorithm ");
		}else if(alt instanceof Bipartite) {
			System.out.print("-> Bipartite Checker for BFS ");
		}else if(alt instanceof Kosajaru) {
			System.out.print("-> Kosajaru Algorithm ");
		}
		long start = System.nanoTime();*/
		algorithm.execute();
		/*long end = System.nanoTime();
        System.out.println("Execution time in nano seconds: "+(end-start));*/
		slider.setMax(algorithm.getListStepSize()-1);
		createCodeBox();
		isPlaying = false;
	}
	public void setGUI(Slider slider,VBox codeBox,Label label) {
		this.slider=slider;
		this.label = label;
		label.setText("");
		slider.setValue(0);
		slider.valueProperty().addListener(sliderChangeListener);
		this.codeBox=codeBox;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	public Slider getSlider() {
		return slider;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	public KeyFrame play() {
		KeyFrame kFrame =new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			private int i=status;
			@Override
		    public void handle(ActionEvent event) { 
				if(i>=algorithm.getListStepSize()) return;
				else if(i==0) algorithm.doDisplay(0,label, isPlaying);
				else getSlider().setValue(getStatus());
				status++;
				i++;
			}
		});
		return kFrame;
	}
	public void playOne() {
		status ++;
		if(status>=algorithm.getListStepSize()) {
			status = algorithm.getListStepSize()-1;
			return;
		}
		slider.setValue(status);
	}
	public void returnOne() {
		if(status==0) {
			return;}
		status--;
		slider.setValue(status);
	}
	public void playAll() {
		slider.setValue(algorithm.getListStepSize()-1);
		status = algorithm.getListStepSize()-1;
	}
	public void createCodeBox() {
		codeBox.getChildren().clear();
		for(int i=0;i<algorithm.getListPseudoCodeSize();i++) {
			codeBox.getChildren().add(algorithm.getPseudoCode(i).getGraphic());
		}
	}
	public int timelineCycleCount() {
		return algorithm.getListStepSize()-status;
	}
	public int getNumOfSteps() {
		return algorithm.getListStepSize();
	}
}
