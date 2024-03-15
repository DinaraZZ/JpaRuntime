package kz.runtime.jpa;

public class Test {
    public static void main(String[] args) {
        System.out.println(isEmpty("\n\n\n"));
        System.out.println(isDouble("65.6"));
    }

    public static boolean isEmpty(String str) {
        if (str.trim().length() == 0) return true;
        return false;
    }

    public static boolean isDouble(String str) {
        String regex = "\\d+.*\\d*";
        return str.matches(regex);
    }
}
