package gui;

public class Capacitor extends Container {
	
	private double capacity = 1;

	public Capacitor() {
		addImage("/img/capacitor.png");
		slider.setMin(0);
		slider.setMax(100);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void showSlider() {
		slider.setVisible(true);
	}

	public void changeCapacity(double value) {
		capacity = value;
		//TODO: should probably update the gui after this is called.
	}
	
	public double getCapacity() {
		return capacity;
	}

}
