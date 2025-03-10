package Beecrowd;
import java.util.Scanner;

public class Area {
    public static double triangulo(double a, double c){
        double area = (a * c) / 2;
        return area;
    }
    public static double circulo(double c){
        double pi = 3.14159;
        double area = pi * Math.pow(c, 2);
        return area;
    }
    public static double trapezio(double a, double b, double c){
        double area = ((a + b) * c) / 2;
        return area;
    }
    public static double quadrado(double b){
        double area = Math.pow(b, 2);
        return area;
    }
    public static double retangulo(double a, double b){
        double area = a * b;
        return area;
    }
    public static void resultado(double a, double b, double c){
        System.out.printf("TRIANGULO: %.3f\n", triangulo(a, c));
        System.out.printf("CIRCULO: %.3f\n", circulo(c));
        System.out.printf("TRAPEZIO: %.3f\n", trapezio(a, b, c));
        System.out.printf("QUADRADO: %.3f\n", quadrado(b));
        System.out.printf("RETANGULO: %.3f\n", retangulo(a, b));
    }
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        double a = ler.nextDouble();
        double b = ler.nextDouble();
        double c = ler.nextDouble();
        resultado(a, b, c);
    }
}
