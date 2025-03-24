import java.util.*;
public class PCartas {

    public static void mover(int[] cartas,int qtCartas){
        int resp = cartas[qtCartas-1];
        for(int i = qtCartas - 1; i > 0; i--){
            cartas[i] = cartas[i-1];
        }
        cartas[0]= resp;
    }
    
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        int n = ler.nextInt();
        while(n != 0){
            int qtCartas = n;
            int[] pilha = new int[n];
            for(int i = n; i > 0; i--){
                pilha[n-i] = i;
            }
            System.out.printf("Discarded cards: ");
            while(qtCartas >= 2){
                if(qtCartas >= 3){
                    System.out.printf("%d, ", pilha[qtCartas - 1]);
                }
                else{
                    System.out.printf("%d", pilha[qtCartas - 1]);
                }
                qtCartas--;
                mover(pilha, qtCartas);
            }
            System.out.printf("\n");
            System.out.printf("Remaining card: %d\n", pilha[0]);

            n = ler.nextInt();
        }
        ler.close();
    }
}
