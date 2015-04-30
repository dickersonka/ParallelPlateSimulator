package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	protected HBox rotationButtonBox;
	protected ImageView img = new ImageView();
	protected Button deleteButton = new Button();
	
	protected Direction outputDir, inputDir;
	protected Container outputRecipient;
	
	public Container() {
		this.setPrefSize(STANDARD_SQUARE_TILE_DIMENSIONS, STANDARD_SQUARE_TILE_DIMENSIONS);
		this.getChildren().add(img);
		setupOnMousePressed();
	}
	
	public Container(Controller controller) {
		this();

		this.controller = controller;
		sliderBox = controller.getSliderBox();
		rotationButtonBox = makeRotationButtons();
		setupDeleteButton();
		setupOnMousePressed();
		
		outputDir = Direction.NORTH;
		inputDir = Direction.SOUTH;
		checkConnections();
	}
	
	private void setupOnMousePressed() {
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showComponentControls();
			}
		});
	}
	
	private void setupDeleteButton() {
		deleteButton.setText("\u232B");
		deleteButton.setFont(new Font(18));
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				controller.removeComponent(Container.this);
			}
		});
	}
	
	/*private void setupDragDrop() {
		this.setOnDragDropped(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				controller.dropTile(e);
			}
		});
	}*/
	
	public void setImage(String imageName) {
		img.setImage(new Image(imageName));
	}
	
	public void turnImageClockwise() {
		getImage().setRotate(getImage().getRotate() + 90.0);
		
		outputDir = outputDir.getClockwiseDir();
		inputDir = inputDir.getClockwiseDir();
		checkConnections();
	}
	
	public void turnImageAntiClockwise() {
		getImage().setRotate(getImage().getRotate() - 90.0);
		
		outputDir = outputDir.getAntiClockwiseDir();
		inputDir = inputDir.getAntiClockwiseDir();
		checkConnections();
	}
	
	protected void checkConnections() {
		//TODO: Look at the tiles in the input and output directions to see if everything is oriented correctly.
	}
	
	protected void giveInput(CircuitData c) {
		outputRecipient.giveInput(c);
	}
	
	protected Node getImage() {
		return this.getChildren().get(0);
	}
	
	protected void showComponentControls() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(rotationButtonBox);
		sliderBox.getChildren().add(deleteButton);
	}
	
	private HBox makeRotationButtons() {
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

	public enum Direction {
		NORTH {
			@Override
			public Direction getClockwiseDir() {
				return EAST;
			}

			@Override
			public Direction getAntiClockwiseDir() {
				return WEST;
			}
		}, EAST {
			@Override
			public Direction getClockwiseDir() {
				return SOUTH;
			}

			@Override
			public Direction getAntiClockwiseDir() {
				return NORTH;
			}
		}, SOUTH {
			@Override
			public Direction getClockwiseDir() {
				return WEST;
			}

			@Override
			public Direction getAntiClockwiseDir() {
				return EAST;
			}
		}, WEST {
			@Override
			public Direction getClockwiseDir() {
				return NORTH;
			}

			@Override
			public Direction getAntiClockwiseDir() {
				return SOUTH;
			}
		};
		
		public abstract Direction getClockwiseDir();
		public abstract Direction getAntiClockwiseDir();
	}
}
