package modelos;
import excepciones.NoExisteVideojuegoException;
import excepciones.VideojuegoDuplicadoException;
import java.io.*;
public class ModeloFicheroObjeto implements IModelo{
    File ficheroCompleto;
    File ficheroTemporal;
    
    public ModeloFicheroObjeto(){
        ficheroCompleto = new File("./ficheroObjeto.dat");
        ficheroTemporal = new File("./ficheroObjetos.tmp");
    }
    
    @Override
    public boolean añadirVideojuego(Videojuego v) throws Exception{
        boolean repetido=false;
        Videojuego aux;
        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;
        try { 
            ficheroCompleto.createNewFile();
            ficheroTemporal.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(ficheroTemporal));
            if (ficheroCompleto.length()!=0){
                ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));  
                while (true){
                    aux=(Videojuego)ois.readObject();
                    if(aux.equals(v)){
                        repetido=true;
                    }
                    oos.writeObject(aux);
                }
            } 
        } catch (EOFException eofex) {
            if (repetido) {
                throw new VideojuegoDuplicadoException("El videojuego ya existe.");
            }
            oos.writeObject(v);
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        }finally{
            try {
                oos.close();
                if (ois!=null){
                    ois.close();
                }
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);
                repetido = true;
            } catch (IOException ioexWr) {
                throw new IOException("No se han podido escribir los objetos en el fichero.");
            } 
        }
        return repetido;
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) throws Exception {
        boolean existe = false;
        Videojuego aux;
        ObjectInputStream ois= null;
        ObjectOutputStream oos = null;    
        try {  
            ficheroTemporal.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(ficheroTemporal));
            ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
            while (true){
                aux=(Videojuego)ois.readObject();
                if (aux.getIdJuego()!=idJuego) {
                   oos.writeObject(aux); 
                }else{
                   existe=true; 
                }
            }
        } catch (EOFException eofex){
            try {
                oos.close();
                ois.close();
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        }
        if (!existe) {
            throw new NoExisteVideojuegoException("No existe el videojuego");
        }
        return existe;
    }

    @Override
    public boolean modificarVideojuego(int idJuego, Videojuego juego) throws Exception {
        boolean sinFallos=false, existe=false;
        Videojuego aux;
        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;
        
        try {
            ficheroTemporal.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(ficheroTemporal));
            ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
            while (true){
                aux=(Videojuego)ois.readObject();
                if(aux.getIdJuego()==idJuego){
                    oos.writeObject(juego);
                    existe=true;
                } else{
                   oos.writeObject(aux); 
                } 
            }
        } catch (EOFException eofex) {
            
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es, pero me pide que lo capture, algo ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es."); 
        }finally{
            try {
                oos.close();
                ois.close();
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);  
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
        sinFallos = true;
        if (!existe){
            throw new NoExisteVideojuegoException("No existe el videojuego");
        }
        return sinFallos;
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws Exception{
        Videojuego aux=null;
        ObjectInputStream ois =null;
        boolean existe=false;
        try {
            ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
            while (true){
                aux=(Videojuego)ois.readObject();
                if (aux.getIdJuego()==idJuego) {
                    existe = true;
                    break;
                }
            }
        } catch (EOFException eofex){
            
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } finally {
            try {
                ois.close();
                if (!existe){ 
                    throw new NoExisteVideojuegoException("No se ha encontrado ningun videojuego con ese id");
                }
                return aux;
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws Exception {
        Videojuego aux=null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
        boolean existe = false;        
        try {            
            while (true){
                aux=(Videojuego)ois.readObject();
                if (aux.getTitulo().equals(nombreJuego)) {
                   break;
                }
            }
        } catch (EOFException eofex){
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        } finally {
            try {
                ois.close();
                if (!existe){ 
                    throw new NoExisteVideojuegoException("No se ha encontrado ningun videojuego con ese titulo");
                }
                return aux;
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
    }

    @Override
    public Videojuego primerVideojuego() throws Exception{
        Videojuego aux=null;
        ObjectInputStream ois = null;      
        try {
            if (ficheroCompleto.length()!=0){
                ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
                aux=(Videojuego)ois.readObject();
            }
        }catch (EOFException eofex){
        }catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        } finally {
            try {
                if (ois!=null)
                    ois.close();  
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
        return aux;
    }

    @Override
    public Videojuego ultimoVideojuego() throws Exception{
        Videojuego aux=null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));;      
        try {           
            while (true){
                aux=(Videojuego)ois.readObject();
            }
        } catch (EOFException eofex){
            
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        } finally {
            try {
                ois.close();
                return aux;
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) throws Exception {
        Videojuego aux=null, auxAnterior=null;
        ObjectInputStream ois=null;    
        try {
            if (ficheroCompleto.length()!=0){
                ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
                while (true){
                    auxAnterior=aux;
                    aux=(Videojuego)ois.readObject();
                    if (aux.equals(v)) {
                        break;
                    }
                }
            }      
        } catch (EOFException eofex){
            aux=null;
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        } finally {
            try {
                if (ois!=null){
                    ois.close();
                } 
                if (auxAnterior!=null){
                    return auxAnterior;
                }else{
                    return aux;
                }    
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) throws Exception{
        Videojuego aux=null;
        ObjectInputStream ois = null;         
        try {
            if (ficheroCompleto.length()!=0){
                ois = new ObjectInputStream(new FileInputStream(ficheroCompleto));
                while (true){
                    aux=(Videojuego)ois.readObject();
                    if (aux.equals(v)) {
                        aux=(Videojuego)ois.readObject();
                        break; 
                    }
                }
            }
        } catch (EOFException eofex){
            aux=null;
        } catch (IOException ioex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } catch (ClassNotFoundException cnfex) {
            throw new ClassCastException("No se que error es pero ha fallado.");
        } catch (Exception cnfex) {
            throw new Exception("Se ha producido un error que vete tu a saber cual es.");
        } finally {
            try {
                if (ois!=null){
                    ois.close();
                }
                if (aux!=null){
                    return aux;
                }else{
                    return v;
                }
            } catch (IOException ioexCl) {
                throw new IOException("Ha fallado al cerrar los ficheros de almacenamiento.");
            }
        }        
    }   
}
