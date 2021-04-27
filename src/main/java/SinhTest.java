import implement.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SinhTest {
    int n,m,k,maxVal;
    ArrayList<Pair> pairs = new ArrayList<>();

    private int getRandomNumber(int l, int r) {
        return (int) ((Math.random() * (r - l)) + l);
    }

    private boolean checkExists(int u, int v) {
        for (int i = 0; i < pairs.size(); i++) {
            if (u == pairs.get(i).fi && v == pairs.get(i).se) return false;
        }
        return true;
    }

    SinhTest(int n) {
        this.n = n;
    }

    void run() throws IOException {
        maxVal = 0;
        boolean check = false;

        m = n/5;
        k = getRandomNumber(n*(n-1)/20 ,n*(n-1)/15); //k is in range of 20-25% of n

        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/testsinh/data2.txt", true));
        writer.append(String.valueOf(n));
        writer.append('\n');
        for (int i = 0; i < n; i++) {
            int d = getRandomNumber(35,60);
            maxVal = Integer.max(maxVal,d);
            writer.append(String.valueOf(d));
            writer.append(' ');
        }

        writer.append('\n');
        writer.append(String.valueOf(m));
        writer.append('\n');
        while (!check) {
            int c = getRandomNumber(35,65);
            if (c>=maxVal) check = true;
            writer.append(String.valueOf(c));
            writer.append(' ');
            break;
        }
        for (int i = 0; i < m-1; i++) {
            int c = getRandomNumber(35,65);
            writer.append(String.valueOf(c));
            writer.append(' ');
        }

        writer.append('\n');
        writer.append(String.valueOf(k));
        writer.append('\n');
        for (int i = 0; i < k; i++) {
            int u,v;
            while (true) {
                u = getRandomNumber(1,n);
                v= getRandomNumber(1,n);
                while (u==v) {
                    v= getRandomNumber(1,n);
                }
                if (u>v) {
                    int temp = v;
                    v=u;
                    u=temp;
                }
                if (checkExists(u,v)) {
                    pairs.add(new Pair(u,v));
                    break;
                }
            }

            writer.append(String.valueOf(u));
            writer.append(' ');
            writer.append(String.valueOf(v));
            writer.append('\n');
        }

        writer.close();

        return;
    }
    
    public static void main(String[] args) throws IOException {
        SinhTest st = new SinhTest(30);
        st.run();
    }
}
