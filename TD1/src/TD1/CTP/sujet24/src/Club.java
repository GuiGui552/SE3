// Nathanaël BERTRAND - 2C

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class Club {
    static final int nbMatchs=19;
    
    int res;
    int[] points=new int[nbMatchs]; // points obtenus pour chacun des matchs
    int id; // identifiant du club

    public void textParse(int min, int max) throws FileNotFoundException {
        Scanner in = new Scanner(new File("Club.txt"));
        PrintWriter out = new PrintWriter("resultat.txt");

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] split_line = line.split("\\|");
            res = Integer.parseInt(split_line[0]);
            for (int i = 1; i < nbMatchs + 1; i++) {
                points[i] = Integer.parseInt(split_line[i]);
            }
            if (!(min <= points[0] && points[0] <= max)) continue;
            id = Integer.parseInt(split_line[split_line.length - 1]);

            out.print(res);
            out.print("|");
            for (int p : points) {
                out.print(p);
                out.print("|");
            }
            out.println(id);

//            out.println(line);    // Aurait été plus simple
        }

        in.close();
        out.close();
    }

    public void binParse(int min, int max) throws IOException {
        FileChannel f = FileChannel.open(
                FileSystems.getDefault().getPath("Club.txt"),
                StandardOpenOption.READ
        );
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES * 21);
        buf.clear();
        while (f.position() != f.end()) { // Incorrect. Ligne pour vérifier que la fin du fichier n'a pas été atteinte
            while (buf.hasRemaining())
                if (f.read(buf) == -1)
                    return;
            buf.flip();

            res = buf.getInt();
            for (int i = 1; i < nbMatchs + 1; i++) {
                points[i] = buf.getInt();
            }
            if (!(min <= points[0] && points[0] <= max)) continue;
            id = buf.getInt();

            System.out.print(res);
            System.out.print("|");
            for (int p : points) {
                System.out.print(p);
                System.out.print("|");
            }
            System.out.println(id);
        }
    }

    public static void main(String[] args) throws IOException {
        Club c = new Club();
        c.textParse(10, 15);
        c.binParse(10, 15);
    }


}
