import java.util.*;
public class Operando {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        float numero1 = Float.parseFloat(ler.nextLine());
        float numero2 = Float.parseFloat(ler.nextLine());
        double raiz, log;

        if(numero1 < numero2){
            raiz = Math.cbrt(numero1);
            log = Math.log(numero1) / Math.log(numero2);
        }
        else{
            raiz = Math.sqrt(numero2);
            log = Math.log(numero2) / Math.log(numero1);
        }
        System.out.println("Raiz: " + raiz);
        System.out.println("Log: " + log);
    }
}
