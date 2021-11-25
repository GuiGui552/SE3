import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CSV {
    final static char SEPARATEUR = ',';
    private static ArrayList<ArrayList> CSV = new ArrayList<ArrayList>();

    static void traiterLigne(String ligne) {
        int i = 0;
        int nbTermes = 0;
        String s = "";
        while (i < ligne.length()) {
            char c = ligne.charAt(i++);
            if (c == ',') {
                nbTermes++;
                s += " / ";
            } else s += c;
        }
        nbTermes++;
        if (s.length() > 0) System.out.println(s);
        System.out.println("NbTermes = " + nbTermes);
    }

    static ArrayList decouperLigne(String ligne) {
        int i = 0;
        String s = "";
        ArrayList<String> tab = new ArrayList<String>();
        boolean inTerme=false;
        System.out.println("découperLigne"+ligne);
        while (i < ligne.length()) {
            char c = ligne.charAt(i++);
            if(c == '"'){
                inTerme = !inTerme;
            }else {
                if (c == SEPARATEUR|| (c=='"'&& inTerme)) {
                    tab.add(s);
                    s = "";
                } else {
                    s += c;
                }
            }
        }
        System.out.println("ligne = "+s);
        tab.add(s);
        return tab;
    }

    public static void afficherLigne() {
        String file = "fichier.csv";
        try {
            Scanner in = new Scanner(new File(file));
            String ligne;
            while (in.hasNext()) {
                ligne = in.nextLine();
                //traiterLigne(ligne);
                CSV.add(decouperLigne(ligne));
                System.out.println(ligne);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void ajouterTerme(int numLigne,int numTerme,String terme){
        try {
            ArrayList<String> ligne = CSV.get(numLigne);
            ligne.add(numTerme,terme);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static boolean contientVirgule(String mot){
        System.out.println("contient virgule"+mot);
        boolean virgule = false;
        for(int i = 0;i< mot.length();i++){
            char c = mot.charAt(i);
            if (c == ',') virgule = true;
        }
        return virgule;
    }
    public static void ecrire(String nomCSV) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(nomCSV);
        for(ArrayList<String> ligne : CSV){
            for(String mot : ligne){
                if (contientVirgule(mot)){
                    out.format('"'+mot+'"');
                }
                out.format(mot+' ');
            }
            out.format("\n");
        }
        out.close();
    }
    public static void supprimerTerme(int numLigne, int numTerme){
        try {
            ArrayList<String> ligne = CSV.get(numLigne);
            ligne.remove(numTerme);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Supprimer");
        }
    }

    public static void main(String[] args) {
        afficherLigne();
        System.out.println(CSV);
        try {
            //ajouterTerme(3,0,"Terme");
            ecrire("fini.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}