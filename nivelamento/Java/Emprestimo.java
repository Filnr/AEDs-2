import java.util.*;
public class Emprestimo {
    public static void main(String args[]){
        Scanner ler = new Scanner(System.in);
        System.out.println("Bem vindo ao banco do seu Zé");
        System.out.print("Digite o seu salário: ");
        float salario = ler.nextFloat();
        System.out.print("Digite o valor da prestação: ");
        float prestacao = ler.nextFloat();
        if(salario * 0.4 < prestacao){
            System.out.println("Emprestimo Negado");
        }
        else{
            System.out.println("Emprestimo Autorizado");
        }
    }
}
