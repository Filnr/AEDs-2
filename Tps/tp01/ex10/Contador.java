import java.util.*;
public class Contador{

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

	public static int contaPalavras(String entrada){
		int qtPalavras = 1;
		for(int i = 0; i < entrada.length(); i++){
			if(entrada.charAt(i) == ' '){
				qtPalavras++;
			}
		}
		return qtPalavras;
	}

	public static void main(String[] args){
		Scanner ler = new Scanner(System.in, "UTF-8");
		String entrada = ler.nextLine();
		while(!ehFim(entrada)){
			System.out.println(contaPalavras(entrada));
			entrada = ler.nextLine();
		}
	}
}
