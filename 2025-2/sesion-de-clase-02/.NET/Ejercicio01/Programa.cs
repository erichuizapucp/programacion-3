using System;

namespace Ejercicio01 {
    public class Programa {
        public static void Main(string[] args) {
            Alumno persona = (Alumno)new Alumno.AlumnoBuilder()
                .ConActividad(TipoActividad.Baile)
                .ConNombre("Juan")
                .ConEdad(22)
                .Build();

            //Console.WriteLine(persona);
            persona.Imprimir();

            IPracticable practicable = (IPracticable)new Alumno.AlumnoBuilder()
                .ConActividad(TipoActividad.Deporte)
                .ConNombre("Ana")
                .ConEdad(21)
                .Build();

            practicable.Practicar();
        }
    }
}
