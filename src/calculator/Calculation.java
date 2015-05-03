package calculator;

public class Calculation {
	public Calculation() {
		
	}
	
	private double e0 = 8.854*Math.pow(10, -12);
	public double calculate(int missingtwo, double area, double capacitance, double k, double distance) throws IllegalAccessException{
		if(missingtwo >= 1){
			throw new IllegalAccessException("need more information"); 
		}
		else{
			if(area == 0){
				return capacitance * distance / e0 / k;
			}
			else if(capacitance == 0){
				return k * e0 * area / distance;					
			}
			else if(k == 0){
				return capacitance * distance / e0 / area;
			}
			else if(distance == 0){
				return k * e0 * area / capacitance;
			}
			
		}
		throw new IllegalAccessException("You already put 4 values in there");
		
	}
	
}
