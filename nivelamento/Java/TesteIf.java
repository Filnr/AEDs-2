import java.util.*;
public class TesteIf {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite um numero: ");
        int numero1 = ler.nextInt();
        System.out.print("Digite outro numero: ");
        int numero2 = ler.nextInt();
        int resultado;
        if(numero1 > 45 || numero2 > 45){
            resultado = numero1 + numero2;
            System.out.println("A soma dos numeros é " + resultado);
        }
        else if(numero1 > 20 && numero2 > 20){
            if(numero1 > numero2){
                resultado = numero1 - numero2;
                System.out.println("A subtração de " + numero1 + " por " + numero2 + " vale: " + resultado);
            }
            else{
                resultado = numero2 - numero1;
                System.out.println("A subtração de " + numero2 + " por " + numero1 + " vale: " + resultado);
            }
        }
        else if((numero1 < 10 && numero2 != 0) || (numero2 < 10 && numero1 != 0)){
            resultado = numero1 / numero2;
            System.out.println("A divisão de " + numero1 + " por " + numero2 + " vale: " + resultado);
        }
        else{
            System.out.println("Filipe");
        }
        ler.close();
    }
}
