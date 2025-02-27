public class Pal {
    public static boolean ehFim(String entrada) {
        //Função que verifica se a entrada é o FIM
        //Inicialmente apenas compara se a entrada tem o tamanho certo para ser FIM depois verificaa string
        boolean fim = false;
        if (entrada.length() == 3) {
            if ((entrada.charAt(0) == 'F') && (entrada.charAt(1) == 'I') && (entrada.charAt(2) == 'M')) {
                fim = true;
            }
        }
        return fim;
    }

    public static void main(String args[]) {
        // Trocando o charset atual para ler corretamente as entradas
        MyIO.setCharset("UTF-8");
        String palavra = MyIO.readLine();
        while (!ehFim(palavra)) {
            //Crio uma variavel que recebe o tamanho - 1, para ja ser utilizada como índice
            int tamanho = palavra.length() - 1;
            boolean iguais = true;
            int i = 0;
            //Compara a cada letra das extremidades repetidamente, e se houver diferença, o while é interrompido
            while (iguais && tamanho > i) {
                if (palavra.charAt(i) != palavra.charAt(tamanho)) {
                    iguais = false;
                }
                i++;
                tamanho--;
            }
            //Imprime a resposta da linha e ja recebe um nova string
            if (iguais) {
                MyIO.println("SIM");
            } else {
                MyIO.println("NAO");
            }
            palavra = MyIO.readLine();
        }

    }
}
