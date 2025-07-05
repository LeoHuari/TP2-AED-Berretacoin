package aed;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private int dinero;

    public Usuario(int id, int dinero){
        this.id = id;
        this.dinero = dinero;
    }

    public int getId(){
        return this.id;
    }

    public int getDinero(){
        return this.dinero;
    }

    public void setDinero(int m){
        this.dinero = m;
    }

    @Override
    public int compareTo(Usuario o) {
        int res = this.dinero - o.dinero;

        if (res == 0) {
            res = -(this.id - o.id);
        }

        return res;
    }
    
}
