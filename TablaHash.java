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

    public static class TablaHashDireccionamientoAbierto {
        private static class Entrada {
            int key;
            String value;
            boolean esDeleted;

            Entrada(int key, String value) {
                this.key = key;
                this.value = value;
                this.esDeleted = false;
            }
        }

        private int m;
        private int n;
        private Entrada[] tabla;

        public TablaHashDireccionamientoAbierto(int tamano) {
            this.m = tamano;
            this.n = 0;
            this.tabla = new Entrada[m];
        }

        public TablaHashDireccionamientoAbierto() {
            this(7);
        }

        public int hash(int key, int i) {
            return (Math.abs(key) % m + i) % m;
        }

        public void insertar(int key, String value) {
            if (n >= m) {
                System.out.println("Error: Tabla llena.");
                return;
            }
            int primerDeleted = -1;

            for (int i = 0; i < m; i++) {
                int pos = hash(key, i);
                Entrada actual = tabla[pos];

                if (actual == null) {
                    int posInsertar = (primerDeleted != -1) ? primerDeleted : pos;
                    tabla[posInsertar] = new Entrada(key, value);
                    n++;
                    return;
                } else if (actual.esDeleted) {
                    if (primerDeleted == -1) primerDeleted = pos;
                } else if (actual.key == key) {
                    actual.value = value;
                    return;
                }
            }

            if (primerDeleted != -1) {
                tabla[primerDeleted] = new Entrada(key, value);
                n++;
            }
        }

        public String buscar(int key) {
            for (int i = 0; i < m; i++) {
                int pos = hash(key, i);
                Entrada actual = tabla[pos];

                if (actual == null) return "NOT_FOUND";
                if (!actual.esDeleted && actual.key == key) {
                    return actual.value;
                }
            }
            return "NOT_FOUND";
        }

        public boolean eliminar(int key) {
            for (int i = 0; i < m; i++) {
                int pos = hash(key, i);
                Entrada actual = tabla[pos];

                if (actual == null) return false;
                if (!actual.esDeleted && actual.key == key) {
                    actual.esDeleted = true;
                    n--;
                    return true;
                }
            }
            return false;
        }

        public void imprimirTabla() {
            for (int i = 0; i < m; i++) {
                if (tabla[i] == null) {
                    System.out.println(i + " -> [VACIO]");
                } else if (tabla[i].esDeleted) {
                    System.out.println(i + " -> DELETED");
                } else {
                    System.out.println(i + " -> (" + tabla[i].key + ", " + tabla[i].value + ")");
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       1. PRUEBAS ENCADENAMIENTO ");
        System.out.println("========================================");
        TablaHashEncadenamiento enc = new TablaHashEncadenamiento(7);

        enc.insertar(18, "Ana");
        enc.insertar(10, "Luis");
        enc.insertar(24, "Maria");
        enc.insertar(31, "Carlos");

        System.out.println("Estado inicial:");
        enc.imprimirTabla();

        System.out.println("\nBúsquedas:");
        System.out.println("buscar(24) -> " + enc.buscar(24));
        System.out.println("buscar(99) -> " + enc.buscar(99));

        System.out.println("\nEliminando 24:");
        enc.eliminar(24);
        enc.imprimirTabla();

        System.out.println("\n=============================================");
        System.out.println("     2. PRUEBAS DIRECCIONAMIENTO ABIERTO ");
        System.out.println("=============================================");
        TablaHashDireccionamientoAbierto dir = new TablaHashDireccionamientoAbierto(7);

        dir.insertar(10, "Luis");
        dir.insertar(24, "Maria");
        dir.insertar(31, "Carlos");

        System.out.println("Estado inicial con Sondeo Lineal:");
        dir.imprimirTabla();

        System.out.println("\nEliminando llave 24:");
        dir.eliminar(24);
        dir.imprimirTabla();

        System.out.println("\nBúsqueda de 31 a través de la marca DELETED:");
        System.out.println("buscar(31) -> " + dir.buscar(31));
    }
}