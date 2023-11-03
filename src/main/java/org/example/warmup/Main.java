package org.example.warmup;


import java.util.Scanner;

public class Main {
    static class Drob {
        private int chislit;
        private int znamen;

        public Drob() {
        }

        public Drob(int chislit, int znamen) {
            if(chislit != znamen) {
                this.chislit = chislit;
                this.znamen = znamen;
            }else {
                this.chislit = 1;
                this.znamen = 1;
            }
        }

        public int getZnamen() {
            return znamen;
        }

        public void setZnamen(int znamen) {
            this.znamen = znamen;
        }

        public int getChislit() {
            return chislit;
        }

        public void setChislit(int chislit) {
            this.chislit = chislit;
        }

        @Override
        public String toString() {
            return this.chislit + " " + this.znamen;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(" ");

        Drob first = new Drob(Integer.valueOf(s[0]), Integer.valueOf(s[1]));
        Drob second = new Drob(Integer.valueOf(s[2]), Integer.valueOf(s[3]));

        System.out.println(sum(first, second));

    }

    public static Drob sum(Drob first, Drob second) {

        int znamenatel = nok(first.getZnamen(), second.getZnamen());
        int chistlitel = first.getChislit() * (znamenatel / first.getZnamen()) +
                second.getChislit() * (znamenatel / second.getZnamen());

        return new Drob(chistlitel, znamenatel);

    }

    static int nok(int a, int b) {
        if (a == b) {
            return a;
        }
        return a * b / nod(a, b);
    }

    static int nod(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
