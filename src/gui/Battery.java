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
				Battery.this.controller.getCapacitor().changeFieldLines(slider.getValue());
			}
		});
		
		setImage("/img/battery.png");
	}
	
	@Override
	protected void showComponentControls() {
		super.showComponentControls();
		
		sliderBox.getChildren().add(slider);
		Label voltageLabel = new Label();
		voltageLabel.setText("Voltage");
		sliderBox.getChildren().add(voltageLabel);
	}

	@Override
	protected void highlight() {
		this.getChildren().add(CANNOT_DROP_HIGHLIGHT);
	}

	@Override
	protected void dehighlight() {
		this.getChildren().remove(CANNOT_DROP_HIGHLIGHT);
	}

	@Override
	protected boolean canBeDroppedOn() {
		return false;
	}

}
