public class Pal{
    public static void main(String args[]){
        MyIO.setCharset("UTF-8");
        String palavra = MyIO.readLine();
	boolean erro = false;
        while(!palavra.equals("FIM") && !palavra.equals("fim") && !erro){
	    if(palavra == null){
		    erro = true;
	    }
            int tamanho = palavra.length() - 1;
            boolean iguais = true;
            int i = 0;
            while(iguais && tamanho > i){
                if(palavra.charAt(i) != palavra.charAt(tamanho)){
                    iguais = false;
                }
                i++;
                tamanho--;
            }
            if(iguais){
                MyIO.println("SIM");
            }
            else{
                MyIO.println("NAO");
            }
            palavra = MyIO.readLine();
        }

    }
}
