using System.Threading;

namespace Ejercicio3;

public class ConsumidorID
{
    public void Run()
    {
        string nombreConsumidor = Thread.CurrentThread.Name ?? "Consumidor";
        Identificador.ImprimirIdActualConsumido(nombreConsumidor);
    }
}
