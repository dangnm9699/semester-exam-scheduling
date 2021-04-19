import com.google.ortools.Loader;
import implement.dangnm.CPSolverChoco;
import implement.dangnm.CPSolverOrTools;

public class App {
    public static void main(String[] args) {
        Loader.loadNativeLibraries();
        String data = "/data1.txt";
        CPSolverChoco cpSolver = new CPSolverChoco(data);
        cpSolver.solve();

//        CPSolverOrTools cpSolverOrTools = new CPSolverOrTools(data);
//        cpSolverOrTools.solve();
    }
}
