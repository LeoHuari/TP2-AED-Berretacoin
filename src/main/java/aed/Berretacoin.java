package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Heap<Usuario> heapUsers;
    private ArrayList<Heap<Usuario>.HandleHeap> handlesUsers;
    private ArrayList<Bloque> blockchain;

    public Berretacoin(int n_usuarios){
        Usuario[] users = new Usuario[n_usuarios];
        
        for(int i = 0; i < n_usuarios; i++){
            Usuario u = new Usuario(i+1);
            users[i] = u;
        }
        
        this.heapUsers = new Heap<>(users);
        this.handlesUsers = this.heapUsers.getHandles();
        
        this.blockchain = new ArrayList<>();
    }

    public void agregarBloque(Transaccion[] transacciones){
        Bloque block = new Bloque(this.blockchain.size());
        block.añadirTransacciones(transacciones);

        int id_comprador = 0;
        int id_vendedor = 0;
        int monto = 0;

        for(int i = 0; i < transacciones.length; i++){
            monto = transacciones[i].monto();
            id_comprador = transacciones[i].id_comprador();
            id_vendedor = transacciones[i].id_vendedor();

            if (id_comprador != 0) {
                Usuario comprador = this.handlesUsers.get(id_comprador-1).getValor();
                comprador.setDinero(comprador.getDinero() - monto);
                this.handlesUsers.get(id_comprador-1).modificar(comprador);
            }

            Usuario vendedor = this.handlesUsers.get(id_vendedor-1).getValor();
            vendedor.setDinero(vendedor.getDinero() + monto);
            this.handlesUsers.get(id_vendedor-1).modificar(vendedor);
        }

        this.blockchain.add(block);
    }

    public Transaccion txMayorValorUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).transaccionMayorMonto();
    }

    public Transaccion[] txUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).getTransacciones();
    }

    public int maximoTenedor(){
        return this.heapUsers.ver().getId();
    }

    public int montoMedioUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).montoMedio();
    }

    public void hackearTx(){
        int ultimo = this. blockchain.size()-1;
        Transaccion t = this.blockchain.get(ultimo).hackear();
        
        int monto = t.monto();
        int id_comprador = t.id_comprador();
        int id_vendedor = t.id_vendedor();

        if (id_comprador != 0) {
            Usuario comprador = this.handlesUsers.get(id_comprador-1).getValor();
            comprador.setDinero(comprador.getDinero() + monto);
            this.handlesUsers.get(id_comprador-1).modificar(comprador);
        }

        Usuario vendedor = this.handlesUsers.get(id_vendedor-1).getValor();
        vendedor.setDinero(vendedor.getDinero()-monto);
        this.handlesUsers.get(id_vendedor-1).modificar(vendedor);
    }
}
