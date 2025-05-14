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
        primero = new Nodo(lista.primero);
        ultimo = new Nodo(lista.ultimo);
        size = lista.size;
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

        if (size == 1) {
            primero = ultimo;
            primero.sig = newNodo;
        }

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
        Nodo nodoRes = usarPrimero ? primero : ultimo;
        T res = nodoRes.valor;

        Iterador<T> iterador = iterador(nodoRes);

        if(usarPrimero) {
            int pos = 0;
    
            while(iterador.haySiguiente() && i != pos) {
                res = iterador.siguiente();
    
                pos++;
            }
        } else {
            int pos = size - 1;
    
            while(iterador.hayAnterior() && i != pos) {
                res = iterador.anterior();
    
                pos--;
            }
        }

        return res;
    }

    public void eliminar(int i) {
        Boolean exists = obtener(i) != null;
        
        if(!exists) return;

        Boolean usarPrimero = i <= (size / 2);
        Nodo eliminar = usarPrimero ? primero : ultimo;
        
        Iterador<T> iterador = iterador(eliminar);

        if(usarPrimero) {
            int pos = 0;

            while(iterador.haySiguiente() && i != pos) {
                eliminar = eliminar.sig;
                
                iterador.siguiente();

                pos++;
            }
        } else {
            int pos = size - 1;
    
            while(iterador.hayAnterior() && i != pos) {
                iterador.anterior();

                eliminar = eliminar.ant;
                
                pos--;
            }
        }
        
        Nodo anterior = eliminar.ant;
        Nodo siguiente = eliminar.sig;

        if(siguiente != null) siguiente.ant = anterior;
        if(anterior != null) anterior.sig = siguiente;

        if(i == 0) primero = siguiente;
        if(i == (size - 1)) ultimo = anterior;

        size--;
    }

    public void modificarPosicion(int indice, T elem) {
        Boolean exists = obtener(indice) != null;
        
        if(!exists) return;

        Boolean usarPrimero = indice <= (size / 2);
        Nodo modificar = usarPrimero ? primero : ultimo;
        
        Iterador<T> iterador = iterador(modificar);

        if(usarPrimero) {
            int pos = 0;

            while(iterador.haySiguiente() && indice != pos) {
                modificar = modificar.sig;
                
                iterador.siguiente();

                pos++;
            }
        } else {
            int pos = size - 1;
    
            while(iterador.hayAnterior() && indice != pos) {
                iterador.anterior();

                modificar = modificar.ant;
                
                pos--;
            }
        }

        Nodo newNodo = new Nodo(elem);

        if(modificar.ant != null) {
            modificar.ant.sig = newNodo;
        }

        if(modificar.ant != null) {
            modificar.sig.ant = newNodo;
        }

        return;
    }
    
    @Override
    public String toString() {
        String string = "[";

        Iterador<T> iterador = iterador(primero);

        if(primero != null) {
            string = string.concat(primero.valor.toString());
        } 

        while (iterador.haySiguiente()) {
            // System.out.println(iterador.siguiente().toString());

            string = string.concat(", " + iterador.siguiente().toString());
        }

        string = string.concat("]");

        return string;
    }

    private class ListaIterador implements Iterador<T> {
        Nodo nodo;

        public ListaIterador(Nodo n) {
            nodo = n;
        }

        public boolean haySiguiente() {
            if(nodo == null) return false;
            
            return nodo.sig != null;
        }
        
        public boolean hayAnterior() {
            if(nodo == null) return false;

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
