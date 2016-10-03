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

	public static void main(String[] args) {
		System.out.println(Bulle.getBullesFromFile("norma_N5_tau4_dt2_delai820_000000.txt"));
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
