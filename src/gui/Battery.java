package gui;

public class Battery extends Container {

	private double voltage = 1;
	
	public Battery() {
		addImage("/img/dc_voltage.png");
		slider.setMin(-1.5);
		slider.setMax(1.5);
	}
	
	@Override
	public void showSlider() {
		slider.setVisible(true);
	}
	
	public void changeVoltage(double value) {
		voltage = value;
		//TODO: should probably update the gui after this is called
	}
	
	public double getVoltage() {
		return voltage;
	}

}
