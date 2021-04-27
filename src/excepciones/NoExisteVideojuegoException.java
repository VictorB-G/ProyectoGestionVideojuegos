package excepciones;
public class NoExisteVideojuegoException extends Exception{
    public NoExisteVideojuegoException(String message){
        super(message);
    }
}
