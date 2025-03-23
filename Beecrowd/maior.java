import java.util.*;
public class maior {
    public static void main(String[] args){
        Scanner leitor = new Scanner(System.in);
        int a = leitor.nextInt();
        int b = leitor.nextInt();
        int c = leitor.nextInt();
        int maiorAB = (a + b + Math.abs(a - b)) / 2;
        int maior = (maiorAB + c + Math.abs(maiorAB - c)) / 2;
        System.out.println(maior + " eh o maior\n");
        leitor.close();
    }
}
