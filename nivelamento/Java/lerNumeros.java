import java.util.*;
public class lerNumeros {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite o 1º número: ");
        int numero = ler.nextInt();
        int maior = numero, menor = numero;
        for(int i = 1; i < 10; i++){
            System.out.print("Digite o " + (i + 1) + "º número: ");
            numero = ler.nextInt();
            if(numero > maior){
                maior = numero;
            }
            else if(numero < menor){
                menor = numero;
            }
        }
        System.out.println("Maior: " + maior);
        System.out.println("Menor: " + menor);
    }
}
