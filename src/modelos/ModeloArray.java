package modelos;
import excepciones.NoExisteVideojuegoException;
import excepciones.VideojuegoDuplicadoException;
import modelos.IModelo;
import java.util.Arrays;
import java.util.Comparator;
public class ModeloArray implements IModelo{
    private Videojuego[] videojuegos;
    private int juegoActual;

    public ModeloArray() {
        videojuegos = new Videojuego[50];
        juegoActual=0;
    }

    @Override
    public boolean añadirVideojuego(Videojuego v) throws VideojuegoDuplicadoException{
        boolean existe=false;
        for (juegoActual=0; videojuegos[juegoActual]!=null; juegoActual++){
            if (videojuegos[juegoActual].equals(v)) {
                existe=true;
            }
        }
        aumentarTamaño();
        if (v!=null && !existe){
            videojuegos[juegoActual]=v;
            Arrays.sort(videojuegos, Comparator.nullsLast(new VideojuegoNaturalOrder()));
            juegoActual=0;
            while (videojuegos[juegoActual].getIdJuego()!=v.getIdJuego()) {                
                juegoActual++;
            }
            return true;
        } else {
            throw new VideojuegoDuplicadoException("Este videojuego ya esta guardado.");
        }
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) {
        if (videojuegos[juegoActual]!=null) {
            videojuegos[juegoActual]=null;
            Arrays.sort(videojuegos, Comparator.nullsLast(new VideojuegoNaturalOrder()));
            juegoActual=0;
            return true;
        } else {
            return false;
        }   
    }
    
    @Override
    public boolean modificarVideojuego(int idJuego, Videojuego juego) {
        if (videojuegos[juegoActual]!=null) {
            videojuegos[juegoActual]=juego;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws NoExisteVideojuegoException {
        juegoActual=0;
        while (videojuegos[juegoActual]!=null) {
            if (videojuegos[juegoActual].getIdJuego()==idJuego) {
                return videojuegos[juegoActual];
            }
            juegoActual++;
        }
        throw new NoExisteVideojuegoException("No existe ese videojuego.");
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws NoExisteVideojuegoException {
        juegoActual=0;
        while (videojuegos[juegoActual]!=null) {
            if (videojuegos[juegoActual].getTitulo().equals(nombreJuego)) {
                return videojuegos[juegoActual];
            }
            juegoActual++;
        }
        throw new NoExisteVideojuegoException("No existe ese videojuego.");
    }

    @Override
    public Videojuego primerVideojuego() {
        juegoActual=0;
        return videojuegos[0];
    }

    @Override
    public Videojuego ultimoVideojuego() {
        for (juegoActual=0; videojuegos[juegoActual]!=null; juegoActual++){}
        if (juegoActual!=0){
            juegoActual--;
            return videojuegos[juegoActual];
        }
            
        return null;
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) throws Exception{
        juegoActual--;
        if (juegoActual>-1){
            return videojuegos[juegoActual];
        }else{
            juegoActual=0;
            return videojuegos[0];
        }
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) throws Exception{
        juegoActual++;
        if (videojuegos[juegoActual]==null) {
            juegoActual--;
            return videojuegos[juegoActual];
        }else{
           return videojuegos[juegoActual]; 
        }
    }
    
    private void aumentarTamaño(){
        if (juegoActual==videojuegos.length-1) {
            Videojuego[] aux = Arrays.copyOf(videojuegos, juegoActual+30);
            videojuegos=aux;
        }
    }  
}