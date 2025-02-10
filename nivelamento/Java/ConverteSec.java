import java.util.Scanner;
public class ConverteSec {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        int horas = ler.nextInt();
        char doisPontos = ler.next().charAt(0);
        int minutos = ler.nextInt();
        int segundos = (horas * 3600) + (minutos * 60);
        System.out.println(segundos);
    }
}