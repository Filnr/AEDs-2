
import java.util.*;

public class Anagrama {

    public static void troca(char[] str, int menor, int i) {
        // Efetua a troca do maior caracter pelo menor
        char temp = str[menor];
        str[menor] = str[i];
        str[i] = temp;
    }

    public static void ordenaAnagrama(char[] str) {
        // Ordena a string para que se for anagrama, quando ordenado as duas strings devem ficar iguais
        // Utiliza o algoritimo de seleção pois as strings não são ordenadas e não possuem tamanho definido
        for (int i = 0; i < (str.length - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < str.length; j++) {
                if (str[menor] > str[j]) {
                    menor = j;
                }
            }
            if(menor != i){
                troca(str, menor, i);
            }
        }
    }

    public static boolean saoIguais(char[] str1, char[] str2) {
        // Compara todos os elementos de vetor pelo outro em busca de diferença
        // Como os vetores ja foram ordenados, se houver diferença não são anagramas
        boolean iguais = true;
        int i = 0;
        while (i < str1.length && iguais) {
            if (str1[i] != str2[i]) {
                iguais = false;
            } else {
                i++;
            }
        }
        return iguais;
    }

    public static void preencheVetor(char[] vetor, String str){
        for(int i = 0; i < str.length(); i++){
            char letra = str.charAt(i);
            // Verifica se é uma letra maiuscula, se for torna minuscula para não ter diferença na comparação
            if(letra >= 'A' && letra <= 'Z'){
                letra = (char) (letra + 32);
            }
            vetor[i] = letra;
        }
    }

    public static boolean ehAnagrama(String str1, String str2) {
        // Verifica se é anagrama, primeiro compara o tamanho para economizar processos
        boolean isAnagrama = true;
        if (str1.length() != str2.length()) {
            isAnagrama = false;
        } else {
            // Ordena os vetores para que sejam facilmente comparados
            char[] string1 = new char[str1.length()];
            char[] string2 = new char[str2.length()];
            preencheVetor(string1, str1);
            preencheVetor(string2, str2);
            ordenaAnagrama(string1);
            ordenaAnagrama(string2);
            isAnagrama = saoIguais(string1, string2);
        }
        return isAnagrama;
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
        Scanner ler = new Scanner(System.in, "UTF-8");
        // Le a entrada e quebra a entrada em 2 strings diferentes
        String entrada = ler.nextLine();
        while (!ehFim(entrada)) {
            String str1 = "", str2 = "";
            int i = 0;
            while (entrada.charAt(i) != ' ') {
                str1 += entrada.charAt(i);
                i++;
            }
            // pula 3 posições para que o " - " não seja lido
            i += 3;
            while (i < entrada.length()) {
                str2 += entrada.charAt(i);
                i++;
            }
            if (ehAnagrama(str1, str2)) {
                MyIO.println("SIM");
            } else {
                MyIO.println("NÃO");
            }
            entrada = ler.nextLine();
        }
        ler.close();
    }
}
