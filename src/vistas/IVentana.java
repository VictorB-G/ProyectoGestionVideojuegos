package vistas;
import clases.Controlador;
import modelos.Videojuego;
public interface IVentana {
    public abstract void setControlador(Controlador c);
    public abstract void mostrar(); //Visualice la GUI
    public abstract Videojuego getVideojuego();
    public abstract void setVideojuego(Videojuego juego);
    public abstract int getAccion();
    public abstract int getBusquedaId();
    public abstract String getBusquedaTitulo();
}
