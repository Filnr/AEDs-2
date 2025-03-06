import java.util.Scanner;

public class Is {

    public static String tornaMinusculas(String entrada, int posicao) {
        String resultado = "";
        if(posicao < entrada.length()){
            char letra = entrada.charAt(posicao);
            // Verifica se é um caracter maiusculo para torna-lo minusculo
            if (letra >= 'A' && letra <= 'Z') {
                // Diferença entre maiúscula e minúscula no ASCII
                letra = (char) (letra + ('a' - 'A'));
            }
            resultado += letra + tornaMinusculas(entrada, posicao+1);
        }
        return resultado;
    }

    public static String chamaTornaMin(String entrada){
        return tornaMinusculas(entrada, 0);
    }

    public static boolean buscaVogal(String entrada, char[] vogais, int posicao,int i){
        boolean ehVogal = false;
        if(posicao < vogais.length) {
                // Se a letra for vogal encerra a busca
                // Diminuindo o custo do algoritmo caso encontre a vogal cedo
                if (vogais[posicao] == entrada.charAt(i)) {
                    ehVogal = true;
                }
                else{
                    ehVogal = buscaVogal(entrada, vogais, posicao+1, i);
                }
        }
        return ehVogal;
    }

    public static boolean chamaVogal(String entrada, char[] vogais, int i){
        return buscaVogal(entrada, vogais, 0, i);
    }

    public static boolean soVogais(String entrada, int posicao) {
        // Vetor de vogais para facilitar a analise da string
        char[] vogais = new char[] { 'a', 'e', 'i', 'o', 'u' };
        boolean strVogal = true;
        boolean ehVogal;
        if(posicao < entrada.length() && strVogal) {
            ehVogal = chamaVogal(entrada, vogais, posicao);
            // Caso a letra não seja vogal, ja encerra o loop  e retorna falso
            if (!ehVogal) {
                strVogal = false;
            }
            else{
                strVogal = soVogais(entrada, posicao+1);
            }
        }
        return strVogal;
    }

    public static boolean chamaVogais(String entrada){
        // Modifica a string para que o algoritimo não seja sensivel a case;
        String entradaMinuscula = chamaTornaMin(entrada);
        return soVogais(entradaMinuscula, 0);
    }

    public static boolean buscaConsoantes(String entrada, char[] consoantes, int i, int posicao){
        boolean ehC = false;
            if (posicao < consoantes.length) {
                if (consoantes[posicao] == entrada.charAt(i)) {
                    // Se a letra for consoante encerra a busca
                    // Diminuindo o custo do algoritmo caso encontre a consoante cedo
                    ehC = true;
                }
                else{
                    ehC = buscaConsoantes(entrada, consoantes, i, posicao+1);
                }
            }
        return ehC;
    }

    public static boolean chamaBuscaCon(String entrada, char[] consoantes, int i){
        return buscaConsoantes(entrada, consoantes, i, 0);
    }

    public static boolean soConsoantes(String entrada, int posicao) {
        // Array de consoantes para facilitar a busca
        char[] consoantes = new char[] { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't',
                'v', 'w', 'x', 'y', 'z' };
        Boolean strC = true;
        boolean ehC;
        if (posicao < entrada.length() && strC) {
            ehC = chamaBuscaCon(entrada, consoantes, posicao);
            if (!ehC) {
                // se a função de busca retorna falso, encerra as comparações
                strC = false;
            }
            else{
                strC = soConsoantes(entrada, posicao+1);
            }
        }
        return strC;
    }

    public static boolean chamaConsoa(String entrada){
        // Modifica a string para que o algoritimo não seja sensivel a case;
        String entradaMinuscula = chamaTornaMin(entrada);
        return soConsoantes(entradaMinuscula, 0);
    }

    public static boolean ehDigito(char letra) {
        // Verifica se o caractere está no intervalo de '0' a '9', ou seja, se é digito
        return letra >= '0' && letra <= '9';
    }

    public static boolean isInt(String entrada, int posicao) {
        boolean ehNumero = true;
        // Loop que verifica todos char, ou até encontrar um char que não seja numero
        if (posicao < entrada.length()) {
            if (!ehDigito(entrada.charAt(posicao))) {
                ehNumero = false;
            }
            else{
                ehNumero = isInt(entrada, posicao+1);
            }
        }
        return ehNumero;
    }

    public static boolean chamaIsInt(String entrada){
        return isInt(entrada, 0);
    }

    public static boolean isReal(String entrada, int posicao, boolean primeiroDot) {
        boolean ehReal = true;
        if (posicao < entrada.length()) {
            // Verifica se o char atul é numero ou pontuação
            if (!ehDigito(entrada.charAt(posicao)) && (entrada.charAt(posicao) != '.' || entrada.charAt(posicao) != ',')) {
                ehReal = false;
            }
            if (entrada.charAt(posicao) != '.' || entrada.charAt(posicao) != ',') {
                if (!primeiroDot) {
                    primeiroDot = true;
                } else { // Se já tem um ponto, não pode ter outro
                    ehReal = false;
                }
            }
            else{
                ehReal = isReal(entrada, posicao+1, primeiroDot);
            }
        }
        return ehReal;
    }

    public static boolean chamaReal(String entrada){
        Boolean primeiroDot = false;
        return isReal(entrada, 0, primeiroDot);
    }

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

    public static void main(String[] args) {
        // troca a leitura para permitir a leitura de todos os caracteres
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        // Condicionais com todas as possiveis respostas
        while (!ehFim(entrada)) {
            if (chamaVogais(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (chamaConsoa(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (chamaIsInt(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (chamaReal(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            entrada = ler.nextLine();
        }
    }
}
