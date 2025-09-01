using System;

namespace Ejercicio01 {
    public class Alumno : Persona, IPracticable {
        public TipoActividad Actividad { get; set; }

        public Alumno() : base() {
            this.Actividad = TipoActividad.Ninguna;
            Console.WriteLine("Constructor: Alumno");
        }

        Alumno(AlumnoBuilder builder) : base(builder) {
            this.Actividad = builder.Actividad;
        }

        public override string ToString() {
            return string.Format("[Alumno] Nombre: {0}, Edad: {1}, Actividad: {2}", this.Nombre, this.Edad, this.Actividad);
        }

        public override void Imprimir() {
            Console.WriteLine(this); // Llama al metodo ToString()
        }

        public void Practicar() {
            Console.WriteLine("El alumno está practicando: " + this.Actividad);
        }

        public class AlumnoBuilder : Persona.PersonaBuilder {
            public TipoActividad Actividad { get; private set; }

            public AlumnoBuilder ConActividad(TipoActividad actividad) {
                this.Actividad = actividad;
                return this;
            }

            public override Persona Build() {
                return new Alumno(this);
            }
        }
    }
}
