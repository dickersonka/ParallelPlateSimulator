package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import calculator.Calculation;

public class DensityTest {
	
	Calculation calculation = new Calculation();
	private int ratio = 6;
	
	@Test
	public void testVoltageChange() {
		double initialSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.1, 0.5));
		double finalSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.1, 1.5));
		assertTrue(finalSpacing < initialSpacing);
	}
	
	@Test
	public void testNegativeVoltageChange() {
		double initialSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.1, 1.5));
		double finalSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.1, -1.5));
		assertEquals(finalSpacing, initialSpacing, 0.01);
	}
	
	@Test
	public void testSeparationChange() {
		double initialSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.1, 1.5));
		double finalSpacing = calculation.getSpacing(ratio, 0.1, calculation.getNumLines(10, 0.05, 1.5));
		assertTrue(finalSpacing < initialSpacing);
	}

}
