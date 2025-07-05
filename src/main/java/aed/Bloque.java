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

    public Transaccion transaccionMayorMonto(){
        return this.heapTransacciones.ver().getValor();
    }

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

    public int montoMedio(){
        int promedio = 0;
        if (cantTransacciones != 0) {
            promedio = montos / cantTransacciones;
        }
        return promedio;
    }

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
