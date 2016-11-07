package ihm;

import main.Trajectoire;
import lib.libBulle;
import main.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

/**
 * Created by valentinpitel on 06/10/2016.
 */
public class IHM_Main extends JFrame {

    private JPanel pan_nord;
    private JPanel pan_ouest;
    private JPanel pan_sud;
    private JButton btn_choixFichier;
    private JButton btn_execution;
    private JButton btn_save;

    private JLabel lbl_fichier;
    private JLabel lbl_lstTrajectoires;
    private JLabel lbl_modeleBulle;


    private JComboBox cb_modeleBulle;


    //temporaire
    private JTextArea txt_explicationLstBulles;
    private JList lst_trajectoires;

    private Viewer viewer;
    private View viewGraph;
    private Graph graph;

    private String nomFichier;
    private String nomFichier1;
    private String nomFichier2;
    private String nomFichier3;
    private int trajectoireChoisie;
    private ArrayList<Integer> ar_trajsChoisies;
    private List<List<Integer>> ar_traj;
    private boolean plusieursSelections;

    private static final String aucunFichier = "Aucun fichier selectionné.";
    private static final String[] modeleBulle = {"3-2", "4-4-3", "..."};
    private ArrayList<Trajectoire> ar_traj_bulles;

    public IHM_Main() {
        super("Projet Bulle");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Container cont = getContentPane();
        this.ar_traj = new ArrayList<List<Integer>>();
        this.nomFichier = null;
        this.trajectoireChoisie = -1;
        this.plusieursSelections= false;
        this.ar_trajsChoisies= new ArrayList<Integer>();
        graph = new SingleGraph("Test");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewGraph = viewer.addDefaultView(false);


        //contient le choix du fichier de données
        pan_nord = new JPanel();

        pan_ouest = new JPanel();//choix du layout à définir
        pan_sud = new JPanel();
        pan_sud.add(new JLabel("Cliquez sur une ou plusieurs trajectoires pour les afficher en rouge"));
        // pan_nord
        btn_choixFichier = new JButton("Choix du fichier de données");
        lbl_fichier = new JLabel(aucunFichier);

        btn_choixFichier.addActionListener(new actionChoixFichier());


        btn_save = new JButton("Sauvegarder les résultats");
        btn_save.setEnabled(false);
        btn_save.addActionListener(new actionSaveTraj());

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.LINE_AXIS));
        p1.add(btn_choixFichier);
        p1.add(btn_save);


        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
        p2.add(lbl_fichier);

        pan_nord.setLayout(new BoxLayout(pan_nord, BoxLayout.PAGE_AXIS));
        pan_nord.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        pan_nord.add(p1);
        pan_nord.add(p2);

        // pan ouest
        pan_ouest.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        lbl_modeleBulle = new JLabel("Modèle trajectoire :");
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        pan_ouest.add(lbl_modeleBulle, c);
        cb_modeleBulle = new JComboBox(modeleBulle);
        cb_modeleBulle.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pan_ouest.add(cb_modeleBulle, c);

        btn_execution = new JButton("Affichage graph");
        btn_execution.setEnabled(false);
        btn_execution.addActionListener(new actionAffichageGraph());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        pan_ouest.add(btn_execution, c);

        lbl_lstTrajectoires = new JLabel("Liste des Trajectoires :");
        lbl_lstTrajectoires.setVisible(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.insets = new Insets(2, 0, 0, 0);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        pan_ouest.add(lbl_lstTrajectoires, c);

        JScrollPane scrollPane = new JScrollPane();
        lst_trajectoires = new JList();
        lst_trajectoires.setEnabled(false);

      //  lst_trajectoires.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lst_trajectoires.addMouseListener(new actionLstTrajectoires());

        scrollPane.setViewportView(lst_trajectoires);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(2, 0, 0, 0);
        c.gridx = 0;
        c.gridwidth = 3;
        c.weighty = 0.75; //100% du reste de l'espace dispo
        c.gridy = 3;
        pan_ouest.add(scrollPane, c);
        pan_ouest.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        //------------------------------------------------------------//

        cont.add(pan_nord, BorderLayout.NORTH);
        cont.add(pan_ouest, BorderLayout.WEST);
        cont.add(pan_sud, BorderLayout.SOUTH);
        cont.add((Component) viewGraph, BorderLayout.CENTER);

        cont.setPreferredSize(new Dimension(900, 500));
        cont.setMinimumSize(new Dimension(800, 400));

        pack();
        setVisible(true);
        setResizable(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void majLstTrajectoire(List<List<Integer>> lst_traj) {
            DefaultListModel dlm = new DefaultListModel();
            trajectoireChoisie = -1;

        for (int i = 0; i < lst_traj.size()-1; i++)
            dlm.addElement("Trajectoire " + (i + 1));

        this.lst_trajectoires.setModel(dlm);
        lst_trajectoires.setEnabled(true);
        lbl_lstTrajectoires.setVisible(true);
    }

    public void  affichageGraph() {
        System.out.println("isOk");
        ArrayList<Bulle> bulles = libBulle.getBullesFromFile(nomFichier);
        //ArrayList<ArrayList<Bulle>> bulles = libBulle.getBulles443FromFiles("NORMA_N3_2-5V_DT2_000000.txt","NORMA_N3_2-5V_DT2_000001.txt","NORMA_N3_2-5V_DT2_000002.txt");
        //ArrayList<Trajectoire> res = Trajectoire.getDirection(bulles,Trajectoire.FORMATAGE_5);
        ArrayList<Trajectoire> res = Trajectoire.getDirection(bulles,Trajectoire.FORMATAGE_443);
        ar_traj_bulles = res;

        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.stylesheet","url(./pointRouge.css)");

        int id=0;
        ar_traj.clear();
        List<Integer> traj;
        for(int i=0; i<res.size();i++) {
            Trajectoire dir =res.get(i);
            Node prec = null;
            traj= new ArrayList<Integer>();
            for (Bulle b : dir) {
                graph.addNode(id+ "");
                Node n = graph.getNode(id+"");
                traj.add(id);
                if(n!=null) {
                    n.setAttribute("xy", b.getX(), b.getY());
                    if (prec != null && i!=res.size()-1) {
                        graph.addEdge(id + prec.getId(), n, prec);
                    }
                    id++;
                    prec = n;
                }
            }
            ar_traj.add(traj);
        }
        for(int i = 0; i< ar_traj.get(ar_traj.size()-1).size();i++){
            graph.getNode(ar_traj.get(ar_traj.size()-1).get(i)).setAttribute("ui.class","sansTrajectoire");
        }
    }

    //Sauvegarde des trajectoires valides dans un fichier.
    // Modif de Florian le 13/10/2016
    class actionSaveTraj implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFileChooser filechoose = new JFileChooser();
            filechoose.setCurrentDirectory(new File("."));  /* ouvrir la boite de dialogue dans répertoire courant */
            filechoose.setDialogTitle("Enregistrer vos resultats"); /* nom de la boite de dialogue */

            filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /* pour afficher seulement les répertoires */

            String approve = new String("Enregistrer"); /* Le bouton pour valider l’enregistrement portera la mention Enregistrer */
            int resultatEnregistrer = filechoose.showDialog(filechoose, approve);
            if (resultatEnregistrer == JFileChooser.APPROVE_OPTION){

                String chemin = filechoose.getSelectedFile().getAbsolutePath()+"\\";

                try {
                    PrintWriter writer = new PrintWriter(chemin + nomFichier.split("\\.")[0] + "traité.txt", "UTF-8");
                    int i = 1;
                    for (Trajectoire dir : ar_traj_bulles) {
                        for (Bulle b : dir) {
                            writer.println(b + "   " + i);
                        }
                        i++;
                    }
                    writer.close();
                } catch (Exception ex) {
                    System.out.println("Probleme lors de l'enregistrement du fichier. Code d'erreur 0x1");
                }

            }
        }
    }

    //action du bouton btn_choixFichier
    class actionChoixFichier implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // création de la boîte de dialogue
            JFileChooser dialogue = new JFileChooser();
            dialogue.setMultiSelectionEnabled(true);
            dialogue.setCurrentDirectory(new File(System.getProperty("user.dir")));
            // affichage
            int returnVal = dialogue.showOpenDialog(null);

            //Le bouton save n'est pas sélectionnable.
            btn_save.setEnabled(false);


            if (returnVal != JFileChooser.APPROVE_OPTION) {
                if (nomFichier == null) {
                    lbl_fichier.setText(aucunFichier);
                }
            } else {
                if(dialogue.getSelectedFiles().length>3) {
                    lbl_fichier.setText(aucunFichier);
                }else if(dialogue.getSelectedFiles().length==3 )     {
                    nomFichier1 = dialogue.getSelectedFiles()[0].getName();
                    nomFichier2= dialogue.getSelectedFiles()[1].getName();
                    nomFichier3= dialogue.getSelectedFiles()[2].getName();
                    lbl_fichier.setText("Fichiers séléctionés : " + nomFichier +" ,"+ nomFichier2 +" ,"+nomFichier3);
                    btn_execution.setEnabled(true);
                }else {
                    nomFichier = dialogue.getSelectedFile().getName();
                    lbl_fichier.setText("Fichier séléctioné : " + nomFichier);
                    btn_execution.setEnabled(true);
                }
                graph.clear();
                trajectoireChoisie = -1;
                lst_trajectoires.setModel(new DefaultListModel());
            }
        }
    }

    //affiche le graph et met à jour la liste de trajectoires
    class actionAffichageGraph implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            graph.clear();
            trajectoireChoisie = -1;

            affichageGraph();
            majLstTrajectoire(ar_traj);
            btn_save.setEnabled(true);
        }
    }

    class actionLstTrajectoires implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (((JList) e.getSource()).isEnabled())
                if (e.getClickCount() == 1) {
                    if(plusieursSelections){
                        for(int i = 0; i< ar_trajsChoisies.size();i++)
                            for (int j = 0; j < ar_traj.get(ar_trajsChoisies.get(i)).size(); j++)
                                graph.getNode(ar_traj.get(ar_trajsChoisies.get(i)).get(j)).setAttribute("ui.class", "nonSelection");
                        plusieursSelections=false;
                        ar_trajsChoisies.clear();
                        trajectoireChoisie=-1;
                    }
                    if(lst_trajectoires.getSelectedIndices().length >1 ) {
                        for (int i = 0; i < lst_trajectoires.getSelectedIndices().length; i++) {
                            ar_trajsChoisies.add(lst_trajectoires.getSelectedIndices()[i]);
                            for (int j = 0; j < ar_traj.get(lst_trajectoires.getSelectedIndices()[i]).size(); j++)
                                graph.getNode(ar_traj.get(lst_trajectoires.getSelectedIndices()[i]).get(j)).setAttribute("ui.class", "selection");
                        }
                        plusieursSelections=true;
                    }else {
                        if (trajectoireChoisie != -1) {
                            //affichage des bullesen noir
                            for (int i = 0; i < ar_traj.get(trajectoireChoisie).size(); i++)
                                graph.getNode(ar_traj.get(trajectoireChoisie).get(i)).setAttribute("ui.class", "nonSelection");
                            trajectoireChoisie=-1;
                        }
                        trajectoireChoisie = lst_trajectoires.getSelectedIndex();
                        //affichage des bulles en rouge
                        for (int i = 0; i < ar_traj.get(trajectoireChoisie).size(); i++) {
                            graph.getNode(ar_traj.get(trajectoireChoisie).get(i)).setAttribute("ui.class", "selection");
                        }
                    }
                }

        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public static void main(String[] arg) {
        IHM_Main test = new IHM_Main();
    }

}
