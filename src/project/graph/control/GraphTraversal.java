package project.graph.control;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import project.graph.model.algorithm.BFS;
import project.graph.model.algorithm.Bipartite;
import project.graph.model.algorithm.Kosajaru;
import project.graph.model.graph.Graph;

public class GraphTraversal implements Initializable{
	Graph graph;
	Graph reverseGraph;
	Graph unDirectedGraph;
	Scene extraScene;
	Timeline timeline=null;
	Context context;
	@FXML
	ComboBox<String> comboBox;
	@FXML
	Label label;
	@FXML
	AnchorPane root1;
	@FXML
	Button stop;
	@FXML
	Button run;
	@FXML
	Button	replay;
	@FXML
	Slider slider;
	@FXML
	Button doStep;
	@FXML
	Button undoStep;
	@FXML
	GridPane controlPane;
	@FXML
	Label title;
	@FXML
	AnchorPane codePane;
	@FXML
	VBox codeBox;
	@FXML
	StackPane stackPane;
	@FXML
	Button codeShow;
	@FXML
	Button cc;
	@FXML
	Button doAll;
	@FXML
	MenuButton speedButton;
	@FXML
	RadioMenuItem standard;
	@FXML
	AnchorPane paraPane;
	AnchorPane createPane;
	double rate;
	boolean isCodeShowed;
	boolean isCCClicked;
	RadioMenuItem currentSpeed=null;
	Rectangle cover = new Rectangle(800,640);
	Rectangle background;
	TextInputDialog dialog;
	String menu;
	int startPoint;
	ObservableList<String> list = FXCollections.observableArrayList("Create Graph","Kosajaru Algorithm","Bipartite Graph Checker for BFS","BFS Algorithm");
	public String getMenu() {
		return menu;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
		paraPane.getChildren().clear();
		graph.initGraph(paraPane);
		paraPane.getChildren().add(cover);
		
	}
	public void setExtraScene(Scene extraScene) {
		this.extraScene = extraScene;
	}
	public void setCreatePane(AnchorPane pane) {
		this.createPane = pane;
	}
	public void setBackGround(Rectangle background) {
		this.background = background;
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		comboBox.setItems(list);
		AnchorPane.setTopAnchor(cover, 0.0);
		AnchorPane.setLeftAnchor(cover, 0.0);
		cover.setFill(Color.TRANSPARENT);
		timeline = new Timeline();
		label.setVisible(false);
		isCCClicked = false;
		controlPane.setVisible(false);
		codePane.setVisible(false);
		codeShow.setVisible(false);
		isCodeShowed = false;
		currentSpeed = standard;
		currentSpeed.setSelected(true);
		speedButton.setText(currentSpeed.getText()+"x");
		rate = Double.parseDouble(currentSpeed.getText())*1.5;
		paraPane.toFront();
		context = new Context();
		context.setGUI(slider, codeBox,label);
		dialog = new TextInputDialog("0");
		startPoint=0;
		dialog.setTitle("Breadth First Search");
		dialog.setHeaderText(null);
		dialog.setContentText("set start Vertex:");
	}
	public AnchorPane getParaPane() {
		return paraPane;
	}
	public void clickCC(MouseEvent evt) {
		if(isCCClicked==false) {
		label.setVisible(true);
		isCCClicked=true;}
		else {
			label.setVisible(false);
			isCCClicked=false;
		}
	}
	public void comboBoxChange(ActionEvent event) {
		int i;
		menu = comboBox.getValue();
		for(i=0;i<list.size();i++) {
			if(menu.equals(list.get(i))) break;
		}
		slider.setValue(0);
		switch (i) {
		case 0:
			Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			graph.initGraph(createPane);
			createPane.getChildren().add(background);
			background.toBack();
			primaryStage.setScene(extraScene);
			title.setText("Graph Traversal: Create Graph");
			codeShow.setVisible(false);
			paraPane.toFront();
			paraPane.setVisible(true);
			controlPane.setVisible(false);
			label.setVisible(false);
			if(isCodeShowed==true){
			codePane.setVisible(false);
			stackPane.setLayoutX(200);
			label.setLayoutX(300);
			controlPane.setPrefWidth(1200);
			isCodeShowed = false;
		}
			clear(root1);
			break;
		case 1:
			Kosajaru kosajaru = new Kosajaru(graph, root1);
			context.setUpAlgorithm(kosajaru);
			title.setText("Graph Traversal: Kosajaru Algorithm");
			init(root1,graph);
			codeShow.setVisible(true);
			isCCClicked = false;
			timeline.setRate(rate);
			timeline.setOnFinished(evt ->{
				context.setPlaying(false);
				showButton(replay);
			});
			controlPane.setVisible(true);
			showButton(replay);
			clear(paraPane);
			break;
		case 2:
			unDirectedGraph=graph.convertToUndirectedGraph();
			Bipartite bipartite = new Bipartite(unDirectedGraph, root1);
			context.setUpAlgorithm(bipartite);
			title.setText("Graph Traversal: Bipartite Graph Checker for BFS");
			init(root1,unDirectedGraph);
			codeShow.setVisible(true);
			isCCClicked = false;
			timeline.setRate(rate);
			timeline.setOnFinished(evt ->{
				context.setPlaying(false);
				showButton(replay);
			});
			controlPane.setVisible(true);
			showButton(replay);
			clear(paraPane);
			break;
		case 3:
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> {
				startPoint = Integer.parseInt(name);
			});
			BFS bfs= new BFS(graph, root1, startPoint);
			context.setUpAlgorithm(bfs);
			title.setText("Graph Traversal: Breadth First Search");
			init(root1,graph);
			codeShow.setVisible(true);
			isCCClicked = false;
			timeline.setRate(rate);
			timeline.setOnFinished(evt ->{
				context.setPlaying(false);
				showButton(replay);
			});
			controlPane.setVisible(true);
			showButton(replay);
			clear(paraPane);
			break;
		default:
			break;
		}
	}
	public void mouseClickedBtn(MouseEvent event) {
		//timeline = new Timeline();
		timeline.getKeyFrames().add(context.play());
		timeline.setCycleCount(context.timelineCycleCount());
		showButton(stop);
		//System.out.println(status+ " :"+ timeline.getCurrentTime().toSeconds());
		timeline.play();
		context.setPlaying(true);
		//timeline.play();
		
		
	}
	public void replay(MouseEvent event) {
		//init(root1,graph);
		label.setText("");
		if(comboBox.getValue().equals("Bipartite Graph Checker for BFS")) {
			unDirectedGraph.initGraph(root1);
		}
		else {
			graph.initGraph(root1);
		}
		context.setStatus(0);
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(context.play());
		timeline.setCycleCount(context.timelineCycleCount());
		timeline.setRate(rate);
		showButton(stop);
		timeline.playFromStart();
		context.setPlaying(true);
		
	}
	public void init(AnchorPane root,Graph variant) {
		label.setText("");
		root.toFront();
		variant.initGraph(root);
		root.getChildren().add(cover);
		cover.toFront();
		root.setVisible(true);
		
	}
	public void clear(AnchorPane root) {
		root.getChildren().clear();
		root.setVisible(false);
	}
	public void doStep(MouseEvent event) {
		if(context.isPlaying()) stop(null);
		if(context.getStatus()==0) {
			showButton(run);
		}
		context.playOne();
		if(context.getStatus()>=context.getNumOfSteps()-1) {
			showButton(replay);
		}
	}
	public void undoStep(MouseEvent event) {
		if(context.isPlaying()) stop(null);
		if(context.getStatus() == context.getNumOfSteps()-1) {
			showButton(run);
		}
		context.returnOne();
	}
	public void showCode(MouseEvent event) {
		if(isCodeShowed==false) {
		codePane.setVisible(true);
		stackPane.setLayoutX(20);
		label.setLayoutX(120);
		controlPane.setPrefWidth(840);
		isCodeShowed = true;
		}else {
			codePane.setVisible(false);
			stackPane.setLayoutX(200);
			label.setLayoutX(300);
			controlPane.setPrefWidth(1200);
			isCodeShowed = false;
		}
	}
	public void doAll(MouseEvent event) {
		if(context.isPlaying()) stop(null);
		context.playAll();
		showButton(replay);
	}
	public void stop(MouseEvent event){
		timeline.stop();
		timeline.getKeyFrames().clear();
		showButton(run);
		context.setPlaying(false);
	}
	
	public void onSpeedSelected(ActionEvent event) {
		currentSpeed.setSelected(false);
		currentSpeed = ((RadioMenuItem)event.getSource());
		currentSpeed.setSelected(true);
		speedButton.setText(currentSpeed.getText()+"x");
		rate = Double.parseDouble(currentSpeed.getText())*1.5;
		timeline.setRate(rate);
		
	}
	//Choose show Button replay, play or stop
	public void showButton(Button button) {
		run.setVisible(false);
		stop.setVisible(false);
		replay.setVisible(false);
		button.setVisible(true);
		button.toFront();
	}
}
