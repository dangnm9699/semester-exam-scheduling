package implement;

import implement.common.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author dangnm9699
 * @author tuanbmhust
 */

public abstract class XepLichThiSolverImpl implements XepLichThiSolver {
    protected int N;
    protected int M;
    protected int K;
    protected int[] d;
    protected int[] c;
    protected Pair[] p;

    protected long runtime;

    /**
     * @param input input data filename
     *              path from source root (resources directory)
     *              examples:
     *              data.txt => /data.txt
     *              directory/data.txt => /directory/data.txt
     */
    protected void readData(String input) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(Objects.requireNonNull(getClass().getResource(input)).getFile()));
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
        } catch (IOException ioException) {
            System.out.printf("[ERROR] %s is not file or directory\n", input);
            System.exit(1);
        }
    }
}
