import java.util.*;
public class Maior {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite um numero: ");
        int numero = ler.nextInt();
        int maior = numero;
        for(int i = 0; i < 10; i++)
        {
            System.out.print("Digite o " + (i + 1) + " numero: ");
            numero = ler.nextInt();
            if(maior < numero){
                maior = numero;
            }
        }
        System.out.println("O maior numero digitado foi: " + maior);
        ler.close();
    }
}
