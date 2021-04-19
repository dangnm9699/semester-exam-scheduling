package implement.dangnm;

import implement.ProblemSolver;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


/**
 * Constraint Programming Solver
 */
public class CPSolverChoco extends ProblemSolver {
    private final Model model;
    private final int[] oneM;

    /**
     * @param dataResource path from Source Root, example: "/data.txt" for data.txt in resource directory
     */
    public CPSolverChoco(String dataResource) {
        readData(dataResource);
        model = new Model("Project 8; Group; Semester 20202");
        oneM = new int[M];
        for (int i = 0; i < M; i++) {
            oneM[i] = 1;
        }
    }

    /**
     *
     */
    public void solve() {
        IntVar[] X = new IntVar[N];
        IntVar[][] Y = new IntVar[N][M];

        for (int i = 0; i < N; i++) {
            X[i] = model.intVar("X[" + i + "]", 0, N - 1);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Y[i][j] = model.intVar("Y[" + i + "][" + j + "]", 0, 1);
            }
        }

        for (int i = 0; i < N; i++) {
            model.scalar(Y[i], c, ">=", d[i]).post();
        }

        for (int i = 0; i < K; i++) {
            model.arithm(X[p[i].fi], "!=", X[p[i].se]);
            model.arithm(X[p[i].se], "!=", X[p[i].fi]);
        }

        for (int j = 0; j < M; j++) {
            for (int fi = 0; fi < N - 1; fi++) {
                for (int se = fi + 1; se < N; se++) {
                    model.ifThen(
                            model.arithm(X[fi], "=", X[se]),
                            model.arithm(Y[fi][j], "+", Y[se][j], "<", 2)
                    );
                }
            }
        }

        for (int i = 0; i < N; i++) {
            model.scalar(Y[i], oneM, "=", 1).post();
        }

        IntVar obj = model.intVar(0, N - 1);
        for (int i = 0; i < N; i++) {
            model.arithm(obj, ">=", X[i]).post();
        }

        model.setObjective(Model.MINIMIZE, obj);
        Solver solver = model.getSolver();
        while (solver.solve()) {
            System.out.println("[SOLUTION FOUND]");
            System.out.printf("Số kíp tối thiểu: %d\n", obj.getValue() + 1);
            for (int i = 0; i < N; i++) {
                int room = 0;
                for (int j = 0; j < M; j++)
                    if (Y[i][j].getValue() == 1) {
                        room = j;
                        break;
                    }
                System.out.printf("Lớp thi %2d: Kíp %d, Phòng %d\n", i, X[i].getValue(), room);
            }
        }

    }
}
