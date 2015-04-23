package gui;

public class Battery extends Container {

	private double voltage = 1;
	
	public Battery() {
		//TODO: addImage("picofbattery");
		slider.setMin(-1.5);
		slider.setMax(1.5);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void showSlider() {
		slider.setVisible(true);
	}
	
	@Override
	public void hideSlider() {
		slider.setVisible(false);
	}
	
	public void changeVoltage(double value) {
		voltage = value;
		//TODO: should probably update the gui after this is called
	}
	
	public double getVoltage() {
		return voltage;
	}

}
