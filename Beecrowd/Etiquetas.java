import java.util.*;
public class Etiquetas {

    public static void main(String[] args){
        Scanner leitor = new Scanner(System.in);
        int qtLinguas = leitor.nextInt();
        leitor.nextLine();
        String[] linguas = new String[qtLinguas];
        String[] bNatal = new String[qtLinguas];
        for(int i = 0; i < qtLinguas; i++){
            linguas[i] = leitor.nextLine();
            bNatal[i] = leitor.nextLine();
        }
        int qtCriancas = leitor.nextInt();
        leitor.nextLine();
        String[] nomes = new String[qtCriancas];
        String[] msg = new String[qtCriancas];

        for(int i = 0 ; i < qtCriancas; i++){
            nomes[i] = leitor.nextLine();
            msg[i] = leitor.nextLine();
        }

        for(int i = 0; i < qtCriancas; i++){
            System.out.println(nomes[i]);
            for(int j = 0; j < qtLinguas; j++){
                if(linguas[j].equals(msg[i])){
                    System.out.println(bNatal[j]);
                    j = qtLinguas;
                }
            }
            System.out.println();
        }

        leitor.close();
    }
}
