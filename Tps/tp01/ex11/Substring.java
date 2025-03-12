//package ex11;
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

    public static int encontraSub(String entrada){
        // Função que busca a quantidade de letras da substring
        int qt = 0;
        // Variavel que controla se um numero é repetido ou não
        int qtIguais = 0;
        // Array que salva as letras ja lidas que não são repetidas
        char[] letras = new char[entrada.length()];
        for(int i = 0; i < entrada.length(); i++){
            int j = 0;
            while(j < i){
                // se uma letra igual for encontrada aumenta o qtIguais
                if(letras[j] == entrada.charAt(i)){
                    qtIguais++;
                    j = i;
                }
                else{
                    j++;

                }
            }
            // se não houver letras iguais registrada, registra a letra, e aumenta em 1 o qt
            if(qtIguais == 0){
                letras[i] = entrada.charAt(i);
                qt++;
            }
            // se houver letras repetidas, encerra o loop
            else if(qtIguais > 0){
                
            }
        }
        return qt;
    }

    public static void main(String[] args){
        // Troca a entrada padrão para ler corretamente
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            // Ja chama a função que que retornaa resposta no println
            System.out.println(encontraSub(entrada));
            entrada = ler.nextLine();
        }
    }
}
