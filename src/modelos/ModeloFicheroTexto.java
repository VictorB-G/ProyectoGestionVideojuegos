package modelos;
import excepciones.NoExisteVideojuegoException;
import excepciones.VideojuegoDuplicadoException;
import java.io.*;
import java.time.Year;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ModeloFicheroTexto implements IModelo{
    File ficheroCompleto;
    File ficheroTemporal;
    StringTokenizer st;
    
    public ModeloFicheroTexto(){
        ficheroCompleto = new File("./ficheroObjeto.txt");
        ficheroTemporal = new File("./ficheroObjetos.txt");       
    }
    
    @Override
    public boolean añadirVideojuego(Videojuego v) throws Exception{
        boolean repetido = false;
        String aux;
        BufferedReader br = null;
        BufferedWriter bw = null;        
        try {
            ficheroCompleto.createNewFile();
            ficheroTemporal.createNewFile();
            bw = new BufferedWriter(new FileWriter(ficheroTemporal));
            br = new BufferedReader(new FileReader(ficheroCompleto));
            //Leemos todo un fichero y lo escribimos en el otro
            while (true){
                aux=br.readLine();
                st = new StringTokenizer(aux, "#");
                //Si el id se repite ponemos un boolean a true
                if (v.getIdJuego()==Integer.parseInt(st.nextToken())){
                    repetido = true;
                }
                bw.write(aux);
                bw.newLine();
            }           
        } catch (NullPointerException ex){
            //NullPointer indica que ha leido hasta el final, si el videojuego
            //esta repetido, salta una excepción por lo que no se añade al final
            try {
                if (repetido){
                    throw new VideojuegoDuplicadoException("El videojuego ya existe.");
                }
                bw.write(v.toString());
                bw.newLine();
            } catch (IOException ex1) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }        
        } catch (IOException ex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        }finally{
            try {
                bw.close();
                br.close();
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return true;
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) throws Exception{
        boolean existe = false;
        String aux;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            ficheroTemporal.createNewFile();
            bw = new BufferedWriter(new FileWriter(ficheroTemporal));
            br = new BufferedReader(new FileReader(ficheroCompleto));
            //Recorrremos todo el fichero secuencialmente
            while (true) {                
                aux = br.readLine();
                st = new StringTokenizer(aux, "#");
                //Mientras el id no coincida los copiamnos
                if (idJuego!=Integer.parseInt(st.nextToken())) {
                    bw.write(aux);
                    bw.newLine();
                }else{
                    existe=true;
                }
            }
        }catch (NullPointerException ex){
            //Cuando llegue al final, si no existe lanzamos una excepcion
            if (!existe) {
                throw new NoExisteVideojuegoException("No existe el videojuego que intentas eliminar.");
            }
        }catch(IOException ex){
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        }finally{
            try {
                bw.close();
                br.close();
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return existe;
    }

    @Override
    public boolean modificarVideojuego(int idJuego, Videojuego juego) throws Exception{
        boolean existe = false;
        String aux;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            ficheroTemporal.createNewFile();
            bw = new BufferedWriter(new FileWriter(ficheroTemporal));
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {                
                aux = br.readLine();
                st = new StringTokenizer(aux, "#");
                //Si coincide el id, escribimos el nuevo, si no, escrinimos lo antiguo
                if (idJuego==Integer.parseInt(st.nextToken())) {
                    existe=true;
                    bw.write(juego.toString());
                    bw.newLine();
                }else{
                    bw.write(aux);
                    bw.newLine();
                }
            } 
        }catch (NullPointerException ex){
            //Cuando llegue al final, si no existe lanzamos una excepcion
            if (!existe) {
                throw new NoExisteVideojuegoException("No existe el videojuego que intentas modificar.");
            }
        }catch (IOException ex){
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        }finally{
            try {
                bw.close();
                br.close();
                ficheroCompleto.delete();
                ficheroTemporal.renameTo(ficheroCompleto);
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return existe;
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws Exception{
        boolean existe = false;
        String aux=null;
        Videojuego auxV;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {                
                aux = br.readLine();
                st = new StringTokenizer(aux, "#");
                //Comprobamos si existe y dejamos de leer el fichero
                if (idJuego==Integer.parseInt(st.nextToken())) {
                    existe=true;
                    break;
                }
            }
        }catch (NullPointerException ex){

        }catch(IOException ex){
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        }finally{
            try {
                //Si no existe lanzamos excepción y si si enviamos el videojuego
                br.close();
                if (!existe) {
                    throw new NoExisteVideojuegoException("No existe el videojuego.");
                }
                auxV=convertirAVideojuego(aux);
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws Exception {
        boolean existe = false;
        String aux=null;
        Videojuego auxV;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {                
                aux = br.readLine();
                st = new StringTokenizer(aux, "#");
                st.nextToken();
                //Comprobamos si existe y dejamos de leer el fichero
                if (nombreJuego.equals(st.nextToken())) {
                    existe=true;
                    break;
                }
            }
        }catch (NullPointerException ex){

        }catch(IOException ex){
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        }finally{
            try {
                br.close();
                //Si no existe lanzamos excepción y si no enviamos el videojuego
                if (!existe) {
                    throw new NoExisteVideojuegoException("No existe el videojuego.");
                }
                auxV=convertirAVideojuego(aux);     
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }

    @Override
    public Videojuego primerVideojuego() throws Exception{
        String aux=null;
        Videojuego auxV;
        BufferedReader br = null;
        if (ficheroCompleto.length()==0){
            return null;
        }
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            aux=br.readLine();
            auxV=convertirAVideojuego(aux);  
        } catch (IOException ex) {
            throw new IOException("Ha fallado algo al al leer los datos del fichero.");
        }finally{
            try {
                auxV=convertirAVideojuego(aux);
                br.close();
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }

    @Override
    public Videojuego ultimoVideojuego() throws Exception{
        String aux=null, auxAnterior=null;
        Videojuego auxV;
        BufferedReader br = null;
        if (ficheroCompleto.length()==0){
            return null;
        }
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {
                auxAnterior=aux;
                aux=br.readLine();
                st = new StringTokenizer(aux, "#");
            }
        } catch (NullPointerException ex){
            auxV = convertirAVideojuego(auxAnterior);
        } catch (IOException ex){
            throw new IOException("Ha fallado algo al al leer los datos del fichero.");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) throws Exception{
        String aux=null, auxAnterior=null;
        Videojuego auxV;
        BufferedReader br = null;
        if (v==null){
            return null;
        }
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {
                auxAnterior=aux;
                aux=br.readLine();
                st = new StringTokenizer(aux, "#");
                if (v.getIdJuego()==Integer.parseInt(st.nextToken())) {
                    throw new NullPointerException();
                }
            }
        } catch (NullPointerException ex){
            if (auxAnterior==null) {
                auxAnterior=aux;   
            }
            auxV = convertirAVideojuego(auxAnterior);
        } catch (IOException ex) {
            throw new IOException("Ha fallado algo al al leer los datos del fichero.");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) throws Exception{
        String aux=null;
        Videojuego auxV = null;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(ficheroCompleto));
            while (true) {
                aux=br.readLine();
                st = new StringTokenizer(aux, "#");
                if (v.getIdJuego()==Integer.parseInt(st.nextToken())) {
                    aux=br.readLine();
                    aux.length();
                    break;
                }
            }
        } catch (NullPointerException ex){
            
        } catch (IOException ex) {
            throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
        } finally {
            try {
                if (aux==null){
                    auxV = v;
                } else {
                    auxV = convertirAVideojuego(aux);
                }
                br.close();
            } catch (IOException ex) {
                throw new IOException("Ha fallado algo al al almacenar los datos en el fichero.");
            }
        }
        return auxV;
    }
    
    private Videojuego convertirAVideojuego (String aux){
        Videojuego auxV = new Videojuego();
        st = new StringTokenizer(aux,"#");
        auxV.setIdJuego(Integer.parseInt(st.nextToken()));
        auxV.setTitulo(st.nextToken());
        auxV.setGenero(st.nextToken());
        auxV.setDesarrollador(st.nextToken());
        auxV.setMultijugador(clases.Enum.Multijugador.valueOf(st.nextToken()));
        auxV.setConsola(clases.Enum.Consola.valueOf(st.nextToken()));
        auxV.setPegi(st.nextToken());
        auxV.setDescripcion(st.nextToken());
        auxV.setAño(Year.of(Integer.parseInt(st.nextToken())));
        auxV.setStock(Boolean.parseBoolean(st.nextToken()));
        auxV.setPrecio(Double.parseDouble(st.nextToken()));
        
        return auxV;
   }
}