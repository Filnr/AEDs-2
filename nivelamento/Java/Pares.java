public class Pares {
    public static void main(String args[]){
        int i = 0;
        int num = 0;
        while(i < 10){
            System.out.println((i+1) + "º numero par: " + (num+=2));
            i++;
        }
    }
}
