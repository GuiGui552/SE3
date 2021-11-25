
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Temperature {
    static int temp;
    static int jour;

    static void ecrire(String filename) {
        try {
            PrintWriter out=new PrintWriter(filename);
            int annee=2021;
            int mois=1;
            Random rand =new Random();
            for(mois=1;mois<13;mois++)
                for(int jour=0;jour<32;jour++) {
                    double temp = rand.nextInt(40) - 10;
                    //out.println(jour + " " + mois + " " + annee + " " + temp);
                    out.format("%d/%d/%d .2f\n",jour,mois,annee);
                }
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    static void lire(String filename) {
        //limitation: ne traite que les temperatures d'une meme annee ...
        //par exemple un fichier conteant les temparature du janvier sur plusieurs annee ?
        try {
            Scanner in = new Scanner(new File(filename));
            int jour, mois = 0, annee, nbJours = 0;
            double somme = 0;
            int moisEncours = 0;
            int nb;
            in.useDelimiter("/|\\s+");

            while (in.hasNext()) {
                jour = in.nextInt();
                mois = in.nextInt();
                annee = in.nextInt();
                double temp = in.nextDouble();
                
                //System.out.println(jour+" "+mois+" "+annee+" "+temp);
            }

            if (moisEncours !=0) {
                if (moisEncours > 0) {
                    if (nbJours >0) {
                        System.out.format("Moyenne annee : %.3f", moisEncours, somme / nbJours);
                    }
                }
                moisEncours = mois;
                somme = 0.0;
                nbJours = 0;
            }
            somme += temp;
            nbJours++;
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        ecrire("temperature.txt");
        lire("temperature.txt");
    }
}