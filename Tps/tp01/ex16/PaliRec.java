import java.util.*;
public class PaliRec{
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

    public static boolean chamaVerifica(String entrada, int tamanho){
        // Função que chama a outra função para evitar erros
        return verificaPali(entrada, 0, tamanho);
    }

    public static boolean verificaPali(String entrada, int inicio, int fim){
        // Incia a variavel considerando que é verdadeira
        boolean ehPalindromo = true;
        if(fim > inicio){
            // Caso houver diferença em alguma posição, troca o bool para falso e encerra a função
            if(entrada.charAt(fim) != entrada.charAt(inicio)){
                ehPalindromo = false;
            }
            else{
                // A função so se repete quando as letras forem iguais
                verificaPali(entrada, inicio+1, fim-1);
            }
        }
        return ehPalindromo;
    }

    public static void main(String args[]) {
        // Trocando o charset atual para ler corretamente as entradas
        Scanner ler = new Scanner(System.in, "UTF-8");
        String palavra = ler.nextLine();
        while (!ehFim(palavra)) {
            //Crio uma variavel que recebe o tamanho - 1, para ja ser utilizada como índice
            int tamanho = palavra.length() - 1;
            //Imprime a resposta da linha e ja recebe um nova string
            if (chamaVerifica(palavra, tamanho)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            palavra = ler.nextLine();
        }
    }
}