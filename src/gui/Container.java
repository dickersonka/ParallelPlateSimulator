package gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Container extends Pane {
	
	public Container() {
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showSliders();
			}
		});
		this.setPrefSize(32, 32); //TODO: is this the right size for a 32x32 pic? same measurements?
	}
	
	public void addImage(String imageName) {
		ImageView iv1 = new ImageView(new Image(imageName));
		this.getChildren().add(iv1);
	}
	
	public void showSliders() {
	}
	
	public void turnImage() {
		this.getChildren().get(0).setRotate(90.0);
	}
	
	public void changeValue(double value) {
		
	}

}
