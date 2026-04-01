# Instrucciones para compilar y ejecutar el proyecto

Este documento proporciona una guía paso a paso para compilar, ejecutar y empaquetar el proyecto de seguro vehicular. Asegúrate de seguir las instrucciones en el orden indicado para evitar errores.

## Compilar los paquetes

1. **Compilar el paquete `modelo`**:
   Este paquete contiene las clases base del modelo, como `Cotizable`, `Vehiculo` y `Marca`. Estas clases son fundamentales para el funcionamiento del proyecto.

   ```
   javac -cp . pe/edu/segurosoft/modelo/*.java
   ```

2. **Compilar el paquete `seguros`**:
   Este paquete contiene las clases relacionadas con los diferentes tipos de seguros, como `SeguroBasico`, `SeguroBronce`, `SeguroPlata` y `SeguroOro`.

   ```
   javac -cp . pe/edu/segurosoft/seguros/*.java
   ```

3. **Compilar el paquete `coberturas`**:
   Este paquete incluye las clases que implementan las coberturas adicionales, como `CoberturaAsistenciaVial`, `CoberturaContraRobos` y `CoberturaContraDesastresNaturales`.

   ```
   javac -cp . pe/edu/segurosoft/coberturas/*.java
   ```

4. **Compilar el archivo principal `Program.java`**:
   Este archivo contiene el punto de entrada principal del programa.

   ```
   javac -cp . pe/edu/segurosoft/Program.java
   ```

## Ejecutar el programa

Ejecutar el archivo principal directamente desde las clases compiladas:

```
java -cp . pe.edu.segurosoft.Program
```

## Crear el archivo JAR

Para empaquetar todos los archivos `.class` excepto `Program.class` en un archivo `seguros.jar`:

```
jar cvf seguros.jar pe/edu/segurosoft/modelo/*.class pe/edu/segurosoft/seguros/*.class pe/edu/segurosoft/coberturas/*.class
```

## Compilar el archivo principal usando el JAR

Después de crear el archivo `seguros.jar`, puedes compilar el archivo principal `Program.java` utilizando el JAR como parte del classpath.

### Para Windows:

```
javac -cp seguros.jar;. pe/edu/segurosoft/Program.java
```

### Para macOS/Linux:

```
javac -cp seguros.jar:. pe/edu/segurosoft/Program.java
```

## Ejecutar el archivo principal usando el JAR

Después de compilar el archivo principal con el JAR, puedes ejecutarlo de la siguiente manera:

### Para Windows:

```
java -cp seguros.jar;. pe/edu/segurosoft/Program
```

### Para macOS/Linux:

```
java -cp seguros.jar:. pe/edu/segurosoft/Program
```

## Crear un archivo JAR ejecutable

Para crear un archivo JAR ejecutable que incluya un archivo `MANIFEST.MF` con la configuración adecuada:

```
jar cvfm segurosoft.jar MANIFEST.MF pe/edu/segurosoft/Program.class
```

## Ejecutar el archivo JAR ejecutable

Finalmente, puedes ejecutar el archivo JAR ejecutable con el siguiente comando:

```
java -jar segurosoft.jar
```
