import java.util.*;
public class maiorMenor {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite um numero: ");
        int numero = ler.nextInt();
        int maior = numero, menor = numero;
        for(int i = 1; i < 3; i++)
        {
            System.out.print("Digite o " + (i + 1) + " numero: ");
            numero = ler.nextInt();
            if(maior < numero){
                maior = numero;
            }
            else if(menor > numero){
                menor = numero;
            }
        }
        System.out.println("O maior numero foi: " + maior);
        System.out.println("O menor numero foi: " + menor);
        ler.close();
    }
}
