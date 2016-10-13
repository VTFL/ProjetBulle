package main;
import lib.libBulle;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

/**
 * Created by Utilisateur on 03/10/2016.
 */
public class Main {
    public static void main(String[] args) {
		ArrayList<Bulle> bulles = libBulle.getBullesFromFile("norma_N5_tau4_dt2_delai820_000003.txt");
		System.out.println(bulles);
		System.out.println(bulles.get(4).getDistance(bulles.get(5)));
		ArrayList<Trajectoire> res = Trajectoire.getDirection(bulles,Trajectoire.FORMATAGE_5);

		System.out.println(res.size());
		SingleGraph g = new SingleGraph("test");

		g.addAttribute("ui.antialias");
		g.addAttribute("ui.quality");
		int id=0;
		for(int i=0; i<res.size();i++) {
			Trajectoire dir =res.get(i);
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
	}





}
