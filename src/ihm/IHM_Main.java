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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by valentinpitel on 06/10/2016.
 */
public class IHM_Main extends JFrame{
    private JPanel pan_nord;
    private JPanel pan_ouest;
    private JPanel pan_sud;
    private JButton btn_choixFichier;
    private JButton btn_execution;

    private JLabel lbl_fichier;
    private JLabel lbl_lstButlles;
    private JLabel lbl_modeleBulle;


    private JComboBox cb_modeleBulle;


    //temporaire
    private JTextArea txt_explicationLstBulles;
    private JList lst_trajectoires;

    private Viewer viewer;
    private View viewGraph;
    private Graph graph;

    private String nomFichier;
    private final String aucunFichier="Aucun fichier selectionné.";
    private static final String[] modeleBulle={"3-2","4-4-3","..."};

    public IHM_Main(){
        super("Projet Bulle");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        Container cont = getContentPane();

        this.nomFichier=null;


        graph = new SingleGraph("Test");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewGraph = viewer.addDefaultView(false);


        //contient le choix du fichier de données
        pan_nord = new JPanel();

        pan_ouest = new JPanel();//choix du layout à définir
        pan_sud = new JPanel();
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
        pan_nord.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        pan_nord.add(p1);
        pan_nord.add(p2);

        // pan ouest
        pan_ouest.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        lbl_modeleBulle = new JLabel("Modèle trajectoire :");
        c.weightx=0.5;
        c.gridx=0;
        c.gridy=0;
        pan_ouest.add(lbl_modeleBulle,c);
        cb_modeleBulle = new JComboBox(modeleBulle);
        cb_modeleBulle.setEditable(false);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=0.5;
        c.gridx=2;
        c.gridy=0;
        pan_ouest.add(cb_modeleBulle,c);

        btn_execution = new JButton("Affichage graph");
        btn_execution.setEnabled(false);
        btn_execution.addActionListener(new actionAffichageGraph());
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=0.8;
        c.gridx=0;
        c.gridwidth=3;
        c.gridy=1;
        pan_ouest.add(btn_execution,c);

        lbl_lstButlles = new JLabel("Liste des bulles :");
        c.fill=GridBagConstraints.HORIZONTAL;
        c.weightx=0.8;
        c.insets = new Insets(2,0,0,0);
        c.gridx=0;
        c.gridwidth=2;
        c.gridy=2;
        pan_ouest.add(lbl_lstButlles,c);

        JScrollPane scrollPane = new JScrollPane();
        String[] data={"test","test2","test3","test2"
                ,"test2","test3","test2"
                ,"test2","test3","test2"
                ,"test2","test3","test2"
                ,"test2","test3","test2"
                ,"test2","test3","test2"
                ,"test2","test3","test2"
                ,"test3","test2","test3","test2","test3"};
        lst_trajectoires= new JList(data);
        lst_trajectoires.addMouseListener(new actionLstTrajectoires());

        scrollPane.setViewportView(lst_trajectoires);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        c.fill=GridBagConstraints.BOTH;
        c.insets = new Insets(2,0,0,0);
        c.gridx=0;
        c.gridwidth=3;
        c.weighty=1; //100% du reste de l'espace dispo
        c.gridy=3;
        pan_ouest.add(scrollPane,c);
        pan_ouest.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //------------------------------------------------------------//

        cont.add(pan_nord,BorderLayout.NORTH);
        cont.add(pan_ouest,BorderLayout.WEST);
        cont.add(pan_sud,BorderLayout.SOUTH);
        cont.add((Component)viewGraph,BorderLayout.CENTER);

        cont.setPreferredSize(new Dimension(900, 500));
        cont.setMinimumSize(new Dimension(800, 400));

        pack();
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //action du bouton btn_choixFichier
    //modifs a revoir
    class actionChoixFichier implements ActionListener {
        public  void    actionPerformed(ActionEvent e) {
            // selection d'un fichier

            // création de la boîte de dialogue
            JFileChooser dialogue = new JFileChooser();

            // affichage
            int returnVal=dialogue.showOpenDialog(null);

            // récupération du fichier sélectionné
            System.out.println("Path du fichier choisi : " + dialogue.getSelectedFile());
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                lbl_fichier.setText(aucunFichier);
            }else{
                nomFichier=dialogue.getSelectedFile().getName();
                lbl_fichier.setText("Fichier séléctioné : "+nomFichier);
                btn_execution.setEnabled(true);
            }
            System.out.println("Path du fichier choisi : " + dialogue.getSelectedFile());

        }
    }

    class actionAffichageGraph implements ActionListener {
        public  void    actionPerformed(ActionEvent e) {
            //à modifier
            boolean vefif = true;

            //affichageGraph();
       /*     graph.addNode("ID1");
            graph.getNode("ID1").setAttribute("x",0);
            graph.getNode("ID1").setAttribute("y",0);
            graph.addNode("ID2");
            graph.getNode("ID2").setAttribute("x",1);
            graph.getNode("ID2").setAttribute("y",10);*/
            graph.clear();
            Main test = new Main();
            test.mainIHM_Test(nomFichier,graph);
        }
    }
    class actionLstTrajectoires implements MouseListener {
        public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    System.out.println("dbl click : affichages des bulles d'une autre couleur");
                }

        }
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
    public void initLstTrajectoires() {

    }


    public static void main(String[] arg) {
        IHM_Main test = new IHM_Main();
    }

    public void saveFile(ArrayList<Trajectoire> trajectoires,String nomFichier){
        try{
            PrintWriter writer = new PrintWriter(nomFichier.split(".")[0]+"traité.txt", "UTF-8");
            int i=1;
            for(Trajectoire dir : trajectoires){
                for(Bulle b : dir){
                    writer.println(b+"   "+i);
                }
                i++;
            }
            writer.close();
        }catch(Exception e){System.out.println("Probleme lors de l'enregistrement du fichier. Code d'erreur 0x1");}

    }

}
