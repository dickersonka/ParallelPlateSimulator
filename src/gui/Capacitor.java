package gui;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class Capacitor extends Container {
	
	private double capacity = 1;
	private Slider areaSlider = new Slider();
	private Slider separationSlider = new Slider();
	private Line line1 = new Line();
	private Line line2 = new Line();
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
				changeLines(areaSlider.getValue(), separationSlider.getValue());
			}
		});
		
		separationSlider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				changeLines(areaSlider.getValue(), separationSlider.getValue());
			}
		});
	}
	
	public void changeLines(double area, double separationDistance) {
		double length = areaRatio*Math.sqrt(area);
		double areaOffset = (64-length)/2;
		double distance = distanceRatio*separationDistance;
		double distanceOffset = (64-distance)/2;
		line1 = new Line(areaOffset, distanceOffset, length+areaOffset, distanceOffset);
		line2 = new Line(areaOffset, distanceOffset+distance, length+areaOffset, distanceOffset+distance);
		this.getChildren().set(1, line1);
		this.getChildren().set(2, line2);
		System.out.println(String.valueOf(area));
	}
	
	public void initializeCapacitorImage() {
		this.getChildren().add(line1);
		this.getChildren().add(line2);
		changeLines(50, 5);
		
		
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

	public void changeCapacity(double value) {
		capacity = value;
		//TODO: should probably update the gui after this is called.
	}
	
	public double getCapacity() {
		return capacity;
	}

}
