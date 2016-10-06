import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Bulle {
	private double x;
	private double y;
	private double z;



	public Bulle(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public static ArrayList<Bulle> getBullesFromFile(String filename){
		File file = new File(filename);
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			ArrayList<Bulle> ar = new ArrayList<Bulle>();
			for (String ligne = bf.readLine(); ligne != null; ligne = bf.readLine()){
				double x = Double.valueOf(ligne.split("   ")[1]);
				double y = Double.valueOf(ligne.split("   ")[2]);
				double z = Double.valueOf(ligne.split("   ")[3]);
				ar.add(new Bulle(x,y,z));
			}
			return ar;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	public Double getDistance(Bulle b){
		return Math.sqrt(Math.pow(this.getX()-b.getX(),2) +
				Math.pow(this.getY()-b.getY(),2) );
				//Math.pow(this.getZ()-b.getZ(),2));
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
