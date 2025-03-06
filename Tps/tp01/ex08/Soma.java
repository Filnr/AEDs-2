import java.util.*;
public class Soma {
        public static boolean ehFim(String entrada) {
            //Função que verifica se a entrada é o FIM
            //Inicialmente apenas compara se a entrada tem o tamanho certo para ser FIM depois verificaa string
            boolean fim = false;
            if (entrada.length() == 3) {
                if ((entrada.charAt(0) == 'F') && (entrada.charAt(1) == 'I') && (entrada.charAt(2) == 'M')) {
                    fim = true;
                }
            }
            return fim;
        }
    public static int fazSoma(String entrada){
        // Cria um int para que ele some cada numero da string
        int soma = 0;
        for(int i = 0; i < entrada.length(); i++){
            //Converte o char da entrada para int para somar
            soma += entrada.charAt(i) - '0';
        }
        return soma;
    }

    public static void main(String[] args){
        // Troca a entrada padrão
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            // Imprime o resultado da função diretamente, economizando o espaço de declaração de variavel
            System.out.println(fazSoma(entrada));
            entrada = ler.nextLine();
        }
    }
}
