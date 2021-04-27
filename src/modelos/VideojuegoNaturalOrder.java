package modelos;
import java.util.Comparator;
public class VideojuegoNaturalOrder implements Comparator<Videojuego>{
    @Override
    public int compare(Videojuego arg0, Videojuego arg1) {
        return arg0.getIdJuego()-arg1.getIdJuego();
    }
    
}
