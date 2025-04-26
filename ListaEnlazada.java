package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int size;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(Nodo nodo) {
            valor = nodo.valor;

            sig = nodo.sig;
            ant = nodo.ant;
        }

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        if(size == 0) {
            primero = new Nodo(elem);
            
            primero.ant = null;
            primero.sig = null;

            ultimo = primero;

            size++;

            return;
        }

        Nodo newNodo = new Nodo(elem);

        primero.ant = newNodo;

        newNodo.sig = primero;
        newNodo.ant = null;

        primero = newNodo;
        
        size++; // longitud + 1
    }

    public void agregarAtras(T elem) {
        if(size == 0) {
            ultimo = new Nodo(elem);

            ultimo.ant = null;
            ultimo.sig = null;

            primero = ultimo;

            size++;

            return;
        }

        Nodo newNodo = new Nodo(elem);

        ultimo.sig = newNodo;

        newNodo.ant = ultimo;
        newNodo.sig = null;

        ultimo = newNodo;

        size++; // longitud + 1
    }

    public T obtener(int i) {
        if (i >= size) return null;

        // if(i == 0) return primero.valor;
        // if(i == (size - 1)) return ultimo.valor;

        Boolean usarPrimero = i <= (size / 2);
        System.out.println(usarPrimero);
        T res = null;

        if(usarPrimero) {
            res = primero.valor;

            Iterador<T> iterador = iterador(primero);
            
            int pos = 0;
    
            while(iterador.haySiguiente() && i != pos) {
                res = iterador.siguiente();
    
                pos++;
            }
    
        } else {
            res = ultimo.valor;

            Iterador<T> iterador = iterador(ultimo);
            
            int pos = size - 1;
    
            while(iterador.hayAnterior() && i != pos) {
                res = iterador.anterior();
                System.out.println(res);
    
                pos--;
            }
        }

        return res;
    }

    public void eliminar(int i) {
        T exists = obtener(i);
        
        if(exists == null) return;

        Boolean usarPrimero = i <= (size / 2);

        Nodo eliminar = usarPrimero ? primero : ultimo;
        Iterador<T> iterador = iterador(eliminar);

        if(usarPrimero) {
            int pos = 0;

            while(iterador.haySiguiente() && i != pos) {
                eliminar = eliminar.sig;

                iterador.siguiente();
            }
        } else {
            int pos = size - 1;
    
            while(iterador.hayAnterior() && i != pos) {
                eliminar = eliminar.ant;
                
                pos--;
            }
        }
        
        System.out.println(eliminar.valor);
        Nodo anterior = eliminar.ant;
        Nodo siguiente = eliminar.sig;

        anterior.sig = siguiente;
        siguiente.ant = anterior;
    }

    public void modificarPosicion(int indice, T elem) {
        throw new UnsupportedOperationException("No implementada aun");
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ListaIterador implements Iterador<T> {
        Nodo nodo;

        public ListaIterador(Nodo n) {
            nodo = n;
        }

        public boolean haySiguiente() {
            return nodo.sig != null;
        }
        
        public boolean hayAnterior() {
            return nodo.ant != null;
        }

        public T siguiente() {
            nodo = nodo.sig;

            return nodo.valor; 
        }

        public T anterior() {
            nodo = nodo.ant;

            return nodo.valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador(primero);
    }

    public Iterador<T> iterador(Nodo nodo) {
        return new ListaIterador(nodo);
    }

}
