package Beecrowd;
import java.util.Scanner;
public class Esfera {
    public static double calculaVolume(double raio){
        double volume;
        double pi = 3.14159;
        volume = (4.0/3) * pi * Math.pow(raio, 3);
        return volume;
    }
    public static void resultado(double volume){
        System.out.printf("VOLUME = %.3f\n", volume);
    }
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        double raio = ler.nextDouble();
        resultado(calculaVolume(raio));
    }   
}
