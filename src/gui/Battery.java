package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class Battery extends Container {

	private Slider slider = new Slider();
	
	public Battery(Controller controller) {
		super(controller);
		
		slider.setMin(-1.5);
		slider.setMax(1.5);
		slider.setValue(0);
		slider.setShowTickLabels(true);
		
		slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				this.controller.getCapacitor().changeFieldLines(slider.getValue());
			}
		});
		
		setImage("/img/battery.png");
	}
	
	@Override
	public void showSliders() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(0, slider);
		Label voltageLabel = new Label();
		voltageLabel.setText("Voltage");
		sliderBox.getChildren().add(1, voltageLabel);
	}

}
