import implement.common.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator(30);
        try {
            dataGenerator.exec();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private int N;
    private int M;
    private int K;
    private int[] d;
    private int dMax = 0;
    private int[] c;
    private Pair[] p;

    private final Random random = new Random();
    private String filename;
    private FileWriter writer;

    public DataGenerator(int n) {
        setup(n);
    }

    public void exec() throws IOException {
        openFile();
        writer = new FileWriter(filename);
        writeClasses();
        writeRooms();
        writeConflicts();
        writer.close();
    }

    private void writeClasses() throws IOException {
        writer.write(N + "\n");
        for (int i = 0; i < N; i++) {
            d[i] = randInt(35, 60);
            dMax = Integer.max(d[i], dMax);
            writer.write(d[i] + " ");
        }
        writer.write("\n");
    }

    private void writeRooms() throws IOException {
        writer.write(M + "\n");
        while (true) {
            int cRand = randInt(35, 65);
            if (cRand >= dMax) {
                c[0] = cRand;
                writer.write(c[0] + " ");
                break;
            }
        }
        for (int i = 1; i < M; i++) {
            c[i] = randInt(35, 60);
            writer.write(c[i] + " ");
        }
        writer.write("\n");
    }

    private void writeConflicts() throws IOException {
        writer.write(K + "\n");
        for (int i = 0; i < K; i++) {
            int fi, se;
            while (true) {
                fi = randInt(1, N);
                se = randInt(1, N);
                while (fi == se) se = randInt(1, N);
                if (fi > se) {
                    int tmp = fi;
                    fi = se;
                    se = tmp;
                }
                if (!existed(i, fi, se)) {
                    p[i] = new Pair(fi, se);
                    break;
                }
            }
            writer.write(fi + " " + se + "\n");
        }
    }

    private boolean existed(int cur, int fi, int se) {
        if (cur == 0) return false;
        for (int i = 0; i < cur; i++) {
            if (fi == p[i].fi && se == p[i].se) return true;
        }
        return false;
    }

    private int randInt(int l, int r) {
        return l + random.nextInt(r - l + 1);
    }

    private void openFile() throws IOException {
        filename = getFilename();
        System.out.println(filename);
        File file = new File(filename);
        if (file.createNewFile()) {
            System.out.println("File " + filename + " created");
        } else {
            System.out.println("File " + filename + " existed");
        }
    }

    private String getFilename() {
        return "src/main/resources/data_" + N + ".txt";
    }

    private void setup(int n) {
        setN(n);
        setM(n);
        setK(n);
        d = new int[N];
        c = new int[M];
        p = new Pair[K];
    }

    private void setN(int n) {
        N = n;
    }

    private void setM(int n) {
        M = n / 5;
    }

    private void setK(int n) {
        K = n * (n - 1) / 20;
    }
}
