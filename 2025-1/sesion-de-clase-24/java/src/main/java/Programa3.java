

/**
 *
 * @author eric
 */
public class Programa3 {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria();
        
        Runnable retirar100 = new Retiro(cuenta, 100.00);
        Runnable retirar70 = new Retiro(cuenta, 70.00);
        Runnable retirar60 = new Retiro(cuenta, 60.00);
        Runnable retirar30 = new Retiro(cuenta, 30.00);
        
        Runnable depositar100 = new Deposito(cuenta, 100);
        Runnable depositar50 = new Deposito(cuenta, 50);
        Runnable depositar300 = new Deposito(cuenta, 300);

        Thread t1 = new Thread(retirar100, "Gerente General");
        Thread t2 = new Thread(retirar70, "Director RRHH");
        Thread t3 = new Thread(retirar70, "Director Ventas");
        Thread t4 = new Thread(retirar30, "Asistente Adminitrativo 1");
        Thread t5 = new Thread(retirar60, "Director Almacen");
        Thread t6 = new Thread(retirar100, "Asistente Administrativo 2");
        Thread t7 = new Thread(retirar100, "Director Sistemas");
        Thread t8 = new Thread(retirar100, "Asistente Administrativo 3");
        Thread t9 = new Thread(retirar100, "Asistente Administrativo 4");
        Thread t10 = new Thread(retirar100, "Asistente Administrativo 5");
        Thread t11 = new Thread(retirar100, "Asistente Administrativo 6");
        Thread t12 = new Thread(retirar100, "Asistente Administrativo 7");
        Thread t13 = new Thread(retirar100, "Asistente Administrativo 8");
        
        Thread t14 = new Thread(depositar50, "Cliente 1");
        Thread t15 = new Thread(depositar100, "Cliente 2");
        Thread t16 = new Thread(depositar300, "Cliente 3");
        Thread t17 = new Thread(depositar100, "Cliente 4");
        Thread t18 = new Thread(depositar50, "Cliente 5");
        Thread t19 = new Thread(depositar50, "Cliente 6");
        Thread t20 = new Thread(depositar50, "Cliente 7");
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        
        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
        t19.start();
        t20.start();
    }
}
