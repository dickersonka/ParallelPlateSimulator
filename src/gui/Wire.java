package gui;

import gui.Link.LinkType;

import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE_IMG = "/img/straight_wire.png";
	public final static String CORNER_WIRE_IMG = "/img/corner_wire.png";
	public final static String T_SECTION_OUT_WIRE_IMG = "/img/t_section_out_wire.png";
	public final static String T_SECTION_IN_WIRE_IMG = "/img/t_section_in_wire.png";
	public final String CONTAINER_TYPE = "WIR";
	
	private WireType type;
	private Link extraLink;
	private ComboBox<WireType> typeChooser = new ComboBox<WireType>();

	public Wire() {
		this(WireType.STRAIGHT);
	}
	
	public Wire(WireType type) {
		super();
		setupTypeChooser();
		setType(type);
	}
	
	public Wire(String s) {
		this();
		
		Scanner reader = new Scanner(s);
		outLink.setDirection(Direction.fromString(reader.next()));
		inLink.setDirection(Direction.fromString(reader.next()));
		setType(WireType.fromString(reader.next()));
		
		alignImageToInput();
		updateOutput();
		updateInput();
		controller.validateCircuit();
		
		showComponentControls();
		reader.close();
	}
	
	private void setupTypeChooser() {
		ObservableList<WireType> wireTypeList = FXCollections.observableArrayList(WireType.values());
		typeChooser.setItems(wireTypeList);
		typeChooser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				setType(typeChooser.getSelectionModel().getSelectedItem());
			}
		});
	}
	
	public void setType(WireType type) {
		this.type = type;
		typeChooser.getSelectionModel().select(this.type);
		
		Direction inDir = inLink.getDirection();
		
		switch(this.type) {
		case STRAIGHT:
			setImage(STRAIGHT_WIRE_IMG);
			outLink.setDirection(inDir.getOppositeDir());
			extraLink = null;
			updateOutput();
			break;
		case CORNER:
			setImage(CORNER_WIRE_IMG);
			outLink.setDirection(inDir.getAntiClockwiseDir());
			extraLink = null;
			updateOutput();
			break;
		case T_SECTION_OUT:
			setImage(T_SECTION_OUT_WIRE_IMG);
			outLink.setDirection(inLink.getDirection().getOppositeDir());
			extraLink = new Link(LinkType.OUT, inDir.getAntiClockwiseDir());
			updateOutput();
			break;
		case T_SECTION_IN:
			setImage(T_SECTION_IN_WIRE_IMG);
			outLink.setDirection(inLink.getDirection().getOppositeDir());
			extraLink = new Link(LinkType.IN, inDir.getAntiClockwiseDir());
			updateOutput();
			updateInput();
		}
	}
	
	public WireType getType() {return type;}
	public Link getExtraLink() {return extraLink;}
	
	@Override
	protected void updateOutput() {
		super.updateOutput();
		
		if(type == WireType.T_SECTION_OUT)
			extraLink.connectFrom(this);
	}
	
	@Override
	protected void updateInput() {
		super.updateInput();
		
		if(type == WireType.T_SECTION_OUT)
			extraLink.connectFrom(this);
	}
	
	@Override
	public Link getLinkInDir(Direction d) {
		Link result = super.getLinkInDir(d);
		
		if(result != null || extraLink == null)
			return result;
		else if(extraLink.getDirection() == d)
			return extraLink;
		else
			return null;
	}
	
	@Override
	public void turnImageClockwise() {
		if(extraLink != null)
			extraLink.turnClockwise();
		super.turnImageClockwise();
	}
	
	@Override
	public void turnImageAntiClockwise() {
		if(extraLink != null)
			extraLink.turnAntiClockwise();
		super.turnImageAntiClockwise();
	}
	
	@Override
	public void giveInput(CircuitData c) {
		if (type == WireType.T_SECTION_OUT) {
			double cEq = c.getCEquivalent();
			c.setVoltages(cEq, controller.getTotalVoltage());
			//TODO: how I handle this depends on the input and output of the wire! Right now it's not actually implemented
			//if the current is flowing into one input and out of the two inputs, then it needs to go down one, then the other.
			//it also needs to pass on a clone of circuit data instead of c (outputRecipient.giveInput(c.clone())) for each.
		}
		else {
			outLink.getLinked().giveInput(c);
		}
	}

	@Override
	protected void showComponentControls() {
		super.showComponentControls();
		sliderBox.getChildren().add(typeChooser);
	}

	@Override
	protected void highlight() {
		this.getChildren().add(CANNOT_DROP_HIGHLIGHT);
	}

	@Override
	protected void dehighlight() {
		this.getChildren().remove(CANNOT_DROP_HIGHLIGHT);
	}

	@Override
	protected boolean canBeDroppedOn() {
		return false;
	}
	
	@Override
	protected boolean canBeDragged() {
		return true;
	}

	@Override
	protected String getComponentType() {
		return CONTAINER_TYPE;
	}
	
	@Override
	protected String getTypeSpecificData() {
		return " " + type.toString();
	}
	
	public enum WireType {
		STRAIGHT {
			@Override
			public String toString() {
				return "Straight";
			}
		}, CORNER {
			@Override
			public String toString() {
				return "Corner";
			}
		}, T_SECTION_OUT {
			@Override
			public String toString() {
				return "T-section:Out";
			}
		}, T_SECTION_IN {
			@Override
			public String toString() {
				return "T-section:In";
			}
		};
		
		public abstract String toString();
		
		public static WireType fromString(String s) throws IllegalArgumentException {
			s = s.toUpperCase();
			
			if(s.equals("STRAIGHT"))
				return STRAIGHT;
			else if(s.equals("CORNER"))
				return CORNER;
			else if(s.equals("T-SECTION:OUT"))
				return T_SECTION_OUT;
			else if(s.equals("T-SECTION:IN"))
				return T_SECTION_IN;
			else
				throw new IllegalArgumentException();
		}
		
		public boolean isTSection() {
			return this == T_SECTION_OUT || this == T_SECTION_IN;
		}
	};
}
