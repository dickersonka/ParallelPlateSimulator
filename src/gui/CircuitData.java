package gui;

import java.util.ArrayList;

public class CircuitData {
	private double voltage;
	private ArrayList<Double> capacitiesInSeries = new ArrayList<Double>();
	private ArrayList<Capacitor> capacitorsInSeries = new ArrayList<Capacitor>();
	
	public CircuitData() {}

	public void setVoltage(double v) {
		voltage = v;
	}

	public double getVoltage() {
		return voltage;
	}
	
	public void addToSeries(Capacitor capacitor) {
		capacitiesInSeries.add(capacitor.getCapacity());
		capacitorsInSeries.add(capacitor);
	}
	
	public Double getCEquivalent() {
		double sum = 0;
		for (Double capacity: capacitiesInSeries) {
			sum += (1/capacity);
		}
		if (sum != 0) {
			return 1.0/sum;
		}
		else {
			return 0.0;
		}
	}
	
	public void setVoltages(Double cEq) {
		double charge = cEq*voltage;
		for (Capacitor capacitor: capacitorsInSeries) {
			capacitor.changeFieldLines(charge/capacitor.getCapacity());
		}
	}
	
	public CircuitData getClone() {
		CircuitData clone = new CircuitData();
		clone.setVoltage(voltage);
		//TODO: is this always going to be the voltage there?
		return clone;
		
	}
	
}
