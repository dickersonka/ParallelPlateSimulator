package gui;

public class Wire extends Container {
	public final static String STRAIGHT_WIRE = "/img/straight_wire";
	public final static String CORNER_WIRE = "/img/corner_wire";
	public final static String T_SECTION_WIRE = "/img/t_section_wire";

	public Wire() {
		addImage(STRAIGHT_WIRE);
	}

	@Override
	public void showSliders() {}

}
