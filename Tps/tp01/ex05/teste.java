import java.util.Scanner;

public class teste {

    public static boolean avaliarExpressao(String expressao) {
        while (expressao.length() > 1) {
            int inicio = 0, fim = 0;
            for (int i = 0; i < expressao.length(); i++) {
                if (expressao.charAt(i) == '(') {
                    inicio = i;
                } else if (expressao.charAt(i) == ')') {
                    fim = i;
                    break;
                }
            }

            String operador = expressao.substring(inicio - 2, inicio).trim();
            char resultado;
            if (operador.equals("ot")) {
                resultado = (expressao.charAt(inicio + 1) == '0') ? '1' : '0';
            } else if (operador.equals("nd")) {
                resultado = (expressao.contains("2")) ? '0' : '1';
            } else if (operador.equals("or")) {
                resultado = (expressao.contains("1")) ? '1' : '0';
            } else {
                continue;
            }

            expressao = expressao.substring(0, inicio - 2) + resultado + expressao.substring(fim + 1);
        }
        return expressao.charAt(0) == '1';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine().replaceAll(" ", "");

        while (entrada.charAt(0) != '0') {
            char A = entrada.charAt(1);
            char B = entrada.charAt(2);
            char C = (entrada.charAt(0) == '3') ? entrada.charAt(3) : '*';

            StringBuilder expressaoBooleana = new StringBuilder();
            for (int i = (entrada.charAt(0) == '3') ? 4 : 3; i < entrada.length(); i++) {
                char atual = entrada.charAt(i);
                expressaoBooleana.append((atual == 'A') ? A : (atual == 'B') ? B : (atual == 'C') ? C : atual);
            }

            System.out.println(avaliarExpressao(expressaoBooleana.toString()) ? "1" : "0");
            entrada = scanner.nextLine().replaceAll(" ", "");
        }
        scanner.close();
    }
}
