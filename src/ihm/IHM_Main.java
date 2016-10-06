package ihm;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by valentinpitel on 06/10/2016.
 */
public class IHM_Main extends JFrame {
    private JPanel pan_nord;
    private JPanel pan_ouest;
    private JPanel pan_centre;
    private JPanel pan_sud;
    private JButton btn_choixFichier;
    private JLabel lbl_fichier;

    private String nomFichier;
    private static Graph graph;
    private Viewer viewer;
    private View viewGraph;
    public IHM_Main(){
        super("Projet Bulle");

        Container cont = getContentPane();

        this.nomFichier=null;

        graph = new SingleGraph("Test");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewGraph = viewer.addDefaultView(false);


        pan_nord = new JPanel();
        pan_centre = new JPanel();
        pan_centre.setBackground(Color.black);


        btn_choixFichier=new JButton("Choix du fichier de données");
        lbl_fichier=new JLabel("Aucun fichier selectionné.");
        btn_choixFichier.addActionListener(new actionChoixFichier());


        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(btn_choixFichier);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        p2.add(lbl_fichier);
        pan_nord.setLayout(new BoxLayout(pan_nord,BoxLayout.PAGE_AXIS));
        pan_nord.add(p1);
        pan_nord.add(p2);


       // pan_centre.add((Component)view);

        cont.add(pan_nord,BorderLayout.NORTH);

        //affichage graph
        cont.add((Component)viewGraph,BorderLayout.CENTER);
        cont.setPreferredSize(new Dimension(900, 500));

        pack();
        setVisible(true);
        //	setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //action du bouton btn_choixFichier
    class actionChoixFichier implements ActionListener {
        public  void    actionPerformed(ActionEvent e) {
            //selection d'un fichier

            // création de la boîte de dialogue
            JFileChooser dialogue = new JFileChooser();

            // affichage
            dialogue.showOpenDialog(null);

            // récupération du fichier sélectionné
            System.out.println("Path du fichier choisi : " + dialogue.getSelectedFile());
            nomFichier=dialogue.getSelectedFile().getName();
            lbl_fichier.setText("Fichier séléctioné : "+nomFichier);
            //affichageGraph();
        }
    }

    public static void main(String[] arg) {
        IHM_Main test = new IHM_Main();
    }
}
