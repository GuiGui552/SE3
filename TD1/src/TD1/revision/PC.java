package revision;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

class PC {
    int id; // l'identifiant
    float prix; // l'attribut A
    int ram; // l'attribut B
}

class DS {
    static final int nbThreads=4;
    
    PC[] liste=new PC[9999];

    DS() {
        for(int i=0;i<liste.length;i++) {
            liste[i].id=i;
            liste[i].prix=600+i%400;
            liste[i].ram=4*(1+i%8);
        }
    }

    void traiter() {

    }

    public static void main(String[] args) {
        DS bin = new DS();
    }
}
