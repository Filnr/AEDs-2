import java.util.*;
public class Senha{

    public static boolean ehValida(String entrada){
        // Funçao que avalia se a entrada é uma senha valida ou não
        boolean ehValido;
        if(entrada.length() < 8){
            // se a senha for menor que 8 letras ja é descartada
            ehValido = false;
        }
        else{
            // Criaação de bools de controle para garantir que haja pelo menos 1 caracter de cada tipo
            boolean temMaiuscula = false;
            boolean temMinuscula = false;
            boolean temNumero = false;
            boolean temEspecial = false;
            for(int i = 0; i < entrada.length(); i++){
                // Loop que itera por cada letra verificando
                char letra = entrada.charAt(i);
                if(letra >= 'A' && letra <= 'Z'){
                    temMaiuscula = true;
                }
                else if(letra >= 'a' && letra <= 'z'){
                    temMinuscula = true;
                }
                else if(letra >= '0' && letra <= '9'){
                    temNumero = true;
                }
                else if(letra != (char) 127 && letra != (char) 32){
                    temEspecial = true;
                }
            }
            // Verifica se todos os booleans são positivos, somente se todos forem positivos a senha é valida
            ehValido = temEspecial && temMinuscula && temMaiuscula && temNumero;
        }
        return ehValido;
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
        // Troca a entrada padrão para ler corretamente
        Scanner ler = new Scanner(System.in, "UTF-8");
        String entrada = ler.nextLine();
        while(!ehFim(entrada)){
            // chama a função que que retornaa resposta no println
            if(ehValida(entrada)){
                System.out.println("SIM");
            }
            else{
                System.out.println("NAO");
            }
            entrada = ler.nextLine();
        }
    }
}