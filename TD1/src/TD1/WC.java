import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class WC {

    static void ecrire(String filename) {
        try {
            PrintWriter out=new PrintWriter(filename);

            out.println("il y a 18 mots et 4 lignes dans ce fichier"+"\n"+
                    "la deuxieme      ligne"+"\n"+
                    "la troisieme ligne"+"\n"+
                    "oscour");
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static void wc(String filename) {
        try {
            Scanner in=new Scanner(new File(filename));
            Scanner in2= new Scanner(new File(filename));
            int mots=0, ligne=0, charac=0;

            while(in.hasNext()) {
                mots++;
                in.next();
            }
            while (in2.hasNextLine()){
                ligne++;
                charac+=in2.nextLine().length();
            }
            System.out.println("il y a "+mots+" mots, "+charac+" charactères et "+ligne+" lignes");
            in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        ecrire("test wc");
        wc("test wc");
    }
}