import java.util.*;

public class disney {

    public static boolean ehFim(String entrada){
        Boolean fim = false;
        if(entrada.length() == 3){
            if(entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M'){
                fim = true;
            }
        }
        return fim;
    }

    public static int converteStr(String entrada){
        int valor = 0;
        int multiplicador = 1;
        for(int i = entrada.length() - 1; i > 0; i--){
            int numero = entrada.charAt(i) - '0';
            valor += numero * multiplicador;
            multiplicador *= 10;
        }
        return valor;
    }

    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        String entrada = ler.nextLine();
        Show[] shows = new Show[100];
        int tam = 0;
        while(!ehFim(entrada)){
            shows[tam] = new Show(converteStr(entrada));
            tam++;
            entrada = ler.nextLine();
        }
        for(int i = 0; i < tam; i++){
            shows[i].imprimir();
        }
        ler.close();
    }
}