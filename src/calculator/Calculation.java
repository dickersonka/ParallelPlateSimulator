package calculator;

public class Calculation {
	Calculation() {
		
	}
	
	private double e0 = 8.854;
	
	public double calculate(int missingtwo, double area, double capacitance, double k, double distance){
		if(missingtwo >= 2){
			return 0;
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
		return 1;
		
	}
	
}
