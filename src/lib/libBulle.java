package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import main.Bulle;

/**
 * Created by thiba on 10/10/2016.
 */
public class libBulle {
    public static ArrayList<Bulle> getBullesFromFile(String filename){
        File file = new File(filename);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            ArrayList<Bulle> ar = new ArrayList<Bulle>();
            for (String ligne = bf.readLine(); ligne != null; ligne = bf.readLine()){
                double x = Double.valueOf(ligne.split("   ")[1]);
                double y = Double.valueOf(ligne.split("   ")[2]);
                double z = Double.valueOf(ligne.split("   ")[3]);
                ar.add(new Bulle(x,y,z));
            }
            return ar;
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }
}
