import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Direction extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.10;
	final static double ANGLE = 20*Math.PI/180; // radian

    //renvoi un angle alpha a partir des distance a b et c
    public static double alKashi(double a,double b,double c){
        return Math.acos((Math.pow(a,2) + Math.pow(b,2) - Math.pow(c,2))/(2*a*b));
    }
    public static double det( Bulle a, Bulle b){
		return (a.getX() + b.getY()) - (a.getY()+b.getX());
	}
	public static double angleOrienté(Bulle a,Bulle b,Bulle c){
		//System.out.println(det(a,b));
		if(det(a,b) < 0){
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b));
		}else{
			return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b))*-1;
		}
	}


	public static ArrayList<Direction> getDirection(ArrayList<Bulle> ar) {
		ArrayList<Direction> couples = new ArrayList<Direction>();
		ArrayList<Bulle> bulles = new ArrayList<Bulle>(ar);
		ArrayList<Direction> res = new ArrayList<Direction>();

		for (Bulle b1 : bulles) {
			for (Bulle b2 : bulles) {
				if (!b1.equals(b2)) {
					Direction dir = new Direction();
					dir.add(b1);
					dir.add(b2);

				}
			}
		}

		// il faut retiré les doublons de couples
		/*
		while((!bulles.isEmpty()) && (!couples.isEmpty())){
			Direction tmp =couples.get(0);

				for(int j = 0;j<bulles.size();j++) {
					double distancePrec = tmp.get(0).getDistance(tmp.get(1));
					//System.out.println(distancePrec);
					for (int i = 2; i < 5; i++) {
						Bulle b = bulles.get(j);

						double distance = b.getDistance(tmp.get(i-1));
						double alpha = angleOrienté(b, tmp.get(i - 1), tmp.get(i-2));
						//System.out.println(ANGLE);
						if (((distancePrec - (distancePrec * INTERVALLE_PRECISION)) < distance)
								&& (distance < (distancePrec + (distancePrec * INTERVALLE_PRECISION)))
								//&& (((ANGLE - (ANGLE * INTERVALLE_PRECISION)) < alpha ) || ((ANGLE - (ANGLE * INTERVALLE_PRECISION)*-1 > alpha )))
								&& ((alpha < (ANGLE + (ANGLE * INTERVALLE_PRECISION))) || (alpha > (ANGLE + (ANGLE * INTERVALLE_PRECISION)*-1)))) {
								tmp.add(b);

						}else{
							break;
						}

						distancePrec = distance;
					}
					if(tmp.size() == 5){
						bulles.removeAll(tmp);
						res.add(tmp);
						ArrayList<Direction> aaa = couples.stream().filter((Direction dir)-> {
								if(tmp.stream().anyMatch((Bulle b)-> dir.contains(b))) {
										return false;
								}else{
										return true;
								}
							}
						).collect(Collectors.toCollection(ArrayList<Direction>::new));
						couples.removeAll(aaa);

						break;
					}
				}

			couples.remove(0);
		}*/
		System.out.println(couples.size());
		ArrayList<Direction> triplets = new ArrayList<Direction>();
		for(Direction couple : couples){
			Double d = couple.get(0).getDistance(couple.get(1));
			for(Bulle b3 : ar) {
				if(!couple.stream().anyMatch((Bulle b) -> b.equals(b3))){
					if(d-(d*INTERVALLE_PRECISION)< b3.getDistance(couple.get(1))
							&& b3.getDistance(couple.get(1))< d+(d*INTERVALLE_PRECISION)) {
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

            double alpha = angleOrienté(triplet.get(0),triplet.get(2),triplet.get(1));
            //System.out.println(alpha);
            for(Bulle b4 : ar) {
				if(!triplet.stream().anyMatch((Bulle bulle) -> bulle.equals(b4))){

					double beta = angleOrienté(triplet.get(1),b4,triplet.get(2));
                    //System.out.println(alpha);

                    if(((d-(d*INTERVALLE_PRECISION))< b4.getDistance(triplet.get(2)))
                            && (b4.getDistance(triplet.get(2))< (d+(d*INTERVALLE_PRECISION)))
                            && ((alpha-(alpha*INTERVALLE_PRECISION))< beta)
                            && (beta < (alpha+(alpha*INTERVALLE_PRECISION))) ) {

						Direction dir = new Direction();
						for(Bulle bulle : triplet){
							dir.add(bulle);
						}
						dir.add(b4);
						quatuor.add(dir);
					}
				}
			}
		}
        System.out.println(quatuor.size());
		//System.out.println(bulles.size());
		SingleGraph g = new SingleGraph("test");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		int id=0;
		for(int i=0; i<10;i++) {
			Direction dir =quatuor.get(i);
			Node prec = null;

			for (Bulle b : dir) {
				g.addNode(id+ "");
				Node n = g.getNode(id+"");
				if(n!=null) {
					id++;
					n.setAttribute("xy", b.getX(), b.getY());
					if (prec != null) {
						g.addEdge(id + prec.getId(), n, prec);
						id++;
					}
					prec = n;
				}

			}



		}
		g.display(false);
        System.out.println(res.get(0));
        System.out.println(res.get(1));
        System.out.println(res.get(2));
		return null;
	}
}