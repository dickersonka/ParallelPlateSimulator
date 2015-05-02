package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import calculator.Calculation;
import gui.Controller;

public class CalculatorTest {
	
	Controller c = new Controller();
	Calculation cal = new Calculation();
	
	@Test(expected = IllegalAccessException.class)
	public void testMissingtwo() throws IllegalAccessException {
		int missingtwo = 1;
		double area = 0; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		cal.calculate(missingtwo, area, capacitance, k, distance);
		
	}
	
	@Test(expected = IllegalAccessException.class)
	public void testFourValueAlreadyEnter() throws IllegalAccessException{
		int missingtwo = 0;
		double area = 2; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		cal.calculate(missingtwo, area, capacitance, k, distance);
	}
	
	@Test
	public void testCalArea() throws IllegalAccessException {
		int missingtwo = 0;
		double area = 0; 
		double capacitance = 4; 
		double k = 2;
		double distance = 8.854;
		double answer = 2.0;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance) == answer);
	}
	
	@Test
	public void testCalCapacitance() throws IllegalAccessException{
		int missingtwo = 0;
		double area = 4; 
		double capacitance = 0; 
		double k = 2;
		double distance = 8.854;
		double answer = 8.0;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance) == answer);
	}
	
	@Test
	public void testCalK() throws IllegalAccessException{
		int missingtwo = 0;
		double area = 4; 
		double capacitance = 4; 
		double k = 0;
		double distance = 8.854;
		double answer = 1.0;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance) == answer);
	}
	
	@Test
	public void testCalDistance() throws IllegalAccessException{
		int missingtwo = 0;
		double area = 2; 
		double capacitance = 4; 
		double k = 2;
		double distance = 0;
		double answer = 8.854;
		assertTrue(cal.calculate(missingtwo, area, capacitance, k, distance) == answer);
	}
}
