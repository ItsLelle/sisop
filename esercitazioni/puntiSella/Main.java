package esercitazioni.puntiSella;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    static class Point{
        int x;
        int y;
        int valore;

        public Point(int x, int y, int valore){
            this.x = x;
            this.y = y;
            this.valore = valore;
        }
    }

    public static void main(String[] args) {
        int[][] matrice = {{2,7,2,5,2},{2,5,9,7,2},{1,4,1,4,1},{2,5,2,9,2}};
        int numColonne = matrice[0].length;
        int numRighe = matrice.length;
        System.out.format("matrice composta da %d righe e %d colonne %n", matrice.length, matrice[0].length);

        for(int i = 0; i<numRighe; i++){
            for(int j = 0; j < numColonne; j++){
                System.out.println(matrice[i][j]+"");
            }
            System.out.println();
        }
        Massimo[] maxThread = new Massimo[numRighe];
        for(int i = 0; i<numRighe; i++){
            maxThread[i] = new Massimo(matrice, i);
            maxThread[i].start();
        }

        Minimo[]minThread = new Minimo[numColonne];
        for(int j = 0; j<numColonne; j++){
            minThread[j] = new Minimo(matrice, j);
            minThread[j].start();
        }

        LinkedList<Point> p = new LinkedList<>();

        for(int i = 0; i<numRighe; i++){
            ArrayList<Integer> jMax = maxThread[i].getPosMax();
            for(Integer max:jMax){
                ArrayList<Integer> iMin = minThread[max].getPosMin();
                if(iMin.contains(i)){
                    p.add(new Point(i, max, matrice[i][max]));
                }
            }
        }

        if(p.isEmpty()){
            System.out.println("nessun dato di sella");

        }else{
            for(Point po:p){
                System.out.println(String.format("Punto di sella in posizione(%d,%d) con valore %d", po.x, po.y,po.valore));
            }
        }
    }

    

    
}
