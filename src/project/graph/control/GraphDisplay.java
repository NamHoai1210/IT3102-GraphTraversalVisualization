package project.graph.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import project.graph.model.Edge;
import project.graph.model.Graph;
import project.graph.model.Vertex;
import project.graph.model.ui.Arrow;
import project.graph.model.ui.VertexNode;

public class GraphDisplay implements Initializable{
	private Graph graph=new Graph();
	private Arrow arrow;
	Scene mainScene;
	public static final double RADIUS = 20.0f;
	int id=0;
	
	private Rectangle background = new Rectangle(800, 640, Color.TRANSPARENT);;
	@FXML
	private Label guide;
	@FXML
	private AnchorPane root;
	private AnchorPane paraPane;
	private EventHandler<MouseEvent> mouseMoved = new EventHandler<MouseEvent>() {
    	@Override
    	 public void handle(MouseEvent evt) {
    		Double x = evt.getX()-arrow.getStartX();
			Double y = evt.getY()-arrow.getStartY();
			Double xy = Math.sqrt(x*x + y*y);
			x = (x/xy)*RADIUS;
			y = (y/xy)*RADIUS;
    		arrow.setEnd(evt.getX()-x/2, evt.getY()-y/2);
    	}
	};
	private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent k) {
			if(k.getCode()==KeyCode.DELETE) {
				int i;
				for(i=0;i<graph.getVertexsSize();i++) {
					if(graph.getVertex(i).getGraphic().isClicked()) {
						root.getChildren().remove(graph.getVertex(i).getGraphic());
						List<Edge> delEdges = graph.delVertex(i);
					//System.out.println(delEdges);
						for(int j= 0;j<delEdges.size();j++) {
							root.getChildren().remove(delEdges.get(j).getGraphic());
						}
						break;
					}
				}
				if(i==graph.getVertexsSize()) {
				for(i=0;i<graph.getEdgeSize();i++) {
					if(graph.getEdge(i).getGraphic().isClicked()) {
						root.getChildren().remove(graph.getEdge(i).getGraphic());
						graph.delEdge(i);
						break;
					}
				}}
				
			}
		}
	};
	private EventHandler<MouseEvent> arrowMouseClicked = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent evt) {
			Arrow tmp =((Arrow) evt.getSource());
			if(tmp.isClicked()) {tmp.changeTo(0);return;}
			int i;
			for(i=0;i<graph.getVertexsSize();i++) {
				if(graph.getVertex(i).getGraphic().isClicked()) {
					graph.getVertex(i).getGraphic().changeTo(0);
					break;}
			}
			if(i==graph.getVertexsSize()) {
			for(i=0;i<graph.getEdgeSize();i++) {
				if(graph.getEdge(i).getGraphic().isClicked()) {
					graph.getEdge(i).getGraphic().changeTo(0);
					break;}
			}}
			 tmp.changeTo(2);
			//arrow.changeTo(2);
		};
	};
	private EventHandler<MouseEvent> vertexMouseClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			VertexNode v = ((VertexNode)e.getSource());
			Vertex vertex = graph.getAVertex(Integer.parseInt(v.getText()));
			if(!vertex.getGraphic().isDraged() && vertex.getGraphic().isClicked()) {
				vertex.getGraphic().changeTo(0);
			}else {
				int i;
				for(i=0;i<graph.getVertexsSize();i++) {
					if(graph.getVertex(i).getGraphic().equals(vertex.getGraphic())) continue;
					if(graph.getVertex(i).getGraphic().isClicked()) break;
				}
				
				if(i==graph.getVertexsSize()) {
					for(i=0;i<graph.getEdgeSize();i++) {
						if(graph.getEdge(i).getGraphic().isClicked()) {
							graph.getEdge(i).getGraphic().changeTo(0);
							break;}
					}
					vertex.getGraphic().changeTo(1);
				}
				else {
					if(graph.getVertex(i).getGraphic().isDraged()) {
					Edge edge = graph.getEdge(vertex,graph.getVertex(i));
					Double x = vertex.getGraphic().getX()-arrow.getStartX();
					Double y = vertex.getGraphic().getY()-arrow.getStartY();
					Double xy = Math.sqrt(x*x + y*y);
					if(edge==null) {
					x = (x/xy)*RADIUS;
					y = (y/xy)*RADIUS;}
					else {
						Double angle = Math.asin(Math.abs(x)/xy);
						angle = 45.0-angle;
						if(x<0) x = Math.cos(angle)*RADIUS*-1;
						else x = Math.cos(angle)*RADIUS;
						if(y<0) y = Math.sin(angle)*RADIUS*-1;
						else y = Math.sin(angle)*RADIUS;
						edge.getGraphic().setEnd(graph.getVertex(i).getGraphic().getX()+x, graph.getVertex(i).getGraphic().getY()+y);
					}
					arrow.setEnd(vertex.getGraphic().getX()-x, vertex.getGraphic().getY()-y);
					arrow.toBack();
					background.toBack();
					graph.getVertex(i).getGraphic().changeTo(0);
					graph.addEdge(new Edge(true, graph.getVertex(i),vertex, arrow));
					root.removeEventFilter(MouseEvent.MOUSE_MOVED, mouseMoved);
					}else {
						vertex.getGraphic().changeTo(1);
						graph.getVertex(i).getGraphic().changeTo(0);
					}
				}
				
			}
		};
	};
	private EventHandler<MouseEvent> vertexMouseDragged = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			VertexNode v = ((VertexNode)e.getSource());
			Vertex vertex = graph.getAVertex(Integer.parseInt(v.getText()));
			if(vertex.getGraphic().isClicked()) {
				arrow = new Arrow(vertex.getGraphic().getX(),vertex.getGraphic().getY(),vertex.getGraphic().getX(),vertex.getGraphic().getY());
				arrow.changeTo(0);
				background.toBack();
				arrow.addEventFilter(MouseEvent.MOUSE_CLICKED,arrowMouseClicked);
				//arrow.setStart(vertex.getX(),vertex.getY());
				root.getChildren().add(arrow);
				vertex.getGraphic().setDraged(true);
				root.addEventFilter(MouseEvent.MOUSE_MOVED,mouseMoved);
			}
			
		}
	};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		guide.setText("click on the empty space to add an edge\n"
				+ "Click on one vertex and join the other vertex to add an edge\n"
				+ "click on top or edge + press 'delete' to delete one");
		background.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClicked);
		id = graph.getVertexsSize();
		graph.initGraph(root);
		root.getChildren().add(background);
		background.toBack();
		
	}
	
	private EventHandler<MouseEvent> mouseClicked = new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
		Vertex vertex = new Vertex(id);
	       vertex.getGraphic().addEventFilter(MouseEvent.MOUSE_CLICKED, vertexMouseClicked);
	vertex.getGraphic().addEventFilter(MouseEvent.DRAG_DETECTED, vertexMouseDragged);      
	       vertex.getGraphic().setXY(mouseEvent.getX(), mouseEvent.getY());
	       graph.addVertex(vertex);
	       id++;
	       Double y = mouseEvent.getY()-RADIUS;
	       Double x = mouseEvent.getX()-RADIUS;
	       AnchorPane.setTopAnchor(vertex.getGraphic(), y);
	       AnchorPane.setLeftAnchor(vertex.getGraphic(), x);
	       root.getChildren().add(vertex.getGraphic());
	}
	};
	public void setParaPane(AnchorPane pane) {
		this.paraPane = pane;
	}
	public GraphDisplay() {
		super();
	}
	public void setMainScene(Scene mainScene) {
		this.mainScene = mainScene;
	}
	public EventHandler<KeyEvent> getKeyPressed() {
		return keyPressed;
	}
	
	public Label getGuide() {
		return guide;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
		createSampleGraph();
	}
	public AnchorPane getRoot() {
		return root;
	}
	@FXML
	public void clearGraph(MouseEvent event) {
		graph.getListVertexs().clear();
		graph.getListEdges().clear();
		graph.getListAdj().clear();
		id=0;
		root.getChildren().clear();
		root.getChildren().add(background);
	}
	@FXML
	public void Done(MouseEvent event) {
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		paraPane.getChildren().clear();
		graph.initGraph(paraPane);
		primaryStage.setScene(mainScene);
	}
	@FXML
	public void loadGraph(MouseEvent event) {
		id = graph.getVertexsSize();
		graph.initGraph(root);
		root.getChildren().add(background);
		background.toBack();
	}
	public void createVertex(Graph graph, Double x,Double y) {
		Vertex vertex = new Vertex(graph.getVertexsSize());
	       vertex.getGraphic().addEventFilter(MouseEvent.MOUSE_CLICKED, vertexMouseClicked);
	vertex.getGraphic().addEventFilter(MouseEvent.DRAG_DETECTED, vertexMouseDragged);      
	       vertex.getGraphic().setXY(x,y);
	       y = y-RADIUS;
	       x = x-RADIUS;
	       AnchorPane.setTopAnchor(vertex.getGraphic(), y);
	       AnchorPane.setLeftAnchor(vertex.getGraphic(), x);
	       graph.addVertex(vertex);
	}
	public void createEdge (Graph graph, Vertex v1, Vertex v2) {
		Arrow arrow = new Arrow(v1.getGraphic().getX(),v1.getGraphic().getY(),v1.getGraphic().getX(),v1.getGraphic().getY());
		arrow.changeTo(0);
		arrow.addEventFilter(MouseEvent.MOUSE_CLICKED,arrowMouseClicked);
		Edge edge = graph.getEdge(v2,v1);
		Double x = v2.getGraphic().getX()-arrow.getStartX();
		Double y = v2.getGraphic().getY()-arrow.getStartY();
		Double xy = Math.sqrt(x*x + y*y);
		if(edge==null) {
		x = (x/xy)*RADIUS;
		y = (y/xy)*RADIUS;}
		else {
			Double angle = Math.asin(Math.abs(x)/xy);
			angle = 45.0-angle;
			if(x<0) x = Math.cos(angle)*RADIUS*-1;
			else x = Math.cos(angle)*RADIUS;
			if(y<0) y = Math.sin(angle)*RADIUS*-1;
			else y = Math.sin(angle)*RADIUS;
			edge.getGraphic().setEnd(v1.getGraphic().getX()+x, v1.getGraphic().getY()+y);
		}
		arrow.setEnd(v2.getGraphic().getX()-x, v2.getGraphic().getY()-y);
		graph.addEdge(new Edge(true, v1,v2, arrow));
	}
	public void createSampleGraph(){
		createVertex(graph, 400.0, 150.0);
		createVertex(graph, 150.0, 150.0);
		createVertex(graph, 150.0, 320.0);
		createVertex(graph, 650.0, 150.0);
		createVertex(graph, 650.0, 320.0);
		createEdge(graph, graph.getVertex(1), graph.getVertex(0));
		createEdge(graph, graph.getVertex(2), graph.getVertex(1));
		createEdge(graph, graph.getVertex(0), graph.getVertex(2));
		createEdge(graph, graph.getVertex(0), graph.getVertex(3));
		createEdge(graph, graph.getVertex(3), graph.getVertex(4));
	}
}
