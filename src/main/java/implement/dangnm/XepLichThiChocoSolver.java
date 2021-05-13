package implement.dangnm;

import implement.XepLichThiSolverImpl;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


/**
 * Constraint Programming using Choco-Solver
 *
 * @author dangnm9699
 */
public class XepLichThiChocoSolver extends XepLichThiSolverImpl {
    private final Model model;
    private final Solution solution;
    //Variables
    private IntVar[] X;
    private IntVar[][] Y;
    private IntVar objective;

    /**
     * @param data Read {@link XepLichThiSolverImpl#readData(String)} for more information
     */
    public XepLichThiChocoSolver(String data) {
        readData(data);
        model = new Model("Mini-project 8: Xep lich thi");
        solution = new Solution(model);
    }

    @Override
    public void printSolution() {
        System.out.printf("[RUNTIME]: %d (ms)\n[BEST SOLUTION FOUND]\n", runtime);
        System.out.printf("Số kíp tối thiểu: %d\n", solution.getIntVal(objective));
        for (int i = 0; i < N; i++) {
            int room = 0;
            for (int j = 0; j < M; j++)
                if (solution.getIntVal(Y[i][j]) == 1) {
                    room = j + 1;
                    break;
                }
            System.out.printf("Lớp thi %2d: Kíp %d, Phòng %d\n", i + 1, solution.getIntVal(X[i]), room);
        }
    }

    @Override
    public void solve() {
        long start = System.currentTimeMillis();
        setupVariables();
        setupConstraint();
        setupObjective();
        model.setObjective(Model.MINIMIZE, objective);
        Solver solver = model.getSolver();
        while (solver.solve()) {
            solution.record();
        }
        runtime = System.currentTimeMillis() - start;
    }

    private void setupVariables() {
        X = new IntVar[N];
        Y = new IntVar[N][M];

        for (int i = 0; i < N; i++) {
            X[i] = model.intVar("X[" + i + "]", 1, N);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Y[i][j] = model.intVar("Y[" + i + "][" + j + "]", 0, 1);
            }
        }
    }

    private void setupConstraint() {
        for (int i = 0; i < N; i++) {
            model.scalar(Y[i], c, ">=", d[i]).post();
        }

        for (int i = 0; i < K; i++) {
            model.arithm(X[p[i].fi], "!=", X[p[i].se]);
        }

        for (int j = 0; j < M; j++) {
            for (int fi = 0; fi < N - 1; fi++) {
                for (int se = fi + 1; se < N; se++) {
                    model.ifThen(
                            model.arithm(X[fi], "=", X[se]),
                            model.arithm(Y[fi][j], "+", Y[se][j], "<=", 1)
                    );
                }
            }
        }

        for (int i = 0; i < N; i++) {
            model.sum(Y[i], "=", 1).post();
        }
    }

    private void setupObjective() {
        objective = model.intVar(1, N);
        model.max(objective, X).post();
    }
}
