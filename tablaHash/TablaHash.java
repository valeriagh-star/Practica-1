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
    }
}