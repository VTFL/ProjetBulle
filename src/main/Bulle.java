package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Bulle {
	private double x;
	private double y;
	private double truc1;
	private double truc2;
	private double truc3;



	public Bulle(double x, double y,double truc1, double truc2, double truc3) {
		this.x = x;
		this.y = y;
		this.truc1 = truc1;
		this.truc2 = truc2;
		this.truc3 = truc3;

	}



	public Double getDistance(Bulle b){
		return Math.sqrt(Math.pow(this.getX()-b.getX(),2) +
				Math.pow(this.getY()-b.getY(),2));
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

	public double getTruc1() {
		return truc1;
	}

	public void setTruc1(double truc1) {
		this.truc1 = truc1;
	}

	public double getTruc2() {
		return truc2;
	}

	public void setTruc2(double truc2) {
		this.truc2 = truc2;
	}

	public double getTruc3() {
		return truc3;
	}

	public void setTruc3(double truc3) {
		this.truc3 = truc3;
	}

	@Override
	public String toString() {
		return "Bulle{" +
				"x=" + x +
				", y=" + y +
				", truc1=" + truc1 +
				", truc2=" + truc2 +
				", truc3=" + truc3 +
				'}';
	}
}
