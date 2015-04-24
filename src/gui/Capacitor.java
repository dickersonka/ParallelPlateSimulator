package gui;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Capacitor extends Container {
	
	private Slider areaSlider = new Slider();
	private Slider separationSlider = new Slider();
	private Line topPlate = new Line();
	private Line bottomPlate = new Line();
	private Line topWire = new Line();
	private Line bottomWire = new Line();
	private double voltage = 0;
	private ArrayList<Group> fieldLines = new ArrayList<Group>();
	public final int areaRatio = 6;
	public final int distanceRatio = 6;

	public Capacitor(Controller controller) {
		super(controller);
		
		setupSliders();
		setImage("/img/background.png");
		initializeCapacitorImage();
		
	}
	
	public void setupSliders() {
		areaSlider.setMin(1);
		areaSlider.setMax(100);
		areaSlider.setValue(50);
		areaSlider.setShowTickLabels(true);
		areaSlider.setOrientation(Orientation.HORIZONTAL);
		
		separationSlider.setMin(0.1);
		separationSlider.setMax(10);
		separationSlider.setValue(5);
		separationSlider.setShowTickLabels(true);
		
		areaSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				changeCapacitor(areaSlider.getValue(), separationSlider.getValue());
			}
		});
		
		separationSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				changeCapacitor(areaSlider.getValue(), separationSlider.getValue());
			}
		});
	}
	
	public void changeCapacitor(double area, double separationDistance) {
		double length = areaRatio*Math.sqrt(area);
		double areaOffset = (64-length)/2;
		double distance = distanceRatio*separationDistance;
		double distanceOffset = (64-distance)/2;
		topPlate = new Line(areaOffset, distanceOffset, length+areaOffset, distanceOffset);
		bottomPlate = new Line(areaOffset, distanceOffset+distance, length+areaOffset, distanceOffset+distance);
		topPlate.setStrokeWidth(4);
		bottomPlate.setStrokeWidth(4);
		topWire = new Line(32, 0, 32, distanceOffset);
		bottomWire = new Line(32, distanceOffset+distance, 32, 64);
		topWire.setStrokeWidth(4);
		bottomWire.setStrokeWidth(4);
		this.getChildren().set(1, topPlate);
		this.getChildren().set(2, bottomPlate);
		this.getChildren().set(3, topWire);
		this.getChildren().set(4, bottomWire);
		changeFieldLines(voltage);
	}
	
	public void changeFieldLines(double batteryVoltage) {
		voltage = batteryVoltage;
		this.getChildren().removeAll(fieldLines);
		fieldLines.clear();
		double yStart = 0;
		double yEnd = 0;
		if (voltage > 0) {
			yStart = topPlate.getStartY();
			yEnd = bottomPlate.getStartY();
		}
		else if (voltage < 0) {
			yStart = bottomPlate.getStartY();
			yEnd = topPlate.getStartY();
		}
		//TODO: depending on separation, area, and voltage, this will vary the density! some sort of loop will be required
		//TODO: electric field line density increases as distance decreases
				//electric field line density stays the same as area changes
				//electric field line density increases as voltage increases, flips as voltage is switched;
		//what is the range of delta v/d? 0 to 1.5/0.1 = 15;
		//so let's say we have a max density of 15 lines per 30 pixels of area?
		double range = Math.sqrt(areaSlider.getValue())/30*voltage/separationSlider.getValue();
		//TODO: these values need to be adjusted. right now it's not showing anything unless it's too small to see!
		int cr = (int) range;
		System.out.println(String.valueOf(range));
		
		for (int i = 0; i < (int) range; i++) {
			//TODO: then change the x values so that it's evenly distributed;
			fieldLines.add(new Arrow(i*5, yStart, i*5, yEnd).getArrow());
		}
		//fieldLines.add(new Arrow(5, yStart, 5, yEnd).getArrow());
		this.getChildren().addAll(fieldLines);
	}
	
	private void initializeCapacitorImage() {
		this.getChildren().add(topPlate);
		this.getChildren().add(bottomPlate);
		this.getChildren().add(topWire);
		this.getChildren().add(bottomWire);
		changeCapacitor(50, 5);
	
	}
	
	@Override
	public void showSliders() {
		sliderBox.getChildren().clear();
		sliderBox.getChildren().add(0, areaSlider);
		Label areaLabel = new Label();
		areaLabel.setText("Plate Area");
		sliderBox.getChildren().add(1, areaLabel);
		sliderBox.getChildren().add(2, separationSlider);
		Label distanceLabel = new Label();
		distanceLabel.setText("Plate Separation Distance");
		sliderBox.getChildren().add(3, distanceLabel);
		//TODO: if these go in the boxes like I expect, they will need to be offset. somehow.
	}

}
