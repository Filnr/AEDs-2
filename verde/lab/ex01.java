import java.util.*;

public class ex01 {

    public static boolean ehMaiusculo(char letra){
        return (letra >= 'A' && letra <= 'Z');
    }

    public static int contaMaiusculo(String palavra){
        int qtMai = 0;
        for(int i = 0; i < palavra.length(); i++){
            char letra = palavra.charAt(i);
            if(ehMaiusculo(letra)){
                qtMai++;
            }
        }
        return qtMai;
    }

    public static boolean ehFim(String entrada){
        return ((entrada.length() == 3) && (entrada.charAt(0) == 'F') && (entrada.charAt(1) == 'I') && (entrada.charAt(2) == 'M'));
    }

    public static void main(String[] args){
        Scanner leitor = new Scanner(System.in);
        String entrada = leitor.nextLine();
        while(!ehFim(entrada)){
            System.out.println(contaMaiusculo(entrada));
            entrada = leitor.nextLine();
        }
        leitor.close();
    }
}