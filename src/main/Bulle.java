package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Bulle {
	private double x;
	private double y;
	private double z;



	public Bulle(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}



	public Double getDistance(Bulle b){
		return Math.sqrt(Math.pow(this.getX()-b.getX(),2) +
				Math.pow(this.getY()-b.getY(),2) +
				Math.pow(this.getZ()-b.getZ(),2));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Bulle{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
}
