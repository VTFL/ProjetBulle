package test;

import lib.libBulle;
import main.Bulle;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.awt.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by thiba on 10/10/2016.
 */
public class afficherFichier {
    public static void main(String[] args) {
        Graph graph = new SingleGraph("isOk");
        ArrayList<Bulle> bb = libBulle.getBullesFromFile("norma_N5_tau4_dt2_delai820_000000.txt");
        Node n;
        ArrayList<Integer> nb = new ArrayList<>();
        graph.addAttribute("ui.stylesheet","url(./pointRouge.css)");
        int count=0;
        try{
            FileReader fr = new FileReader("traject0.txt");
            Scanner sc = new Scanner(fr);
            String s = "";
            while(sc.hasNextLine()){
                s=sc.nextLine();
                nb.add(Integer.parseInt(s.split(" ")[0]));
                nb.add(Integer.parseInt(s.split(" ")[1]));
                nb.add(Integer.parseInt(s.split(" ")[2]));
                nb.add(Integer.parseInt(s.split(" ")[3]));
                nb.add(Integer.parseInt(s.split(" ")[4]));
            }
        }catch(Exception e){e.printStackTrace();}

        int i=0;
        for(Bulle b : bb){
            graph.addNode(""+i);
            graph.getNode(""+i).setAttribute("xy", b.getX(), b.getY());
            if(nb.contains(i+1)){
                count++;
                graph.getNode(""+i).addAttribute("ui.class", "important");
                nb.remove(nb.indexOf(i+1));
            }
            i++;
        }
        graph.display(false);
        for (Integer in : nb) System.out.println(in);
        /*System.out.println(nb.size()+"  =  "+count+"   !  "+bb.size());
        System.out.println((count*100)/bb.size());*/
    }
}
