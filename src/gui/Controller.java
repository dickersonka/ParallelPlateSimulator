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
		setTile(1,1,new Battery());
		setTile(1,3,new Capacitor());
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
}
