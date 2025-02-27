import java.util.Random;
import java.util.Scanner;

public class AlteraAle {

    public static boolean ehFim(String entrada) {
        // Função que verifica se a entrada é o FIM
        // Inicialmente apenas compara se a entrada tem o tamanho certo para ser FIM
        // depois verificaa string
        boolean fim = false;
        if (entrada.length() == 3) {
            if ((entrada.charAt(0) == 'F') && (entrada.charAt(1) == 'I') && (entrada.charAt(2) == 'M')) {
                fim = true;
            }
        }
        return fim;
    }

    public static String alteraChar(String texto, char letraSort, char letraSoma) {
        // Inicializa uma String vazia para receber os char modificado
        String saida = "";
        for (int i = 0; i < texto.length(); i++) {
            // Condicional que avalia se deve ou não modificar o char lido
            if (texto.charAt(i) == letraSort) {
                saida += letraSoma;
            } else {
                saida += texto.charAt(i);
            }
        }
        return saida;
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        Random gerador = new Random();
        // Gerador com seed 4 para sempre gerar os mesmos sorteios como orientado
        gerador.setSeed(4);
        String entrada = ler.nextLine();
        while (!(ehFim(entrada))) {
            // Cria 2 char diferentes, para um ficar responsavel de comparar o char sorteado
            // O outro char fica responsavel por substituir o char sorteado
            char letraSort = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            char letraSoma = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            String resultado = alteraChar(entrada, letraSort, letraSoma);
            System.out.println(resultado);
            entrada = ler.nextLine();
        }
        ler.close();
    }

}