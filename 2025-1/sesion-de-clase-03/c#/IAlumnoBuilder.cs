public interface IAlumnoBuilder {
	IAlumnoBuilder conTipo(char tipo);
	IAlumnoBuilder conCodigo(int codigo);
	IAlumnoBuilder conNombre(string nombre);
	IAlumnoBuilder conApellido(string apellido);
	IAlumnoBuilder conEdad(int edad);
	IAlumnoBuilder conCiclo(int ciclo);
	IAlumnoBuilder conPromedio(double promedio);
}