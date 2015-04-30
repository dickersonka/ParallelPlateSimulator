package gui;

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
	VBox sliderBox;
	@FXML
	ImageView straightWire;
	
	private Capacitor capacitor;
	public final int NUM_TILE_ROWS = 7;
	public final int NUM_TILE_COLS = 10;

	@FXML
	public void initialize() {
		
		sliderBox.setSpacing(20);
		for(int i=0; i<NUM_TILE_COLS * NUM_TILE_ROWS; i++) {
			circuitGrid.getChildren().add(new EmptySpace());
		}
		
		addBasicCircuit();
		circuitComponents();
	}
	
	private void addBasicCircuit() {
		setTile(2,1, new Battery(this));
		capacitor = new Capacitor(this);
		setTile(2,3, capacitor);
		getTile(2,3).turnImageClockwise();
		getTile(2,3).turnImageClockwise();
		
		setTile(1,1, new Wire(this, WireType.CORNER));
		setTile(1,2, new Wire(this));
		getTile(1,2).turnImageClockwise();
		setTile(1,3, new Wire(this, WireType.CORNER));
		getTile(1,3).turnImageClockwise();
		
		setTile(3,1, new Wire(this, WireType.CORNER));
		getTile(3,1).turnImageAntiClockwise();
		setTile(3,2, new Wire(this));
		getTile(3,2).turnImageClockwise();
		setTile(3,3, new Wire(this, WireType.CORNER));
		getTile(3,3).turnImageClockwise();
		getTile(3,3).turnImageClockwise();
		
	}
	
	private Container getTile(int row, int col) {
		return (Container) circuitGrid.getChildren().get(toIdx(row,col));
	}
	
	private void setTile(int row, int col, Container c) {
		circuitGrid.getChildren().set(toIdx(row,col), c);
	}
	
	private int toIdx(int row, int col) throws IllegalArgumentException{
		if(row >= 0 && row < NUM_TILE_ROWS && col >= 0 && col < NUM_TILE_COLS) {
			return (row * NUM_TILE_COLS) + col;
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
	
	private void circuitComponents() {
		Image wire =  new Image(Wire.STRAIGHT_WIRE_IMG);
		straightWire.setImage(wire);
	}
	
	@FXML
	private void dragDetected(MouseEvent t){
		straightWire.setOpacity(0.5);
		straightWire.toFront();
		straightWire.setMouseTransparent(true);
		straightWire.setVisible(true);
		straightWire.relocate(
                (int) (t.getSceneX() - straightWire.getBoundsInLocal().getWidth() / 2),
                (int) (t.getSceneY() - straightWire.getBoundsInLocal().getHeight() / 2));
		
		Dragboard db = circuitGrid.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();

        content.putString(Wire.STRAIGHT_WIRE_IMG);
        db.setContent(content);
        
		t.consume();
	}
	
	@FXML
	private void dragOver(DragEvent e){
		Point2D localPoint = circuitGrid.getScene().getRoot().sceneToLocal(new Point2D(e.getSceneX(), e.getSceneY()));
		straightWire.relocate(
				(int) (localPoint.getX() - straightWire.getBoundsInLocal().getWidth() / 2),
				(int) (localPoint.getY() - straightWire.getBoundsInLocal().getHeight() / 2));
			e.consume();
	}
	
	@FXML
	private void dragDone(DragEvent e){
		straightWire.setVisible(false);
		e.consume();
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
	
	
	private double e0 = 8.854;
	private String e = " x 10^(-12) F / m";
	private double area;
	private double capacitance;
	private double distance;
	private double k;
	private int morethantwomissing = 0;
	
	Calculation c = new Calculation();
	
	@FXML
	private void runCalculator(){
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
		if(getValue().equals("need more information")){
			questions.setText(getValue()); 
		}
		else if(getValue().equals("You already put 4 values in there")){
			questions.setText(getValue());
		}
		else{
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
		}
		morethantwomissing = 0;
	}
	
	private String getValue(){
		return c.calculate(morethantwomissing, area, capacitance, k, distance);
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
