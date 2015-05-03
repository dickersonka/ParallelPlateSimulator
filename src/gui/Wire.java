package gui;

import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE_IMG = "/img/straight_wire.png";
	public final static String CORNER_WIRE_IMG = "/img/corner_wire.png";
	public final static String T_SECTION_WIRE_IMG = "/img/t_section_wire.png";
	public final String CONTAINER_TYPE = "WIR";
	
	private WireType type;
	private ComboBox<WireType> typeChooser = new ComboBox<WireType>();

	public Wire() {
		this(WireType.STRAIGHT);
	}
	
	public Wire(WireType type) {
		super();
		setType(type);
		
		ObservableList<WireType> wireTypeList = FXCollections.observableArrayList(WireType.values());
		typeChooser.setItems(wireTypeList);
		typeChooser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				setType(typeChooser.getSelectionModel().getSelectedItem());
			}
		});
		typeChooser.getSelectionModel().select(type);
	}
	
	public Wire(String s) {
		this();
		
		Scanner reader = new Scanner(s);
		outputDir = Direction.fromString(reader.next());
		inputDir = Direction.fromString(reader.next());
		setType(WireType.fromString(reader.next()));
		
		alignImageToInput();
		updateOutput();
		updateInput();
		controller.validateCircuit();
		
		showComponentControls();
		reader.close();
	}
	
	public void setType(WireType type) {
		this.type = type;
		
		switch(this.type) {
		case STRAIGHT:
			setImage(STRAIGHT_WIRE_IMG);
			outputDir = inputDir.getClockwiseDir().getClockwiseDir();
			updateOutput();
			break;
		case CORNER:
			setImage(CORNER_WIRE_IMG);
			outputDir = inputDir.getAntiClockwiseDir();
			updateOutput();
			break;
		case T_SECTION:
			setImage(T_SECTION_WIRE_IMG);
			outputDir = inputDir.getClockwiseDir().getClockwiseDir();
			updateOutput();
		}
	}
	
	@Override
	public void giveInput(CircuitData c) {
		if (type == WireType.T_SECTION) {
			double cEq = c.getCEquivalent();
			c.setVoltages(cEq, controller.getTotalVoltage());
			//TODO: how I handle this depends on the input and output of the wire! Right now it's not actually implemented
			//if the current is flowing into one input and out of the two inputs, then it needs to go down one, then the other.
			//it also needs to pass on a clone of circuit data instead of c (outputRecipient.giveInput(c.clone())) for each.
		}
		else {
			outputRecipient.giveInput(c);
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
		}, T_SECTION {
			@Override
			public String toString() {
				return "T-Section";
			}
		};
		
		public abstract String toString();
		
		public static WireType fromString(String s) throws IllegalArgumentException {
			s = s.toUpperCase();
			
			if(s.equals("STRAIGHT"))
				return STRAIGHT;
			else if(s.equals("CORNER"))
				return CORNER;
			else if(s.equals("T-SECTION"))
				return T_SECTION;
			else
				throw new IllegalArgumentException();
		}
	};
}
