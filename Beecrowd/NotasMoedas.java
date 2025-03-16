import java.util.*;
public class NotasMoedas{
    public static void main(String args[]){
        Scanner leitor = new Scanner(System.in);
        double valor = leitor.nextDouble();
        int nota100 = 0, nota50 = 0, nota20 = 0, nota10 = 0, nota5 = 0, nota2 = 0;
        System.out.println("NOTAS:");
        while(valor >= 100){
            valor -= 100;
            nota100++;
        }
        System.out.printf("%d nota(s) de R$ 100.00\n", nota100);
        while(valor >= 50){
            valor -= 50;
            nota50++;
        }
        System.out.printf("%d nota(s) de R$ 50.00\n", nota50);
        while(valor >= 20){
            valor -= 20;
            nota20++;
        }
        System.out.printf("%d nota(s) de R$ 20.00\n", nota20);
        while(valor >= 10){
            valor -= 10;
            nota10++;
        }
        System.out.printf("%d nota(s) de R$ 10.00\n", nota10);
        while(valor >= 5){
            valor -= 5;
            nota5++;
        }
        System.out.printf("%d nota(s) de R$ 5.00\n", nota5);
        while(valor >= 2){
            valor -= 2;
            nota2++;
        }
        System.out.printf("%d nota(s) de R$ 2.00\n", nota2);
        System.out.println("MOEDAS:");
        int moeda1 = 0, moeda50 = 0, moeda25 = 0, moeda10 = 0, moeda5 = 0, moeda01 = 0;
        while(valor >= 1){
            valor -= 1;
            moeda1++;
        }
        System.out.printf("%d moeda(s) de R$ 1.00\n", moeda1);
        while(valor >= 0.5){
            valor -= 0.5;
            moeda50++;
        }
        System.out.printf("%d moeda(s) de R$ 0.50\n", moeda50);
        while(valor >= 0.25){
            valor -= 0.25;
            moeda25++;
        }
        System.out.printf("%d moeda(s) de R$ 0.25\n", moeda25);
        while(valor >= 0.10){
            valor -= 0.10;
            moeda10++;
        }
        System.out.printf("%d moeda(s) de R$ 0.10\n", moeda10);
        while(valor >= 0.05){
            valor -= 0.05;
            moeda5++;
        }
        System.out.printf("%d moeda(s) de R$ 0.05\n", moeda5);
        while(valor >= 0.01){
            valor -= 0.01;
            moeda01++;
        }
        System.out.printf("%d moeda(s) de R$ 0.01\n", moeda01);
        leitor.close();
    }
}