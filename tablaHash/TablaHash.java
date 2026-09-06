package tablaHash;

public class TablaHash {

    public static class TablaHashEncadenamiento {
        private static class Nodo {
            int key;
            String value;
            Nodo siguiente;

            Nodo(int key, String value) {
                this.key = key;
                this.value = value;
                this.siguiente = null;
            }
        }

        private int m;
        private int n;
        private Nodo[] tabla;

        public TablaHashEncadenamiento(int tamano) {
            this.m = tamano;
            this.n = 0;
            this.tabla = new Nodo[m];
        }

        public TablaHashEncadenamiento() {
            this(7);
        }

        public int hash(int key) {
            return Math.abs(key) % m;
        }

        public double factorCarga() {
            return (double) n / m;
        }

        public void imprimirTabla() {
            for (int i = 0; i < m; i++) {
                StringBuilder sb = new StringBuilder();
                Nodo actual = tabla[i];
                while (actual != null) {
                    if (sb.length() > 0) sb.append(" -> ");
                    sb.append("(").append(actual.key).append(", ").append(actual.value).append(")");
                    actual = actual.siguiente;
                }
                System.out.println(i + " -> " + sb.toString());
            }
        }

        public void insertar(int key, String value) {
            int posicion = hash(key);
            Nodo actual = tabla[posicion];

            while (actual != null) {
                if (actual.key == key) {
                    actual.value = value;
                    return;
                }
                actual = actual.siguiente;
            }

            Nodo nuevoNodo = new Nodo(key, value);
            if (tabla[posicion] == null) {
                tabla[posicion] = nuevoNodo;
            } else {
                Nodo aux = tabla[posicion];
                while (aux.siguiente != null) {
                    aux = aux.siguiente;
                }
                aux.siguiente = nuevoNodo;
            }
            n++;
        }

        public String buscar(int key) {
            int posicion = hash(key);
            Nodo actual = tabla[posicion];

            while (actual != null) {
                if (actual.key == key) {
                    return actual.value;
                }
                actual = actual.siguiente;
            }
            return "NOT_FOUND";
        }

        public boolean eliminar(int key) {
            int posicion = hash(key);
            Nodo actual = tabla[posicion];
            Nodo anterior = null;

            while (actual != null) {
                if (actual.key == key) {
                    if (anterior == null) {
                        tabla[posicion] = actual.siguiente;
                    } else {
                        anterior.siguiente = actual.siguiente;
                    }
                    n--;
                    return true;
                }
                anterior = actual;
                actual = actual.siguiente;
            }
            return false;
        }
    }
}