package tablaHash;

public class TablaHash {

    // Nodo para la lista enlazada propia (Manejo de colisiones por encadenamiento).
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

    private int m; // Número de cubetas (m = 7).
    private int n; // Cantidad de elementos almacenados.
    private Nodo[] tabla;

    public TablaHash(int tamano) {
        this.m = tamano;
        this.n = 0;
        this.tabla = new Nodo[m];
    }

    public TablaHash() {
        this(7);
    }

    // Función de dispersión: h(k) = k mod m
    public int hash(int key) {
        return Math.abs(key) % m;
    }

    // Insertar un nuevo elemento o actualizar si la llave existe.
    public void insertar(int key, String value) {
        int posicion = hash(key);
        Nodo actual = tabla[posicion];

        // 1. Actualización de llave repetida.
        while (actual != null) {
            if (actual.key == key) {
                actual.value = value;
                return;
            }
            actual = actual.siguiente;
        }

        // 2. Si no existe, insertar al final de la lista de la cubeta.
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

    // Buscar el valor asociado a una llave.
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

    // Eliminar únicamente el elemento correspondiente.
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

    // Factor de carga: α = n / m
    public double factorCarga() {
        return (double) n / m;
    }

    // Visualizar la tabla hash completa.
    public void imprimirTabla() {
        for (int i = 0; i < m; i++) {
            StringBuilder sb = new StringBuilder();
            Nodo actual = tabla[i];

            while (actual != null) {
                if (sb.length() > 0) {
                    sb.append(" -> ");
                }
                sb.append("(").append(actual.key).append(", ").append(actual.value).append(")");
                actual = actual.siguiente;
            }

            System.out.println(i + " -> " + sb.toString());
        }
    }

    // Batería de Pruebas Obligatorias.
    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("  VERIFICACIÓN DE CASOS DE PRUEBA OBLIGATORIOS");
        System.out.println("================================================");

        // Prueba 1: Tabla vacía
        System.out.println("\n--- Prueba 1: Búsqueda en tabla vacía ---");
        TablaHash htPruebas = new TablaHash();
        System.out.println("buscar(10) -> " + htPruebas.buscar(10)); // NOT_FOUND

        // Prueba 2: Inserción básica y búsqueda
        System.out.println("\n--- Prueba 2: Inserción básica ---");
        htPruebas.insertar(18, "Ana");
        htPruebas.insertar(10, "Luis");
        htPruebas.insertar(23, "Elena");
        System.out.println("buscar(18) -> " + htPruebas.buscar(18)); // Ana
        System.out.println("buscar(10) -> " + htPruebas.buscar(10)); // Luis
        System.out.println("buscar(23) -> " + htPruebas.buscar(23)); // Elena

        // Prueba 3: Colisiones
        System.out.println("\n--- Prueba 3: Manejo de colisiones ---");
        htPruebas.insertar(24, "Maria");
        htPruebas.insertar(31, "Carlos");
        System.out.println("buscar(10) -> " + htPruebas.buscar(10)); // Luis
        System.out.println("buscar(24) -> " + htPruebas.buscar(24)); // Maria
        System.out.println("buscar(31) -> " + htPruebas.buscar(31)); // Carlos

        // Prueba 4: Eliminación con colisión
        System.out.println("\n--- Prueba 4: Eliminación con colisión ---");
        htPruebas.eliminar(24);
        System.out.println("buscar(24) -> " + htPruebas.buscar(24)); // NOT_FOUND
        System.out.println("buscar(10) -> " + htPruebas.buscar(10)); // Luis
        System.out.println("buscar(31) -> " + htPruebas.buscar(31)); // Carlos

        // Prueba 5: Llave inexistente
        System.out.println("\n--- Prueba 5: Eliminar llave inexistente ---");
        boolean resultadoEliminar = htPruebas.eliminar(999);
        System.out.println("eliminar(999) realizado sin errores. ¿Eliminado?: " + resultadoEliminar);

        // Prueba 6: Actualización de llave existente
        System.out.println("\n--- Prueba 6: Actualización de llave existente ---");
        htPruebas.insertar(18, "Ana Maria");
        System.out.println("buscar(18) -> " + htPruebas.buscar(18)); // Ana Maria

        System.out.println("\n============================");
        System.out.println("  EJECUCIÓN FINAL");
        System.out.println("==============================");
        
        TablaHash htFinal = new TablaHash();
        htFinal.insertar(18, "Ana");
        htFinal.insertar(10, "Luis");
        htFinal.insertar(24, "Maria");
        htFinal.insertar(31, "Carlos");

        System.out.println("\nEstado de la tabla:");
        htFinal.imprimirTabla();

        System.out.println("\nBúsquedas:");
        System.out.println("buscar(24) -> " + htFinal.buscar(24));
        System.out.println("buscar(99) -> " + htFinal.buscar(99));

        System.out.println("\nEjecutando: eliminar(24)");
        htFinal.eliminar(24);

        System.out.println("\nEstado de la tabla tras eliminar:");
        htFinal.imprimirTabla();

        System.out.println("\nBúsquedas tras eliminar:");
        System.out.println("buscar(24) -> " + htFinal.buscar(24));
        System.out.println("buscar(31) -> " + htFinal.buscar(31));
        
        System.out.printf("\nFactor de Carga Final (α): %.3f\n", htFinal.factorCarga());
    }
}