package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Trajectoire extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.10;
	final static double ANGLE = 140*Math.PI/180; // radian
	final static double ANGLE_ERREUR = 20*Math.PI/180;
	final static double DISTANCE_MAX = 0.87;
	final static double POIDS_DISTANCE = 1/2;

    final public static int[] FORMATAGE_5 = new int[]{3}; //indice des long
    final public static int[] FORMATAGE_443 = new int[]{4,4}; // indice des long


    private double angleTrajectoire;
    private int[] indiceLong;

    public Trajectoire() {
        angleTrajectoire = 0;
        indiceLong =FORMATAGE_5;
    }

    public int[] getIndiceLong() {
        return indiceLong;
    }

    public void setIndiceLong(int[] indiceLong) {
        this.indiceLong = indiceLong;
    }

    public double getAngleTrajectoire() {
        return angleTrajectoire;
    }

    public void setAngleTrajectoire(double angleTrajectoire) {
        this.angleTrajectoire = angleTrajectoire;
    }



    //renvoi un angle alpha a partir des distance a b et c
    public static double alKashi(double a,double b,double c){
        return Math.acos((Math.pow(a,2) + Math.pow(b,2) - Math.pow(c,2))/(2*a*b));
    }
    public static double det( Bulle a, Bulle b){
		return (a.getX()*b.getY()-a.getY()*b.getX());
	}
	public static double angleOriente(Bulle a,Bulle b,Bulle c){
		//if(det(a,b) <= 0){
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b));
		/*}else{
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b))*-1;
		}*/
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

	public boolean isBulleOK( Bulle b ){
		int i = this.size();
		double distance = b.getDistance(this.get(i - 1));
		double alpha = angleOriente(b, this.get(i - 2), this.get(i - 1));
		double distancePrec = this.get(i-2).getDistance(this.get(i-1));
		double angleErreur = ANGLE_ERREUR;

		if (Arrays.stream(indiceLong).anyMatch(value -> value == i)) {
			// Long
            distancePrec = 2 * distancePrec;
			angleErreur = ANGLE_ERREUR;
		}
		//Arrays.stream(indiceLong).anyMatch(value -> value == i+1)
		if (Arrays.stream(indiceLong).anyMatch(value -> value == (i-1))) {
			// AprèsLong
			distancePrec = distancePrec/2;
		}
		if ((((distancePrec - (distancePrec * INTERVALLE_PRECISION)) < distance)
				&& (distance < (distancePrec + (distancePrec * INTERVALLE_PRECISION))))
				&& (((alpha > ANGLE) ) || (alpha < ANGLE * -1))
				&& ((this.angleTrajectoire == 0)
					||((
						Math.signum(angleTrajectoire) == Math.signum(alpha))
						&& (angleTrajectoire - angleErreur < alpha)
						&& (angleTrajectoire + angleErreur > alpha))

				))
				{
					if(this.angleTrajectoire != 0) {
						System.out.println(alpha + " : " + Math.signum(alpha)+"    " + this.angleTrajectoire+"  : " + Math.signum(this.angleTrajectoire));
					}else{
						//System.out.println(alpha + "    " + angleTrajectoire);
					}

			return true;
		}else {
			return false;
		}
	}

	public Trajectoire ajoutBulleTrajectoire(ArrayList<Bulle> bulles){
		return this.ajoutBulleTrajectoire(bulles.iterator());
	}

	public Trajectoire ajoutBulleTrajectoire(Iterator<Bulle> it){
		Trajectoire res = new Trajectoire();
		Bulle bulle = it.next();

		if(this.isBulleOK(bulle) && this.size()==4){
			this.add(bulle);
			res = this;
		}else if(this.isBulleOK(bulle) && it.hasNext()){
			for(Bulle b : this){res.add(b);}
			res.add(bulle);
			res.setAngleTrajectoire(angleOriente(bulle, res.get(this.size() - 2), res.get(this.size() - 1)));
			res = res.ajoutBulleTrajectoire(it);
		}else if(it.hasNext()){
			res = this.ajoutBulleTrajectoire(it);
		}else{
			res=null;
		}
		return res;
	}

	public int heuristique1(Bulle b1, Bulle b2){
		int i = this.size();
		double distance1 = b1.getDistance(this.get(i - 1));
		double alpha1 = Math.abs(angleOriente(b1, this.get(i - 2), this.get(i - 1)));
		double distance2 = b2.getDistance(this.get(i - 1));
		double alpha2 = Math.abs(angleOriente(b2, this.get(i - 2), this.get(i - 1)));
		double x = DISTANCE_MAX / Math.PI; // la valeur qui sert a pondéré les angle et la distance pour avoir un rapport égale entre les deux avec DISTANCE_MAX = ANGLE_MAX*x
		double val1 = ((alpha1-ANGLE)*x)*POIDS_DISTANCE-distance1;
		double val2 = ((alpha2-ANGLE)*x)*POIDS_DISTANCE-distance2;
        /*System.out.println("--------------");
        System.out.println(distance1);
        System.out.println(distance2);*/

        if(distance1 == 0){
            /*System.out.println("--");
            System.out.println(alpha1-ANGLE);
            System.out.println(alpha2-ANGLE);*/
            val1=Double.POSITIVE_INFINITY;

        }else if(distance2 == 0){
            val2=Double.POSITIVE_INFINITY;
        }

		if(val1 < val2){
			return 1;
		}else if(val1 > val2){
			return -1;
		}else{
			return 0;
		}
	}



	public static ArrayList<Trajectoire> getDirection(ArrayList<Bulle> ar,int[] indiceLong) {
		ArrayList<Trajectoire> couples = new ArrayList<Trajectoire>();
		ArrayList<Bulle> bulles = new ArrayList<Bulle>(ar);
		ArrayList<Trajectoire> res = new ArrayList<Trajectoire>();

		for (Bulle b1 : bulles) {
			for (Bulle b2 : bulles) {
				if (!b1.equals(b2) && b1.getDistance(b2)<DISTANCE_MAX) {
					Trajectoire dir = new Trajectoire();
                    dir.setIndiceLong(indiceLong);
					dir.add(b1);
					dir.add(b2);
					couples.add(dir);
				}
			}
		}
		couples.sort((o1, o2) -> {if(o1.get(0).getDistance(o1.get(1)) > o2.get(0).getDistance(o2.get(1)))return 1; else return-1;});

		while((!bulles.isEmpty()) && (!couples.isEmpty())){
			bulles.sort((o1, o2) -> {if(o1.getDistance(couples.get(0).get(1)) > o2.getDistance(couples.get(0).get(1)))return 1; else return-1;});
			//bulles.sort((o1, o2) -> couples.get(0).heuristique1(o1,o2) );
            //ajouter l'équivalence de tout des angle

			Trajectoire tmp = couples.get(0).ajoutBulleTrajectoire(bulles);
			if(tmp == null){
				couples.remove(0);
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

		}
		Trajectoire test =res.get(5);
		System.out.println(angleOriente(test.get(2), test.get(0), test.get(1))+ " : " + angleOriente(test.get(4), test.get(2), test.get(3)));

		Trajectoire vide = new Trajectoire();
		for(Bulle b : bulles) vide.add(b);
		res.add(vide);

		return res;
	}
}