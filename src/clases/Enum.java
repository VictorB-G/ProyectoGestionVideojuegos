package clases;
import java.io.Serializable;
public class Enum implements Serializable{
    public enum Consola implements Serializable{
        NINTENDO_SWITCH, PLAYSTATION_5, XBOX_SERIES_X, 
        NINTENDO_WII_U, NINTENDO_3DS, PLAYSTATION_4, XBOX_ONE,
        NINTENDO_WII, NINTENDO_DS, PLAYSTATION_3, XBOX_360;
        
        
        //Creo que es un poco chapuza esta forma de mostrarlo de forma amigable en el programa
        //REVISAR ATRIBUTOS CLASE ENUM Y CAMBIARLO SI ES OPORTUNO
        @Override
        public String toString(){
            switch (this){
                case NINTENDO_SWITCH: return "Nintendo Switch";
                case NINTENDO_WII_U: return "Nintendo Wii U";
                case NINTENDO_3DS: return "Nintendo 3DS";
                case NINTENDO_DS: return "Nintendo DS";
                case NINTENDO_WII: return "Nintendo WII";
                case PLAYSTATION_5: return "PlayStation 5";
                case PLAYSTATION_4: return "PlayStation 4";
                case PLAYSTATION_3: return "PlayStation 3";
                case XBOX_SERIES_X: return "Xbox Series X";
                case XBOX_ONE: return "Xbox One";
                case XBOX_360: return "Xbox 360";
            }
            return "";
        }
    }
    
    public enum Multijugador implements Serializable{
        ONLINE, LOCAL, NO;
    }
    
    public enum Pegi implements Serializable{
        PEGI3, PEGI7, PEGI12, PEGI16, PEGI18;
        
        @Override
        public String toString(){
            switch (this){
                case PEGI3: return "Pegi 3";
                case PEGI7: return "Pegi 7";
                case PEGI12: return "Pegi 12";
                case PEGI16: return "Pegi 16";
                case PEGI18: return "Pegi 18";                        
            }
            return "";
        }
    }
}
