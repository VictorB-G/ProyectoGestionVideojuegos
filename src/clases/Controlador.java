package clases;
import modelos.Videojuego;
import modelos.IModelo;
import vistas.IVentana;
public class Controlador {
    private IModelo modelo;
    private IVentana vista;

    public Controlador(IModelo modelo, IVentana vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
        
    public void notificacion() throws Exception{
        Videojuego aux=vista.getVideojuego();
        boolean auxB;
        switch (vista.getAccion()){
            // 0 - Añadir
            case 0:
                auxB = modelo.añadirVideojuego(aux);
                break;
            // 1 - Modificar
            case 1:
                auxB = modelo.modificarVideojuego(aux.getIdJuego(), aux);
                break;
            // 2 - Eliminar    
            case 2:
                auxB = modelo.eliminarVideojuego(aux.getIdJuego());
                break;
            // 3 - Consultar videojuego por indice
            case 3:
                aux = modelo.consultarVideojuego(vista.getBusquedaId());
                break;
            // 4 - Consultar videojuego por titulo
            case 4: 
                aux = modelo.consultarVideojuego(vista.getBusquedaTitulo());
                break;
            // 5 - Ir al primer videojuego
            case 5:
                aux = modelo.primerVideojuego();
                break;
            // 6 - Ir al videojuego anterior
            case 6:
                aux = modelo.videojuegoAnterior(aux);
                break;
            // 7 - Ir al siguiente videojurgo
            case 7:
                aux = modelo.videojuegoSiguiente(aux);
                break;
            // 8 - Ir al ultimo videojuego    
            case 8:
                aux = modelo.ultimoVideojuego();
                break;
        }
        vista.setVideojuego(aux);
    }
}
