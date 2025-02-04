import java.util.Scanner;
public class Leitura {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Pense e digite um numero: ");
        int numero = scan.nextInt();
        System.out.println("Você pensou no numero " + numero);
        scan.close();
    }
}
