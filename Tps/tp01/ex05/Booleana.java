import java.util.Scanner;

public class Booleana{
    
    public static boolean interpretaExp(String expBool) {
        // Função responsavel por interpretar a expressão booleana e retornar o valor booleano
        String resultado = "";
        int inicio = 0, fim = 0;
        while (expBool.length() > 1) {
            inicio = -1;
            for (int i = 0; i < expBool.length(); i++) {
                if (expBool.charAt(i) == '(') {
                    inicio = i;
                }
            }
            if (inicio == -1) {
                // Caso não tenha mais parenteses, encerra a função
                break;
            }
            fim = -1;
            for (int i = inicio; i < expBool.length(); i++) {
                if (expBool.charAt(i) == ')') {
                    fim = i;
                    i = expBool.length();
                }
            }
            if (fim == -1) {
                break;
            }
            // Verificar qual operação está sendo realizada
            resultado = "";
            // Processador responsavel por calcular o operador NOT
            if (inicio >= 3 && expBool.charAt(inicio - 1) == 't' && 
                expBool.charAt(inicio - 2) == 'o' && expBool.charAt(inicio - 3) == 'n') {
                for (int i = 0; i < expBool.length(); i++) {
                    if (i == (inicio - 3)) {
                        // Troca os valores das variaveis
                        if (inicio + 1 < expBool.length() && expBool.charAt(inicio + 1) == '0') {
                            resultado += '1';
                        } else {
                            resultado += '0';
                        }
                    } else if (i > (inicio - 3) && i <= fim) {
                        // Pular os caracteres da expressão processada
                    } else {
                        resultado += expBool.charAt(i);
                    }
                }
            }
            // Processador responsavel por calcular o operador AND
            else if (inicio >= 3 && expBool.charAt(inicio - 1) == 'd' && 
                    expBool.charAt(inicio - 2) == 'n' && expBool.charAt(inicio - 3) == 'a') {
                for (int i = 0; i < expBool.length(); i++) {
                    if (i == (inicio - 3)) {
                        boolean todosUm = true;
                        if (inicio + 1 < expBool.length() && inicio + 3 < expBool.length() && fim - 1 >= 0) {
                            if (expBool.charAt(inicio + 1) != '1' || 
                                expBool.charAt(inicio + 3) != '1' || 
                                expBool.charAt(fim - 1) != '1') {
                                todosUm = false;
                            }
                        } else {
                            todosUm = false;
                        }
                        // Caso as variaveis sejam todas 1, o operador AND retorna 1
                        resultado += todosUm ? '1' : '0';
                    } else if (i > (inicio - 3) && i <= fim) {
                    } else {
                        resultado += expBool.charAt(i);
                    }
                }
            }
            // Processador responsavel por calcular o operador OR
            else if (inicio >= 2 && expBool.charAt(inicio - 1) == 'r' && expBool.charAt(inicio - 2) == 'o') {
                for (int i = 0; i < expBool.length(); i++) {
                    if (i == (inicio - 2)) {
                        // Verificar se pelo menos um operando é 1 para retornar 1 como resultado
                        boolean algumUm = false;
                        if (inicio + 1 < expBool.length()) {
                            for (int j = inicio + 1; j < fim; j += 2) {
                                if (j < expBool.length() && expBool.charAt(j) == '1') {
                                    algumUm = true;
                                    j = fim;
                                }
                            }
                        }
                        resultado += algumUm ? '1' : '0';
                    } else if (i > (inicio - 2) && i <= fim) {
                        // Pular os caracteres da expressão processada
                    } else {
                        resultado += expBool.charAt(i);
                    }
                }
            }
            expBool = removerEspacos(resultado);
        }
        // Retorna true se o resultado final for 1
        return expBool.length() > 0 && expBool.charAt(0) == '1';
    }

    public static String removerEspacos(String texto) {
        // Remove os espaços para que não atrapalhe a leitura, e nem aumente o custo de operação
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
            // Define o indice de inicio a depender se possui 2 ou 3 variaveis
            int inicioExp;
            if(temC){
                inicioExp = 4;
            }
            else{
                inicioExp = 3;
            }
            // Substitui as letras das variaveis pelo valor 
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
            // Chama a função que interpreta a expressão booleana e imprime o resultado
            if (interpretaExp(expressaoBooleana)) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
            entrada = removerEspacos(leitor.nextLine());
        }
        leitor.close();
    }
}