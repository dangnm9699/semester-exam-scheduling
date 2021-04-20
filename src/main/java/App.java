import com.google.ortools.Loader;
import implement.dangnm.CPSolverChoco;
import implement.dangnm.CPSolverOrTools;
import implement.tuanbm.BackTracking;
import implement.tuanbm.LinearSolver;

public class App {
    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        String data = "/data1.txt";

        //Backtrack
        BackTracking backTracking = new BackTracking(data);
        backTracking.solve();

        //Mix Integer Programming Google Or-tools
        LinearSolver linearSolver = new LinearSolver(data);
        linearSolver.solve();

        //Constraint Programming Choco-Solver
        CPSolverChoco cpSolver = new CPSolverChoco(data);
        cpSolver.solve();

        //Constraint Programming Google Or-tools
        CPSolverOrTools cpSolverOrTools = new CPSolverOrTools(data);
        cpSolverOrTools.solve();

    }
}
