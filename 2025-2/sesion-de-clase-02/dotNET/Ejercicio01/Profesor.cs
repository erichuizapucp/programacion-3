using System;

namespace Ejercicio01 {
    public class Profesor : Persona {
        public Profesor() {
            Console.WriteLine("Profesor: Constructor");
        }

        Profesor(ProfesorBuilder builder) : base(builder) { 
        }

        public override string ToString() {
            return string.Format("[Profesor] Nombre: {0}, Edad: {1}", this.Nombre, this.Edad);
        }

        public override void Imprimir() {
            Console.WriteLine(this);
        }

        public class ProfesorBuilder : PersonaBuilder {
            public override Persona Build() {
                return new Profesor(this);
            }
        }
    }
}
