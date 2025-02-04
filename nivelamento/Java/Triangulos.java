import java.util.*;

public class Triangulos {
    public static void main(String args[]) {
        try {
            Scanner ler = new Scanner(System.in);
            float[] lados = new float[3];
            for(int i = 0; i < 3; i++) {
                System.out.print("Digite o lado " + (i + 1) + ": ");
                lados[i] = ler.nextFloat();
            }
            if(lados[0] + lados[1] > lados[2] && lados[1] + lados[2] > lados[0] && lados[0] + lados[2] > lados[1]){
                if(lados[0] == lados[1] && lados[1] == lados[2]){
                    System.out.println("O triangulo digitado é Equilatero");
                }
                else if(lados[0] == lados[1] && lados[0] != lados[2] || lados[0] != lados[1] && lados[0] == lados[2] || lados[0] != lados[1] && lados[1] == lados[2]){
                    System.out.println("O triangulo digitado é Isosceles");
                }
                else{
                    System.out.println("O triangulo digitado é Escaleno");
                }
            }
            else{
                System.out.println("O lados digitados não formam um triangulo");
            }
            
            ler.close();
        } catch (Exception erro){
            System.out.println("Erro");
        }
    }
}
