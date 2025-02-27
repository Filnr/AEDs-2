package ex06;

import java.util.*;

public class Is {

    public static String tornaMinusculas(String entrada) {
        String resultado = "";
        for (int i = 0; i < entrada.length(); i++) {
            char letra = entrada.charAt(i);
            // Verifica se é um caracter maiusculo para torna-lo minusculo
            if (letra >= 'A' && letra <= 'Z') {
                // Diferença entre maiúscula e minúscula no ASCII
                letra = (char) (letra + ('a' - 'A'));
            }
            resultado += letra;
        }
        return resultado;
    }

    public static boolean soVogais(String entrada) {
        // Modifica a string para que o algoritimo não seja sensivel a case;
        String entradaMinuscula = tornaMinusculas(entrada);
        // Vetor de vogais para facilitar a analise da string
        char[] vogais = new char[] { 'a', 'e', 'i', 'o', 'u' };
        Boolean strVogal = true;
        boolean ehVogal;
        int i = 0;
        while (i < entradaMinuscula.length() && strVogal) {
            ehVogal = false;
            int j = 0;
            while (j < vogais.length && !ehVogal) {
                // Se a letra for vogal encerra a busca
                // Diminuindo o custo do algoritmo caso encontre a vogal cedo
                if (vogais[j] == entradaMinuscula.charAt(i)) {
                    ehVogal = true;
                }
                else{
                    j++;
                }
            }
            // Caso a letra não seja vogal, ja encerra o loop  e retorna falso
            if (!ehVogal) {
                strVogal = false;
            }
            else{
                i++;
            }
        }
        return strVogal;
    }

    public static boolean soConsoantes(String entrada) {
        String entradaMinuscula = tornaMinusculas(entrada);
        // Array de consoantes para facilitar a busca
        char[] consoantes = new char[] { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't',
                'v', 'w', 'x', 'y', 'z' };
        Boolean strC = true;
        boolean ehC;
        int i = 0;
        while (i < entradaMinuscula.length() && strC) {
            ehC = false;
            int j = 0;
            while (j < consoantes.length && !ehC) {
                if (consoantes[j] == entradaMinuscula.charAt(i)) {
                    // Se a letra for consoante encerra a busca
                    // Diminuindo o custo do algoritmo caso encontre a consoante cedo
                    ehC = true;
                }
                else{
                    j++;
                }
            }
            if (!ehC) {
                // se no while de dentro não retorna true, encerra os whiles e retorna falso
                strC = false;
            }
            else{
                i++;
            }
        }
        return strC;
    }

    public static boolean ehDigito(char letra) {
        // Verifica se o caractere está no intervalo de '0' a '9', ou seja, se é digito
        return letra >= '0' && letra <= '9';
    }

    public static boolean isInt(String entrada) {
        boolean ehNumero = true;
        int i = 0;
        // Loop que verifica todos char, ou até encontrar um char que não seja numero
        while (i < entrada.length() && ehNumero) {
            if (!ehDigito(entrada.charAt(i))) {
                ehNumero = false;
            }
            else{
                i++;
            }
        }
        return ehNumero;
    }

    public static boolean isReal(String entrada) {
        boolean ehReal = true;
        Boolean primeiroDot = false;
        int i = 0;
        while (i < entrada.length() && ehReal) {
            // Verifica se o char atul é numero ou pontuação
            if (!ehDigito(entrada.charAt(i)) && (entrada.charAt(i) != '.' || entrada.charAt(i) != ',')) {
                ehReal = false;
            }
            if (entrada.charAt(i) != '.' || entrada.charAt(i) != ',') {
                if (!primeiroDot) {
                    primeiroDot = true;
                } else { // Se já tem um ponto, não pode ter outro
                    ehReal = false;
                }
            }
            i++;
        }
        return ehReal;
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
            if (soVogais(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (soConsoantes(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (isInt(entrada)) {
                System.out.print("SIM ");
            } else {
                System.out.print("NAO ");
            }
            if (isReal(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            entrada = ler.nextLine();
        }
    }
}
