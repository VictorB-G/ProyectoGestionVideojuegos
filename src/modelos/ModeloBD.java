package modelos;
import excepciones.NoExisteVideojuegoException;
import excepciones.VideojuegoDuplicadoException;
import java.sql.*;
import java.time.Year;
public class ModeloBD implements IModelo{
    boolean conexionCorrecta;
    private Connection conexion;
    private String sentenciaSQL;
    private Statement sentencia;
    
    public ModeloBD(){
        try {
            conexion =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/programacion","root","");
            conexionCorrecta=true;
        } catch (SQLException ex) {
            conexionCorrecta=false;
        }
    }
    @Override
    public boolean añadirVideojuego(Videojuego v) throws Exception{
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "INSERT INTO videojuegos VALUES ("+v.getIdJuego()+",'"+v.getTitulo()+
                "','"+v.getGenero()+"','"+v.getConsola().name()+"','"+v.getDesarrollador()+"','"+
                v.getPegi()+"','"+v.getMultijugador().name()+"',"+v.getAño().getValue()+",'"+
                v.getDescripcion()+"',"+v.getPrecio()+","+v.isStock()+");";  
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sentenciaSQL);
            
        } catch (SQLException ex) {
            if (ex.getErrorCode()==1062){
                throw new VideojuegoDuplicadoException("EL videojuego ya se encuentra en la base de datos.");
            }
            throw new SQLException("Ha habido algun problema al intentar añadir los datos a la Base De Datos.");
        }
        
        return true;
    }

    @Override
    public boolean eliminarVideojuego(int idJuego) throws Exception {
        int filas=0;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "DELETE FROM videojuegos WHERE idJuego="+idJuego+";";  
        try {
            sentencia = conexion.createStatement();
            filas = sentencia.executeUpdate(sentenciaSQL);  
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar eliminar los datos de la Base De Datos.");
        }finally{
            if (filas==0){
                throw new NoExisteVideojuegoException("No existe el videojuego que intentas eliminar");
            }
        }    
        return true;
    }

    @Override
    public boolean modificarVideojuego(int idJuego, Videojuego juego) throws Exception {
        int filas=0;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "UPDATE videojuegos SET titulo='"+juego.getTitulo()+"', genero='"+
                juego.getGenero()+"', consola='"+juego.getConsola().name()+"', desarrollador='"+
                juego.getDesarrollador()+"', pegi='"+juego.getPegi()+"',multijugador='"+
                juego.getMultijugador().name()+"', año="+juego.getAño().getValue()+
                ", descripcion='"+juego.getDescripcion()+"', precio="+juego.getPrecio()+
                ", stock="+juego.isStock()+" WHERE idJuego="+idJuego+";";  
        try {
            sentencia = conexion.createStatement();
            filas = sentencia.executeUpdate(sentenciaSQL);  
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }finally{
            if (filas==0){
                throw new NoExisteVideojuegoException("No existe el videojuego que intentas modificar");
            }
        }    
        return true;
    }

    @Override
    public Videojuego consultarVideojuego(int idJuego) throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego v;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "SELECT * FROM videojuegos WHERE idJuego="+idJuego+";";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                v = convertirAVideojuego(rs);
                rs.close();
            }else{
                throw new NoExisteVideojuegoException("No existe ningun videojuego con ese ID.");
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }
        if (v==null){
            throw new NoExisteVideojuegoException("No existe ningun videojuego con ese ID.");
        }
        return v;
    }

    @Override
    public Videojuego consultarVideojuego(String nombreJuego) throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego v=null;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "SELECT * FROM videojuegos WHERE titulo='"+nombreJuego+"';";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                v = convertirAVideojuego(rs);
                rs.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }
        if (v==null){
            throw new NoExisteVideojuegoException("No existe ningun videojuego con ese titulo.");
        }
        return v;
    }

    @Override
    public Videojuego primerVideojuego() throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego v=null;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "SELECT * FROM videojuegos ORDER BY idJuego LIMIT 1;";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                v = convertirAVideojuego(rs);
                rs.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }   
        return v;
    }

    @Override
    public Videojuego ultimoVideojuego() throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego v=null;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        sentenciaSQL = "SELECT * FROM videojuegos ORDER BY idJuego DESC LIMIT 1;";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                v = convertirAVideojuego(rs);
                rs.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }   
        return v;
    }

    @Override
    public Videojuego videojuegoAnterior(Videojuego v) throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego auxV = null;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        if (v==null){
            auxV=ultimoVideojuego();
            return auxV;
        }
        sentenciaSQL = "SELECT * FROM videojuegos WHERE idJuego<"+v.getIdJuego()+" ORDER BY idJuego DESC LIMIT 1;";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                auxV = convertirAVideojuego(rs);
                rs.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }   
        if (datos && auxV!=null){
            return auxV;
        }else{
            return v;
        }  
    }

    @Override
    public Videojuego videojuegoSiguiente(Videojuego v) throws Exception {
        ResultSet rs;
        boolean datos;
        Videojuego auxV = null;
        if (conexionCorrecta){
        } else {
            throw new SQLException("No se ha podido establecer la conexion con la base de datos");
        }
        if (v==null){
            auxV=primerVideojuego();
            return auxV;
        }
        sentenciaSQL = "SELECT * FROM videojuegos WHERE idJuego>"+v.getIdJuego()+" ORDER BY idJuego LIMIT 1;";  
        try {
            sentencia = conexion.createStatement();
            datos = sentencia.execute(sentenciaSQL);  
            if (datos){
                rs = sentencia.getResultSet();
                auxV = convertirAVideojuego(rs);
                rs.close();
            }
        } catch (SQLException ex) {
            throw new SQLException("Ha habido algun problema al intentar modificar los datos de la Base De Datos.");
        }
        if (datos && auxV!=null){
            return auxV;
        }else{
            return v;
        } 
    }

    private Videojuego convertirAVideojuego(ResultSet rs) {
        Videojuego aux = new Videojuego();
        try {
            rs.next();
            aux.setIdJuego(rs.getInt(1));
            aux.setTitulo(rs.getString(2));
            aux.setGenero(rs.getString(3));
            aux.setConsola(clases.Enum.Consola.valueOf(rs.getString(4)));
            aux.setDesarrollador(rs.getString(5));
            aux.setPegi(rs.getString(6));
            aux.setMultijugador(clases.Enum.Multijugador.valueOf(rs.getString(7)));
            aux.setAño(Year.of(rs.getInt(8)));
            aux.setDescripcion(rs.getString(9));
            aux.setPrecio(rs.getDouble(10));
            aux.setStock(rs.getBoolean(11));
        } catch (SQLException ex) {
            return null;
        }
        return aux;
    }
    
}
