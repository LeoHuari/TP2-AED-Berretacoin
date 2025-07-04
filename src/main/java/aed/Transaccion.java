package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        int res = this.monto - otro.monto;
        boolean mismoMonto = res == 0;
        if (mismoMonto) {
            res = this.id - otro.id;
        }
        return res;
    }

    @Override
    public boolean equals(Object otro){
        boolean esTransaccion = this.getClass() == otro.getClass();
        boolean igualdad = false;
        if (esTransaccion) {
            Transaccion t = (Transaccion)otro;
            igualdad = this.id == t.id 
                    && this.id_comprador == t.id_comprador
                    && this.id_vendedor == t.id_vendedor
                    && this.monto == t.monto;
        }
        return igualdad;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }
    
    public int id_vendedor() {
        return id_vendedor;
    }
}