import java.util.Scanner;

public class Booleano {
    public static int analisaExp(String entrada) {
        boolean encontrouIni = false;
        int i = 0;
        while (i < entrada.length() && !encontrouIni) {
            if (entrada.charAt(i) == '(') {
                encontrouIni = true;
            } else {
                i++;
            }
        }
        return i;
    }
    
    public static int analisaFinal(String entrada, int inicio) {
        boolean encontrouFim = false;
        while (inicio < entrada.length() && !encontrouFim) {
            if (entrada.charAt(inicio) == ')') {
                encontrouFim = true;
            } else {
                inicio++;
            }
        }
        return inicio;
    }
    
    public static String processaNot(String entrada, int inicio, int fim) {
        String resultado = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (i == inicio - 3) {
                if (entrada.charAt(inicio + 1) == '0' && (inicio + 1) < entrada.length()) {
                    resultado += '1';
                } else {
                    resultado += '0';
                }
            } else if (i > inicio && i <= fim) {
                resultado += "";
            } else {
                resultado += entrada.charAt(i);
            }
        }
        return resultado;
    }
    
    public static String processaAND(String entrada, int inicio, int fim) {
        String resultado = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (i == (inicio - 3) && (inicio + 3) < entrada.length() && (fim - 1 >= 0)) {
                if (entrada.charAt(inicio + 1) == '1' && entrada.charAt(inicio + 3) == '1'
                        && entrada.charAt(fim - 1) == '1') {
                    resultado += '1';
                } else {
                    resultado += '0';
                }
            } else if (i > (inicio - 3) && i <= fim) {
                resultado += "";
            } else {
                resultado += entrada.charAt(i);
            }
        }
        return resultado;
    }
    
    public static String processaOR(String entrada, int inicio, int fim) {
        String resultado = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (i == (inicio - 2) && (inicio + 3) < entrada.length() && (fim - 3) >= 0) {
                if (entrada.charAt(inicio + 1) == '1'
                        || entrada.charAt(inicio + 3) == '1'
                        || entrada.charAt(fim - 1) == '1'
                        || entrada.charAt(fim - 3) == '1') {
                    resultado += '1';
                } else {
                    resultado += '0';
                }
            } else if (i > (inicio - 2) && i <= fim) {
                resultado += "";
            } else {
                resultado += entrada.charAt(i);
            }
        }
        return resultado;
    }
    
    public static boolean avaliarExpressao(String entrada) {
        String resultado = "";
        while (entrada.length() > 1) {
            int inicio = analisaExp(entrada);
            int fim = analisaFinal(entrada, inicio);
            if ((inicio - 2) >= 0) {
                if (entrada.charAt(inicio - 2) == 'o' && entrada.charAt(inicio - 1) == 't') {
                    resultado = processaNot(entrada, inicio, fim);
                } else if (entrada.charAt(inicio - 2) == 'n' && entrada.charAt(inicio - 1) == 'd') {
                    resultado = processaAND(entrada, inicio, fim);
                } else if (entrada.charAt(inicio - 2) == 'o' && entrada.charAt(inicio - 1) == 'r') {
                    resultado = processaOR(entrada, inicio, fim);
                }
            }
            entrada = formataString(resultado);
        }
        return entrada.charAt(0) == '1';
    }
    
    public static String formataString(String entrada) {
        String saida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.charAt(i) != ' ') {
                saida += entrada.charAt(i);
            }
        }
        return saida;
    }
    
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        String entrada = formataString(leitor.nextLine());
        
        while (entrada.charAt(0) != '0') {
            char A = entrada.charAt(1);
            char B = entrada.charAt(2);
            char C = (entrada.charAt(0) == '3') ? entrada.charAt(3) : '*';
            String exp = "";
            int inicio;
            boolean tem3 = false;
            
            if (entrada.charAt(0) == '3') {
                inicio = 4;
                tem3 = true;
            } else {
                inicio = 3;
            }
            
            for (int i = inicio; i < entrada.length(); i++) {
                char letra = entrada.charAt(i);
                if (letra == 'A') {
                    exp += A;
                } else if (letra == 'B') {
                    exp += B;
                } else if (letra == 'C' && tem3) {
                    exp += C;
                } else {
                    exp += letra;
                }
            }
            
            if (avaliarExpressao(exp)) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
            
            entrada = formataString(leitor.nextLine());
        }
        
        leitor.close();
    }
}