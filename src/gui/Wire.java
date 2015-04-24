package gui;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE = "/img/straight_wire.png";
	public final static String CORNER_WIRE = "/img/corner_wire.png";
	public final static String T_SECTION_WIRE = "/img/t_section_wire.png";

	public Wire() {
		setImage(STRAIGHT_WIRE);
	}

	@Override
	public void showSliders() {}

}
