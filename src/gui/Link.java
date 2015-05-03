package gui;

public class Link {
	private Controller controller;
	private Container linkedComponent;
	private LinkType type;
	private Direction dir;
	
	public Link(LinkType t, Direction d) {
		this.controller = ControllerPointer.getController();
		this.type = t;
		this.dir = d;
	}
	
	public void flipType() {
		type = type.getOppositeType();
	}
	
	public boolean linkUpIsValid() {
		if(linkedComponent == null)
			return false;
		
		Link other = linkedComponent.getLinkInDir(dir.getOppositeDir());
		
		if(other == null)
			return false;
		
		return dir == other.getDirection().getOppositeDir() && type == other.getType().getOppositeType();
	}
	
	public void connectFrom(Container parentContainer) {
		linkedComponent = controller.getComponentInDir(parentContainer, dir);
	}
	
	public void turnClockwise() {
		dir = dir.getClockwiseDir();
	}
	
	public void turnAntiClockwise() {
		dir = dir.getAntiClockwiseDir();
	}
	
	public void flipDirection() {
		dir = dir.getOppositeDir();
	}
	
	public Container getLinked() {return linkedComponent;}
	public LinkType getType() {return type;}
	public Direction getDirection() {return dir;}
	public void setDirection(Direction dir) {this.dir = dir;}
	
	public enum LinkType {
		OUT {
			@Override
			public LinkType getOppositeType() {
				return IN;
			}
		}, IN {
			@Override
			public LinkType getOppositeType() {
				return OUT;
			}
		};
		
		public abstract LinkType getOppositeType();
	}
}
