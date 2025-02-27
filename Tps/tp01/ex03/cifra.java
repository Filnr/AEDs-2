public class cifra {
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
    public static void main(String[] args){
        // Lendo a primeira linha para ja garantir que não é o FIM
        String entrada = MyIO.readLine();
        while(!ehFim(entrada)){
            // Criando uma string vazia para receber cada caracter criptografado
            String saida = "";
            for(int i = 0; i < entrada.length(); i++){
                saida += (char)(entrada.charAt(i) + 3);
            }
            // Repetindo o processo de leitura
            MyIO.println(saida);
            entrada = MyIO.readLine();
        }
    }
}
