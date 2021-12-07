package TD4;

public class Max extends Thread{

    protected int beg, end , posMax;
    protected int tab[];
    public Max(int tab[], int beg, int end){}
    public void run(){
        posMax = beg;
        for (int i = 0; i<end; i++){
            if (tab[i] > tab[posMax]){
                posMax = i;
            }
        }
    }

    public int getPosMax() {
        return posMax;
    }
}
