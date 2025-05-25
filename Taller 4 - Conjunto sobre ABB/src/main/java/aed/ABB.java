package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;
    // private int _altura;
    
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

        // Nodo(Nodo nodo) {
        //     valor = nodo.valor;
        //     izq = nodo.izq;
        //     der = nodo.der;
        //     padre = nodo.padre;
        // }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
        // _altura = 0;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        Nodo nodo = getMinNodo();

        return nodo != null ? nodo.valor : null;  
    }

    public Nodo getMinNodo () {
        if(_raiz == null) return null;

        Nodo min = _raiz;

        // System.out.println("Minimo base" + min.valor);
        
        while(min.izq != null) {
            min = min.izq;
            // System.out.println("Nuevo Minimo " + min.valor);

        }

        return min; 
    }

    public T maximo(){
        if(_raiz == null) return null;

        Nodo max = _raiz;

        // System.out.println("Max base " + max.valor);

        while(max.der != null) {
            max = max.der;
            // System.out.println("Max " + max.valor);
        }

        return max.valor;     
    }

    public void insertar(T elem){
        if(pertenece(elem)) return;

        _cardinal++;

        Nodo newNodo = new Nodo(elem);

        if(_raiz == null) {
            _raiz = newNodo;
            
            return;
        }

        Nodo actual = _raiz;

        int status = 0;

        while(actual != null)  {
            status = elem.compareTo(actual.valor);

            // elem > valor raiz
            if(status > 0) {
                if (actual.der == null) {
                    break;
                }

                actual = actual.der;

                continue;
            }

            if (actual.izq == null) {
                break;
            }

            actual = actual.izq;
    
            // elem < valor raiz
        }
        
        if(status > 0) {
            newNodo.padre = actual;

            actual.der = newNodo;
            

            // System.out.println("Inserto " + newNodo.valor + ", padre es " + newNodo.padre.valor);
            return;
        }

        newNodo.padre = actual;

        actual.izq = newNodo;

        return;
    }

    public boolean pertenece(T elem){
        return obtener(elem) != null;
    }

    public Nodo obtener(T elem){
        Nodo actual = _raiz;

        int status = 0;

        // && (actual.izq == null && actual.der == null))
        while(actual != null)  {
            status = elem.compareTo(actual.valor);

            if(status == 0) return actual;

            if(status > 0) {
                actual = actual.der;

                continue;
            }

            actual = actual.izq;
    
            // elem < valor raiz
        }

        return null;
    }

    public void eliminar(T elem){
        if(!pertenece(elem)) return;

        _cardinal--;

        Nodo eliminar = obtener(elem);
        Nodo padreEliminar = eliminar.padre;

        System.out.println("Valor a eliminar: " + eliminar.valor);

        // no tiene hijos
        // correcto
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

            System.out.println(elem + " Tiene 2 hijos");
            System.out.println(sucesor.valor + " sucesor");
            
            // if(eliminar.izq.valor.compareTo(elem) != 0) {
            //     eliminar.izq.padre = sucesor;
            // }
            
            // if(eliminar.der.valor.compareTo(elem) != 0) {
            //     eliminar.der.padre = sucesor;
            // }

            eliminar.izq.padre = sucesor;
            eliminar.der.padre = sucesor;

            sucesor.padre.izq = null;

            sucesor.izq = eliminar.izq;

            if(eliminar.der.valor.compareTo(sucesor.valor) != 0) {
                sucesor.der = eliminar.der;
            } else {
                sucesor.der = eliminar.der.der;
            }
            
            if(padreEliminar != null) {
                sucesor.padre = padreEliminar;

                if(padreEliminar.der != null && elem.compareTo(padreEliminar.der.valor) == 0) {
                    padreEliminar.der = sucesor; // valor que estoy eliminando
                } else if (padreEliminar.izq != null && elem.compareTo(padreEliminar.izq.valor) == 0) {
                    padreEliminar.izq = sucesor;
                }
            } else {
                _raiz = sucesor;
                _raiz.padre = null;
            }

            return;
        }

        // tiene 1 solo hijo y es el menor
        // correcto
        Nodo sucesor = eliminar.izq != null ? eliminar.izq : eliminar.der;

        System.out.println(elem + " Tiene 1 solo hijo");
        System.out.println(sucesor.valor + " sucesor");

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

                // break;
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