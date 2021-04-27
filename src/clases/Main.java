package clases;
import modelos.*;
import vistas.*;
public class Main {
    public static void main(String[] args) {
//        VentanaConsola v = new VentanaConsola();
        VentanaSwing v = new VentanaSwing();
//        ModeloArray m = new ModeloArray();
//        ModeloArrayList m = new ModeloArrayList();
//        ModeloMapa m = new ModeloMapa();
//        ModeloFicheroTexto m = new ModeloFicheroTexto();
//        ModeloFicheroObjeto m = new ModeloFicheroObjeto();
        ModeloBD m = new ModeloBD();
        Controlador c = new Controlador(m, v);
        v.setControlador(c);
        v.mostrar();
    }
    
}