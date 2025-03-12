import java.util.*;

public class BooleanaRec {

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

    public static String tiraEspaco(String entrada) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != ' ') {
                saida += entrada.charAt(i);
            }
        }
        return saida;
    }

    public static void preencheVar(String entrada, int[] var) {
        int n = entrada.charAt(0) - '0';
        int posicao = 0;
        for (int i = 1; i <= n; i++) {
            var[posicao] = entrada.charAt(i) - '0';
            posicao++;
        }
    }

    public static String formaExp(String entrada, int n, int[] var) {
        String expressaoBool = "";
        for (int i = n + 1; i < entrada.length(); i++) {
            char letra = entrada.charAt(i);
            if (letra >= 'A' && letra <= 'Z') {
                expressaoBool += var[letra - 'A'];
            } else {
                expressaoBool += letra;
            }
        }
        return expressaoBool;
    }

    public static boolean ehNumero(char entrada) {
        return (entrada >= '0' && entrada <= '9');
    }

    public static String processaNOT(String exp, int inicio, int posicao) {
        String resultado = "";
        char letra;
        if (posicao < exp.length()) {
            if (posicao == inicio) {
                posicao += 4;
                if (exp.charAt(posicao)== '1') {
                    letra = '0';
                } else {
                    letra = '1';
                }
                resultado = letra + processaNOT(exp, inicio, posicao+2);
            } else {
                letra = exp.charAt(posicao);
                resultado += letra + processaNOT(exp, inicio, posicao+1);
            }
        }
        return resultado;
    }

    public static String chamaNot(String exp, int posicao) {
        return processaNOT(exp, posicao, posicao);
    }

    public static String processaAND(String exp, int inicio, int posicao){
        resultado
    }

    public static String interpretaExp(String exp, int posicao) {
        if (posicao < exp.charAt(posicao)) {
            if (exp.charAt(posicao) == 'a') {
                exp = 
            } else if (exp.charAt(posicao) == 'n') {
                exp = chamaNot(interpretaExp(exp, posicao), posicao);
            } else if (exp.charAt(posicao) == 'o') {
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String entrada = tiraEspaco(leitor.nextLine());
        while (!ehFim(entrada)) {
            int n = entrada.charAt(0) - '0';
            int[] var = new int[n];
            preencheVar(entrada, var);
            chamaNot(formaExp(entrada, n, var), 0);
            entrada = tiraEspaco(leitor.nextLine());
        }
    }
}