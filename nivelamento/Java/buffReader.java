import java.io.*;

public class buffReader{
    public static void main(String args[]){
        try{
            BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Pense e digite um numero: ");
            String algo = leitor.readLine();
            System.out.println("Você pensou no numero " + algo);
            leitor.close();
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
        
    }
}