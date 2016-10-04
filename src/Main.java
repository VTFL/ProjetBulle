import java.util.ArrayList;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Main {
    public static void main(String[] args) {
		ArrayList<Bulle> ar = Bulle.getBullesFromFile("norma_N5_tau4_dt2_delai820_000000.txt");
        System.out.println(ar);
		System.out.println(ar.get(4).getDistance(ar.get(5)));
		Direction.getDirection(ar);
	}




}
