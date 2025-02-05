public class Log10 {
    public static void main(String args[]){
        double log = 1;
        for(int i = 0; i < 10; i++)
        {
            System.out.println("Log de " + i + ": " + Math.log10(log));
            log++;
        }
    }
}
