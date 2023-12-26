package esercitazioni.barbiereAddormentato;

public class Cliente extends Thread{
    private Sala sala;
    private int ID;
    
    public Cliente(Sala s, int i){
        sala = s;
        ID = i;
    }

    @Override
    public void run() {
        try{
            System.out.format("Il cliente %d vuole tagliarsi i capelli%n", ID);
            boolean ret = sala.attendiTaglio();
            if(ret)
                System.out.format("Il cliente %d è riuscito a tagliare i capelli%n", ID);
            else
                System.out.format("Il cliente %d abbandona la sala%n", ID);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getID(){
        return ID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sala == null) ? 0 : sala.hashCode());
        result = prime * result + ID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        if (sala == null) {
            if (other.sala != null)
                return false;
        } else if (!sala.equals(other.sala))
            return false;
        if (ID != other.ID)
            return false;
        return true;
    }
}
