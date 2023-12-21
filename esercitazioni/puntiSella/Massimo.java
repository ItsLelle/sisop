package esercitazioni.puntiSella;

import java.util.ArrayList;

public class Massimo  extends Thread{

    private int[][] matrice;
    private int riga;
    private int numColonne;
    private int max;
    private ArrayList<Integer>posMax = new ArrayList<>();


    public Massimo(int[][] matrice, int riga){
        this.matrice = matrice;
        this.riga = riga;
        numColonne = matrice[0].length;
    }

    @Override
    public void run() {
        max = matrice[riga][0];
        for(int j = 1; j < numColonne; j++){
            if(matrice[riga][j] > max){
                max = matrice[riga][j];
            }
        }
        for(int j = 0; j < numColonne; j++){
            if(matrice[riga][j] == max){
                posMax.add(j);
            }
        }
    }

    public ArrayList<Integer>getPosMax(){
        try{
            join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return posMax;
    }
    
}
