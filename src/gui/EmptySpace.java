package gui;


public class EmptySpace extends Container {
	public final String CONTAINER_TYPE = "EMP";
	
	public EmptySpace() {
		setImage("/img/empty_tile.png");
	}

	@Override
	protected void highlight() {
		this.getChildren().add(CAN_DROP_HIGHLIGHT);
	}

	@Override
	protected void dehighlight() {
		this.getChildren().remove(CAN_DROP_HIGHLIGHT);
	}

	@Override
	protected boolean canBeDroppedOn() {
		return true;
	}
	
	@Override
	protected boolean canBeDragged() {
		return false;
	}

	@Override
	protected String getComponentType() {
		return CONTAINER_TYPE;
	}
	
	@Override
	protected String getSpecificData() {
		return "";
	}
	
	@Override
	protected void showComponentControls() {}
	
	@Override
	protected void giveInput(CircuitData c) {}
}
