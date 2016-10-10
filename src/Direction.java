import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Direction extends ArrayList<Bulle> {
	final static double INTERVALLE_PRECISION = 0.20;
	final static double ANGLE = 20*Math.PI/180; // radian
	//0,2 m/s (xy) 0,1m/s(z)
	// dt = 2ms
	// 4x10-3 um

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
		System.out.println(Direction.angleOriente(c,b,o)*180/Math.PI);
		System.out.println(c.getDistance(d));

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
					/*if(!couples.stream().anyMatch((Direction d) -> d.contains(b1) && d.contains(b2))){
						couples.add(dir);
					}*/
					couples.add(dir);

				}
			}
		}

		// il faut retiré les doublons de couples
		while((!bulles.isEmpty()) && (!couples.isEmpty())){
				boolean sortieBoucle = false;
				for(int j = 0;j<bulles.size() && !sortieBoucle;j++) {
					Bulle b = bulles.get(j);
					Direction resDir = new Direction();
					resDir.add(couples.get(0).get(0));
					resDir.add(couples.get(0).get(1));

					double distancePrec = resDir.get(0).getDistance(resDir.get(1));

					for (int i = 2; i < 5 && !sortieBoucle ; i++) {

						double distance = b.getDistance(resDir.get(i-1));
						double alpha = angleOriente(b, resDir.get(i-1), resDir.get(i-2));

						if(i==3){
							// la 4ème bulle
							distancePrec = 2*distancePrec;
						}
						if ((((distancePrec - (distancePrec * INTERVALLE_PRECISION)) < distance)
								&& (distance < (distancePrec + (distancePrec * INTERVALLE_PRECISION))))
								&& ((((alpha < ANGLE) && (alpha > 0)) || (alpha > ANGLE*-1) && (alpha < 0)))
								){

								resDir.add(b);
								distancePrec = distance;

							if(i==2){
								sortieBoucle = true;
								System.out.println("yolo");

								bulles.removeAll(resDir);
								res.add(resDir);

								ArrayList<Direction> aaa = couples.stream().filter((Direction dir)-> {
											if(resDir.stream().anyMatch((Bulle bulle)-> dir.contains(bulle))) {
												return true;
											}else{
												return false;
											}
										}
								).collect(Collectors.toCollection(ArrayList<Direction>::new));
								couples.removeAll(aaa);
							}


						}else{
							break;
						}


					}

				}

			couples.remove(0);
		}

        System.out.println(res.size());
		System.out.println(bulles.size());
		SingleGraph g = new SingleGraph("test");

		g.addAttribute("ui.antialias");
		g.addAttribute("ui.quality");
		int id=0;
		for(int i=0; i<res.size();i++) {
			Direction dir =res.get(i);
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
        //System.out.println(res.get(0));
        //System.out.println(res.get(1));
        //System.out.println(res.get(2));
		return null;
	}
}