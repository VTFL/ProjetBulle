import java.util.ArrayList;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Direction extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.10;


	public static ArrayList<Direction> getDirection(ArrayList<Bulle> ar) {
		ArrayList<Direction> couples = new ArrayList<Direction>();

		for (Bulle b1 : ar) {
			for (Bulle b2 : ar) {
				if (!b1.equals(b2)) {
					Direction dir = new Direction();
					dir.add(b1);
					dir.add(b2);
					couples.add(dir);
				}
			}
		}
		System.out.println(couples.size());
		ArrayList<Direction> triplets = new ArrayList<Direction>();
		for(Direction couple : couples){
			Double d = couple.get(0).getDistance(couple.get(1));
			for(Bulle b3 : ar) {
				if(!couple.stream().anyMatch((Bulle b) -> b.equals(b3))){
					if(d-(d*INTERVALLE_PRECISION)< b3.getDistance(couple.get(1)) && b3.getDistance(couple.get(1))< d+(d*INTERVALLE_PRECISION)) {
						Direction dir = new Direction();
						dir.add(couple.get(0));
						dir.add(couple.get(1));
						dir.add(b3);
						triplets.add(dir);

					}
				}
			}
		}
		System.out.println(triplets.size());

		ArrayList<Direction> quatuor = new ArrayList<Direction>();
		for(Direction triplet : triplets){
			Double d = triplet.get(1).getDistance(triplet.get(2));
			for(Bulle b4 : ar) {
				if(!triplet.stream().anyMatch((Bulle b) -> b.equals(b4))){
					if(d-(d*INTERVALLE_PRECISION)< b4.getDistance(triplet.get(2)) && b4.getDistance(triplet.get(2))< d+(d*INTERVALLE_PRECISION)) {

						Direction dir = new Direction();
						for(Bulle b : triplet){
							dir.add(b);
						}
						dir.add(b4);
						quatuor.add(dir);
					}
				}
			}
		}
		System.out.println(quatuor.size());
		return null;
	}
}