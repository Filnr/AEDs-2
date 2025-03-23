import java.util.*;
public class Cedulas {

    public static int nota100(int valor){
        int notas = 0;
        while(valor >= 100){
            notas++;
            valor -= 100;
        }
        System.out.printf("%d nota(s) de R$ 100,00\n", notas);
        return notas;
    }

    public static int nota50(int valor){
        int notas = 0;
        while(valor >= 50){
            notas++;
            valor -= 50;
        }
        System.out.printf("%d nota(s) de R$ 50,00\n", notas);
        return notas;
    }

    public static int nota20(int valor){
        int notas = 0;
        while(valor >= 20){
            notas++;
            valor -= 20;
        }
        System.out.printf("%d nota(s) de R$ 20,00\n", notas);
        return notas;
    }

    public static int nota10(int valor){
        int notas = 0;
        while(valor >= 10){
            notas++;
            valor -= 10;
        }
        System.out.printf("%d nota(s) de R$ 10,00\n", notas);
        return notas;
    }

    public static int nota5(int valor){
        int notas = 0;
        while(valor >= 5){
            notas++;
            valor -= 5;
        }
        System.out.printf("%d nota(s) de R$ 5,00\n", notas);
        return notas;
    }

    public static int nota2(int valor){
        int notas = 0;
        while(valor >= 2){
            notas++;
            valor -= 2;
        }
        System.out.printf("%d nota(s) de R$ 2,00\n", notas);
        return notas;
    }

    public static int nota1(int valor){
        int notas = 0;
        while(valor >= 1){
            notas++;
            valor -= 1;
        }
        System.out.printf("%d nota(s) de R$ 1,00\n", notas);
        return notas;
    }

    public static void main(String[] args){
        Scanner leitor = new Scanner(System.in);
        int valor = leitor.nextInt();
        int n100 = nota100(valor);
        valor -= n100 * 100;
        int n50 = nota50(valor);
        valor -= n50 * 50;
        int n20 = nota20(valor);
        valor -= n20 * 20;
        int n10 = nota10(valor);
        valor -= n10 * 10;
        int n5 = nota5(valor);
        valor -= n5 * 5;
        int n2 = nota2(valor);
        valor -= n2 * 2;
        nota1(valor);
        leitor.close();
    }
}
