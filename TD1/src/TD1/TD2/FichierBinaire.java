package TD2;

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

class FichierBinaire {
    class Produit{
        int ref; // une référence
        float prix; // un prix
        int quantite;
        // nombre d'octets pour stocker un produit
        static final int BYTES=Integer.BYTES+Float.BYTES+Integer.BYTES;

        public Produit() {
            this.ref = 0;
            this.prix = 0;
            this.quantite = 0;
        }

        public Produit(int ref, float prix, int quantite) {
            this.ref = ref;
            this.prix = prix;
            this.quantite = quantite;
        }
    };



    FileChannel f; // le fichier binaire
    ByteBuffer buf; // le tampon pour écrire dans le fichier

    /**
     * écrire un produit à la position courante du fichier
     */
    void ecrireProduit(Produit prod) throws IOException{
        // copier le produit dans le tampon
        buf.clear(); // avant d'écrire, on vide le tampon
        buf.putInt(prod.ref);
        buf.putFloat(prod.prix);
        buf.putInt(prod.quantite);
        // copier le tampon dans le fichier
        buf.flip(); // passage à une lecture du tampon
        while(buf.hasRemaining()) // tant qu'on n'a pas écrit tout le buffer
            f.write(buf);
    }

    /**
     * lire un produit à la position courante du fichier
     */
    Produit lireProduit() throws IOException{
        // copie du fichier vers le tampon
        buf.clear(); // avant d'écrire, on vide le tampon
        while(buf.hasRemaining()) // tant qu'on n'a pas rempli le buffer
            if(f.read(buf)==-1)
                return null;
        // copie du tampon vers le produit
        buf.flip(); // passage à une lecture du tampon
        Produit prod=new Produit();
        // il faut relire les données dans le même ordre que lors de l'écriture
        prod.ref=buf.getInt();
        prod.prix=buf.getFloat();
        prod.quantite = buf.getInt();
        return prod;
    }

    String getProduit(int n){
        return "Produit"+n+":\n...";
    }

    FichierBinaire(String filename) throws IOException {
        //ouverture en lecture/écriture, avec création du fichier
        f=FileChannel.open(
                FileSystems.getDefault().getPath(filename),
                StandardOpenOption.READ,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
        // création d'un buffer juste assez grand pour contenir un produit
        buf=ByteBuffer.allocate(Produit.BYTES);
    }

    /**
     * création du fichier
     */
    void ecrire() throws IOException {
        f.truncate(0);//suppr
        Produit prod=new Produit();
        for(int id=1;id<=5;id++) {
            prod.ref=id;
            prod.prix=id*10;
            prod.quantite=(int) Math.pow(id, 2)+15;
            ecrireProduit(prod);
        }
    }

    int chercheId(int id)throws IOException{
        Produit prod;
        int posProd=-1;
        f.position(0); // revenir au début du fichier
        boolean trouve = false;
        while((prod=lireProduit())!=null && !trouve) {
            posProd++;
            if (prod.ref == id) {
                trouve = true;
            }
        }
        if (trouve) return posProd;
        else return -1;
    }

    void ajouterProduit(Produit p)throws IOException{
        int pos;
        if ((pos=chercheId(p.ref))==-1){ // si le produit n'est pas présent
            f.position(f.size());
            ecrireProduit(p);
        }else{
            f.position(pos*Produit.BYTES);
            ecrireProduit(p);
            System.out.println("Mise à jour du produit numéro: "+p.ref);
        }
    }

    void deleteId(int id) throws IOException{
        int p = chercheId(id);
        if (p == -1){
            System.out.println("Ce produit n'existe pas");
            return;
        }
        f.position(f.size() - Produit.BYTES);
        Produit produit = lireProduit();
        f.position(p*Produit.BYTES);
        ecrireProduit(produit);
        f.truncate(f.size()-Produit.BYTES);

    }

    void modifierQuantite(int ref, int quantite)throws IOException{
        int index = chercheId(ref);
        if (index == -1)return;
        f.position(f.size()*Produit.BYTES);
    }

    /**
     * relecture du fichier
     */
    void lire() throws IOException {
        int nbElement=((int)f.size());
        System.out.println("\tlecture du fichier(nb"+nbElement+"):");
        Produit prod;
        //f.position(0);//revenir debut fichier
        f.position(Produit.BYTES); // revenir au début du fichier

        while((prod=lireProduit())!=null)
            System.out.println(prod.ref+"\t"+prod.prix+"\t"+prod.quantite);
    }

    void lireIdProduit(int id) throws IOException{
        Produit p;
        f.position(0); //revenir debut du fichier
        boolean t = false;
        while ((p = lireProduit()) != null && !t){
            if (p.ref == id){
                System.out.println("reference: "+p.ref+"\t"+"prix: "+p.prix+"\t quantité: "+p.quantite);
                t = true;
            }
        }
    }

    void lirePos(int p)throws IOException{
        if (p <= 0 || p > f.size() / Produit.BYTES){
            System.out.println("Erreur");
            return;
        }
        Produit produit;
        f.position(Produit.BYTES*(p-1)); // revenir debut fichier
        if ((produit = lireProduit()) != null){
            System.out.println("reference: "+produit.ref+"\t"+"prix: "+produit.prix+"\t quantité: "+produit.quantite);
        }
    }

    /**
     * relecture du fichier à l'envers
     */
    void lireALEnvers() throws IOException {
        Produit prod;
        long pos=f.size()-Produit.BYTES; // position du dernier produit

        while(pos>=0) {
            f.position(pos);
            prod=lireProduit();
            System.out.println(prod.ref+"\t"+prod.prix+"\t");
            pos-=Produit.BYTES;
        }
    }




    void run() throws IOException {
        ecrire();
        lire();
        System.out.println("---------");
        lireALEnvers();
        System.out.println("--------");
        lireIdProduit(4);
        System.out.println("---------");
        lirePos(6);
        System.out.println("---------");
        ajouterProduit(new Produit(9,(float)25.5,3));
        ajouterProduit(new Produit(3,(float)25.5,3));
        lire();
        System.out.println("-------------");
        deleteId(4);
        lire();
        f.close();
    }

    public static void main(String[] args) {
        try {
            FichierBinaire bin=new FichierBinaire("catalogue.bin");
            bin.run();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}