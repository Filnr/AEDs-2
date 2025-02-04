import java.util.*;

class avaliaMedia {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        try {
            float nota;
            String nome = scan.nextLine();
            nota = scan.nextFloat();
            while (nota != -1) {
                if (nota >= 60) {
                    System.out.println("Aluno " + nome + " aprovado");
                } else {
                    System.out.println("Aluno " + nome + " reprovado");
                }
                nota = scan.nextFloat();
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida");
        }
        scan.close();
    }
}
