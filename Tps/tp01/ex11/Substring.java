import java.util.*;

public class Substring {
    
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
    
    public static int encontraSub(String entrada) {
        int qt = 0;
        int maior = 0;
        char[] letras = new char[entrada.length()];
        int inicio = 0; // Índice do início da substring sem repetição
        for (int i = 0; i < entrada.length(); i++) {
            char atual = entrada.charAt(i);
            boolean repetido = false;
            int j = inicio;
            // Verifica se o caractere já existe na substring atual
            while (j < qt) {
                if (letras[j] == atual) {
                    repetido = true;
                    // Atualiza o índice de início para o próximo da repetição
                    inicio = j + 1;
                }
                j++;
            }
            if (repetido) {
                // Atualiza o maior tamanho antes de reiniciar a contagem
                if (qt - inicio > maior) {
                    maior = qt - inicio;
                }
            }
            // Adiciona o caractere à lista de caracteres únicos da substring
            letras[qt] = atual;
            qt++;
            // Atualiza o maior tamanho encontrado
            if (qt - inicio > maior) {
                maior = qt - inicio;
            }
        }
        return maior;
    }

    public static void main(String[] args) {
        // Função responsavel por comandar o funcionamento do programa
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while (!ehFim(entrada)) {
            System.out.println(encontraSub(entrada));
            entrada = ler.nextLine();
        }
        ler.close();
    }
}
