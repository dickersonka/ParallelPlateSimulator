package gui;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Capacitor extends Container {
	
	private double capacity = 1;
	private Slider areaSlider = new Slider();
	private Slider separationSlider = new Slider();
	public final int areaRatio = 3;

	public Capacitor(Controller controller) {
		super(controller);
		
		areaSlider.setMin(1);
		areaSlider.setMax(100);
		areaSlider.setValue(50);
		areaSlider.setShowTickLabels(true);
		areaSlider.setOrientation(Orientation.HORIZONTAL);
		separationSlider.setMin(0.1);
		separationSlider.setMax(10);
		separationSlider.setValue(5);
		separationSlider.setShowTickLabels(true);
		//TODO: once get updated background image, remove remove line
		initializeCapacitorImage();
		
	}
	
	public void initializeCapacitorImage() {
		this.getChildren().remove(0);
		Line line1 = new Line(5, 10, areaRatio*Math.sqrt(50)+5, 10);
		Line line2 = new Line(5, 20, areaRatio*Math.sqrt(50)+5, 20);
		line1.setStroke(Color.BLACK);
		this.getChildren().add(line1);
		this.getChildren().add(line2);
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
