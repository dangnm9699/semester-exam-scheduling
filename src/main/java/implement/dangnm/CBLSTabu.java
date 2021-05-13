package implement.dangnm;

import implement.XepLichThiSolverImpl;
import library.localsearch.constraints.basic.Implicate;
import library.localsearch.constraints.basic.IsEqual;
import library.localsearch.constraints.basic.LessOrEqual;
import library.localsearch.constraints.basic.NotEqual;
import library.localsearch.functions.basic.FuncMult;
import library.localsearch.functions.basic.FuncPlus;
import library.localsearch.functions.max_min.Max;
import library.localsearch.functions.sum.Sum;
import library.localsearch.model.ConstraintSystem;
import library.localsearch.model.IFunction;
import library.localsearch.model.LocalSearchManager;
import library.localsearch.model.VarIntLS;
import library.localsearch.search.TabuSearch;

/**
 * Constraint based local search with tabu search
 *
 * @author dangnm9699
 */
public class CBLSTabu extends XepLichThiSolverImpl {
    private VarIntLS[] X;
    private VarIntLS[][] Y;
    private VarIntLS objective;
    private ConstraintSystem CS;

    /**
     * @param data Read {@link XepLichThiSolverImpl#readData(String)} for more information
     */
    public CBLSTabu(String data) {
        readData(data);
    }

    @Override
    public void printSolution() {
        System.out.printf("[RUNTIME]: %d (ms)\n[BEST SOLUTION FOUND]\n", runtime);
        System.out.printf("Số kíp tối thiểu: %d\n", objective.getValue());
        for (int i = 0; i < N; i++) {
            int room = 0;
            for (int j = 0; j < M; j++)
                if (Y[i][j].getValue() == 1) {
                    room = j + 1;
                    break;
                }
            System.out.printf("Lớp thi %2d: Kíp %d, Phòng %d\n", i + 1, X[i].getValue(), room);
        }
    }

    @Override
    public void solve() {
        long start = System.currentTimeMillis();
        stateModel();
        search();
        runtime = System.currentTimeMillis() - start;
    }

    private void stateModel() {
        LocalSearchManager mgr = new LocalSearchManager();
        //Variables
        X = new VarIntLS[N];
        Y = new VarIntLS[N][M];

        for (int i = 0; i < N; i++) {
            X[i] = new VarIntLS(mgr, 1, N);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Y[i][j] = new VarIntLS(mgr, 0, 1);
            }
        }
        //Constraints
        CS = new ConstraintSystem(mgr);
        //C1
        for (int i = 0; i < K; i++) {
            CS.post(new NotEqual(X[p[i].fi], X[p[i].se]));
            CS.post(new NotEqual(X[p[i].se], X[p[i].fi]));
        }
        //C2
        for (int j = 0; j < M; j++) {
            for (int fi = 0; fi < N - 1; fi++) {
                for (int se = fi + 1; se < N; se++) {
                    CS.post(
                            new Implicate(
                                    new IsEqual(X[fi], X[se]),
                                    new LessOrEqual(
                                            new FuncPlus(Y[fi][j], Y[se][j]),
                                            1
                                    )
                            )
                    );
                }
            }
        }
        //C3
        for (int i = 0; i < N; i++) {
            CS.post(new IsEqual(
                    new Sum(Y[i]),
                    1
            ));
        }
        //C4
        for (int i = 0; i < N; i++) {
            IFunction[] c1 = new IFunction[M];
            for (int j = 0; j < M; j++) {
                c1[j] = new FuncMult(Y[i][j], c[j]);
            }
            CS.post(new LessOrEqual(d[i], new Sum(c1)));
        }
        //Objective
        objective = new VarIntLS(mgr, 1, N);
        CS.post(new IsEqual(new Max(X), objective));

        //mandatory
        mgr.close();
    }

    private void search() {
        TabuSearch tabuSearch = new TabuSearch();
        tabuSearch.search(CS, 20, 10000, 10000, 100);
    }
}
