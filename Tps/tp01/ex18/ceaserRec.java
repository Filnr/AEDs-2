import java.util.*;

public class ceaserRec {
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

    public static boolean ehLetra(char letra){
        return (letra >= 'A' && letra <= 'Z') || (letra >= 'a' && letra <= 'z');
    }

    public static boolean ehPontuacao(char letra){
        return (letra <= '/' && letra >= ' ') || (letra == '-') || (letra == ']') || (letra >= ':' && letra <= '@') || (letra >= ']' && letra <= '_'); 
    }

    public static boolean ehNumero(char letra){
        return (letra >= '0' && letra <= '9');
    }

    public static String codificador(String entrada, int posicao) {
        String codigo = "";
        if (posicao < entrada.length()) {
            // Codigo recebe a letra atual mais os caracteres da recursão para formar o
            // codigo
            char letra = entrada.charAt(posicao);
            // verifica se a letra é numero, pontuação ou numero para codificar, se não for, não é codificado
            if(ehLetra(letra) || ehPontuacao(letra) || ehNumero(letra)){
                codigo += (char)(letra + 3);
            }
            else{
                codigo += letra;
            }
            codigo = codigo + codificador(entrada, posicao + 1);
        }
        return codigo;
    }

    public static String chamaCodificador(String entrada) {
        // Função que serve para chamar o codificador e dificultar a a chamada errada do codificador
        return codificador(entrada, 0);
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        // Compara a entrada com FIM para garantir que não ha erro
        while (!ehFim(entrada)) {
            System.out.println(chamaCodificador(entrada));
            entrada = ler.nextLine();
        }
    }
}