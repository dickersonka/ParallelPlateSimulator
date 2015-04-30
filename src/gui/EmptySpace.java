package gui;

public class EmptySpace extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5211823242481439468L;

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
	protected void showComponentControls() {}
	
	@Override
	protected void giveInput(CircuitData c) {}
}
