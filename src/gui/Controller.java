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
	
	Container[][] tiles;
	
	public final int NUM_TILE_ROWS = 20;
	public final int NUM_TILE_COLS = 15;

	@FXML
	public void initialize() {
		tiles = new Container[NUM_TILE_ROWS][NUM_TILE_COLS];
		
		for(int c=0; c<NUM_TILE_COLS; c++) {
			for(int r=0; r<NUM_TILE_ROWS; r++) {
				//tiles[r][c] = new EmptySpace
			}
		}
	}
}
