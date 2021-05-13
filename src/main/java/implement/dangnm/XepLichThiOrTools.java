package implement.dangnm;

import com.google.ortools.sat.*;
import implement.XepLichThiSolverImpl;

/**
 * Constraint Programming using Or-Tools
 */
public class XepLichThiOrTools extends XepLichThiSolverImpl {
    private final CpModel cpModel;
    private CpSolver solver;
    private CpSolverStatus status;
    //Variables
    private IntVar[] X;
    private IntVar[][] Y;
    private IntVar objective;

    /**
     * @param data Read {@link XepLichThiSolverImpl#readData(String)} for more information
     */
    public XepLichThiOrTools(String data) {
        readData(data);
        cpModel = new CpModel();
    }

    @Override
    public void printSolution() {
        if (status == CpSolverStatus.OPTIMAL) {
            System.out.printf("[RUNTIME]: %d (ms)\n[BEST SOLUTION FOUND]\n", runtime);
            System.out.printf("Số kíp tối thiểu: %d\n", solver.value(objective));
            for (int i = 0; i < N; i++) {
                int room = 0;
                for (int j = 0; j < M; j++)
                    if (solver.value(Y[i][j]) == 1) {
                        room = j + 1;
                        break;
                    }
                System.out.printf("Lớp thi %2d: Kíp %d, Phòng %d\n", i + 1, solver.value(X[i]), room);
            }
        }
    }

    @Override
    public void solve() {
        long start = System.currentTimeMillis();
        setupVariables();
        setupConstraint();
        setupObjective();
        cpModel.minimize(objective);
        solver = new CpSolver();
        status = solver.solve(cpModel);
        runtime = System.currentTimeMillis() - start;

    }

    private void setupVariables() {
        X = new IntVar[N];
        Y = new IntVar[N][M];

        for (int i = 0; i < N; i++) {
            X[i] = cpModel.newIntVar(1, N, "X[" + i + "]");
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Y[i][j] = cpModel.newIntVar(0, 1, "Y[" + i + "][" + j + "]");
            }
        }
    }

    private void setupConstraint() {
        for (int i = 0; i < N; i++) {
            cpModel.addGreaterOrEqual(LinearExpr.scalProd(Y[i], c), d[i]);
        }

        for (int i = 0; i < K; i++) {
            cpModel.addDifferent(X[p[i].fi], X[p[i].se]);
        }

        for (int j = 0; j < M; j++) {
            for (int i1 = 0; i1 < N - 1; i1++) {
                for (int i2 = i1 + 1; i2 < N; i2++) {
                    IntVar b = cpModel.newBoolVar("b[" + j + "][" + i1 + "][" + i2 + "]");
                    cpModel.addLessOrEqual(LinearExpr.sum(new IntVar[]{Y[i1][j], Y[i2][j]}), 1).onlyEnforceIf(b);
                    cpModel.addEquality(X[i1], X[i2]).onlyEnforceIf(b);
                    cpModel.addDifferent(X[i1], X[i2]).onlyEnforceIf(b.not());
                }
            }
        }

        for (int i = 0; i < N; i++) {
            cpModel.addEquality(LinearExpr.sum(Y[i]), 1);
        }
    }

    private void setupObjective() {
        objective = cpModel.newIntVar(1, N, "objective");
        cpModel.addMaxEquality(objective, X);
    }
}