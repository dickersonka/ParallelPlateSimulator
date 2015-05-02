package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import calculator.Calculation;
import gui.Controller;

public class CalculatorTest {
	
	Controller c = new Controller();
	Calculation cal = new Calculation();
	
	@Test
	public void testMissingtwo(){
		int missingtwo = 1;
		double area = 0; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance).equals("need more information"));
	}
	
	@Test
	public void testFourValueAlreadyEnter(){
		int missingtwo = 0;
		double area = 2; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance).equals("You already put 4 values in there"));
	}
	
	@Test
	public void testCalArea(){
		int missingtwo = 0;
		double area = 0; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		String answer = "2.0";
		assertTrue(answer.equals(cal.calculate(missingtwo, area, capacitance, k, distance)));
	}
	
	@Test
	public void testCalCapacitance(){
		int missingtwo = 0;
		double area = 4; 
		double capacitance = 0; 
		double k = 2;
		double distance = 8.854;
		String answer = "8.0";
		assertTrue(answer.equals(cal.calculate(missingtwo, area, capacitance, k, distance)));
	}
	
	@Test
	public void testCalK(){
		int missingtwo = 0;
		double area = 4; 
		double capacitance = 4; 
		double k = 0;
		double distance = 8.854;
		String answer = "1.0";
		assertTrue(answer.equals(cal.calculate(missingtwo, area, capacitance, k, distance)));
	}
	
	@Test
	public void testCalDistance(){
		int missingtwo = 0;
		double area = 2; 
		double capacitance = 4; 
		double k = 2;
		double distance = 0;
		String answer = "8.854";
		assertTrue(answer.equals(cal.calculate(missingtwo, area, capacitance, k, distance)));
	}
}
