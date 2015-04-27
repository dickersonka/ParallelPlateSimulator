package calculator;

public class Calculation {
	public Calculation() {
		
	}
	
	private double e0 = 8.854;
	public String calculate(int missingtwo, double area, double capacitance, double k, double distance){
		if(missingtwo >= 1){
			return "need more information";
		}
		else{
			if(area == 0){
				return Double.toString(capacitance * distance / e0 / k);
			}
			else if(capacitance == 0){
				return Double.toString(k * e0 * area / distance);					
			}
			else if(k == 0){
				return Double.toString(capacitance * distance / e0 / area);
			}
			else if(distance == 0){
				return Double.toString(k * e0 * area / capacitance);
			}
		}
		return "You already put 4 values in there";
		
	}
	
}
