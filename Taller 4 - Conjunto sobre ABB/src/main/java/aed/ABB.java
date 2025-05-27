package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;
    
    private class Nodo {
        T valor;
        Nodo izq;
        Nodo der;
        Nodo padre;

        Nodo(T v) {
            valor = v;
            izq = null;
            der = null;
            padre = null;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(Nodo base){
        Nodo nodo = base != null ? base : _raiz;

        if(nodo == null) return null;

        return nodo.izq != null ? minimo(nodo.izq) : nodo.valor;
    }

    public T minimo() {
        return minimo(_raiz);
    }

    public Nodo getMinNodo () {
        return getMinNodo(_raiz);
    }

    public Nodo getMinNodo (Nodo nodo) {
        if(nodo == null) return null;

        return nodo.izq != null ? getMinNodo(nodo.izq) : nodo; 
    }

    public T maximo(Nodo nodo){
        if(nodo == null) return null;

        return nodo.der != null ? maximo(nodo.der) : nodo.valor;     
    }

    public T maximo() {
        return maximo(_raiz);     
    }

    public void insertar(T elem, Nodo base){
        int status = base.valor.compareTo(elem);

        if (status > 0) {
            if(base.izq == null) {
                Nodo newNodo = new Nodo(elem);
                newNodo.padre = base;
                base.izq = newNodo;
    
                _cardinal++;
            } else {
                insertar(elem, base.izq);
            }
        }

        if (status < 0) {
            if(base.der == null) {
                Nodo newNodo = new Nodo(elem);
                newNodo.padre = base;
                base.der = newNodo;
    
                _cardinal++;
            } else {
                insertar(elem, base.der);
            }
        }
    }

    public void insertar(T elem){
        if(_raiz == null) {
            _raiz = new Nodo(elem);

            _cardinal++;
        } else {
            int status = _raiz.valor.compareTo(elem);

            if(status == 0) return;

            Nodo next = status > 0 ? _raiz.izq : _raiz.der;

            if(next == null) {
                Nodo newNodo = new Nodo(elem);
                newNodo.padre = _raiz;

                if(status > 0) {
                    _raiz.izq = newNodo;
                } else {
                    _raiz.der = newNodo;
                }

                _cardinal++;
            } else {
                insertar(elem, next);
            }
        }
    }

    public boolean pertenece(T elem){
        return obtener(elem) != null;
    }

    public Nodo obtener(T elem) {
        return obtener(elem, null);
    }

    public Nodo obtener(T elem, Nodo base){
        Nodo actual = base != null ? base : _raiz;

        if(actual == null) return null;

        int status = actual.valor.compareTo(elem);

        if(status == 0) return actual;

        Nodo next = status > 0 ? actual.izq : actual.der;

        if(next == null) return null;

        return obtener(elem, next);
    }

    public void eliminar(T elem){
        Nodo eliminar = obtener(elem);

        if(eliminar == null) return;

        _cardinal--;

        Nodo padreEliminar = eliminar.padre;

        System.out.println("Valor a eliminar: " + eliminar.valor);

        // no tiene hijos
        if(eliminar.izq == null && eliminar.der == null) {
            System.out.println("No tiene hijos");

            if(padreEliminar != null) {
                if(elem.compareTo(padreEliminar.valor) > 0) {
                    padreEliminar.der = null;
                } else {
                    padreEliminar.izq = null;
                }
            } else {
                _raiz = null;
            }

            return;
        }

        // tiene 2 hijos
        if(eliminar.izq != null && eliminar.der != null) {
            Nodo sucesor = sucesor(eliminar);

            eliminar(sucesor.valor);

            _cardinal++;

            if(padreEliminar != null) {
                sucesor.padre = padreEliminar;

                if(padreEliminar.der != null && elem.compareTo(padreEliminar.der.valor) == 0) {
                    padreEliminar.der = sucesor; // valor que estoy eliminando
                } 
                
                if (padreEliminar.izq != null && elem.compareTo(padreEliminar.izq.valor) == 0) {
                    padreEliminar.izq = sucesor;
                }
            } else {
                _raiz = sucesor;
                _raiz.padre = null;
            }

            sucesor.izq = eliminar.izq;
            sucesor.der = eliminar.der;

            eliminar.izq.padre = sucesor;

            // Por si es el que acabo de eliminar
            if(eliminar.der != null) {
                eliminar.der.padre = sucesor;
            }

            return;
        }

        // tiene 1 solo hijo y es el menor
        Nodo sucesor = eliminar.izq != null ? eliminar.izq : eliminar.der;

        if(padreEliminar != null) {
            sucesor.padre = padreEliminar;

            if(elem.compareTo(padreEliminar.valor) > 0) {
                padreEliminar.der = sucesor;
            } else {
                padreEliminar.izq = sucesor;
            }
        } else {
            sucesor.padre = null;
            _raiz = sucesor;
        }

        return;
    }

    public String toString(){
        ABB_Iterador iterator = new ABB_Iterador();

        if(cardinal() == 0) return "{}";
        if(cardinal() == 1) return "{" + iterator.siguiente() + "}";

        String string = "{";
        
        int escape = 0;

        while(iterator.haySiguiente() && escape < 10) {
            T value = iterator.siguiente();
            
            string = string.concat(string == "{" ? value.toString() : "," + value);
            
            escape++;
        }

        string = string.concat("," + iterator.siguiente()).concat("}");

        return string;
    }

    public Nodo minAtRight(Nodo node) {
        Nodo minNode = node.der;

        while (minNode.izq != null) {
            minNode = minNode.izq;
        } 

        return minNode;
    }

    public Nodo firstRightAncestor(Nodo node) {
        Nodo ancestor = node.padre;

        while(ancestor != null) {
            // Si el de arriba es mayor al nodo -> Es rama izq
            if(ancestor.valor.compareTo(node.valor) > 0) {
                break;
            } else {
                ancestor = ancestor.padre;
            }
        }

        return ancestor;
    }

    public Nodo sucesor (Nodo node) {
        if(node.valor.compareTo(maximo()) == 0) return null;
        if(node.der != null) return minAtRight(node);

        return firstRightAncestor(node);
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public ABB_Iterador() {
            _actual = getMinNodo();
        }

        public boolean haySiguiente() {            
            return sucesor(_actual) != null;
        }
    
        public T siguiente() {
            if(_actual == null) return null;

            T returnValue = _actual.valor;

            _actual = sucesor(_actual);

            return returnValue;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}