public interface IAlumnoRegularBuilder : IAlumnoBuilder {
	IAlumnoRegularBuilder conAnnoIngreso(int annoIngreso);
	AlumnoRegular build();
}