package Beecrowd;
import java.util.Scanner;
public class CalculoSimples {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        int cod1 = ler.nextInt();
        int qtd1 = ler.nextInt();
        float valor1 = ler.nextFloat();
        int cod2 = ler.nextInt();
        int qtd2 = ler.nextInt();
        float valor2 = ler.nextFloat();
        float total = (qtd1 * valor1) + (qtd2 * valor2);
        System.out.printf("VALOR A PAGAR: R$ %.2f\n", total);
    }
}
