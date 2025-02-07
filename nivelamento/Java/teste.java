import java.util.Scanner;
public class teste{
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        System.out.print("Digite um numero para teste: ");
        int num = ler.nextInt();
        int a = 1;
        for(int i = num; i > 0; i /= 2){
            System.out.println(a*2);
        }
        
    }
}
