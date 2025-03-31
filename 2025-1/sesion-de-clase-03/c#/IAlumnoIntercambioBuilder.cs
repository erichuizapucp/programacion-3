public interface IAlumnoIntercambioBuilder : IAlumnoBuilder {
	IAlumnoIntercambioBuilder conPaisOrigen(string paisOrigen);
    IAlumnoIntercambioBuilder conUniversidadOrigen(string universidadOrigen);
    IAlumnoIntercambioBuilder conDuracion(int duracion);
	
	AlumnoIntercambio build();
}