package gui;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class Battery extends Container {

	private double voltage = 1;
	private Slider slider = new Slider();
	private VBox sliderBox;
	
	public Battery(VBox sliderBox) {
		super(sliderBox);
		
		slider.setMin(-1.5);
		slider.setMax(1.5);
		
		setImage("/img/dc_voltage.png");
	}
	
	@Override
	public void showSliders() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(0, slider);
		Label voltageLabel = new Label();
		voltageLabel.setText("Voltage");
		sliderBox.getChildren().add(1, voltageLabel);
	}
	
	public void changeVoltage(double value) {
		voltage = value;
		//TODO: should probably update the gui after this is called
	}
	
	public double getVoltage() {
		return voltage;
	}

}
