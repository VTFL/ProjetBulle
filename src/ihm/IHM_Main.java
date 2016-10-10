package ihm;
import main.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by valentinpitel on 06/10/2016.
 */
public class IHM_Main extends JFrame {
    private JPanel pan_nord;
    private JPanel pan_ouest;
    private JPanel pan_sud;
    private JButton btn_choixFichier;
    private JLabel lbl_fichier;
    private JLabel lbl_lstButlles;

    //temporaire
    private JTextArea txt_explicationLstBulles;


    private Viewer viewer;
    private View viewGraph;
    private Graph graph;

    private String nomFichier;
    private static String aucunFichier;

    public IHM_Main(){
        super("Projet Bulle");

        Container cont = getContentPane();

        this.nomFichier=null;
        this.aucunFichier="Aucun fichier selectionné.";

        graph = new SingleGraph("Test");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewGraph = viewer.addDefaultView(false);

        //contient le choix du fichier de données
        pan_nord = new JPanel();

        //menu de choix de bulles à afficher
        //possibilité d'afficher chaques bulles dont la trajéctoire a été établie
        //possibilité d'afficher d'une autre couleur la bulle séléctionnée sur le graphe
        pan_ouest = new JPanel();//choix du layout à définir

        // pan_nord
        btn_choixFichier=new JButton("Choix du fichier de données");
        lbl_fichier=new JLabel(aucunFichier);
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

        // pan ouest
        lbl_lstButlles = new JLabel("Liste des bulles ");
        txt_explicationLstBulles = new JTextArea("Affichage de toutes les bulles dont on a trouvé la trajectoire.");
        txt_explicationLstBulles.setPreferredSize(new Dimension(194,360));
        txt_explicationLstBulles.setLineWrap(true);
        txt_explicationLstBulles.setWrapStyleWord(true);
        txt_explicationLstBulles.setOpaque(false);
        txt_explicationLstBulles.setEnabled(false);
        txt_explicationLstBulles.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        pan_ouest.add(lbl_lstButlles);
        pan_ouest.add(txt_explicationLstBulles);
        pan_ouest.setPreferredSize(new Dimension(200,500));
       // pan_ouest.setMinimumSize(new Dimension(500,500));
        //------------------------------------------------------------//

        cont.add(pan_nord,BorderLayout.NORTH);
        cont.add(pan_ouest,BorderLayout.WEST);
        cont.add((Component)viewGraph,BorderLayout.CENTER);

        cont.setPreferredSize(new Dimension(900, 500));
        cont.setMinimumSize(new Dimension(800, 400));

        pack();
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //action du bouton btn_choixFichier
    class actionChoixFichier implements ActionListener {
        public  void    actionPerformed(ActionEvent e) {
            // selection d'un fichier

            // création de la boîte de dialogue
            JFileChooser dialogue = new JFileChooser();

            // affichage
            int returnVal=dialogue.showOpenDialog(null);

            // récupération du fichier sélectionné
            //test
            System.out.println("Path du fichier choisi : " + dialogue.getSelectedFile());
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                lbl_fichier.setText(aucunFichier);
            }else{
                nomFichier=dialogue.getSelectedFile().getName();

                lbl_fichier.setText("Fichier séléctioné : "+nomFichier);
                //affichageGraph();
                graph.addNode("ID1");
                graph.getNode("ID1").setAttribute("x",0);
                graph.getNode("ID1").setAttribute("y",0);
                graph.addNode("ID2");
                graph.getNode("ID2").setAttribute("x",1);
                graph.getNode("ID2").setAttribute("y",10);
            }

        }
    }

    public static void main(String[] arg) {
        IHM_Main test = new IHM_Main();
    }

    public void saveFile(ArrayList<Direction> directions,String nomFichier){
        try{
            PrintWriter writer = new PrintWriter(nomFichier.split(".")[0]+"traité.txt", "UTF-8");
            for(Direction dir : directions){
                for(Bulle b : dir){

                }
            }
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        }catch(Exception e){System.out.println("probleme lors de l'enregistrement du fichier. Code d'erreur 0x1");}

    }
}
