package lib;
import main.Bulle;

/**
 * Created by thiba on 10/10/2016.
 */
public class libDirection {
    public final static double INTERVALLE_PRECISION = 0.10;
    final static double ANGLE = 20*Math.PI/180; // radian

    //renvoi un angle alpha a partir des distance a b et c
    public static double alKashi(double a,double b,double c){
        return Math.acos((Math.pow(a,2) + Math.pow(b,2) - Math.pow(c,2))/(2*a*b));
    }
    public static double det(Bulle a, Bulle b){
        return (a.getX() * b.getY()) - (a.getY()*b.getX());
    }
    public static double angleOrienté(Bulle a,Bulle b,Bulle c){
        //System.out.println(det(a,b));
        if(det(a,b) < 0){
            return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b));
        }else{
            return alKashi(a.getDistance(c),b.getDistance(c),a.getDistance(b))*-1;
        }
    }
}
