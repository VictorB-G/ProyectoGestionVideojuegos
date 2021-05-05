package modelos;
import excepciones.NoExisteVideojuegoException;
import java.util.*;
public class ModeloMapa implements IModelo{
    private TreeMap<Integer,Videojuego> videojuegos;

    public ModeloMapa() {
        videojuegos=new TreeMap();
    }
    
    @Override
    public boolean añadirVideojuego(Videojuego v) throws Exception {
        if (videojuegos.containsKey(v.getIdJuego())){
            throw new Exception("No se ha podido añadir el videojuego.");
        }else{
            videojuegos.put(v.getIdJuego(), v);
            return true;
        }
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) throws NoExisteVideojuegoException {
        if (videojuegos.containsKey(idJuego)) {
            videojuegos.remove(idJuego);
            return true;
        }else{
            throw new NoExisteVideojuegoException("No existe el videojuego");
        }
    }

    @Override
    public boolean modificarVideojuego(int idJuego, Videojuego juego) throws NoExisteVideojuegoException {
        if (videojuegos.containsKey(idJuego)) {
            videojuegos.replace(idJuego, juego);
            return true;
        }else{
            throw new NoExisteVideojuegoException("No existe el videojuego");
        }
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws NoExisteVideojuegoException {
        Videojuego aux;
        if (videojuegos.containsKey(idJuego)) {
            aux=videojuegos.get(idJuego);
            return aux;
        }else{
            throw new NoExisteVideojuegoException("No existe el videojuego");
        }
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws NoExisteVideojuegoException {
        Videojuego aux=null;
        Set set;
        set = videojuegos.keySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            aux = videojuegos.get(it.next());
            if (aux.getTitulo().equals(nombreJuego)){
                return aux;
            }else{
                aux=null;
            }
        }
        if (aux==null){
            throw new NoExisteVideojuegoException("No existe el videojuego.");
        }
        return null;
    }

    @Override
    public Videojuego primerVideojuego() throws NoExisteVideojuegoException {
        Videojuego aux;
        try{
            aux=videojuegos.get(videojuegos.firstKey());
            return aux;
        }catch (NoSuchElementException ex){
            throw new NoExisteVideojuegoException("No hay ningun videojuego.");
        } 
    }

    @Override
    public Videojuego ultimoVideojuego() throws NoExisteVideojuegoException {
        Videojuego aux;
        try{
            aux=videojuegos.get(videojuegos.lastKey());
            return aux;
        }catch (NoSuchElementException ex){
            throw new NoExisteVideojuegoException("No hay ningun videojuego.");
        }
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) {
        Videojuego aux = null, auxAnterior;
        Set set;
        set = videojuegos.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            auxAnterior = aux;
            aux = videojuegos.get(it.next());
            if (aux.equals(v)) {
                if (auxAnterior==null){
                    return v;
                }
                return auxAnterior;
            }
        }
        return v;
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) {
        Videojuego aux;
        Set set;
        set = videojuegos.keySet();
        Iterator  it = set.iterator();
        try {
            if (v==null) {
                return videojuegos.get(videojuegos.firstKey());
            }
            while (it.hasNext()) {
                aux = videojuegos.get(it.next());
                if (aux.equals(v)) {
                    aux = videojuegos.get(it.next());
                    return aux;
                }
            }
        }catch (NoSuchElementException ex){
           return v; 
        }
        return null;  
    }   
}