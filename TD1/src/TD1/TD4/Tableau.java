package TD4;

import java.util.Random;

public class Tableau {

    public static int MIN_VALUE = 1;
    public static int MAX_VALUE = 20;
    public static Random rnd = new Random();

    public static int[] creerTableau (int taille, int min , int max) {
        int[] tableau = new int[taille];
        int diff = max-min;
        for(int i=0;i<tableau.length;i++)
            tableau[i]=rnd.nextInt(MAX_VALUE-MIN_VALUE)+MIN_VALUE;
        return tableau;
    }

    public static int[] creerTableau (int taille) {
        return creerTableau(taille,MIN_VALUE, MAX_VALUE);
    }

    public static void afficher(int [] tab) {
        for (int i=0;i<tab.length;i++) {
            System.out.println("tab["+i+"]="+tab[i]);
        }
    }

}
