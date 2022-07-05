package project.graph.traversal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.graph.control.GraphTraversal;
import project.graph.control.GraphCreation;
import project.graph.model.Graph;

public class Client extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Graph graph = new Graph();
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../control/GraphDisplay.fxml"));
		Parent root = loader.load();
		GraphCreation creation = loader.getController();
		FXMLLoader anotherLoader = new FXMLLoader(this.getClass().getResource("../control/GraphCreation.fxml"));
		Parent anotherRoot = anotherLoader.load();
		GraphTraversal traversal = anotherLoader.getController();
		Scene mainScene = new Scene(anotherRoot);
		Scene extraScene = new Scene(root);
		extraScene.setOnKeyPressed(creation.getKeyPressed());
		creation.setGraph(graph);
		traversal.setGraph(graph);
		creation.setParaPane(traversal.getParaPane());
		traversal.setExtraScene(extraScene);
		creation.setMainScene(mainScene);
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}
}
