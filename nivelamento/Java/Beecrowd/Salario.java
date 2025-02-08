package Beecrowd;

import java.util.Scanner;
public class Salario {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        int numero = ler.nextInt();
        int horas = ler.nextInt();
        float valor = ler.nextFloat();
        float salario = horas * valor;
        System.out.println("NUMBER = " + numero);
        System.out.printf("SALARY = U$ %.2f\n", salario);
    }
}
