public class Main {
    public static void foo(Integer i) {
        System.out.println("foo(Integer)");
    }

    public static void foo(short i) {
        System.out.println("foo(short)");
    }

    public static void foo(long i) {
        System.out.println("foo(long)");
    }

    public static void foo(Object i) {
        System.out.println("foo(object)");
    }

    public static void foo(int... i) {
        System.out.println("foo(int...)");
    }

    public static void main(String[] args) {
        System.out.println(foo());
    }

    class SomeClass {
        static int i = 1;
    }
    static int foo() {
        try {
            Main.SomeClass someClass = null;
            return someClass.i;
        } catch (Exception e) {
            return 2;
        } catch (Throwable e) {
            return 3;
        } finally {
            return 4;
        }
    }
}

