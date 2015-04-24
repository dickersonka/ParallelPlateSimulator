package gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class Container extends Pane {
	public final static int STANDARD_SQUARE_TILE_DIMENSIONS = 32;
	protected VBox sliderBox;
	protected ImageView img = new ImageView();
	
	public Container() {
		this.setPrefSize(STANDARD_SQUARE_TILE_DIMENSIONS, STANDARD_SQUARE_TILE_DIMENSIONS);
		this.getChildren().add(img);
	}
	
	public Container(VBox sliderBox) {
		this();
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showSliders();
			}
		});
		this.sliderBox = sliderBox;
	}
	
	public void setImage(String imageName) {
		img.setImage(new Image(imageName));
	}
	
	public void turnImageClockwise() {
		getImage().setRotate(getImage().getRotate() + 90.0);
	}
	
	public void turnImageAntiClockwise() {
		getImage().setRotate(getImage().getRotate() - 90.0);
	}
	
	protected Node getImage() {
		return this.getChildren().get(0);
	}
	
	public abstract void showSliders();
	
	//public abstract void changeValue(double value);

}
