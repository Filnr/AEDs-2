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

    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        /*String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            Show filme = new Show(entrada);
            filme.imprimir();
            entrada = ler.nextLine();
        }*/
        int linha = ler.nextInt();
        Show filme = new Show(linha);
        filme.imprimir();
        ler.close();
    }
}