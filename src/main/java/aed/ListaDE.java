package aed;

public class ListaDE<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo{
        Nodo anterior;
        T valor;
        Nodo siguiente;

        public Nodo(T valor){this.valor = valor;}
    }

    public ListaDE(){
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
    }

    public int longitud(){
        return this.longitud;
    }

    public void agregarAdelante(T v){
        Nodo nuevo = new Nodo(v);

        if(this.primero == null){
            this.primero = nuevo;
            this.ultimo = nuevo;
        }else{
            this.primero.anterior = nuevo;
            nuevo.siguiente = this.primero;
            this.primero = nuevo;
        }

        this.longitud++;
    }

    public void agregarAtras(T v){
        Nodo nuevo = new Nodo(v);

        if (this.primero == null) {
            this.primero = nuevo;
            this.ultimo = nuevo;
        }else{
            this.ultimo.siguiente = nuevo;
            nuevo.anterior = ultimo;
            this.ultimo = nuevo;
        }

        this.longitud++;
    }

    public Nodo obtenerNodo(int i){
        Nodo actual = this.primero;

        for(int j = 0; j < i; j++){
            actual = actual.siguiente;
        }
        
        return actual;
    }

    public T obtener(int i){
        return this.obtenerNodo(i).valor;
    }

    public void eliminar(int i){
        Nodo actual = this.obtenerNodo(i);

        if (this.longitud == 1 && i == 0) {
            this.primero = null;
            this.ultimo = null;
            this.longitud--;
            return;
        }
        if (actual.siguiente == null) {
            actual.anterior.siguiente = null;
            this.ultimo = actual.anterior;
            this.longitud--;
            return;
        }
        if (actual.anterior == null) {
            actual.siguiente.anterior = null;
            this.primero = actual.siguiente;
            this.longitud--;
            return;
        }
        actual.anterior.siguiente = actual.siguiente;
        actual.siguiente.anterior = actual.anterior;

        this.longitud--;
    }

    private void eliminarNodo(Nodo n){

    }

    public void modificarPosicion(int i, T elem){
        Nodo actual = this.obtenerNodo(i);
        actual.valor = elem;
    }

    public ListaDE(ListaDE<T> lista){
        Nodo actual = lista.primero;

        while (actual != null) {
            T v = actual.valor;
            this.agregarAtras(v);
            actual = actual.siguiente;
        }

        this.longitud = lista.longitud();
    }

    @Override
    public String toString(){

    }
}
