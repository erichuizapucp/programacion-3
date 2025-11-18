using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sesion_de_clase_24_net
{
    class Identificador
    {
        private static int id = 0;
        private static readonly Object lockObj = new Object();

        public static void Incrementar() {
            lock (lockObj)
            {
                id++;
                Console.WriteLine(id);
            }

        }
        public static int ObtenerId() {
            lock (lockObj)
            {
                return id;
            }
        }
    }
}
