import java.util.*;
public class inverte {
    public static String inverteString(String entrada){
        // Cria uma String que recebera as letras ao contraria da entrada
        String invertida = "";
        // Tamanho recebe o tamanho da string - 1 para não acessar posições erradas
        int tamanho = entrada.length() - 1;
        for(int i = tamanho; i >= 0; i--){
            invertida += entrada.charAt(i);
        }
        return invertida;
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

    public static void main(String[] args){
        // Troca a entrada padrão para ler corretamente a entrada
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            // Chama a função que imprime o resultado ja imprimindo para evitar a criação de uma nova String
            System.out.println(inverteString(entrada));
            entrada = ler.nextLine();
        }
    }
}
