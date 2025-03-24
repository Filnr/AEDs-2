import java.util.*;

public class OrdemTam {

    public static void ordena(String[] palavras, int[] tam,int indice){
        for(int i = 0; i < indice - 1; i++){
            for(int j = 0; j < (indice - i) - 1; j++){
                if(tam[j] < tam[j+1]){
                    String temp = palavras[j];
                    palavras[j] = palavras[j+1];
                    palavras[j+1] = temp;
                    int tempo = tam[j];
                    tam[j] = tam[j+1];
                    tam[j+1] = tempo; 
                }
            }
        }
    }

    public static void imprime(String[] palavras, int indice){
        for(int i = 0; i < indice; i++){
            if(i == indice - 1){
                System.out.printf("%s\n", palavras[i]);
            }
            else{
                System.out.printf("%s ", palavras[i]);
            }
        }
    }

    public static void interpreta(String entrada){
        int[] tam = new int[100];
        String[] palavras = new String[100];
        int indice = 0;
        palavras[indice] = "";
        for(int i = 0; i < entrada.length(); i++){
            char letra = entrada.charAt(i);
            if(letra == ' '){
                indice++;
                palavras[indice] = "";
            }
            else{
                tam[indice]++;
                palavras[indice] += letra;
            }
        }
        indice++;
        ordena(palavras, tam, indice);
        imprime(palavras, indice); 
    }

    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        int n = ler.nextInt();
        ler.nextLine();
        for(int i = 0; i < n; i++){
            String entrada = ler.nextLine();
            interpreta(entrada);
        }
        ler.close();
    }
}
