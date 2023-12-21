package esercitazioni.puntiSella;

import java.util.ArrayList;

public class Minimo  extends Thread{

    private int[][] matrice;
    private int colonna;
    private int numRighe;
    private int min;
    private ArrayList<Integer> posMin = new ArrayList<>();

    public Minimo(int[][]matrice, int colonna){
        this.matrice = matrice;
        this.colonna = colonna;
        numRighe = matrice.length;
    }

    @Override
    public void run() {
        min = matrice[0][colonna];
        for(int i = 1; i<numRighe; i++){
            if(matrice[i][colonna] < min){
                min = matrice[i][colonna];
            }
        }
        for(int i = 1; i<numRighe; i++){
            if(matrice[i][colonna] == min){
            posMin.add(i);
            }
        }
    }

    public ArrayList<Integer> getPosMin(){
        try{
            join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return posMin;
    }
    
}
