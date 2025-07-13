package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTests {
    Heap<Integer> heap;
    Heap<Contenedor> heap2;
    ArrayList<Heap<Contenedor>.HandleHeap> handles;

    public Boolean esHeap(ArrayList<Integer> array) {
        for (int i = 0; i <= (array.size() - 2) / 2; ++i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < array.size() && (Integer) array.get(i) < (Integer) array.get(left)) {
                return false;
            }

            if (right < array.size() && (Integer) array.get(i) < (Integer) array.get(right)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testSimple() {
        heap = new Heap<>();
        int[] valores = new int[] { 4, 1, 7, 3 };

        for (int i = 0; i < valores.length; ++i) {
            heap.agregar(valores[i]);
        }

        Assertions.assertTrue(this.esHeap(heap.getHeap()));
        Assertions.assertEquals(7, (Integer) heap.extraer());
        Assertions.assertTrue(this.esHeap(heap.getHeap()));
        Assertions.assertEquals(4, (Integer) heap.extraer());
        Assertions.assertTrue(this.esHeap(heap.getHeap()));
        Assertions.assertEquals(3, (Integer) heap.extraer());
        Assertions.assertTrue(this.esHeap(heap.getHeap()));
        Assertions.assertEquals(1, (Integer) heap.extraer());
        Assertions.assertEquals(0, heap.cardinalidad());
        Assertions.assertTrue(heap.esVacio());
    }

    @Test
    public void testIsEmptyInitially() {
        heap = new Heap<>();
        Assertions.assertTrue(heap.esVacio());
    }

    @Test
    public void testDeDuplicados() {
        heap = new Heap<>();
        heap.agregar(5);
        heap.agregar(5);
        heap.agregar(5);
        assertEquals(3, heap.cardinalidad());
        assertEquals(5, heap.extraer());
        assertEquals(5, heap.extraer());
        assertEquals(5, heap.extraer());
    }

    private class Contenedor implements Comparable<Contenedor>{
        private int id;
        private int valor;

        public Contenedor(int id, int valor){
            this.id = id;
            this.valor = valor;
        }

        @Override
        public int compareTo(Contenedor o) {
            int res = this.valor - o.valor;
            if (res == 0) {
                res = -(this.id - o.id);
            }
            return res;
        }

        @Override
        public boolean equals(Object o){
            boolean mismaClase = this.getClass().equals(o.getClass());
            boolean res = false;
            if (mismaClase) {
                Contenedor c = (Contenedor) o;
                res = this.id == c.id && this.valor == c.valor;  
            }
            return res;
        }

        public void cambiarValor(int valor){
            this.valor = valor;
        }
    }

    @BeforeEach
    void setUp(){
        heap2 = new Heap<>();

        for(int i = 0; i < 10; i++){
            heap2.agregar(new Contenedor(i, 0));
        }

        handles = heap2.getHandles();
    }

    @Test
    public void simpleTestNoPirmitivo(){
        for(int i = 0; i <10 ; i++){
            assertEquals(new Contenedor(i, 0), heap2.extraer());
        }
    }
    
    @Test
    public void testHandles(){
        for(int i = 0; i < 10; i++){
            assertEquals(handles.get(i).getValor(), heap2.extraer());
        }
    }

    @Test
    public void testModificarHandles(){
        assertEquals(new Contenedor(0, 0), heap2.ver());

        Contenedor c = handles.get(1).getValor();
        c.cambiarValor(10);
        handles.get(1).modificar(c);

        assertEquals(new Contenedor(1, 10), heap2.ver());

        Contenedor c2 = handles.get(5).getValor();
        c2.cambiarValor(20);
        handles.get(5).modificar(c2);

        assertEquals(new Contenedor(5, 20), heap2.ver());
    }

}
