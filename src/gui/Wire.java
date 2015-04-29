package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE = "/img/straight_wire.png";
	public final static String CORNER_WIRE = "/img/corner_wire.png";
	public final static String T_SECTION_WIRE = "/img/t_section_wire.png";
	private Button clockwiseButton;
	private Button antiClockwiseButton;

	public Wire(Controller controller) {
		super(controller);
		
		setImage(STRAIGHT_WIRE);
		setupRotationButtons();
	}
	
	public void setupRotationButtons() {
		clockwiseButton = new Button();
		clockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageClockwise();
			}
		});
		
		Image clockwiseRotation = new Image(getClass().getResourceAsStream("/img/clockwise.png"));
		clockwiseButton.setGraphic(new ImageView(clockwiseRotation));

		antiClockwiseButton = new Button();
		antiClockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageAntiClockwise();
			}
		});
		
		Image antiClockwiseRotation = new Image(getClass().getResourceAsStream("/img/anticlockwise.png"));
		antiClockwiseButton.setGraphic(new ImageView(antiClockwiseRotation));
	}

	@Override
	public void showSlidersAndRotations() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(0, clockwiseButton);
		sliderBox.getChildren().add(0, antiClockwiseButton);
		//TODO: how can I position these better? should also look for better rotation images.
	}

}
