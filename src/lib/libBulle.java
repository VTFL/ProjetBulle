package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
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
                double truc1 = Double.valueOf(ligne.split("   ")[3]);
                double truc2 = Double.valueOf(ligne.split("   ")[4]);
                double truc3 = Double.valueOf(ligne.split("   ")[5]);
                ar.add(new Bulle(x,y,truc1,truc2,truc3));
            }
            return ar;
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ArrayList<Bulle>> getBulles443FromFiles(String f1, String f2, String f3){
        ArrayList<ArrayList<Bulle>> ret = new ArrayList();
        ret.add(getBullesFromFile(f1));
        ret.add(getBullesFromFile(f2));
        ret.add(getBullesFromFile(f3));
        return ret;
    }
}
