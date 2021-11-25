import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Remplacer {

    class Subst{
        String keyword, value;
    };

    ArrayList<Subst> substitutions = new ArrayList<Subst>();

     void lireSubstitutions(String filename) throws IOException{
        try {
            Scanner in = new Scanner(new File(filename));
            in.useDelimiter("[;\n]");

            while (in.hasNext()){
                Subst subst = new Subst();
                subst.keyword = in.next();
                subst.value = in.next();
                this.substitutions.add(subst);

                System.out.println("keyword = (" + subst.keyword + ") " + "values = ("+ subst.value + ")");
            }
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    void remplacer(String src, String dst) throws IOException{
        Scanner in = new Scanner(new File(src));
        PrintWriter out = new PrintWriter(dst);

        while (in.hasNext()){
            String ligne = in.nextLine();
            for (Subst subst: substitutions){
                ligne = ligne.replace(subst.keyword, subst.value);
            }
            out.println(ligne);
        }
        in.close();
        out.close();
    }

    void afficherListe(){
        if (this.substitutions.size()==0){
            System.out.println("Liste vide");
        }else{
            System.out.println("\t Liste de substitutions: ");
            for (Subst subst: substitutions){
                System.out.println("keyword = ("+subst.keyword+"), values=("+subst.value+")");
            }
        }
    }

    public static void main(String[] args) {
        try {
            String keywords = "keywords.txt";
            String src = "source.txt";
            String dest = "destination.txt";
            Remplacer r = new Remplacer();
            r.lireSubstitutions(keywords);
            r.remplacer(src,dest);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

}
