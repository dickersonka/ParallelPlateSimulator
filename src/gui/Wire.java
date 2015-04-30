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
	private ComboBox<WireType> typeChooser = new ComboBox<WireType>();

	public Wire(Controller controller) {
		this(controller, WireType.STRAIGHT);
	}
	
	public Wire(Controller controller, WireType type) {
		super(controller);
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
	};
}
