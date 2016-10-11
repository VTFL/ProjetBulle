package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Trajectoire extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.10;
	final static double ANGLE = 145*Math.PI/180; // radian
	final static double DISTANCE_MAX = 1.5;
	//0,2 m/s (xy) 0,1m/s(z)
	// dt = 2ms


    //renvoi un angle alpha a partir des distance a b et c
    public static double alKashi(double a,double b,double c){
        return Math.acos((Math.pow(a,2) + Math.pow(b,2) - Math.pow(c,2))/(2*a*b));
    }
    public static double det( Bulle a, Bulle b){
		return (a.getX()*b.getY()-a.getY()*b.getX());
	}
	public static double angleOriente(Bulle a,Bulle b,Bulle c){
		if(det(a,b) < 0){
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b));
		}else{
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b))*-1;
		}
	}

	public static void main(String[] args) {
		// test calcul distance et angle
		Bulle a,b,c,d,o;
		a = new Bulle(0,1,0);
		b = new Bulle(1,2,0);
		c = new Bulle(2,1,0);
		d = new Bulle(1,0,0);
		o = new Bulle(1,1,0);
		System.out.println(Trajectoire.angleOriente(c,b,o)*180/Math.PI);
		System.out.println(c.getDistance(d));

	}

	public static boolean isBulleOK(Trajectoire dir, Bulle b){
		int i = dir.size();
		double distance = b.getDistance(dir.get(i - 1));
		double alpha = angleOriente(b, dir.get(i - 2), dir.get(i - 1));
		double distancePrec = dir.get(i-2).getDistance(dir.get(i-1));
		if (i == 3) {
			// la 4ème bulle
			distancePrec = 2 * distancePrec;
		}
		if (i == 4) {
			// la 5ème bulle
			distancePrec = distancePrec/2;
		}
		if ((((distancePrec - (distancePrec * INTERVALLE_PRECISION)) < distance)
				&& (distance < (distancePrec + (distancePrec * INTERVALLE_PRECISION))))
				&& ((((alpha > ANGLE) ) || (alpha < ANGLE * -1) ))
				){
			return true;
		}else {
			return false;
		}
	}

	public static Trajectoire ajoutBulleTrajectoire(ArrayList<Bulle> bulles,Trajectoire dir){
		return dir.ajoutBulleTrajectoire(bulles.iterator());
	}

	public Trajectoire ajoutBulleTrajectoire(Iterator<Bulle> it){
		Trajectoire res = new Trajectoire();
		Bulle bulle = it.next();

		if(isBulleOK(this,bulle) && this.size()==4){
			this.add(bulle);
			res = this;
		}else if(isBulleOK(this,bulle) && it.hasNext()){
			for(Bulle b : this){res.add(b);}
			res.add(bulle);
			res = res.ajoutBulleTrajectoire(it);
		}else if(it.hasNext()){
			res = this.ajoutBulleTrajectoire(it);
		}else{
			res=null;
		}
		return res;
	}



	public static ArrayList<Trajectoire> getDirection(ArrayList<Bulle> ar) {
		ArrayList<Trajectoire> couples = new ArrayList<Trajectoire>();
		ArrayList<Bulle> bulles = new ArrayList<Bulle>(ar);
		ArrayList<Trajectoire> res = new ArrayList<Trajectoire>();

		for (Bulle b1 : bulles) {
			for (Bulle b2 : bulles) {
				if (!b1.equals(b2) && b1.getDistance(b2)<DISTANCE_MAX) {
					Trajectoire dir = new Trajectoire();
					dir.add(b1);
					dir.add(b2);
					couples.add(dir);
				}
			}
		}
		couples.sort((o1, o2) -> {if(o1.get(0).getDistance(o1.get(1)) > o2.get(0).getDistance(o2.get(1)))return 1; else return-1;});

		while((!bulles.isEmpty()) && (!couples.isEmpty())){
			bulles.sort((o1, o2) -> {if(o1.getDistance(couples.get(0).get(1)) > o2.getDistance(couples.get(0).get(1)))return 1; else return-1;});

			Trajectoire tmp = ajoutBulleTrajectoire(bulles,couples.get(0));
			if(tmp == null){

			}else{

				bulles.removeAll(tmp);
				res.add(tmp);

				ArrayList<Trajectoire> aaa = couples.stream().filter((Trajectoire dir) -> {
							if (tmp.stream().anyMatch((Bulle bulle) -> dir.contains(bulle))) {
								return true;
							} else {
								return false;
							}
						}
				).collect(Collectors.toCollection(ArrayList<Trajectoire>::new));
				couples.removeAll(aaa);
			}

			couples.remove(0);
		}


		return res;
	}
}