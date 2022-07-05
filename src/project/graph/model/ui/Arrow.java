package project.graph.model.ui;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Arrow extends Group {

    private final Line line;
    private  Line arrow1;
    private  Line arrow2;
    //private int presentTime=0;
    DoubleProperty signalPosition = new SimpleDoubleProperty(0);
    Timeline animation = new Timeline(
            new KeyFrame(Duration.ZERO,       new KeyValue(signalPosition, 0)),
            new KeyFrame(Duration.seconds(1), new KeyValue(signalPosition, 1))
    );
    boolean isClicked;
    boolean isTried;
    //private  Line tmp;
    public Arrow(double sx, double sy, double ex, double ey) {
        this(new Line(sx,sy,ex,ey), new Line(), new Line());
    }
    
    private static final double arrowLength = 10;
    private static final double arrowWidth = 7;

    private Arrow(Line line, Line arrow1, Line arrow2) {
        super(line, arrow1, arrow2);
        this.line = line;
        this.arrow1=arrow1;
        this.arrow2=arrow2;
        line.setStrokeWidth(3);
        arrow1.setStrokeWidth(3);
        arrow2.setStrokeWidth(3);
        changeTo(0);
        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();
            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;
                
                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }
    public boolean isClicked() {
    	return isClicked;
    }
    public boolean isTried() {
    	return isTried;
    }
    // start/end properties

	public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }
    public final void setEnd(double x, double y) {
    	line.setEndX(x);
    	line.setEndY(y);
    }
    public final void setStart(double x, double y) {
    line.setStartX(x);
    line.setStartY(y);
    }
    public void hideArrow() {
    	arrow1.setStroke(Color.TRANSPARENT);
		arrow2.setStroke(Color.TRANSPARENT);
    }
    public Timeline getAnimation() {
		return animation;
	}

	public void changeTo(int status) {
    	//double x,y;
    	switch (status) {
		case 0:
			line.strokeProperty().unbind();
			line.setStroke(Color.DARKGRAY);
			arrow1.setStroke(Color.DARKGRAY);
			arrow2.setStroke(Color.DARKGRAY);
			isClicked = false;
			isTried=false;
			break;
		case 1:
			line.strokeProperty().unbind();
			line.setStroke(Color.DARKGRAY);
			arrow1.setStroke(Color.TRANSPARENT);
			arrow2.setStroke(Color.TRANSPARENT);
			isClicked = false;
			isTried=false;
			break;
		case 2:
			line.strokeProperty().unbind();
			line.setStroke(Color.ORANGE);
			arrow1.setStroke(Color.ORANGE);
			arrow2.setStroke(Color.ORANGE);
			isClicked = true;
			isTried=true;
			break;
		case 3:
			line.strokeProperty().unbind();
			line.setStroke(Color.ORANGE);
			isClicked = true;
			isTried=false;
			break;
		default:
			break;
		}
    }
	public void effect(int time) {
		//presentTime = time;
		if(line.getEndX()<line.getStartX()) {
	        line.strokeProperty().bind(Bindings.createObjectBinding(() -> 
	        new LinearGradient(1, 0, 0, 0, true, CycleMethod.NO_CYCLE, 
	                new Stop(0, Color.SALMON),
	                new Stop(signalPosition.get(), Color.SALMON),
	                new Stop(signalPosition.get(), Color.DARKGRAY),
	                new Stop(1, Color.DARKGRAY)), 
	        signalPosition));}
	        else {
	        	line.strokeProperty().bind(Bindings.createObjectBinding(() -> 
	            new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, 
	                    new Stop(0, Color.SALMON),
	                    new Stop(signalPosition.get(), Color.SALMON),
	                    new Stop(signalPosition.get(), Color.DARKGRAY),
	                    new Stop(1, Color.DARKGRAY)), 
	            signalPosition));
	        }
		isTried = true;
	}
}

