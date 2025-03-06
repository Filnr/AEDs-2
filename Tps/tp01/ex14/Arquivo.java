import java.util.*;
import java.io.*;

public class Arquivo {

    public static void escreveArq(int n) {
        try{
            Scanner leitor = new Scanner(System.in);
        RandomAccessFile arquivo = new RandomAccessFile("arq.txt", "rw");
        for (int i = 0; i < n; i++) {
            double num = leitor.nextDouble();
            arquivo.writeDouble(num);
        }
        leitor.close();
        arquivo.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void lerArq(int n){
        try{
            RandomAccessFile arquivo = new RandomAccessFile("arq.txt", "r");
            for(int i = n - 1; i >= 0; i--){
                arquivo.seek(i * 8);
                double numero = arquivo.readDouble();
                System.out.println(numero);
            }
        arquivo.close();
        }
        catch(IOException e){
             e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in, "UTF-8");
        int n = ler.nextInt();
        escreveArq(n);
        lerArq(n);
    }
}
