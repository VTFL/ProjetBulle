import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Direction extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.10;

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
		SingleGraph g = new SingleGraph("test");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		int id=0;
		for(int i=0; i<1;i++) {
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
        System.out.println(quatuor.get(0));
        System.out.println(quatuor.get(1));
        System.out.println(quatuor.get(2));
		return null;
	}
}