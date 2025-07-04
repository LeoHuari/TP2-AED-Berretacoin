package aed;

import java.util.ArrayList;

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
        if (i < 0 || i > this.longitud) {
            return;
        }

        if (i == 0 && this.longitud == 0) {
            return;
        }
        
        Nodo actual = this.obtenerNodo(i);
        
        eliminarNodo(actual);
    }

    private void eliminarNodo(Nodo n){
        Nodo anterior = n.anterior;
        Nodo siguiente = n.siguiente;
        if (this.longitud == 1) {
            this.primero = null;
            this.ultimo = null;
            this.longitud--;
            return;
        }
        if (this.primero == n) {
            this.primero.siguiente.anterior = null;
            this.primero = this.primero.siguiente;
            this.longitud--;
            return;
        }
        if (this.ultimo == n) {
            this.ultimo.anterior.siguiente = null;
            this.ultimo = this.ultimo.anterior;
            this.longitud--;
            return;
        }
        if (anterior != null) {
            anterior.siguiente = siguiente;
        }
        if (siguiente != null) {
            siguiente.anterior = anterior;
        }
        this.longitud--;
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

    public ArrayList<HandleListaDE> getHandles(){
        Nodo actual = this.primero;
        ArrayList<HandleListaDE> res = new ArrayList<>(this.longitud);
        
        while (actual != null) {
            HandleListaDE handle = new HandleListaDE(actual);
            res.add(handle);
            actual = actual.siguiente;
        }

        return res;
    }

    @Override
    public String toString(){
        StringBuffer res = new StringBuffer("["+this.primero.valor);
        Nodo actual = this.primero.siguiente;
        while (actual != null) {
            res.append(", "+actual.valor);
            actual = actual.siguiente;
        }
        res.append("]");
        return res.toString();
    }

    private class ListaIterador implements Iterador<T>{
        private int indice;
        private Nodo actual;

        public ListaIterador(){
            this.indice = 0;
            this.actual = primero;
        }

        @Override
        public boolean haySiguiente() {
            boolean existe = this.indice < longitud;
            return existe;
        }

        @Override
        public boolean hayAnterior() {
            if (longitud == 0) {
                return false;
            }
            if (longitud == indice) {
                return true;
            }
            boolean existe = indice > 0 && indice <= longitud;
            return existe;
        }

        @Override
        public T siguiente() {
            Nodo res = this.actual;
            if (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            this.indice++;

            return res.valor;
        }

        @Override
        public T anterior() {
            Nodo res = null;
            if (longitud == this.indice) {
                res = actual;
            }else{
                res = actual.anterior;
                actual = actual.anterior;
            }
            this.indice--;

            return res.valor;
        }
        
    }

    public Iterador<T> iterador(){
        return new ListaIterador();
    }

    public class HandleListaDE implements Handle<T>{
        private Nodo puntero;

        public HandleListaDE(Nodo v){
            this.puntero = v;
        }

        @Override
        public T getValor() {
            return this.puntero.valor;
        }

        @Override
        public void modificar(T v) {
            this.puntero.valor = v;
        }

        public void eliminar(){
            eliminarNodo(puntero);
        }

        @Override
        public String toString(){
            return this.puntero.valor.toString();
        }
    }
    
    private HandleListaDE handle(Nodo n){
        return new HandleListaDE(n);
    }
}
