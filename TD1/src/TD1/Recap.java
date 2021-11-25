import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Recap {

    static double total1;
    static double total2;
    static double total3;
    static double total4;
    static double total5;
    static double total6;

    static void lire(String filename) {
        try {
            Scanner in = new Scanner(new File(filename));
            String magasin;
            String annee;
            double ca;
            in.useDelimiter("\\t|\\n");
            in.nextLine(); // permet de sauter une ligne
            while (in.hasNext()) {
                magasin = in.next();
                annee = in.next();
                in.next(); //permet de skip le mois
                in.useLocale(Locale.US);
                ca = in.nextDouble();

                if (annee.equals("2018") && magasin.equals("Lille")) {total1 += ca;}
                else if (annee.equals("2018") && magasin.equals("Arras")) {total2 += ca;}
                else if (annee.equals("2019") && magasin.equals("Arras")) {total3 += ca;}
                else if (annee.equals("2018") && magasin.equals("Lens")) {total4 += ca;}
                else if (annee.equals("2019") && magasin.equals("Lens")) {total5 += ca;}
                else if (annee.equals("2019") && magasin.equals("La Bassée")) {total6 += ca;}
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    static void ecrire(String filename) {
        try {
            PrintWriter out=new PrintWriter(filename);

            out.println("Magasin" + '\t' + "Année" + '\t' + "CA" + '\n' +
                    "Lille" + '\t' + "2018" + '\t' + total1 + '\n' +
                    "Arras" + '\t' + "2018" + '\t' + total2 + '\n' +
                    "Arras" + '\t' + "2019" + '\t' + total3 + '\n' +
                    "Lens" + '\t' + "2018" + '\t' + total4 + '\n' +
                    "Lens" + '\t' + "2019" + '\t' + total5 + '\n' +
                    "La Bassée" + '\t' + "2019" + '\t' + total6);

            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        lire("CA.txt");
        ecrire("recap.txt");
    }
}

//%d int
//%s string
//%f reel
//%5.2f