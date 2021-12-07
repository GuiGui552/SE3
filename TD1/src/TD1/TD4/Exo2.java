package TD4;


class X{
    public int x =0;
    synchronized public void inc(){
        x++;
    }
}

class Action2 extends Thread{
    static final int n = 50000;

    public void run(){
        for (int i = 0; i<n; i++){
            Exo2.x.inc();
        }
    }
}

public class Exo2{
    static X x = new X();

    public static void main(String[] args) {
        Thread t1 = new Action2();
        Thread t2 = new Action2();

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(x.x);
    }
}