import java.util.*;
public class Espelho {
    private static void imprimeEspelho(int fim, int i){
        if(i <= fim){
            if(i >= 10){
                System.out.print(i/10);
                System.out.print(i%10);
            }
            else{
                System.out.print(i);
            }
            imprimeEspelho(fim, i+1);
            if(i >= 100){
                if(i % 10 < 10){
                    System.out.print(i%10);
                    System.out.print(0);
                    System.out.print(i/100);
                }
                else{
                    System.out.print(i%10);
                    System.out.print(i%100);
                    System.out.print(i/100);
                }
            }
            else if(i >= 10){
                System.out.print(i%10);
                System.out.print(i/10);
                
            }
            else{
                System.out.print(i);
            }
        }
    }

    public static void imprimeEspelhoRec(int inicio, int fim){
        imprimeEspelho(fim, inicio);
    }

    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        for(int i = 0; i < 3; i++){
            int inicio = ler.nextInt();
            int fim = ler.nextInt();
            imprimeEspelhoRec(inicio, fim);
            System.out.println();
        }
        ler.close();
    }
}
