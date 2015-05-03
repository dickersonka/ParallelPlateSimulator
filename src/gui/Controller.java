package gui;

import gui.Link.LinkType;
import gui.Wire.WireType;
import calculator.Calculation;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Controller {
	//===================================================\\
	//					Simulator Tab					 \\
	//===================================================\\
	
	@FXML
	TilePane circuitGrid;
	@FXML
	HBox circuitComponentDock;
	@FXML
	Label circuitStatus;
	@FXML
	VBox sliderBox;
	@FXML 
	ImageView straightWire;
	
	private Capacitor capacitor;
	private Battery battery;
	private boolean circuitIsValid = false;
	public final int NUM_TILE_ROWS = 7;
	public final int NUM_TILE_COLS = 10;

	@FXML
	public void initialize() {
		ControllerPointer.setController(this);
		sliderBox.setSpacing(20);
		for(int i=0; i<NUM_TILE_COLS * NUM_TILE_ROWS; i++) {
			circuitGrid.getChildren().add(new EmptySpace());
		}
		
		addBasicCircuit();
		circuitComponents();
	}
	
	private void addBasicCircuit() {
		battery = new Battery();
		setTile(2,1, battery);
		capacitor = new Capacitor();
		setTile(2,3, capacitor);
		getTile(2,3).turnImageClockwise();
		getTile(2,3).turnImageClockwise();
		
		setTile(1,1, new Wire(WireType.CORNER));
		setTile(1,2, new Wire());
		getTile(1,2).turnImageClockwise();
		setTile(1,3, new Wire(WireType.CORNER));
		getTile(1,3).turnImageClockwise();
		
		setTile(3,1, new Wire(WireType.CORNER));
		getTile(3,1).turnImageAntiClockwise();
		setTile(3,2, new Wire());
		getTile(3,2).turnImageAntiClockwise();
		setTile(3,3, new Wire(WireType.CORNER));
		getTile(3,3).turnImageClockwise();
		getTile(3,3).turnImageClockwise();
		
		validateCircuit();
	}
	
	private Container getTile(int row, int col) {
		return (Container) circuitGrid.getChildren().get(toIdx(row,col));
	}
	
	private void setTile(int row, int col, Container c) {
		circuitGrid.getChildren().set(toIdx(row,col), c);
	}
	
	private int toIdx(int row, int col) throws IllegalArgumentException {
		if(row >= 0 && row < NUM_TILE_ROWS && col >= 0 && col < NUM_TILE_COLS) {
			return (row * NUM_TILE_COLS) + col;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private int toRow(int idx) throws IllegalArgumentException {
		if(idx >= 0 || idx < NUM_TILE_ROWS * NUM_TILE_COLS) {
			return idx/NUM_TILE_COLS;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private int toCol(int idx) throws IllegalArgumentException {
		if(idx >= 0 || idx < NUM_TILE_ROWS * NUM_TILE_COLS) {
			return idx % NUM_TILE_COLS;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public VBox getSliderBox() {
		return sliderBox;
	}
	
	public Capacitor getCapacitor() {
		return capacitor;
	}
	
	public void setBattery(Battery b) {
		battery = b;
	}
	
	public double getTotalVoltage() {
		return battery.getTotalVoltage();
	}
	
	private void circuitComponents() {
		circuitComponentDock.getChildren().add(new Wire(WireType.T_SECTION_IN));
		circuitComponentDock.getChildren().add(new Wire(WireType.CORNER));
		circuitComponentDock.getChildren().add(new Wire());
		circuitComponentDock.getChildren().add(new Capacitor());
		circuitComponentDock.getChildren().add(new Wire(WireType.T_SECTION_OUT));
	}
	

	
	@FXML
	public void dragDetected(MouseEvent t){
		
	}
	
	@FXML
	private void dragOver(DragEvent e){

	}
	
	@FXML
	private void dragDone(DragEvent e){
		
	}
	
	public void removeComponent(Container container) {
		if(circuitGrid.getChildren().indexOf(container) > -1){
		circuitGrid.getChildren().set(circuitGrid.getChildren().indexOf(container), new EmptySpace());
		sliderBox.getChildren().clear();
		}
	}

	public Container getComponentInDir(Container center, Direction dir) {
		try {
			int idx = circuitGrid.getChildren().indexOf(center);
			Container result;
			switch(dir) {
			case NORTH:
				result = getTile(toRow(idx)-1, toCol(idx));
				if(result.getClass() == EmptySpace.class)
					return null;
				return result;
			case EAST:
				result = getTile(toRow(idx), toCol(idx)+1);
				if(result.getClass() == EmptySpace.class)
					return null;
				return result;
			case SOUTH:
				result = getTile(toRow(idx)+1, toCol(idx));
				if(result.getClass() == EmptySpace.class)
					return null;
				return result;
			case WEST:
				result = getTile(toRow(idx), toCol(idx)-1);
				if(result.getClass() == EmptySpace.class)
					return null;
				return result;
			}
		} catch(Exception x) {
			return null;
		}
		
		return null;
	}

	public void replaceComponent(Container current, Container replacement) {
		circuitGrid.getChildren().set(getIndexOfComponent(current), replacement);
	}
	
	public void replaceComponent(int idx, Container replacement) {
		circuitGrid.getChildren().set(idx, replacement);
	}
	
	public int getIndexOfComponent(Container c) {
		return circuitGrid.getChildren().indexOf(c);
	}
	
	public void triggerCircuitTraversal() {
		battery.triggerCircuitTraversal();
	}
	
	public void validateCircuit() {
		circuitIsValid = validateComponent(battery, false);
		
		if(circuitIsValid) {
			circuitStatus.setText("Status: OK");
			battery.triggerCircuitTraversal();
		}
		else {
			circuitStatus.setText("Status: Invalid Wiring");
		}
	}
	
	private boolean validateComponent(Container c, boolean validity) {
		if(c == null) {
			return false;
		}
		
		c.updateOutput();
		if(c.getClass() == Wire.class && ((Wire) c).getType().isTSection()) {
			Link l = ((Wire) c).getExtraLink();
			
			if(l.getType() == LinkType.OUT) {
				validity = validity || validateLink(l,validity);
			}
		}
		
		Link l = c.getOutLink();
		return validity || validateLink(l,validity);
	}
	
	private boolean validateLink(Link l, boolean validity) {
		if(l.getLinked() != null)
			l.getLinked().updateInput();
		
		if(!l.linkUpIsValid()) {
			validity = false;
		} else if(l.getLinked() == battery) {
			validity = true;
		} else {
			validity = validateComponent(l.getLinked(), validity);
		}
		
		return validity;
	}

	public boolean isValidCircuit() {
		return circuitIsValid;
	}
	
	//===================================================\\
	//					Calculator Tab					 \\
	//===================================================\\
	
	@FXML
	Button Area;
	@FXML
	Button K;
	@FXML
	Button Capacity;
	@FXML
	Button Distance;
	@FXML
	TextField resultArea;
	@FXML
	TextField resultK;
	@FXML
	TextField resultCapacity;
	@FXML
	TextField resultDistance;
	@FXML
	Label questions;
	
	
	private double area;
	private double capacitance;
	private double distance;
	private double k;
	private int morethantwomissing = 0;
	
	Calculation c = new Calculation();
	
	@FXML
	private void runCalculator() throws IllegalAccessException{
		try{
		if(!isAreaEmpty() && !resultArea.isDisable()){
			area = Double.parseDouble(resultArea.getText());
		}
		else if(resultArea.isDisable()){
			resultArea.clear();
			area = 0;
		}
		else{
			morethantwomissing++;
			area = 0;
		}
		if(!isCapacityEmpty() && !resultCapacity.isDisable()){
			capacitance = Double.parseDouble(resultCapacity.getText());
		}
		else if(resultCapacity.isDisable()){
			resultCapacity.clear();
			capacitance = 0;
		}
		else{
			morethantwomissing++;
			capacitance = 0;
		}
		if(!isKEmpty() && !resultK.isDisable()){
			k = Double.parseDouble(resultK.getText());
		}
		else if(resultK.isDisable()){
			resultK.clear();
			k = 0;
		}
		else{
			morethantwomissing++;
			k = 0;
		}
		if(!isDistanceEmpty() && !resultDistance.isDisable()){
			distance = Double.parseDouble(resultDistance.getText());
		}
		else if(resultDistance.isDisable()){
			resultDistance.clear();
			distance = 0;
		}
		else{
			morethantwomissing++;
			distance = 0;
		}
		getResult();
		} catch(NumberFormatException NFE){
			questions.setText("Enter a valid value"); 
		}
	}
	
	@FXML
	private void areaBtn(){
		resultArea.setDisable(true);
		resultK.setDisable(false);
		resultCapacity.setDisable(false);
		resultDistance.setDisable(false);
		
		Area.setTextFill(Color.BLUE);
		K.setTextFill(Color.BLACK);
		Capacity.setTextFill(Color.BLACK);
		Distance.setTextFill(Color.BLACK);
	}
	
	@FXML
	private void KBtn(){
		resultArea.setDisable(false);
		resultK.setDisable(true);
		resultCapacity.setDisable(false);
		resultDistance.setDisable(false);
		
		Area.setTextFill(Color.BLACK);
		K.setTextFill(Color.BLUE);
		Capacity.setTextFill(Color.BLACK);
		Distance.setTextFill(Color.BLACK);
	}
	
	@FXML
	private void capacityBtn(){
		resultArea.setDisable(false);
		resultK.setDisable(false);
		resultCapacity.setDisable(true);
		resultDistance.setDisable(false);
		
		Area.setTextFill(Color.BLACK);
		K.setTextFill(Color.BLACK);
		Capacity.setTextFill(Color.BLUE);
		Distance.setTextFill(Color.BLACK);
	}
	
	@FXML
	private void distanceBtn(){
		resultArea.setDisable(false);
		resultK.setDisable(false);
		resultCapacity.setDisable(false);
		resultDistance.setDisable(true);
		
		Area.setTextFill(Color.BLACK);
		K.setTextFill(Color.BLACK);
		Capacity.setTextFill(Color.BLACK);
		Distance.setTextFill(Color.BLUE);
	}

	private void getResult(){
		try{
			if(resultArea.isDisable()){
				resultArea.setText(getValue());
			}
			if(resultCapacity.isDisable()){
				resultCapacity.setText(getValue());
			}
			if(resultK.isDisable()){
				resultK.setText(getValue());
			}
			if(resultDistance.isDisable()){
				resultDistance.setText(getValue());
			}
			questions.setText("Enter Three Values");
			morethantwomissing = 0;
		}
		catch(IllegalAccessException e){
			questions.setText("need more information");
			morethantwomissing = 0;
		}
	}
	
	private String getValue() throws IllegalAccessException{
		return Double.toString(c.calculate(morethantwomissing, area, capacitance, k, distance));
	}
	
	private boolean isAreaEmpty(){
		return resultArea.getText().equals("");
	}
	
	private boolean isCapacityEmpty(){
		return resultCapacity.getText().equals("");
	}
	
	private boolean isKEmpty(){
		return resultK.getText().equals("");
	}
	
	private boolean isDistanceEmpty(){
		return resultDistance.getText().equals("");
	}
}
