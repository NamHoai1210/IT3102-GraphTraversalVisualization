package project.graph.model.algorithm.command;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PseudoCode{
	private String content;
	private Label graphic;
	public PseudoCode(String content) {
		this.content=content;
		graphic=new Label(content);
		graphic.setPrefWidth(360);
		graphic.setWrapText(true);
		graphic.setPadding(new Insets(5,5,5,5));
	}
	public String getContent() {
		return content;
	}
	public Label getGraphic() {
		return graphic;
	}
	public void changeTo(int status) {
		switch (status) {
		case 0:
			graphic.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255, 0), new CornerRadii(0), new Insets(0))));
			break;
		case 1:
			graphic.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0, 0.2), new CornerRadii(0), new Insets(0))));
			break;
		default:
			break;
		}
	}
}
