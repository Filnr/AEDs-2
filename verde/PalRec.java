public class PalRec {

    public static boolean palindromo(String palavra, int i, int tamanho){
        boolean ehPalindromo = true;
        if(tamanho > i){
            if(palavra.charAt(i) != palavra.charAt(tamanho)){
                ehPalindromo = false;
            }else{
                ehPalindromo = palindromo(palavra, i+1, tamanho-1);
            }
        }
        return ehPalindromo;
    }
    public static void main(String args[]){
        MyIO.setCharset("UTF-8");
        String palavra = MyIO.readLine();
        while(!palavra.equals("FIM")){
            int tamanho = palavra.length() - 1;
            if(palindromo(palavra, 0, tamanho)){
                MyIO.println("SIM");
            }
            else{
                MyIO.println("NAO");
            }
            palavra = MyIO.readLine();
        }
    }
}
