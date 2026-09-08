package pe.edu.pucp.softprog.modelo.almacen;

public class Producto {
    private int id;
    private boolean activo;
    private String nombre;
    private UnidadMedida unidadMedida;
    private double precio;

    public Producto() {
    }

    public Producto(final Producto producto) {
        if  (producto == null) {
            throw new IllegalArgumentException("producto no puede ser nulo");
        }
        setId(producto.getId());
        setActivo(producto.isActivo());
        setNombre(producto.getNombre());
        setUnidadMedida(producto.getUnidadMedida());
        setPrecio(producto.getPrecio());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id no puede ser negativo");
        }
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        if (unidadMedida == null) {
            throw new IllegalArgumentException("unidadMedida no puede ser nulo");
        }
        this.unidadMedida = unidadMedida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("precio no puede ser negativo");
        }
        this.precio = precio;
    }
}
