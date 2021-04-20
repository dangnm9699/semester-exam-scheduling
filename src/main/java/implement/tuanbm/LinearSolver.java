package implement.tuanbm;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import implement.ProblemSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinearSolver extends ProblemSolver {

    MPSolver solver;
    MPVariable[][][] x;
    MPVariable y;
    MPObjective obj;

    static double nInf = Double.NEGATIVE_INFINITY;

//    private void readFile(String filename) {
//        try{
//            File file = new File("data_sample/"+filename);
//            Scanner scanner = new Scanner(file);
//
//            N = scanner.nextInt();
//            d = new int[N];
//            for (int i = 0; i < N; i++) {
//                d[i] = scanner.nextInt();
//            }
//
//            M = scanner.nextInt();
//            c = new int[M];
//            for (int i = 0; i < M; i++) {
//                c[i] = scanner.nextInt();
//            }
//
//            K = scanner.nextInt();
//            cf1 = new int[K];
//            cf2 = new int[K];
//            for (int i = 0; i < K; i++) {
//                int u = scanner.nextInt();
//                int v = scanner.nextInt();
//                u--;v--;
//                cf1[i] = u;
//                cf2[i] = v;
//            }
//
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

    public LinearSolver(String dataResource){
        readData(dataResource);
    }

    public void solve() {
        long startTime = System.currentTimeMillis();

        //Define variables
        x = new MPVariable[N][M][N];
        solver = MPSolver.createSolver("CBC");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    x[i][j][k] = solver.makeIntVar(0,1,"x[" + i + "," + j +  "," + k +"]");
                }
            }
        }

        y = solver.makeIntVar(0,N-1,"y");

        //Define constraints
        // 1st constraint: 2 mon conflict ko duoc xep cung 1 kip
        for (int cf = 0; cf < K; cf++) {
//            int u = cf1[cf], v = cf2[cf];
            int u = p[cf].fi, v = p[cf].se;
            for (int k = 0; k < N; k++) {
                MPConstraint constraint = solver.makeConstraint(0,1);
                for (int j1 = 0; j1 < M; j1++) {
                    for (int j2 = 0; j2 < M; j2++) {
                        if (j1 != j2) {
                            constraint.setCoefficient(x[u][j1][k],1);
                            constraint.setCoefficient(x[v][j2][k],1);
                        }
                    }
                }
            }
        }

        // 2nd constraint: 1 mon chi duoc xep vao toi da 1 phong o 1 kip
        for (int i = 0; i < N; i++) {
            MPConstraint constraint = solver.makeConstraint(1,1);
            for (int k = 0; k < N; k++) {
                for (int j = 0; j < M; j++) {
                    constraint.setCoefficient(x[i][j][k],1);
                }
            }
        }

        // 3rd constraint: 1 phong chi duoc xep toi da 1 mon vao o 1 kip
        for (int k = 0; k < N; k++) {
            for (int j = 0; j < M; j++) {
                MPConstraint constraint = solver.makeConstraint(0,1);
                for (int i = 0; i < N; i++) {
                    constraint.setCoefficient(x[i][j][k],1);
                }
            }
        }

        // 4th constraint: xep mon i vao phong j
        // d[i]*x[i,j,k] <= c[j]
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                MPConstraint constraint = solver.makeConstraint(0,c[j]);
                for (int k = 0; k < N; k++) {
                    constraint.setCoefficient(x[i][j][k], d[i]);
                }
            }
        }

        // 5th constraint:
        // x[i,j,k] - y <= 0
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    MPConstraint constraint = solver.makeConstraint(nInf, 0);
                    constraint.setCoefficient(y, -1);
                    constraint.setCoefficient(x[i][j][k], k);
                }
            }
        }

        //Define objective
        obj = solver.objective();
        obj.setCoefficient(y, 1);
        obj.setMinimization();

        //Solve
        final MPSolver.ResultStatus status = solver.solve();

        long endTime = System.currentTimeMillis();
        System.out.println("\nTime elapsed = "+ (endTime - startTime)+" ms");

        if (status == MPSolver.ResultStatus.OPTIMAL) {
            System.out.println("Objective value = "+ (obj.value()+1));
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) if (x[i][j][k].solutionValue() == 1){
                        System.out.println("Kip "+(k+1)+": mon "+(i+1)+" xep vao phong "+(j+1));
                        break;
                    }
                }
            }
        } else {
            System.out.println("No solution");
        }
    }

}
