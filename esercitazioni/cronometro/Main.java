package esercitazioni.cronometro;
import java.util.Scanner;

public class Main {
    public static void main1(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        Cronometro c = new Cronometro();

        System.out.println("premi invio per cominciare");
        sc.nextLine();
        c.start();
        System.out.println("premi invio per terminare");
        sc.nextLine();
        c.interrupt();
        System.out.println("programma terminato");
        
    }


   
    /*versione con demoni, ma è meglio usare la prima perchè i demoni non possono accedere a file e con questo approccio brusco 
    potremmo causare danni*/

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cronometro c = new Cronometro();
        c.setDaemon(true);
        System.out.println("Daemon: "+c.isDaemon());
        System.out.println("premi invio per cominciare");
        sc.nextLine();
        c.start();
        System.out.println("premi invio per terminare");
        sc.nextLine();
        System.out.println("programma terminato");
    }
}
