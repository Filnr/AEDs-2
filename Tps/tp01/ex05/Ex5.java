public class Ex5 {

    // Método para avaliar a expressão booleana
    public static boolean avaliarExpressao(String expressao) {
        String resultado = "";
        int inicio = 0, fim = 0;
        boolean respostaFinal;

        while (expressao.length() > 1) {

            // Localiza o início e o fim da expressão booleana
            for (int i = 0; i < expressao.length(); i++) {
                if (expressao.charAt(i) == '(') {
                    inicio = i;
                } else if (expressao.charAt(i) == ')') {
                    fim = i;
                    i = expressao.length();
                }
            }

            // Verifica se a operação é NOT
            if (expressao.charAt(inicio - 1) == 't') {
                resultado = "";

                for (int i = 0; i < expressao.length(); i++) {
                    if (i == (inicio - 3)) {
                        if (expressao.charAt(inicio + 1) == '0') {
                            resultado += '1';
                        } else {
                            resultado += '0';
                        }
                    } else if (i > (inicio - 3) && i <= fim) {
                        resultado += "";
                    } else {
                        resultado += expressao.charAt(i);
                    }
                }

            }
            // Verifica se a operação é AND
            else if (expressao.charAt(inicio - 1) == 'd') {
                resultado = "";

                for (int i = 0; i < expressao.length(); i++) {

                    if (i == (inicio - 3)) {

                        if (expressao.charAt(inicio + 1) == '1'
                                && expressao.charAt(inicio + 3) == '1'
                                && expressao.charAt(fim - 1) == '1') {
                            resultado += '1';
                        } else {
                            resultado += '0';
                        }
                    } else if (i > (inicio - 3) && i <= fim) {
                        resultado += "";
                    } else {
                        resultado += expressao.charAt(i);
                    }

                }
            }
            // Verifica se a operação é OR
            else if (expressao.charAt(inicio - 1) == 'r') {
                resultado = "";

                for (int i = 0; i < expressao.length(); i++) {

                    if (i == (inicio - 2)) {
                        if (expressao.charAt(inicio + 1) == '1'
                                || expressao.charAt(inicio + 3) == '1'
                                || expressao.charAt(fim - 1) == '1' || expressao.charAt(fim - 3) == '1') {
                            resultado += '1';
                        } else {
                            resultado += '0';
                        }
                    } else if (i > (inicio - 2) && i <= fim) {
                        resultado += "";
                    } else {
                        resultado += expressao.charAt(i);
                    }
                }
            }

            expressao = resultado.replaceAll(" ", "");
        }

        respostaFinal = expressao.charAt(0) == '1';

        return respostaFinal;
    }

    // Método principal para processar as entradas
    public static void main(String[] args) {
        String entrada = MyIO.readLine().replaceAll(" ", "");

        while (entrada.charAt(0) != '0') {
            String expressaoBooleana = " ";

            char A = entrada.charAt(1);
            char B = entrada.charAt(2);
            char C = (entrada.charAt(0) == '3') ? entrada.charAt(3) : '*';

            // Substituindo as variáveis A, B, C pelos valores correspondentes na expressão
            if (entrada.charAt(0) == '3') {
                for (int i = 4; i < entrada.length(); i++) {
                    if (entrada.charAt(i) == 'A') {
                        expressaoBooleana += A;
                    } else if (entrada.charAt(i) == 'B') {
                        expressaoBooleana += B;
                    } else if (entrada.charAt(i) == 'C') {
                        expressaoBooleana += C;
                    } else {
                        expressaoBooleana += entrada.charAt(i);
                    }
                }
            } else {
                for (int i = 3; i < entrada.length(); i++) {
                    if (entrada.charAt(i) == 'A') {
                        expressaoBooleana += A;
                    } else if (entrada.charAt(i) == 'B') {
                        expressaoBooleana += B;
                    } else {
                        expressaoBooleana += entrada.charAt(i);
                    }
                }
            }

            if (avaliarExpressao(expressaoBooleana)) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }

            entrada = MyIO.readLine().replaceAll(" ", "");
        }

    }
}
