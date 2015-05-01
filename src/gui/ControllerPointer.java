package gui;

public class ControllerPointer {
	private static Controller controllerRef;
	
	public static void setController(Controller c) {
		controllerRef = c;
	}
	
	public static Controller getController() {
		return controllerRef;
	}
}
