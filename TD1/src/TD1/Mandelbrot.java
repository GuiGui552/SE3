/*import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Mandelbrot {
    public static void main(String[] args) throws Exception {
        int width = 1920, height = 1080, max = 1000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = (col - width/2)*4.0/width;
                double c_im = (row - height/2)*4.0/width;
                double x = 0, y = 0;
                double r2;
                int iteration = 0;
                while (x*x+y*y < 4 && iteration < max) {
                    double x_new = x*x-y*y+c_re;
                    y = 2*x*y+c_im;
                    x = x_new;
                    iteration++;
                }
                if (iteration < max) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, black);
            }
        }

        ImageIO.write(image, "png", new File("mandelbrot.png"));
    }
}*/

import java.io.*;
import static java.nio.charset.StandardCharsets.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

class Mandelbrot {
    /*
      implantation simple et minimaliste des complexes. Attention, il y a trop
      d'allocations. Sur de grosses images avec plein de threads on
      peut obtenir une exception java.lang.OutOfMemoryError: GC
      overhead limit exceeded
     */
    static class Complex {
        private double r,i;
        public Complex() {r=0;i=0;}
        public Complex(double re, double im) {r=re;i=im;}
        public Complex(Complex c) {r=c.r;i=c.i;}
        public Complex add(Complex c) {
            Complex res=new Complex();
            res.r=r+c.r;
            res.i=i+c.i;
            return res;
        }
        public Complex mul(Complex c) {
            Complex res=new Complex();
            res.r=r*c.r-i*c.i;
            res.i=r*c.i+i*c.r;
            return res;
        }
        public double module() {
            return Math.sqrt(r*r+i*i);
        }
        public double module2() {
            return r*r+i*i;
        }
        public String toString() {
            return ""+r+"i*"+i;
        }
    }

    // une couleur RGB
    static class RGB {
        // ce serait mieux d'utiliser des shorts, l'idéal étant d'utiliser un octet non signé (qui n'existe pas en Java)
        int r,g,b;
        public RGB(int r, int g, int b) {
            this.r=r;
            this.g=g;
            this.b=b;
        }
        public RGB(double r, double g, double b) {
            this.r=(int)(r+.5);
            this.g=(int)(g+.5);
            this.b=(int)(b+.5);
        }
    }

    int nbIter(Complex c) {
        int n=0;
        Complex z=new Complex();
        while(n<maxIter && z.module2()<=4) {
            z=z.mul(z).add(c);
            n++;
        }
        return n;
    }

    RGB getColor(int nbIter, RGB min, RGB max) {
        if(nbIter>=maxIter)
            return new RGB(0,0,0); // black
        else {
            double t=nbIter/(double)(maxIter-1);
            // une interpolation linéaire de la couleur donne un
            // résultat assez fade. Il vaut mieux utiliser une
            // interpolation non linéaire. La ligne ci-dessous est une
            // bidouille empirique. Il existe des algos spécialisés
            // pour colorier l'ensemble.
            t=Math.pow(t,0.3);
            return new RGB(min.r+t*(max.r-min.r),
                    min.g+t*(max.g-min.g),
                    min.b+t*(max.b-min.b));
        }
    }

    int maxIter;
    int width,height;
    RGB[][] img;
    RGB minCol,maxCol;
    Complex zll,zur;

    public Mandelbrot(int width, int height, RGB min, RGB max,
                      Complex center, double largeurX, int maxIter) {
        this.width=width;
        this.height=height;
        this.maxIter=maxIter;
        // pour garder un repère orthonormé, on calcule les
        // coordonnées du coin inférieur gauche (Lower Left) et
        // supérieur droit (Upper Right) à partir du centre et du
        // rayon sur l'axe x
        zll=new Complex(center); // attention, ici, il faut une copie de center !
        zll.r-=largeurX/2;
        zll.i-=largeurX/2*height/width;
        zur=new Complex(center); // idem
        zur.r+=largeurX/2;
        zur.i+=largeurX/2*height/width;
        minCol=min;
        maxCol=max;
        img=new RGB[width][height];
    }

    public void compute() {
        // version purement séquentielle
        for(int x=0;x<width;x++)
            for(int y=0;y<height;y++) {
                Complex c=new Complex(zll.r+(zur.r-zll.r)*x/(width-1),
                        zll.i+(zur.i-zll.i)*y/(height-1));
                img[x][y]=getColor(nbIter(c),minCol,maxCol);
            }
    }

    public void savePPMtxt(String fileName) {
        try {
            PrintWriter out=new PrintWriter(fileName);
            out.println("P3");
            out.println(width+" "+height);
            out.println(255);
            for(int y=height-1;y>=0;y--) {
                for(int x=0;x<width;x++)
                    out.print(img[x][y].r+" "+img[x][y].g+" "+img[x][y].b+"\n");
                out.println();
            }
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * convertit un octet non signé (dans l'intervalle 0..255) en
     * entier signé (dans l'intervalle -128..127) pour que la
     * représentation binaire de cet entier signé soit égale à la
     * représentation binaire de l'octet non signé. De ce fait, quand
     * on écrit ce byte dans un fichier binaire, tout se passe comme si
     * on écrivait un octet non signé.
     */
    byte toByte(int b) {
        assert(b>=0 && b<=255);
        if(b<128)
            return (byte)b;
        else
            return (byte)(256-b);
    }

    public void savePPMbin(String fileName) {
        try {
            FileChannel f=FileChannel.open(FileSystems.getDefault().getPath(fileName),StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            ByteBuffer buf=ByteBuffer.allocate(1024);
            String header="P6\n"+width+" "+height+"\n255\n";
            buf.clear();
            buf.put(header.getBytes(US_ASCII));

            for(int y=height-1;y>=0;y--) {
                for(int x=0;x<width;x++) {

                    if(buf.remaining()<3) {
                        buf.flip();
                        while(buf.hasRemaining())
                            f.write(buf);
                        buf.clear();
                    }

                    buf.put(toByte(img[x][y].r));
                    buf.put(toByte(img[x][y].g));
                    buf.put(toByte(img[x][y].b));
                }
            }

            buf.flip();
            while(buf.hasRemaining())
                f.write(buf);
            f.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // source wikipedia
        double[][] liste={
                {-0.7, 0, 3, 1000},
                {-0.87591, 0.20464, 0.53184, 1000},
                {-0.759856, 0.125547, 0.051579, 1000},
                {-0.743030, .126433, .016110, 1000},
                {-.7435669, .1314023, .0022878, 1000},
                {-.74364990, .13188204, .00073801, 1000},
                {-.74364085, .13182733, .00012068, 1000},
                {-.743643135, .131825963, .000014628, 1000},
                {-.7436447860, .1318252536, .0000029336, 1000},
                {-.74364409961, .13182604688, .00000066208, 1000},
                {-0.74364386269, 0.13182590271, 0.00000013526, 10000},
                {-0.743643900055, 0.131825890901, 0.000000049304, 10000},
                {-0.7436438885706, 0.1318259043124, 0.0000000041493, 10000},
                {-0.74364388717342, 0.13182590425182, 0.00000000059849, 10000},
                {-.743643887037151, .131825904205330, .000000000051299, 10000}
        };

        for(int i=0;i<liste.length;i++) {
            System.out.println("mandelbrot"+i+".ppm");
            Mandelbrot img=new Mandelbrot(2000,2000,
                    new RGB(0,10,10), new RGB(0,250,250),
                    new Complex(liste[i][0],liste[i][1]),
                    liste[i][2],(int)liste[i][3]
            );
            img.compute();
            img.savePPMtxt("mandelbrot"+i+".ppm");
            //img.savePPMbin("mandelbrot"+i+".ppm");
        }
    }
}