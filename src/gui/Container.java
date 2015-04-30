package gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class Container extends Pane implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1431027753233575127L;
	
	public final static int STANDARD_SQUARE_TILE_DIMENSIONS = 64;
	public final static DataFormat CONTAINER_FORMAT = new DataFormat("CONTAINER");
	
	protected final static ImageView CANNOT_DROP_HIGHLIGHT = new ImageView(new Image("/img/cannot_drop_highlight.png"));
	protected final static ImageView CAN_DROP_HIGHLIGHT = new ImageView(new Image("/img/can_drop_highlight.png"));
	
	protected Controller controller;
	protected VBox sliderBox;
	protected HBox rotationButtonBox;
	protected Button deleteButton;
	protected ImageView img = new ImageView();
	
	protected Direction outputDir, inputDir;
	protected Container outputRecipient;
	
	public Container() {
		this.setPrefSize(STANDARD_SQUARE_TILE_DIMENSIONS, STANDARD_SQUARE_TILE_DIMENSIONS);
		this.getChildren().add(img);
	}
	
	public Container(Controller controller) {
		this();

		this.controller = controller;
		sliderBox = controller.getSliderBox();
		rotationButtonBox = makeRotationButtons();
		
		setupDeleteButton();
		setupOnMousePressed();
		setupDragDrop();
		
		outputDir = Direction.NORTH;
		updateOutput();
		inputDir = Direction.SOUTH;
		Container input = this.controller.getComponentInDir(this, inputDir);
		if(input != null)
			input.updateOutput();
		this.controller.validateCircuit();
	}
	
	protected void updateOutput() {
		outputRecipient = controller.getComponentInDir(this, outputDir);
	}

	protected void setupOnMousePressed() {
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				showComponentControls();
			}
		});
	}
	
	protected void setupDeleteButton() {
		deleteButton = new Button();
		deleteButton.setText("\u232B");
		deleteButton.setFont(new Font(18));
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				controller.removeComponent(Container.this);
				controller.validateCircuit();
			}
		});
	}
	
	protected void setupDragDrop() {
		this.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Dragboard db = Container.this.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent c = new ClipboardContent();
				c.put(CONTAINER_FORMAT, Container.this);
				db.setContent(c);
				
				e.consume();
			}
		});
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				if(e.getGestureSource() != Container.this &&
						e.getDragboard().hasContent(CONTAINER_FORMAT)) {
					e.acceptTransferModes(TransferMode.MOVE);
				}
				
				e.consume();
			}
		});
		
		this.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				if(e.getGestureSource() != Container.this &&
						e.getDragboard().hasContent(CONTAINER_FORMAT)) {
					Container.this.highlight();
				}
				
				e.consume();
			}
		});
		
		this.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				Container.this.dehighlight();
				
				e.consume();
			}
		});
		
		this.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				boolean success = false;
				Dragboard db = e.getDragboard();
				if(Container.this.canBeDroppedOn() &&
						db.hasContent(CONTAINER_FORMAT)) {
					controller.replaceComponent(Container.this, (Container)db.getContent(CONTAINER_FORMAT));
					controller.validateCircuit();
				}
				
				e.setDropCompleted(success);
				e.consume();
			}
		});
	}
	
	protected abstract void highlight();
	protected abstract void dehighlight();
	protected abstract boolean canBeDroppedOn();

	public void setImage(String imageName) {
		img.setImage(new Image(imageName));
	}
	
	public void turnImageClockwise() {
		getImage().setRotate(getImage().getRotate() + 90.0);
		
		outputDir = outputDir.getClockwiseDir();
		updateOutput();
		inputDir = inputDir.getClockwiseDir();
		Container input = controller.getComponentInDir(this, inputDir);
		if(input != null)
			input.updateOutput();
		controller.validateCircuit();
	}
	
	public void turnImageAntiClockwise() {
		getImage().setRotate(getImage().getRotate() - 90.0);
		
		outputDir = outputDir.getAntiClockwiseDir();
		updateOutput();
		inputDir = inputDir.getAntiClockwiseDir();
		Container input = controller.getComponentInDir(this, inputDir);
		if(input != null)
			input.updateOutput();
		controller.validateCircuit();
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
		
		/*Label componentID = new Label();
		componentID.setText("This: " + this.toString());
		sliderBox.getChildren().add(componentID);
		
		Label outputLabel = new Label();
		if(outputRecipient != null)
			outputLabel.setText("Next: " + outputRecipient.toString());
		else
			outputLabel.setText("NULL");
		sliderBox.getChildren().add(outputLabel);*/
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
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	
	/*
	 * 		Getters for serialization
	 */
	public Controller getController() {return controller;}
	public VBox getSliderBox() {return sliderBox;}
	public HBox getRotationButtonBox() {return rotationButtonBox;}
	public Button getDeleteButton() {return deleteButton;}
	public ImageView getImg() {return img;}
	public Direction getOutputDir() {return outputDir;}
	public Direction getInputDir() {return inputDir;}
	public Container getOutputRecipient() {return outputRecipient;}
	
	/*
	 * 		Setters for serialization
	 */
	public void setSliderBox(VBox sliderBox) {this.sliderBox = sliderBox;}
	public void setRotationButtonBox(HBox rotationButtonBox) {this.rotationButtonBox = rotationButtonBox;}
	public void setDeleteButton(Button deleteButton) {this.deleteButton = deleteButton;}
	public void setImg(ImageView img) {this.img = img;}
	public void setOutputDir(Direction outputDir) {this.outputDir = outputDir;}
	public void setInputDir(Direction inputDir) {this.inputDir = inputDir;}
	public void setOutputRecipient(Container outputRecipient) {this.outputRecipient = outputRecipient;}

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
