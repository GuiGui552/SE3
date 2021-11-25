import java.io.*;
import java.util.Scanner;

class FichierTexte {
    static String[] titres={
            "Un drame au Mexique",
            "Un drame dans les airs",
            "Martin Paz",
            "Maître Zacharius ou l'Horloger qui avait perdu son âme",
            "Un hivernage dans les glaces",
            "Cinq semaines en ballon",
            "Les Aventures du capitaine Hatteras",
            "Le Comte de Chanteleine",
            "Voyage au centre de la Terre",
            "De la Terre à la Lune",
            "Les Forceurs de blocus",
            "Les Enfants du capitaine Grant",
            "Vingt mille lieues sous les mers",
            "Autour de la Lune",
            "Une ville flottante",
            "Aventures de trois Russes et de trois Anglais dans l'Afrique australe",
            "Une fantaisie du docteur Ox",
            "Le Pays des fourrures",
            "Le Tour du monde en quatre-vingts jours",
            "24 minutes en ballon",
            "L'Île mystérieuse",
            "Le Chancellor",
            "Une ville idéale",
            "Michel Strogoff",
            "Hector Servadac",
            "Les Indes noires",
            "Un capitaine de quinze ans",
            "Les Cinq Cents Millions de la Bégum",
            "Les Tribulations d'un Chinois en Chine",
            "Les Révoltés de la Bounty",
            "La Maison à vapeur",
            "La Jangada",
            "Dix heures en chasse",
            "L'École des Robinsons",
            "Le Rayon vert",
            "Kéraban-le-Têtu",
            "L'Étoile du sud",
            "L'Archipel en feu",
            "Frritt-Flacc",
            "L'Épave du Cynthia",
            "Mathias Sandorf",
            "Un billet de loterie",
            "Robur le Conquérant",
            "Nord contre Sud",
            "Gil Braltar",
            "Le Chemin de France",
            "Deux ans de vacances",
            "Famille-Sans-Nom",
            "La Journée d'un journaliste américain en 2889",
            "Sans dessus dessous",
            "César Cascabel",
            "Mistress Branican",
            "Aventures de la famille Raton",
            "Le Château des Carpathes",
            "Claudius Bombarnac",
            "P'tit-Bonhomme",
            "Monsieur Ré-Dièze et Mademoiselle Mi-Bémol",
            "Mirifiques aventures de maître Antifer",
            "L'Île à hélice",
            "Face au drapeau",
            "Clovis Dardentor",
            "Le Sphinx des glaces",
            "Le Superbe Orénoque",
            "Le Testament d'un excentrique",
            "Seconde patrie",
            "Le Village aérien",
            "Les Histoires de Jean-Marie Cabidoulin",
            "Les Frères Kip",
            "Bourses de voyage",
            "Un drame en Livonie",
            "Maître du monde",
            "L'Invasion de la mer"
    };

    static void ecrire(String filename) {
        try {
            PrintWriter out=new PrintWriter(filename);

            for(int i=0;i<titres.length;i++)
                out.println("V"+i+" "+(i%3)+" "+titres[i]);

            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static void lire(String filename) {
        try {
            Scanner in=new Scanner(new File(filename));
            String code,titre;
            int nb;

            while(in.hasNext()) {
                code=in.next();
                nb=in.nextInt();
                in.skip("\\s+");
                titre=in.nextLine();

                System.out.println(code+" "+nb+" "+titre);
            }

            in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        ecrire("biblio.txt");
        lire("biblio.txt");
    }
}