# SoftProg - Proyecto Maven Multi-Módulo

## Estructura de módulos

```
SoftProg (parent)
├── SoftProgModelo
├── SoftProgDBManager
├── SoftProgPersistencia  → depende de: SoftProgModelo, SoftProgDBManager
└── SoftProgTestApp       → depende de: SoftProgPersistencia
```

---

## Comandos de compilación

> Todos los comandos deben ejecutarse desde la raíz del proyecto (`SoftProg/`)

---

### Compilar un módulo específico (sin dependencias)

```bash
# Solo SoftProgModelo
mvn clean package -pl SoftProgModelo

# Solo SoftProgDBManager
mvn clean package -pl SoftProgDBManager

# Solo SoftProgPersistencia
mvn clean package -pl SoftProgPersistencia

# Solo SoftProgApp
mvn clean package -pl SoftProgApp
```

---

### Compilar un módulo junto con sus dependencias (recursivo con `-am`)

El flag `-am` (also-make) compila primero todos los módulos de los que depende el módulo objetivo.

```bash
# SoftProgModelo (no tiene dependencias internas)
mvn clean package -pl SoftProgModelo -am

# SoftProgDBManager (no tiene dependencias internas)
mvn clean package -pl SoftProgDBManager -am

# SoftProgPersistencia + SoftProgModelo + SoftProgDBManager
mvn clean package -pl SoftProgPersistencia -am

# SoftProgApp + todos sus módulos dependientes
mvn clean package -pl SoftProgApp -am
```

---

### Compilar todos los módulos (desde el proyecto principal)

```bash
# Compila todos los módulos en orden
mvn package

# Limpiar y compilar todos los módulos
mvn clean package
```

---

### Instalar todos los módulos en el repositorio local Maven (`.m2`)

Necesario cuando los módulos no se encuentran aún en el repositorio local.

```bash
# Instalar todos los módulos
mvn install

# Instalar un módulo y sus dependencias (recursivo)
mvn install -pl SoftProgTestApp -am

# Instalar saltando los tests
mvn install -DskipTests

# Limpiar, compilar e instalar sin tests
mvn clean install -DskipTests
```

---

### Referencia de flags Maven

| Flag | Descripción |
|------|-------------|
| `-pl <módulo>` | Apunta a un módulo específico |
| `-am` | También compila los módulos de los que depende (`--also-make`) |
| `-amd` | También compila los módulos que dependen del módulo objetivo (`--also-make-dependents`) |
| `-DskipTests` | Omite la ejecución de tests |
| `clean` | Elimina la carpeta `target/` antes de compilar |

