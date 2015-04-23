package gui;


import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Container {
	
	protected Pane pane;
	protected Slider slider;
	
	public Container() {
		pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showSlider();
			}
		});
		pane.setPrefSize(32, 32); //TODO: is this the right size for a 32x32 pic? same measurements?
		slider.setVisible(false);
		
		slider.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				changeValue(slider.getValue());
			}
		});
	}
	
	public void addImage(String imageName) {
		ImageView iv1 = new ImageView(new Image(imageName));
		pane.getChildren().add(iv1);
	}
	
	public void showSlider() {
	}
	
	public void hideSlider() {
	//TODO: so when the grid sees that this pane is no longer selected, it should call this method
	}
	
	public void turnImage() {
		pane.getChildren().get(0).setRotate(90.0);
	}
	
	public void changeValue(double value) {
		
	}

}
