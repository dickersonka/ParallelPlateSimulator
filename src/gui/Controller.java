package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class Controller {
	@FXML
	TilePane circuitGrid;
	@FXML
	HBox circuitComponentDock;
	@FXML
	VBox sliderBox;
	
	public final int NUM_TILE_ROWS = 15;
	public final int NUM_TILE_COLS = 20;

	@FXML
	public void initialize() {
		for(int i=0; i<NUM_TILE_COLS * NUM_TILE_ROWS; i++) {
			circuitGrid.getChildren().add(new EmptySpace());
		}
		
		addBasicCircuit();
	}
	
	private void addBasicCircuit() {
		setTile(2,1,new Battery(this));
		setTile(2,3,new Capacitor(this));
		
		setTile(1,1, new Wire());
		getTile(1,1).setImage(Wire.CORNER_WIRE);
		setTile(1,2, new Wire());
		getTile(1,2).turnImageClockwise();
		setTile(1,3, new Wire());
		getTile(1,3).setImage(Wire.CORNER_WIRE);
		getTile(1,3).turnImageClockwise();
		
		setTile(3,1, new Wire());
		getTile(3,1).setImage(Wire.CORNER_WIRE);
		getTile(3,1).turnImageAntiClockwise();
		setTile(3,2, new Wire());
		getTile(3,2).turnImageClockwise();
		setTile(3,3, new Wire());
		getTile(3,3).setImage(Wire.CORNER_WIRE);
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
}
