import java.util.*;
public class Duendes {

    public static void organiza(String[] duendes, int[] idades, int n){
        for(int i = 0; i < n - 1; i++){
            int maior = i;
            for(int j = (i+1); j < n; j++){
                if(idades[j] > idades[maior]){
                    maior = j;
                }
            }
            int temp = idades[i];
            idades[i] = idades[maior];
            idades[maior] = temp;

            String tempo = duendes[i];
            duendes[i] = duendes[maior];
            duendes[maior] = tempo;
        }
        verificaOrdem(duendes, idades, n);
    }

    public static void verificaOrdem(String[] duendes, int[] idades, int n){
        for(int i = 0; i < n - 1; i++){
            if(duendes[i].compareTo(duendes[i+1]) > 0){ // Se o nome estiver fora de ordem
                // Troca os duendes de posição
                String tempNome = duendes[i];
                duendes[i] = duendes[i+1];
                duendes[i+1] = tempNome;
            }
        }
    }
    
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        int n = ler.nextInt();
        ler.nextLine();
        String[] duendes = new String[n];
        int[] idades = new int[n];
        for(int i = 0; i < n; i++){
            String entrada = ler.nextLine();
            String[] partes = entrada.split(" ");
            duendes[i] = partes[0];
            idades[i] = Integer.parseInt(partes[1]);
        }
        organiza(duendes, idades, n);
        int qtTimes = n / 3;
        for(int i = 0; i < qtTimes; i++){
            System.out.println("Time " + (i+1));
            System.out.printf("%s %d\n", duendes[i], idades[i]);
            System.out.printf("%s %d\n", duendes[i+qtTimes], idades[i+qtTimes]);
            System.out.printf("%s %d\n\n", duendes[i + (2*qtTimes)], idades[i + (2*qtTimes)]);
        }
        ler.close();
    }
}
