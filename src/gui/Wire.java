package gui;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE_IMG = "/img/straight_wire.png";
	public final static String CORNER_WIRE_IMG = "/img/corner_wire.png";
	public final static String T_SECTION_WIRE_IMG = "/img/t_section_wire.png";
	
	private WireType type;

	public Wire(Controller controller) {
		this(controller, WireType.STRAIGHT);
	}
	
	public Wire(Controller controller, WireType type) {
		super(controller);
		setType(type);
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

	@Override
	protected void showComponentControls() {
		sliderBox.getChildren().clear();
		super.showComponentControls();
	}

	public enum WireType {
		STRAIGHT, CORNER, T_SECTION;
	};
}
