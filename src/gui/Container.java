package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class Container extends Pane {
	public final static int STANDARD_SQUARE_TILE_DIMENSIONS = 64;
	protected Controller controller;
	protected VBox sliderBox;
	protected ImageView img = new ImageView();
	
	public Container() {
		this.setPrefSize(STANDARD_SQUARE_TILE_DIMENSIONS, STANDARD_SQUARE_TILE_DIMENSIONS);
		this.getChildren().add(img);
		setupMousePressed();
	}
	
	public Container(Controller controller) {
		this();

		this.controller = controller;
		this.sliderBox = controller.getSliderBox();
		setupMousePressed();
	}
	
	public void setupMousePressed() {
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showSlidersAndRotations();
			}
		});
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
	
	public void showSlidersAndRotations() {
		sliderBox.getChildren().clear();
		HBox buttonBox = getRotationButtons();
		sliderBox.getChildren().add(buttonBox);
	}
	
	protected HBox getRotationButtons() {
		Button clockwiseButton = new Button();
		clockwiseButton.setText("\u21B7");
		clockwiseButton.setFont(new Font(18));
		clockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageClockwise();
			}
		});

		Button antiClockwiseButton = new Button();
		antiClockwiseButton.setText("\u21B6");
		antiClockwiseButton.setFont(new Font(18));
		antiClockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageAntiClockwise();
			}
		});
		
		HBox rotationButtons = new HBox();
		rotationButtons.getChildren().add(antiClockwiseButton);
		rotationButtons.getChildren().add(clockwiseButton);
		return rotationButtons;
	}

}
