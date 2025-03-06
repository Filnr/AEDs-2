import java.util.*;
public class ceaserRec{
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

    public static String codificador(String entrada, int posicao){
        // Incializa uma string vazia que ira receber as letras codificadas
        String codigo = "";
        // condificonal que garante que posições fora da string não serão alcançadas
        if(posicao < entrada.length()){
            // Codigo recebe a letra atual mais os caracteres da recursão para formar o codigo
            codigo += (char) (entrada.charAt(posicao) + 3) + codificador(entrada, posicao+1);
        }
        return codigo;
    }

    public static String chamaCodificador(String entrada){
        // Função que serve para chamar o codificador e dificultar a a chamada errada do codificador
        return codificador(entrada, 0);
    }

    public static void main(String[] args){
        // Lendo a primeira linha para ja garantir que não é o FIM
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            // Criando uma string vazia para receber cada caracter criptografado
            System.out.println(chamaCodificador(entrada));
            entrada = ler.nextLine();
        }
    }
}