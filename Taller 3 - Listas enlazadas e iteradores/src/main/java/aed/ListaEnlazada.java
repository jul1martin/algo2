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
            sig = null;
            ant = null;
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

        ultimo.sig = newNodo;

        newNodo.ant = ultimo;
        newNodo.sig = null;

        ultimo = newNodo;

        size++; // longitud + 1
    }

    public T obtener(int i) {
        if (i >= size) return null;

        Boolean usarPrimero = i <= (size / 2);
        Nodo nodoRes = usarPrimero ? primero : ultimo;
        T res = nodoRes.valor;

        if(usarPrimero) {
            int pos = 0;

            while(nodoRes.sig != null && i != pos) {
                res = nodoRes.sig.valor;
                
                nodoRes = nodoRes.sig;

                pos++;
            }
        } else {
            int pos = size - 1;

            while(nodoRes.ant != null && i != pos) {
                res = nodoRes.ant.valor;

                nodoRes = nodoRes.ant;
                
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

        if(usarPrimero) {
            int pos = 0;

            while(eliminar.sig != null && i != pos) {
                eliminar = eliminar.sig;
                
                pos++;
            }
        } else {
            int pos = size - 1;

            while(eliminar.ant != null && i != pos) {
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

        if(usarPrimero) {
            int pos = 0;

            while(modificar.sig != null && indice != pos) {
                modificar = modificar.sig;

                pos++;
            }
        } else {
            int pos = size - 1;

            while(modificar.ant != null && indice != pos) {
                modificar = modificar.ant;
                
                pos--;
            }
        }
        
        Nodo newNodo = new Nodo(elem);

        if(modificar.ant != null) {
            modificar.ant.sig = newNodo;
            newNodo.ant = modificar.ant;
        }

        if(modificar.sig != null) {
            modificar.sig.ant = newNodo;
            newNodo.sig = modificar.sig;
        }

        return;    
    }

    @Override
    public String toString() {
        String string = "[";

        if(primero != null) {
            string = string.concat(primero.valor.toString());
        } 

        while (primero.sig != null) {
            primero = primero.sig;

            string = string.concat(", " + primero.valor.toString());
        }

        string = string.concat("]");

        return string;    
    }

    private class ListaIterador implements Iterador<T> {
        Nodo nodo;
        String lastItOrientation;


        public ListaIterador(Nodo n) {
            nodo = n;
            lastItOrientation = null;
        }

        public boolean haySiguiente() {
            if(lastItOrientation == "ant") return nodo != null;

            return nodo != null && nodo.sig != null;
        }
        
        public boolean hayAnterior() {
            if(lastItOrientation == "sig") return nodo != null;
            
            return nodo != null && nodo.ant != null;
        }

        public T siguiente() {
            if(nodo == null) return null;
            
            if(lastItOrientation != "sig") {
                lastItOrientation = "sig";
                T valor = nodo.valor;

                return valor;
            }

            nodo = nodo.sig;
            T valor = nodo.valor;

            return valor;
        }
        

        public T anterior() {
            if(nodo == null) return null;

            if(lastItOrientation != "ant") {
                lastItOrientation = "ant";
                T valor = nodo.valor;

                return valor;
            }

            nodo = nodo.ant;
            T valor = nodo.valor;
            
            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador(primero);
    }

}
