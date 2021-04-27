package modelos;
public interface IModelo {
    public abstract boolean añadirVideojuego(Videojuego v) throws Exception;
    public abstract boolean eliminarVideojuego(int idJuego) throws Exception;
    public abstract boolean modificarVideojuego(int idJuego, Videojuego juego) throws Exception;
    public abstract Videojuego consultarVideojuego(int idJuego) throws Exception;
    public abstract Videojuego consultarVideojuego(String nombreJuego) throws Exception;
    public abstract Videojuego primerVideojuego() throws Exception;
    public abstract Videojuego ultimoVideojuego() throws Exception;
    public abstract Videojuego videojuegoAnterior(Videojuego v) throws Exception;
    public abstract Videojuego videojuegoSiguiente(Videojuego v) throws Exception;
}