package aed;

import java.util.ArrayList;

public class Berretacoin {
    private Heap<Usuario> heapUsers;
    private ArrayList<Heap<Usuario>.HandleHeap> handlesUsers;
    private ArrayList<Bloque> blockchain;

    /*
     * Siendo n el int recibido como parametro, se crean n usuarios y se los agregan a un array en O(n)
     * (La creacion de los usuarios es O(1))
     * Este array se usa para crear un heap en O(n)
     * Recibimos los handles de los usuarios en el heap costando O(n)
     * La complejidad final es O(n) + O(n) + O(n) = 3*O(n) = O(n)
     */
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

    /*
     * Siendo t la cantidad de transacciones recibidas como parametros
     * Se crea un nuevo Bloque en O(1)
     * Se añaden las transacciones al bloque costando O(t)
     * Por cada transaccion se actualiza el dinero de los usuarios en O(1)
     * Se actualiza la posicion del usuario en el heap en O(log n) siendo n la cantidad de usuarios en el heap
     * Esto se hace dos veces por transaccion dando una complejidad de t*2*O(log n) = O(t*log n)
     * Por ultimo se agrega el bloque a un ArrayList en O(1) amortizado
     * La complejidad final es O(1) + O(t) + O(t*log n) = O(max{t, t*log n}) = O(t*log n)
     */
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

    /*
     * Se accede a la ultima posicion de la blockchain en O(1)
     * El bloque devuelve la transaccion de mayor valor en O(1)
     * Complejidad total es O(1) + O(1) = O(1)
     */
    public Transaccion txMayorValorUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).transaccionMayorMonto();
    }

    /*
     * Se accede a la ultima posicion de la blockchain en O(1)
     * Devuelve las transacciones del bloque en dicha posicion en O(t)
     * Siendo t la cantidad de transacciones en ese bloque
     * Complejidad total es O(1) + O(t) = O(t)
     */
    public Transaccion[] txUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).getTransacciones();
    }

    /*
     * Utilizando el heap se puede ver el maximo tenedor en O(1)
     */
    public int maximoTenedor(){
        return this.heapUsers.ver().getId();
    }

    /*
     * Se accede a la ultima posicion de la blockchain en O(1)
     * El bloque devuelve el montoMedio en O(1)
     * Complejidad total O(1) + O(1) = O(1)
     */
    public int montoMedioUltimoBloque(){
        int ultimo = this.blockchain.size()-1;
        return this.blockchain.get(ultimo).montoMedio();
    }

    /*
     * Se accede a la ultima posicion de la blockchain en O(1)
     * El bloque extrae la transaccion de mayor valor en O(log t) siendo t la cantidad de transacciones en el bloque
     * Utilizando esa transaccion se actualiza el dinero de los usuarios en O(1)
     * Luego se actualiza su posicion en el heapUsers en O(log n) siendo n la cantidad de usuarios, esto se hace dos veces
     * La complejidad total es O(1) + O(log t) + 2*O(log n) = O(log t + log n)
     */
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
