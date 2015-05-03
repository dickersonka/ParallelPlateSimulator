package gui;

import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class Battery extends Container {
	public final String CONTAINER_TYPE = "BAT";
	
	private Slider slider = new Slider();
	
	public Battery() {
		super();
		
		slider.setMin(-1.5);
		slider.setMax(1.5);
		slider.setValue(0);
		slider.setShowTickLabels(true);
		
		slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				triggerCircuitTraversal();
			}
		});
		
		setImage("/img/battery.png");
	}
	
	public void triggerCircuitTraversal() {
		if(Battery.this.controller.isValidCircuit()) {
			CircuitData cd = new CircuitData();
			cd.setVoltage(Battery.this.slider.getValue());
			Battery.this.outLink.getLinked().giveInput(cd);
		}
	}
	
	public Double getTotalVoltage() {
		return slider.getValue();
	}
	
	public Battery(String s) {
		this();
		
		Scanner reader = new Scanner(s);
		outLink.setDirection(Direction.fromString(reader.next()));
		inLink.setDirection(Direction.fromString(reader.next()));
		
		alignImageToInput();
		updateOutput();
		updateInput();
		controller.validateCircuit();
		controller.setBattery(this);
		
		showComponentControls();
		reader.close();
	}
	
	@Override
	public void giveInput(CircuitData c) {
		double cEq = c.getCEquivalent();
		c.setVoltages(cEq, controller.getTotalVoltage());
	}
	
	@Override
	protected void showComponentControls() {
		super.showComponentControls();
		deleteButton.setDisable(true);
		
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
	
	@Override
	protected boolean canBeDragged() {
		return true;
	}

	@Override
	protected String getComponentType() {
		return CONTAINER_TYPE;
	}
	
	@Override
	protected String getTypeSpecificData() {
		return "";
	}
}
