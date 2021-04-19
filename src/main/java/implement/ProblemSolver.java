package implement;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author dangnm
 */

public class ProblemSolver {
    protected int N;
    protected int M;
    protected int K;
    protected int[] d;
    protected int[] c;
    protected Pair[] p;

    /**
     * This is constructor function
     */
    public ProblemSolver() {

    }

    /**
     * @param src path to data in resources directory
     */
    protected void readData(String src) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(getClass().getResource(src).getFile()));
            N = Integer.parseInt(scanner.next());
            d = new int[N];
            for (int i = 0; i < N; i++) {
                d[i] = Integer.parseInt(scanner.next());
            }
            M = Integer.parseInt(scanner.next());
            c = new int[M];
            for (int i = 0; i < M; i++) {
                c[i] = Integer.parseInt(scanner.next());
            }
            K = Integer.parseInt(scanner.next());
            p = new Pair[K];
            for (int i = 0; i < K; i++) {
                int s1 = Integer.parseInt(scanner.next());
                int s2 = Integer.parseInt(scanner.next());
                p[i] = new Pair(s1 - 1, s2 - 1);
            }
            System.out.println(N);
            System.out.println(Arrays.toString(d));
            System.out.println(M);
            System.out.println(Arrays.toString(c));
            System.out.println(K);
            for (Pair pair : p) {
                System.out.printf("(%2d,%2d)\n", pair.fi, pair.se);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
    }
}
