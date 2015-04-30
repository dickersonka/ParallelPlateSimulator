package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE_IMG = "/img/straight_wire.png";
	public final static String CORNER_WIRE_IMG = "/img/corner_wire.png";
	public final static String T_SECTION_WIRE_IMG = "/img/t_section_wire.png";
	
	private WireType type;
	private ComboBox<String> typeChooser = new ComboBox<String>();
	private ObservableList<String> wireTypes = FXCollections.observableArrayList();

	public Wire(Controller controller) {
		this(controller, WireType.STRAIGHT);
	}
	
	public Wire(Controller controller, WireType type) {
		super(controller);
		setType(type);
		
		wireTypes.add("Straight wire");
		wireTypes.add("Corner wire");
		wireTypes.add("T-section wire");
		typeChooser.setItems(wireTypes);
		typeChooser.getSelectionModel().select(getTypeAsString());
		typeChooser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				changeWireType(typeChooser.getSelectionModel().getSelectedItem());
			}
		});
		
	}
	
	public void changeWireType(String wiretype) {
		switch(wiretype) {
		case "Straight wire":
			setType(WireType.STRAIGHT);
			break;
		case "Corner wire":
			setType(WireType.CORNER);
			break;
		case "T-section wire":
			setType(WireType.T_SECTION);
		}
	}
	
	public void setType(WireType type) {
		this.type = type;
		
		switch(this.type) {
		case STRAIGHT:
			setImage(STRAIGHT_WIRE_IMG);
			break;
		case CORNER:
			setImage(CORNER_WIRE_IMG);
			break;
		case T_SECTION:
			setImage(T_SECTION_WIRE_IMG);
		}
	}
	
	public String getTypeAsString() {
		if (type == WireType.STRAIGHT) {
			return "Straight wire";
		}
		else if (type == WireType.CORNER) {
			return "Corner wire";
		}
		else if (type == WireType.T_SECTION) {
			return "T-section wire";
		}
		throw new IllegalArgumentException("Invalid wiretype");
	}

	@Override
	protected void showComponentControls() {
		sliderBox.getChildren().clear();
		super.showComponentControls();
		sliderBox.getChildren().add(typeChooser);
		
	}

	public enum WireType {
		STRAIGHT, CORNER, T_SECTION;
	};
}
