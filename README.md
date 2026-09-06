# Práctica 1

## Implementación de una Tabla Hash

> ### Integrantes del Equipo:
> * García Herrera Valeria
> * Grajeda Palacios Dulce Abril
> * Pérez Megchun Pablo de Jesús

---

> ### Breve descripción de la práctica:
>> Esta práctica consiste en la implementación de una estructura de datos de **Tabla Hash** en Java. Se incluye la versión principal orientada al manejo de colisiones mediante **Encadenamiento**, así como el reto opcional utilizando **Direccionamiento Abierto con Sondeo Lineal**.
> 
> Ambas implementaciones permiten almacenar pares key-value, resolver colisiones de manera eficiente y gestionar operaciones fundamentales mediante las siguientes funciones:
> * (`insertar`):** Agrega elementos o actualiza el valor si la clave ya existe.
> * (`buscar`):** Localiza un elemento por su clave, retornando su valor correspondiente o un indicador en caso de no encontrarlo.
> * (`eliminar`):** Remueve la clave de la tabla (usando una marca explícita `DELETED` en la versión de direccionamiento abierto).
> * (`factorCarga`):** Calcula la relación entre el número de elementos y el tamaño total de la tabla **($\alpha = \frac{n}{m}$)**.

---

### 1. Lenguaje utilizado:
Utilizamos Java.

---

### 2. Instrucciones para ejecutar el programa:

* Clonar el repositorio remoto e ingresar a la carpeta del proyecto:

   `git clone [https://github.com/valeriagh-star/Practica-1.git](https://github.com/valeriagh-star/Practica-1.git)`

   `cd Practica-1`

* Compilar el archivo directamente desde la raíz del proyecto:
   `javac TablaHash.java`

* Ejecutar la clase principal:
   `java TablaHash`

---

### 3. Explicación de cómo ejecutar los casos de prueba:
Las pruebas unitarias y funcionales vienen integradas en el método `main` de `TablaHash.java`. Al ejecutar `java TablaHash`, se corren automáticamente:
* **Pruebas de Encadenamiento:** Inserta llaves colisionadas (10, 24, 31 en una tabla de tamaño 7), valida búsquedas e interrupciones y prueba la eliminación sobre listas enlazadas.

* **Pruebas de Direccionamiento Abierto con Sondeo Lineal:** Prueba el desplazamiento secuencial tras colisiones, elimina datos usando la marca `DELETED` y comprueba que las búsquedas posteriores continúen sobre las celdas marcadas.

---

### 4. Explicación de la función Hash:
Se utilizó la función de división convencional:

**$$h(k) = |k| \bmod m$$**

Donde $k$ es la clave entera y $m$ es el tamaño de la tabla. Para el **sondeo lineal** (reto opcional), la función extiende el desplazamiento secuencial mediante:

**$$h(k, i) = (h(k) + i) \bmod m$$**

Donde $i$ representa el número de intento o colisión **($i = 0, 1, 2, \dots, m-1$)**.

---

### 5. Explicación del manejo de colisiones:
* **Encadenamiento:** Cada posición de la tabla contiene una lista enlazada. Al haber una colisión, el nuevo nodo se enlaza al final de la lista correspondiente a ese índice. El **encadenamiento** resuelve las colisiones permitiendo varios elementos en
una misma posición mediante una colección.

* **Direccionamiento Abierto:** Si la posición original está ocupada, busca circularmente la primera posición libre o marcada como `DELETED` a la derecha. El **direccionamiento abierto** resuelve las colisiones buscando otra posición
disponible dentro de la tabla. En este método, la posición inicial **h(k)** y la posición final del
elemento pueden ser diferentes.

---

### 6. ¿Por qué tener una colisión no significa que la tabla hash esté implementada incorrectamente?
Una colisión no indica que una tabla hash esté mal implementada, ya que es normal que diferentes claves produzcan el mismo índice debido al tamaño limitado de la tabla. Lo importante es que la implementación utilice correctamente un método para resolver las colisiones y permita almacenar y recuperar todos los elementos sin pérdida de información.

---

### 7. ¿Qué ocurre cuando dos llaves producen el mismo hash o varias caen en la misma cubeta?
* **En encadenamiento:** Las claves que producen el mismo índice se almacenan en la misma cubeta, agregándose consecutivamente a una **lista enlazada**. De esta manera, pueden coexistir varios elementos en un mismo índice.

* **En direccionamiento abierto:** La primera clave ocupa el índice obtenido por la función hash. Si ese índice ya está ocupado, las siguientes claves se colocan en los **índices consecutivos** hasta encontrar una posición disponible.

---

### 8. Factor de carga final obtenido durante las pruebas:
El factor de carga **($\alpha = \frac{n}{m}$)** obtenido en las pruebas fue:
* **Prueba de Encadenamiento ($n=4, m=7$):** $\alpha = \frac{4}{7} \approx 0.57$ (57.14%)

* **Prueba de Sondeo Lineal ($n=3, m=7$):** $\alpha = \frac{3}{7} \approx 0.42$ (42.85%)

Ambos valores aseguran un rendimiento eficiente de tiempo promedio **$\mathcal{O}(1)$**.
