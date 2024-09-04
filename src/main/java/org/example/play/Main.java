package org.example.play;


public class Main {
    public static void main(String[] args) {
        var i = new int[]{0,1};
        try {
            int i1 = i[3];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            int i1 = i[3];
        }
    }

    public static int fact(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * fact(n - 1);
        }
    }
}
