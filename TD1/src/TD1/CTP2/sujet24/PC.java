// BERTRAND Nathanaël 2C

import java.io.IOException;
import java.lang.Thread;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;

class PC {
    int id; // l'identifiant
    float prix; // l'attribut A
    int ram; // l'attribut B
}

class Traitement extends Thread {

    private PC[] tab;
    private int val;
    private int begin;
    private int end;

    public Traitement(PC[] tab, int val, int id) {
        this.tab = tab;
        this.val = val;
        int M = tab.length;
        this.begin = (int) (M / DS.nbThreads) * id;
        if (id == DS.nbThreads - 1)
            this.end = M;
        else
            this.end = (int) (M / DS.nbThreads) * (id + 1);
    }

    public void run() {
        for (int i = begin; i < end; i++) {
            if (tab[i].ram == val) {
                synchronized (DS.res) {
                    DS.res += tab[i].prix;
                }
                synchronized (DS.n) {
                    DS.n++;
                }
            }
        }
    }
}

class DS {
    static final int nbThreads = 4;
    static Float res = 0.0f;
    static Integer n = 0;

    PC[] liste = new PC[9999];

    DS() {
        for (int i = 0; i < liste.length; i++) {
            liste[i] = new PC();
            liste[i].id = i;
            liste[i].prix = 600 + i % 400;
            liste[i].ram = 4 * (1 + i % 8);
        }
    }

    void traiter(int val) {
        Thread[] threads = new Thread[nbThreads];
        for (int i = 0; i < nbThreads; i++) {
            threads[i] = new Traitement(liste, val, i);
            threads[i].start();
        }
        for (int i = 0; i < nbThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // ...
    }

    public static void main(String[] args) {
        DS ds = new DS();
        ds.traiter(4);
        FileChannel f;
        try {
            f = FileChannel.open(FileSystems.getDefault().getPath("resultat.bin"), StandardOpenOption.READ,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
