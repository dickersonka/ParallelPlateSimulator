package gui;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

public class Capacitor extends Container {
	public final static int AREA_RATIO = 6;
	public final static int DISTANCE_RATIO = 600;
	public final String CONTAINER_TYPE = "CAP";
	
	private Slider areaSlider = new Slider();
	private Slider separationSlider = new Slider();
	private Line topPlate = new Line();
	private Line bottomPlate = new Line();
	private Line topWire = new Line();
	private Line bottomWire = new Line();
	private double voltage = 0;
	private ArrayList<Group> fieldLines = new ArrayList<Group>();
	private double e0 = 8.854*Math.pow(10, -12);

	public Capacitor() {
		super();
		
		setupSliders();
		setImage("/img/background.png");
		initializeCapacitorImage();
	}
	
	public Capacitor(String s) {
		this();
		
		Scanner reader = new Scanner(s);
		outLink.setDirection(Direction.fromString(reader.next()));
		inLink.setDirection(Direction.fromString(reader.next()));
		
		alignImageToInput();
		updateOutput();
		updateInput();
		controller.validateCircuit();
		
		showComponentControls();
		reader.close();
	}
	
	public void setupSliders() {
		areaSlider.setMin(1);
		areaSlider.setMax(100);
		areaSlider.setValue(50);
		areaSlider.setShowTickLabels(true);
		areaSlider.setOrientation(Orientation.HORIZONTAL);
		
		separationSlider.setMin(0.01);
		separationSlider.setMax(0.1);
		separationSlider.setValue(0.05);
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
		double length = AREA_RATIO*Math.sqrt(area);
		double areaOffset = (64-length)/2;
		double distance = DISTANCE_RATIO*separationDistance;
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
		controller.triggerCircuitTraversal();
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
		//TODO: electric field line density increases as distance decreases
				//electric field line density stays the same as area changes
				//electric field line density increases as voltage increases, flips as voltage is switched;
		//so let's say we have a max density of 15 lines per 30 pixels of area?
		double lines = Math.abs(Math.sqrt(areaSlider.getValue())/30*voltage/separationSlider.getValue());	
		double spacing = AREA_RATIO*Math.sqrt(areaSlider.getValue())/((int) lines);
		double xStart = topPlate.getStartX() + spacing/2;
		for (int i = 0; i < (int) lines; i++) {
			fieldLines.add(new Arrow(xStart, yStart, xStart, yEnd).getArrow());
			xStart += spacing;
		}
		this.getChildren().addAll(fieldLines);
	}
	
	private void initializeCapacitorImage() {
		this.getChildren().add(topPlate);
		this.getChildren().add(bottomPlate);
		this.getChildren().add(topWire);
		this.getChildren().add(bottomWire);
		changeCapacitor(50, 0.05);
	
	}
	
	public double getCapacity() {
		return e0*areaSlider.getValue()/separationSlider.getValue();
	}
	
	@Override
	protected void showComponentControls() {
		super.showComponentControls();
		HBox controlBox = (HBox)sliderBox.getChildren().get(0);
		controlBox.getChildren().get(0).setDisable(true);
		controlBox.getChildren().get(2).setDisable(true);
		
		sliderBox.getChildren().add(areaSlider);
		Label areaLabel = new Label();
		areaLabel.setText("Plate Area");
		sliderBox.getChildren().add(areaLabel);
		sliderBox.getChildren().add(separationSlider);
		Label distanceLabel = new Label();
		distanceLabel.setText("Plate Separation Distance");
		sliderBox.getChildren().add(distanceLabel);
		//TODO: if these go in the boxes like I expect, they will need to be offset. somehow.
	}
	
	@Override
	public void giveInput(CircuitData c) {
		c.addToSeries(this);
		//TODO: this should not immediately change the field lines. it should wait until it comes back through the list.
		//changeFieldLines(c.getVoltage());
		if (outLink.getLinked() != null) {
			outLink.getLinked().giveInput(c);
		}
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
