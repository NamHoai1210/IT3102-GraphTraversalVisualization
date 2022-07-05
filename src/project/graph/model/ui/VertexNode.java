package project.graph.model.ui;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
public class VertexNode extends StackPane{
	private Circle circle = new Circle();
	private Text text = new Text();
	private boolean isClicked;
	private boolean isDraged;
	public boolean isDraged() {
		return isDraged;
	}
	public void setDraged(boolean isDraged) {
		this.isDraged = isDraged;
	}
	private double x;
	private double y;
	
	public VertexNode(double r,String t) {
		this.text.setText(t);;
		this.circle.setRadius(r);
		this.circle.setStrokeWidth(3);
		changeTo(0);
		this.getChildren().addAll(circle,text);
	}
	public Circle getCircle() {
		return circle;
	}
	public String getText() {
		return text.getText();
	}
	public boolean isClicked() {
		return isClicked;
	}
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	public void setText(String text) {
	this.text.setText(text);
	}
	
	public double getX() {
		return x;
	}
	public void setXY(double x,double y) {
		this.x = x;
		this.y = y;
	}
	public double getY() {
		return y;
	}
	public void changeTo(int version){
		switch (version) {
		case 0:
			circle.setStroke(Color.GRAY);
			circle.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
			isClicked=false;
			isDraged = false;
			break;
		case 1:
			circle.setStroke(Color.SALMON);
			circle.setFill(Color.SALMON);
			text.setFill(Color.WHITE);
			isClicked=true;
			break;
		case 2:
			circle.setStroke(Color.SALMON);
			circle.setFill(Color.SALMON);
			text.setFill(Color.WHITE);
			break;
		case 3:
			circle.setStroke(Color.SALMON);
			circle.setFill(Color.WHITE);
			text.setFill(Color.SALMON);
			break;
		case 4:
			circle.setStroke(Color.MEDIUMAQUAMARINE);
			circle.setFill(Color.WHITE);
			text.setFill(Color.MEDIUMAQUAMARINE);
			break;
		default:
			break;
		}
	}
	public void changeToCustom(Color color) {
		circle.setStroke(color);
		circle.setFill(Color.WHITE);
		text.setFill(color);

	}
}
