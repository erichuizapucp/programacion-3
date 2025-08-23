using System;
using System.Runtime.InteropServices;

namespace Ejercicio01
{
    public abstract class Persona {
        public Persona() {
            Console.WriteLine("Persona: Constructor");
        }

        protected Persona(PersonaBuilder builder) {
            Nombre = builder.Nombre;
            Edad = builder.Edad;
        }

        public string Nombre { get; set; }
        public int Edad { get; set; }

        //private string nombre;

        //public string Nombre { 
        //    get { 
        //        return nombre;
        //    }
        //    set { 
        //        nombre = value;
        //    }
        //}

        public override string ToString() {
            return string.Format("Nombre: {0}, Edad: {1}", this.Nombre, this.Edad);
        }

        //public virtual void Imprimir() {
        //    Console.WriteLine("Nombre: {0}, Edad: {1}",
        //        this.Nombre, this.Edad);
        //}

        public abstract void Imprimir();

        ~Persona() {
            Console.WriteLine("Se destruye el objeto.");
        }

        public abstract class PersonaBuilder {
            public string Nombre { get; protected set; }
            public int Edad { get; protected set; }

            public virtual PersonaBuilder ConNombre(string nombre) {
                this.Nombre = nombre;
                return this;
            }

            public virtual PersonaBuilder ConEdad(int edad) { 
                this.Edad = edad;
                return this;
            }

            public abstract Persona Build();
        }
    }
}
