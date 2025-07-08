package aed;


public class Bloque {
    private int id;
    private Heap<ListaDE<Transaccion>.HandleListaDE> heapTransacciones;
    private ListaDE<Transaccion> transaccionesOrdenadas;
    private int montos;
    private int cantTransacciones;

    public Bloque(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    /*
     * Siendo t la cantidad de transacciones recibidas como parametros
     * Se transaforma el array recibido en una lista enlazada en O(t)
     * Al mismo tiempo se suman los montos y se cuentan la cantidad de transacciones en O(1)
     * Luego se crea un heap utilizando los handles de la lista enlazada
     * Recibir los handles cuesta O(t)
     * Crear el heap cuesta O(t)
     * Complejidad final es 3*O(t) = O(t)
     */
    public void añadirTransacciones(Transaccion[] lt){
        this.transaccionesOrdenadas = new ListaDE<>();
        this.montos = 0;
        this.cantTransacciones = 0;

        for(int i = 0; i < lt.length; i++){
            this.transaccionesOrdenadas.agregarAtras(lt[i]);
            if (lt[i].id_comprador() != 0) {
                this.montos += lt[i].monto();
                this.cantTransacciones++;
            }
        }

        this.heapTransacciones = new Heap<>(this.transaccionesOrdenadas.getHandles());
    }

    /*
     * Utilizando un heap podemos ver la transaccion de mayor valor en O(1)
     */
    public Transaccion transaccionMayorMonto(){
        return this.heapTransacciones.ver().getValor();
    }

    /*
     *  Siendo t la cantidad de transacciones en el bloque
     *  Se transaforma la lista enlazada en un array de transacciones en O(t)
     */
    public Transaccion[] getTransacciones(){
        Transaccion[] res = new Transaccion[this.transaccionesOrdenadas.longitud()];
        Iterador<Transaccion> it = this.transaccionesOrdenadas.iterador();
        int indice = 0;

        while (it.haySiguiente()) {
            res[indice] = it.siguiente();
            indice++;
        }
        
        return res;
    }

    /*
     * Teniendo la cantidad de transacciones y el monto total de las transacciones en el bloque
     * Se puede devolver el promedio haciendo una simple division, la complejidad termina siendo O(1)
     */
    public int montoMedio(){
        int promedio = 0;
        if (cantTransacciones != 0) {
            promedio = montos / cantTransacciones;
        }
        return promedio;
    }

    /*
     * Se "ve" la transaccion de mayor valor con el heap en O(1)
     * Se hace la cuenta correspondiente en monto y cantTransacciones en O(1)
     * Por ultimo se extrae la transaccion en O(log t) siendo t la cantidad de transacciones en el bloque
     * Y al mismo tiempo se elimina la transaccion de la lista enlazada en O(1) por utilizar handles
     * Complejidad final es O(log t)
     */
    public Transaccion hackear(){
        Transaccion res = this.heapTransacciones.ver().getValor();
        if (cantTransacciones != 0) {
            this.montos = this.montos - res.monto();
            cantTransacciones--;
        }
        this.heapTransacciones.extraer().eliminar();
        return res;
    }
}
