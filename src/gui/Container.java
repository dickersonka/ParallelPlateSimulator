package gui;

import gui.Link.LinkType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public abstract class Container extends Pane{
	public final static int STANDARD_SQUARE_TILE_DIMENSIONS = 64;
	public final static DataFormat CONTAINER_FORMAT = new DataFormat("CONTAINER");
	public final static String CONTAINER_TYPE = "CON";
	
	protected final static ImageView CANNOT_DROP_HIGHLIGHT = new ImageView(new Image("/img/cannot_drop_highlight.png"));
	protected final static ImageView CAN_DROP_HIGHLIGHT = new ImageView(new Image("/img/can_drop_highlight.png"));
	
	protected Controller controller;
	protected VBox sliderBox;
	protected HBox controlButtonBox;
	protected Button deleteButton;
	protected ImageView img = new ImageView();
	
	protected Link outLink, inLink;
	//protected Direction outputDir, inputDir;
	//protected Container outputRecipient;
	
	public Container() {
		this.setPrefSize(STANDARD_SQUARE_TILE_DIMENSIONS, STANDARD_SQUARE_TILE_DIMENSIONS);
		this.getChildren().add(img);
		
		controller = ControllerPointer.getController();
		sliderBox = controller.getSliderBox();
		controlButtonBox = makeControlButtons();
		
		setupOnMousePressed();
		setupDragDrop();
		
		outLink = new Link(LinkType.OUT, Direction.NORTH);
		inLink = new Link(LinkType.IN, Direction.SOUTH);
		
		/*outputDir = Direction.NORTH;
		updateOutput();
		inputDir = Direction.SOUTH;
		updateInput();*/
		controller.validateCircuit();
	}
	
	protected void updateOutput() {
		outLink.connectFrom(this);
	}
	
	protected void updateInput() {
		inLink.connectFrom(this);
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
				if(Container.this.canBeDragged()) {
					Dragboard db = Container.this.startDragAndDrop(TransferMode.MOVE);
					ClipboardContent c = new ClipboardContent();
					c.putString(Container.this.toString());
					db.setContent(c);
					//	Next line needed if the dragged component doesn't get removed.
					Container.this.controller.removeComponent(Container.this);
				}
				
				e.consume();
			}
		});
		
		this.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				if(e.getGestureSource() != Container.this &&
						e.getDragboard().hasString()) {
					e.acceptTransferModes(TransferMode.MOVE);
				}
				
				e.consume();
			}
		});
		
		this.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				if(e.getGestureSource() != Container.this &&
						e.getDragboard().hasString()) {
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
						db.hasString()) {
					controller.replaceComponent(Container.this, Container.makeFromString(db.getString()));
					success = true;
					controller.validateCircuit();
				} else {
					String content = db.getString();
					
					int i;
					for(i=0; i<content.length() && !Character.isDigit(content.charAt(i)); i++) {}
					content = content.substring(i, content.length());
					
					int idx = Integer.parseInt(content);
					controller.replaceComponent(idx, Container.makeFromString(db.getString()));
					success = true;
					controller.validateCircuit();
				}
				
				e.setDropCompleted(success);
				e.consume();
			}
		});
	}
	
	protected static Container makeFromString(String s) throws IllegalArgumentException {
		String type = s.substring(0, 3);
		
		if(type.equals("CAP"))
			return new Capacitor(s.substring(3, s.length()));
		else if(type.equals("BAT"))
			return new Battery(s.substring(3, s.length()));
		else if(type.equals("WIR"))
			return new Wire(s.substring(3, s.length()));
		else
			throw new IllegalArgumentException();
	}

	protected abstract void highlight();
	protected abstract void dehighlight();
	protected abstract boolean canBeDroppedOn();
	protected abstract boolean canBeDragged();
	protected abstract String getComponentType();
	protected abstract String getTypeSpecificData();

	public void setImage(String imageName) {
		img.setImage(new Image(imageName));
	}
	
	public void turnImageClockwise() {
		getImage().setRotate(getImage().getRotate() + 90.0);
		
		outLink.turnClockwise();
		updateOutput();
		inLink.turnClockwise();
		updateInput();
		controller.validateCircuit();
	}
	
	public void turnImageAntiClockwise() {
		getImage().setRotate(getImage().getRotate() - 90.0);
		
		outLink.turnAntiClockwise();
		updateOutput();
		inLink.turnAntiClockwise();
		updateInput();
		controller.validateCircuit();
	}
	
	@SuppressWarnings("incomplete-switch")
	protected void alignImageToInput() {
		switch(inLink.getDirection()) {
		case NORTH:
			getImage().setRotate(getImage().getRotate() + 180.0);
			break;
		case EAST:
			getImage().setRotate(getImage().getRotate() - 90.0);
			break;
		case WEST:
			getImage().setRotate(getImage().getRotate() + 90.0);
			break;
		}
	}
	
	protected void giveInput(CircuitData c) {
		outLink.getLinked().giveInput(c);
	}
	
	protected Node getImage() {
		return this.getChildren().get(0);
	}
	
	protected void showComponentControls() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(controlButtonBox);
	}
	
	private HBox makeControlButtons() {
		Button clockwiseButton = new Button();
		clockwiseButton.setText("\u21B7");
		clockwiseButton.setFont(new Font(18));
		clockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageClockwise();
			}
		});
		
		setupDeleteButton();

		Button antiClockwiseButton = new Button();
		antiClockwiseButton.setText("\u21B6");
		antiClockwiseButton.setFont(new Font(18));
		antiClockwiseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				turnImageAntiClockwise();
			}
		});
		
		HBox controlButtons = new HBox();
		controlButtons.getChildren().add(antiClockwiseButton);
		controlButtons.getChildren().add(deleteButton);
		controlButtons.getChildren().add(clockwiseButton);
		controlButtons.setPadding(new Insets(5,0,0,32));
		return controlButtons;
	}
	
	public Link getOutLink() {return outLink;}
	public Link getLinkInDir(Direction d) {
		if(outLink.getDirection() == d)
			return outLink;
		else if(inLink.getDirection() == d) 
			return inLink;
		else
			return null;
	}
	
	public String toString() {
		String result = "";
		
		result += getComponentType();
		result += " " + outLink.getDirection();
		result += " " + inLink.getDirection();
		result += getTypeSpecificData();
		result += " " + controller.getIndexOfComponent(this);
		
		return result;
	}
}
