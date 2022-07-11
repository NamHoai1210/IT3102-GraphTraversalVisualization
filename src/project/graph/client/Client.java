package project.graph.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.graph.control.GraphTraversal;
import project.graph.control.GraphCreation;

public class Client extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../control/GraphDisplay.fxml"));
		Parent root = loader.load();
		GraphCreation creation = loader.getController();
		FXMLLoader anotherLoader = new FXMLLoader(this.getClass().getResource("../control/GraphCreation.fxml"));
		Parent anotherRoot = anotherLoader.load();
		GraphTraversal traversal = anotherLoader.getController();
		Scene mainScene = new Scene(anotherRoot);
		Scene extraScene = new Scene(root);
		extraScene.setOnKeyPressed(creation.getKeyPressed());
		traversal.setGraph(creation.getGraph());
		creation.setParaPane(traversal.getParaPane());
		traversal.setCreatePane(creation.getRoot());
		traversal.setBackGround(creation.getBackGround());
		traversal.setExtraScene(extraScene);
		creation.setMainScene(mainScene);
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}
}
