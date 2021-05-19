package implement.tuanbm;

import implement.XepLichThiSolverImpl;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Backtracking
 * @author tuanbmhust
 */
public class BackTracking extends XepLichThiSolverImpl {
    ArrayList<Integer>[] cf;
    int assign[];
    int room[][];
    int ans = Integer.MAX_VALUE;

    public BackTracking(String dataSource) {
        readData(dataSource);

        //Init
        cf = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            cf[i] = new ArrayList<>();
        }
        for (int i = 0; i < K; i++) {
            int u = p[i].fi, v = p[i].se;
            cf[u].add(v);
            cf[v].add(u);
        }

        assign = new int[N];
        Arrays.fill(assign, -1);

        room = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                room[i][j]= -1;
            }
        }
    }

    @Override
    public void printSolution() {

    }

    public void solve() {
        long startTime = System.currentTimeMillis();
        dfs(0,0);
        long endTime = System.currentTimeMillis();
        System.out.println("\nBacktracking duration = "+(endTime - startTime)+" ms");
        if (ans != Integer.MAX_VALUE) {
            System.out.println("Objective value = " + (ans+1));
            //TO-DO: print out assign subject to room
        } else {
            System.out.println("No solution");
        }
    }

    private boolean check(int u, int kip) {
        if (assign[u] > 0) {
            return false;
        }

        for (int i = 0; i < cf[u].size(); i++) {
            int v = cf[u].get(i);
            if (assign[v] == kip) {
                return false;
            }
        }
        return true;
    }
    private boolean checkDone() {
        for (int i = 0; i < N; i++) {
            if (assign[i] == -1){
                return false;
            }
        }
        return  true;
    }

    private void dfs(int u, int kip) {
        if (u == N) {
            ans = Integer.min(ans, kip);
            return;
        }
        if(kip > ans) {
            return;
        }

        for (int i = 0; i < M; i++) if (check(u,kip) && d[u]<c[i] && room[kip][i] == -1){
            assign[u] = kip;
            room[kip][i] = u;
            dfs(u+1,kip);
            assign[u] = -1;
            room[kip][i] = -1;
        }
        dfs(u,kip+1);

//        int[] room = new int[M];
//        Arrays.fill(room, -1);
//        for (int i = 0; i < N; i++) if (check(i,kip)){
//            for (int j = 0; j < M; j++) if (room[j] == -1 && d[i] <= c[j]){
//                assign[i] = kip;
//                room[j] = i;
//            }
//        }
//        dfs(kip+1);
//        for (int i = 0; i < N; i++) if (assign[i] == kip){
//            assign[i] = -1;
//            for (int j = 0; j < M; j++) if (room[j] == i){
//                room[j] = -1;
//                break;
//            }
//            break;
//        }

        return;
    }
}
