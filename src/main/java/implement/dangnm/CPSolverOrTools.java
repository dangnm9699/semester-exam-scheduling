package implement.dangnm;

import com.google.ortools.sat.*;
import implement.ProblemSolver;

/**
 * Constraint Programming Solver
 */
public class CPSolverOrTools extends ProblemSolver {
    private final CpModel cpModel;
    private final int[] oneM;

    /**
     * @param dataResource path from Source Root, example: "/data.txt" for data.txt in resource directory
     */
    public CPSolverOrTools(String dataResource) {
        readData(dataResource);
        cpModel = new CpModel();
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
            X[i] = cpModel.newIntVar(0, N - 1, "X[" + i + "]");
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Y[i][j] = cpModel.newIntVar(0, 1, "Y[" + i + "][" + j + "]");
            }
        }

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
                    cpModel.addLessThan(LinearExpr.sum(new IntVar[]{Y[i1][j], Y[i2][j]}), 2).onlyEnforceIf(b);
                    cpModel.addEquality(X[i1], X[i2]).onlyEnforceIf(b);
                    cpModel.addDifferent(X[i1], X[i2]).onlyEnforceIf(b.not());
                }
            }
        }

        for (int i = 0; i < N; i++) {
            cpModel.addEquality(LinearExpr.scalProd(Y[i], oneM), 1);
        }

        IntVar obj = cpModel.newIntVar(0, N - 1, "Objective");
        for (int i = 0; i < N; i++) {
            cpModel.addGreaterOrEqual(obj, X[i]);
        }

        cpModel.minimize(obj);
        CpSolver cpSolver = new CpSolver();
        CpSolverStatus status = cpSolver.solve(cpModel);

        if (status == CpSolverStatus.OPTIMAL) {
            System.out.println("[SOLUTION FOUND]");
            System.out.printf("Số kíp tối thiểu: %d\n", cpSolver.value(obj) + 1);
            for (int i = 0; i < N; i++) {
                int room = 0;
                for (int j = 0; j < M; j++)
                    if (cpSolver.value(Y[i][j]) == 1) {
                        room = j;
                        break;
                    }
                System.out.printf("Lớp thi %2d: Kíp %d, Phòng %d\n", i, cpSolver.value(X[i]), room);
            }
        }
    }
}