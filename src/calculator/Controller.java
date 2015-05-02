package calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {
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
	private void initialize(){
	}
	
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
	
	private void getResult() throws IllegalAccessException{
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
