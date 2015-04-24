package gui;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Arrow extends Line {
	
	private Group g = new Group();
	private Polygon triangle;
	
	public Arrow(double xStart, double yStart, double xEnd, double yEnd) {
		this.setStartX(xStart);
		this.setStartY(yStart);
		this.setEndX(xEnd);
		this.setEndY(yEnd);
		//TODO: the following presumes the arrows only go up and down

		if (yEnd > yStart) {
			triangle = new Polygon (xEnd, yEnd, xEnd+2, yEnd-2, xEnd-2, yEnd-2);
		}
		else {
			triangle = new Polygon (xEnd, yEnd, xEnd+2, yEnd+2, xEnd-2, yEnd+2);
		}
		
		g.getChildren().add(this);
		g.getChildren().add(triangle);
		
	}
	
	public Group getArrow() {
		return g;
	}

}
