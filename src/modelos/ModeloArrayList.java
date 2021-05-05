package modelos;
import excepciones.NoExisteVideojuegoException;
import excepciones.VideojuegoDuplicadoException;
import modelos.IModelo;
import java.util.*;
public class ModeloArrayList implements IModelo{
    private ArrayList<Videojuego> listaVideojuegos;
    private int juegoActual;
    
    public ModeloArrayList(){
        listaVideojuegos = new ArrayList<>();
        juegoActual=0;
    }
    
    @Override
    public boolean añadirVideojuego(Videojuego v) throws Exception{
        if (v!=null && !listaVideojuegos.contains(v)){
            listaVideojuegos.add(v);
            Collections.sort(listaVideojuegos);
            return true;
        } else {
            throw new VideojuegoDuplicadoException("Este videojuego ya esta guardado.");
        }
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) {
        if (listaVideojuegos.get(juegoActual)!=null && listaVideojuegos.get(juegoActual).getIdJuego()==idJuego) {
            listaVideojuegos.remove(juegoActual);
            juegoActual=0;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean modificarVideojuego(int codigoJuego, Videojuego nuevo) {
        if (listaVideojuegos.get(juegoActual)!=null) {
            listaVideojuegos.remove(juegoActual);
            listaVideojuegos.add(juegoActual, nuevo);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws NoExisteVideojuegoException {
        juegoActual=0;
        Iterator<Videojuego> it = listaVideojuegos.iterator();
        while (it.hasNext()) {
            if (it.next().getIdJuego()==idJuego) {
                return listaVideojuegos.get(juegoActual);
            }
            juegoActual++;
        }
        throw new NoExisteVideojuegoException("No existe ese videojuego.");
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws NoExisteVideojuegoException {
        juegoActual=0;
        Iterator<Videojuego> it = listaVideojuegos.iterator();
        while (it.hasNext()) {
            if (it.next().getTitulo().equals(nombreJuego)) {               
                return  listaVideojuegos.get(juegoActual);
            }
            juegoActual++;
        }
        throw new NoExisteVideojuegoException("No existe ese videojuego.");
    }

    @Override
    public Videojuego primerVideojuego() {
        juegoActual=0;
        if (listaVideojuegos.size()==0){
            return null;
        }else{
            return listaVideojuegos.get(juegoActual);
        }   
    }

    @Override
    public Videojuego ultimoVideojuego() {
        if (listaVideojuegos.size()==0){
            return null;
        } else {
            juegoActual=listaVideojuegos.size()-1;
            return listaVideojuegos.get(juegoActual);
        }
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) {
        juegoActual--;
        if (listaVideojuegos.size()==0){
            return null;
        } else if (juegoActual>-1) {
            return listaVideojuegos.get(juegoActual);
        }else{
            juegoActual=0;
            return listaVideojuegos.get(0);
        }
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) {
        juegoActual++;
        if (listaVideojuegos.size()==0){
            return null;
        } else if (juegoActual<listaVideojuegos.size()) {
            return listaVideojuegos.get(juegoActual);
        }else{
            juegoActual= listaVideojuegos.size()-1;
            return listaVideojuegos.get(listaVideojuegos.size()-1);
        }      
    }    
}