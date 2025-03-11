import java.util.Scanner;

public class Booleano {
    
    public static boolean avaliarExpressao(String expressao) {
        String resultado = "";
        int inicio = 0, fim = 0;

        while (expressao.length() > 1) {
            // Localizar o próximo parêntese de abertura
            inicio = -1;
            for (int i = 0; i < expressao.length(); i++) {
                if (expressao.charAt(i) == '(') {
                    inicio = i;
                }
            }
            
            if (inicio == -1) {
                break; // Não há mais parênteses para processar
            }

            // Localizar o próximo parêntese de fechamento após o de abertura
            fim = -1;
            for (int i = inicio; i < expressao.length(); i++) {
                if (expressao.charAt(i) == ')') {
                    fim = i;
                    break;
                }
            }
            
            if (fim == -1) {
                break; // Não há parêntese de fechamento correspondente
            }

            // Verificar qual operação está sendo realizada
            resultado = "";
            
            // Operação NOT
            if (inicio >= 3 && expressao.charAt(inicio - 1) == 't' && 
                expressao.charAt(inicio - 2) == 'o' && expressao.charAt(inicio - 3) == 'n') {
                for (int i = 0; i < expressao.length(); i++) {
                    if (i == (inicio - 3)) {
                        // Negar o valor dentro do parêntese
                        if (inicio + 1 < expressao.length() && expressao.charAt(inicio + 1) == '0') {
                            resultado += '1';
                        } else {
                            resultado += '0';
                        }
                    } else if (i > (inicio - 3) && i <= fim) {
                        // Pular os caracteres da expressão processada
                    } else {
                        resultado += expressao.charAt(i);
                    }
                }
            }
            // Operação AND
            else if (inicio >= 3 && expressao.charAt(inicio - 1) == 'd' && 
                    expressao.charAt(inicio - 2) == 'n' && expressao.charAt(inicio - 3) == 'a') {
                for (int i = 0; i < expressao.length(); i++) {
                    if (i == (inicio - 3)) {
                        // Verificar se todos os operandos são 1
                        boolean todosUm = true;
                        
                        // Verifica se há espaço suficiente para processar
                        if (inicio + 1 < expressao.length() && inicio + 3 < expressao.length() && fim - 1 >= 0) {
                            // Verifica se todos os valores são 1
                            if (expressao.charAt(inicio + 1) != '1' || 
                                expressao.charAt(inicio + 3) != '1' || 
                                expressao.charAt(fim - 1) != '1') {
                                todosUm = false;
                            }
                        } else {
                            todosUm = false;
                        }
                        
                        resultado += todosUm ? '1' : '0';
                    } else if (i > (inicio - 3) && i <= fim) {
                        // Pular os caracteres da expressão processada
                    } else {
                        resultado += expressao.charAt(i);
                    }
                }
            }
            // Operação OR
            else if (inicio >= 2 && expressao.charAt(inicio - 1) == 'r' && expressao.charAt(inicio - 2) == 'o') {
                for (int i = 0; i < expressao.length(); i++) {
                    if (i == (inicio - 2)) {
                        // Verificar se pelo menos um operando é 1
                        boolean algumUm = false;
                        
                        // Verifica se há espaço suficiente para processar
                        if (inicio + 1 < expressao.length()) {
                            // Verifica se algum valor é 1
                            for (int j = inicio + 1; j < fim; j += 2) {
                                if (j < expressao.length() && expressao.charAt(j) == '1') {
                                    algumUm = true;
                                    break;
                                }
                            }
                        }
                        
                        resultado += algumUm ? '1' : '0';
                    } else if (i > (inicio - 2) && i <= fim) {
                        // Pular os caracteres da expressão processada
                    } else {
                        resultado += expressao.charAt(i);
                    }
                }
            }
            
            expressao = removerEspacos(resultado);
        }

        // Retorna true se o resultado final for 1
        return expressao.length() > 0 && expressao.charAt(0) == '1';
    }

    public static String removerEspacos(String texto) {
        String resultado = "";
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) != ' ') {
                resultado += texto.charAt(i);
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String entrada = removerEspacos(leitor.nextLine());
        
        while (entrada.length() > 0 && entrada.charAt(0) != '0') {
            String expressaoBooleana = " ";
            
            // Extrair os valores das variáveis
            char A = '0';
            char B = '0';
            char C = '*';
            
            if (entrada.length() > 1) {
                A = entrada.charAt(1);
            }
            
            if (entrada.length() > 2) {
                B = entrada.charAt(2);
            }
            
            boolean temC = entrada.length() > 0 && entrada.charAt(0) == '3';
            if (temC && entrada.length() > 3) {
                C = entrada.charAt(3);
            }
            
            // Definir o índice de início para processar a expressão
            int inicioExp = temC ? 4 : 3;
            
            // Substituir as variáveis pelos seus valores
            for (int i = inicioExp; i < entrada.length(); i++) {
                char atual = entrada.charAt(i);
                
                if (atual == 'A') {
                    expressaoBooleana += A;
                } else if (atual == 'B') {
                    expressaoBooleana += B;
                } else if (atual == 'C' && temC) {
                    expressaoBooleana += C;
                } else {
                    expressaoBooleana += atual;
                }
            }
            
            // Avaliar a expressão e imprimir o resultado
            if (avaliarExpressao(expressaoBooleana)) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
            
            // Ler a próxima entrada
            entrada = removerEspacos(leitor.nextLine());
        }
        
        leitor.close();
    }
}