import java.io.*;
import java.util.Scanner;

// LISSE Guillaume groupe 2-A

class Test {
    class Point {
        int x,y;
    }

    class EnsembleEntier {
        int[] tab; // tableau contenant les éléments de l'ensemble
        int nbElements; // nombre d'éléments actuellement présents dans l'ensemble
    }

    // échange le contenu des 2 points passés en paramètre
    void permuterPoints(Point a, Point b) {
        Point c = a;
        a = b;
        b = c;
    }

    // retourne la position du plus grand élément dans le tableau
    int indiceMax(int[] tab) {
        int tmp=0;
        int position=0;
        for (int i = 0; i < tab.length; i++){
            if (tab[i] > tmp){
                position = i;
            }
        }
        return position;
    }

    // retourne la position dans le tableau tab du premier élement égal à "element"
    int position(int[] tab, int element) {
        int position = 0;
        for (int i =0; i<tab.length; i++){
            if (tab[i] == element){
                position = i;
            }
        }
        return position;
    }

    // retourne le nombre d'occurrences du caractère c dans la chaîne s
    int nbOcc(String s, char c) {
        int cpt = 0;
        for(int i=0; i < s.length(); i++){
            if (s.charAt(i) == c){
                cpt++;
            }
        }
        return cpt;
    }

    // remplace dans la chaîne s chaque occurrence du caractère c par la chaîne "remplacement"
    String replace(String s, char c, String remplacement) {
        for(int i=0; i < s.length(); i++){
            if (s.charAt(i) == c){
                String var = String.valueOf(s.charAt(i));
                var = remplacement;
            }
        }
        return s;
    }

    // demande à l'utilisateur de saisir une note au clavier. Il faut garantir que la note est comprise entre 0 et 20 (et recommencer la saisie sinon).
    float lireNote() {
        Scanner saisieNote = new Scanner(System.in);
        System.out.println("Veuillez saisir une note entre 0 et 20: ");
        float var = saisieNote.nextFloat();
        while (var < 0 || var > 20){
            System.out.println("Veuillez saisir une note entre 0 et 20: ");
            var = saisieNote.nextFloat();
        }
        return var;
    }

    // retourne le nombre de mots dans la chaîne s (les mots sont séparés par un ou plusieurs caractère espace)
    int nombreMots(String s) {
        Scanner in = new Scanner(s);
        int cpt = 0;
        String mot;
        in.useDelimiter("\\s");
        while (in.hasNext()){
            mot = in.next();
            cpt++;
        }
        return cpt;
    }
/*
    // retourne le nombre d'espaces dans la chaîne s
    int nombreEspace(String s) {
    }

    // ajouter un élément à l'ensemble d'entiers
    void ajouter(EnsembleEntier set, int n) {
    }

    // retirer un élément à l'ensemble d'entiers
    void retirer(EnsembleEntier set, int n) {
    }

    // retourne la position de la première occurrence de aiguille dans la chaîne meule (-1 si non trouvé)
    int position(String meule, String aiguille) {

    }



    public static void main(String[] args) {
    }
    */

}