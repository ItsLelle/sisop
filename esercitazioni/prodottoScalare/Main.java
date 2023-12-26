package esercitazioni.prodottoScalare;

public class Main {
    public static void main(String[] args) {
        int a[] = {1,5,8,7};
        int b[] = {3,5,2,4};
        if( a.length != b.length){
            throw new RuntimeException("Vettori con dimensione diversa");
        }
        int n = a.length;
        int m = 4;

        ProdottoScalare p[] = new ProdottoScalare[m];
        int porzione = n/m;
        for(int i = 0; i<a.length; i++){
            int inizio = i*porzione;
            int fine = inizio + porzione -1;
            p[i] = new ProdottoScalare(a, b, inizio, fine);
            p[i].start();
        }

        int ps = 0;
        for(int i = 0; i<p.length;i++){
            ps += p[i].getProdottoScalare();
            System.out.println("ProdottoScalare: "+i+ "= "+p[i].getProdottoScalare());
        }
        System.out.println("Prodotto scalare = "+ps);
    }


    
}
