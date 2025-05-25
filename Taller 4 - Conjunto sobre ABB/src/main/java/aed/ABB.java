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
            actual.der = newNodo;
            
            newNodo.padre = actual;

            // System.out.println("Inserto " + newNodo.valor + ", padre es " + newNodo.padre.valor);
            return;
        }

        actual.izq = newNodo;

        newNodo.padre = actual;

        return;
    }

    public boolean pertenece(T elem){
        Nodo actual = _raiz;

        int status = 0;

        // && (actual.izq == null && actual.der == null))
        while(actual != null)  {
            status = elem.compareTo(actual.valor);

            if(status == 0) return true;

            if(status > 0) {
                actual = actual.der;

                continue;
            }

            actual = actual.izq;
    
            // elem < valor raiz
        }

        return false;
    }

    public Nodo obtener(T elem){
        if(!pertenece(elem)) return null;

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

        // if(false) {
        //     // System.out.println(_raiz.valor);
        //     // System.out.println("Anterior: " + _raiz.padre);
        //     // System.out.println(_raiz.der.valor);
        //     // System.out.println("Anterior: " + _raiz.der.padre.valor);
        //     // System.out.println(_raiz.der.der.valor);
        //     // System.out.println("Anterior: " + _raiz.der.der.padre.valor);
            
        //     System.out.println(_raiz.valor);
        //     System.out.println("Anterior: " + _raiz.padre);
        //     System.out.println(_raiz.izq.valor);
        //     System.out.println("Anterior: " + _raiz.izq.padre.valor);
        //     System.out.println(_raiz.der.valor);
        //     System.out.println("Anterior: " + _raiz.der.padre.valor);
        //     // System.out.println(_raiz.der.izq.valor);
        //     // System.out.println("Anterior: " + _raiz.der.izq.padre.valor);
        //     // System.out.println(_raiz.der.der.valor);
        //     // System.out.println("Anterior: " + _raiz.der.der.padre.valor);
            
        //     // return;
        // }

        _cardinal--;

        int status = elem.compareTo(_raiz.valor);
        Nodo eliminar = _raiz; 
        
        while(status != 0) {
            eliminar = status > 0 ? eliminar.der : eliminar.izq;
            status = elem.compareTo(eliminar.valor);
        }

        System.out.println("Valor a eliminar: " + eliminar.valor);

        if(eliminar.izq == null && eliminar.der == null) {
            System.out.println("No tiene hijos");

            if(eliminar.padre != null) {
                if(elem.compareTo(eliminar.padre.valor) > 0) {
                    eliminar.padre.izq = null;
                } else {
                    eliminar.padre.der = null;
                }
            }

            if(_raiz.valor.compareTo(elem) == 0) {
                _raiz = null;
            }
            
            return;
        }

        // tiene 2 hijos
        if(eliminar.izq != null && eliminar.der != null) {
            System.out.println(eliminar.valor + " Tiene 2 hijos");

            Nodo sucesor = eliminar.der;

            while(sucesor.izq != null) {
                sucesor = sucesor.izq;
            }

            System.out.println(sucesor.valor + " es el sucesor");

            sucesor.padre.izq = null;

            sucesor.padre = eliminar.padre;
            sucesor.izq = eliminar.izq;

            if(eliminar.izq != null && eliminar.izq.valor.compareTo(elem) != 0 ) {
                eliminar.izq.padre = sucesor;
            }

            if(eliminar.der != null && eliminar.der.valor.compareTo(elem) != 0 ) {
                eliminar.der.padre = sucesor;
            }

            if(eliminar.der.valor.compareTo(sucesor.valor) != 0) {
                sucesor.der = eliminar.der;
            } else {
                sucesor.der = eliminar.der.der != null ? eliminar.der.der : null;
            }

            if(eliminar.padre != null) {
                if(eliminar.padre.der != null && elem.compareTo(eliminar.padre.der.valor) == 0) {
                    eliminar.padre.der = sucesor; // valor que estoy eliminando
                }

                // if (eliminar.padre.izq != null && elem.compareTo(eliminar.padre.izq.valor) == 0) {
                //     eliminar.padre.izq = sucesor;
                // }
            } else {
                _raiz = sucesor;
            }

            return;
        }

        // tiene 1 solo hijo y es el menor
        if(eliminar.izq != null) {
            System.out.println(eliminar.valor + " Tiene 1 solo hijo y es el menor");

            // tiene predecesor
            if(eliminar.padre != null) {
                System.out.println(eliminar.valor + " es el padre");

                // es menor al predecesor
                if(elem.compareTo(eliminar.valor) > 0) {
                    eliminar.padre.izq = eliminar.izq;
                } else {
                    // es mayor al predecesor
                    eliminar.padre.der = eliminar.izq;
                }

                return;
            } else {
                eliminar.izq.padre = null;
                _raiz = eliminar.izq;
            }
        } else if (eliminar.der != null) {
            System.out.println(eliminar.valor + " Tiene 1 solo hijo y es el mayor");

            System.out.println(eliminar.der.valor + " hijo sucesor");

            // tiene predecesor
            if(eliminar.padre != null) {
                System.out.println(eliminar.valor + " es el padre");

                // es menor al predecesor
                if(elem.compareTo(eliminar.valor) > 0) {
                    eliminar.padre.izq = eliminar.der;
                } else {
                    // es mayor al predecesor
                    eliminar.padre.der = eliminar.der;
                }
            } else {
                eliminar.der.padre = null;
                _raiz = eliminar.der;
            }
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
            // System.out.println("Siguiente: " + value);
            
            string = string.concat(string == "{" ? value.toString() : "," + value);
            
            escape++;
        }

        string = string.concat("," + iterator.siguiente());

        return string.concat("}");
    }

    public Nodo minAtRight(Nodo node) {
        Nodo minNode = node.der;

        while(minNode.izq != null) {
            minNode = minNode.izq;
        } 

        return minNode;
    }

    public Nodo firstRightAncestor(Nodo node) {
        Nodo ancestor = node.padre;

        while(ancestor != null) {
            // Si el de arriba es mayor al nodo -> Es rama izq
            if(ancestor.valor.compareTo(node.valor) > 0) {
                return ancestor;
            } else {
                ancestor = ancestor.padre;
                break;
            }
        }

        return ancestor;
    }

    public Nodo sucesor (Nodo node) {
        if(node.valor.compareTo(maximo()) == 0) {
            // System.out.println("Se llego al maximo (" + maximo() + "), no hay sucesor a " + node.valor);
        
            return null;
        }

        if(node.der != null) return minAtRight(node);

        return firstRightAncestor(node);
    }

    public T sucesorNum (T elem) {
        Nodo node = obtener(elem);

        if(node.valor.compareTo(maximo()) == 0) {
            // System.out.println("Se llego al maximo (" + maximo() + "), no hay sucesor a " + node.valor);
        
            return null;
        }

        if(node.der != null) return minAtRight(node).valor;

        return firstRightAncestor(node).valor;
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

            // System.out.println("Siguiente " + (_actual != null ? _actual.valor : null));
            return returnValue;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}