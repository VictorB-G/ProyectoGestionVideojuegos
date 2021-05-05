package vistas;
import clases.Controlador;
import clases.Enum;
import java.time.Year;
import modelos.Videojuego;
import java.util.Scanner;
public class VentanaConsola implements IVentana{
    private Controlador controller;
    private Videojuego videojuegoAnt, videojuego;
    private Scanner sc;
    private int accion, busquedaId, eliminarId;
    private String busquedaTitulo;
    private boolean continuar;
    
    public VentanaConsola(){
        sc = new Scanner(System.in);
        continuar = true;
    }
    
    @Override
    public void mostrar() {
        System.out.println("Aplicación de videojuegos iniciada\n\n");
        while (continuar) {            
            menuAcciones(); 
        }   
    }
    
    private void menuAcciones(){
        System.out.println("Selecciona que quieres hacer");
        System.out.println("(0) - Añadir un nuevo videojuego");
        System.out.println("(1) - Modificar un videojuego");
        System.out.println("(2) - Eliminar un videojuego");
        System.out.println("(3) - Buscar un videojuego por su id");
        System.out.println("(4) - Buscar un videojuego por su titulo");
        System.out.println("(5) - Mostrar el primer videojuego");
        System.out.println("(6) - Mostrar el videojuego anterior");
        System.out.println("(7) - Mostrar el videojuego siguiente");
        System.out.println("(8) - Mostrar el ultimo videojuego");
        System.out.println("(9) - Salir de la aplicacion"); 
        try {
            try {
                accion = sc.nextInt();
                ejecutarAccion();
            } catch (NumberFormatException ex){ 
                throw new NumberFormatException("Los tipos de datos que has introducido incorrectos.");
            }
        } catch (Exception ex){
            sc.nextLine();
            System.out.println(ex.getMessage());
        }
    }
    
    private void ejecutarAccion() throws Exception{
        switch (accion){
            case 0:
                crearObjetoVideojuego();
                controller.notificacion();
                break;
            case 1:
                System.out.println("Introduce el id del juego a modificar");
                busquedaId = Integer.parseInt(sc.next());
                accion = 3;
                controller.notificacion();
                accion = 1;
                crearObjetoVideojuego();
                controller.notificacion();
                break;
            case 2:
                eliminarVideojuego();
                controller.notificacion();
                break;
            case 3:
                buscarPorID();
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 4:
                buscarPorTitulo();
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 5:
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 6:
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 7:
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 8:
                controller.notificacion();
                mostrarVideojuego();
                break;
            case 9:
                continuar = false;
                break;
        }
    }
    @Override
    public void setControlador(Controlador c) {
        controller = c;
    }

    @Override
    public Videojuego getVideojuego() {
        return videojuego;
    }

    @Override
    public void setVideojuego(Videojuego juego) {
        videojuego = juego;
    }

    @Override
    public int getAccion() {
        return accion;
    }

    @Override
    public int getBusquedaId() {
        return busquedaId;
    }

    @Override
    public String getBusquedaTitulo() {
        return busquedaTitulo;
    }
    
    private void crearObjetoVideojuego() throws Exception{
        try {
            videojuegoAnt = videojuego;
            videojuego = new Videojuego();
            if (accion==1) {
                videojuego.setIdJuego(busquedaId);
            }else{
                System.out.print("Introduce el ID del juego: ");
                videojuego.setIdJuego(sc.nextInt());
            }
                System.out.print("Introduce el titulo del juego: ");
                videojuego.setTitulo(sc.next());
                System.out.print("Introduce el genero del juego: ");
                videojuego.setGenero(sc.next());
                System.out.print("Introduce la consola del juego: ");
                videojuego.setConsola(elegirConsola());
                System.out.print("Introduce el Desarrollador del juego: ");
                videojuego.setDesarrollador(sc.next());
                System.out.print("Introduce el PEGI del juego: ");
                videojuego.setPegi(elegirPegi());
                System.out.print("Introduce el tipo de multijugador del juego: ");
                videojuego.setMultijugador(elegirMultijugador());
                System.out.print("Introduce el año del juego: ");
                videojuego.setAño(Year.of(sc.nextInt()));
                System.out.print("Introduce la descripción del juego: ");
                videojuego.setDescripcion(sc.next());
                System.out.print("Introduce el precio del juego: ");
                videojuego.setPrecio(sc.nextDouble());
                videojuego.setStock(elegirStock());
                System.out.println("\n");
        } catch (Exception ex) {
            throw new Exception("Has introducido algun dato invalido, recuerda que:\n"
                    + "  El ID debe ser un numero entero.  \n"
                    + "  El año debes ser un numero entero de 4 cifras.  \n"
                    + "  El PEGI y el tipo de multijugador son campos OBLIGATORIOS.");
        }
    }
    
    private Enum.Consola elegirConsola() {
        int valor;
        mostrarConsolas();
        valor=sc.nextInt();
        switch (valor){
            case 0:
                return Enum.Consola.valueOf("NINTENDO_SWITCH");
            case 1:
                return Enum.Consola.valueOf("PLAYSTATION_5");              
            case 2:
                return Enum.Consola.valueOf("XBOX_SERIES_X"); 
            case 3:
                return Enum.Consola.valueOf("NINTENDO_WII_U");
            case 4:
                return Enum.Consola.valueOf("NINTENDO_3DS");
            case 5:
                return Enum.Consola.valueOf("PLAYSTATION_4");
            case 6:
                return Enum.Consola.valueOf("XBOX_ONE");
            case 7:
                return Enum.Consola.valueOf("NINTENDO_WII");
            case 8:
                return Enum.Consola.valueOf("NINTENDO_DS");
            case 9:
                return Enum.Consola.valueOf("PLAYSTATION_3");
            case 10:
                return Enum.Consola.valueOf("XBOX_360");
            default:
                return Enum.Consola.valueOf("NINTENDO_SWITCH");
        }
    }

    private void mostrarConsolas() {
        System.out.println("\n(0) - Nintendo Switch\n(1) - PlayStation 5\n(2) - Xbox Series X\n"
                + "(3) - Nintendo Wii U\n(4) - Nintendo 3DS\n(5) - PlayStation 4\n"
                + "(6) - Xbox One\n(7) - Nintendo Wii\n(8) - Nintendo DS");
    }

    private String elegirPegi() {
        int valor;
        mostrarPegis();
        valor=sc.nextInt();
        switch (valor){
            case 0:
                return "PEGI 3";
            case 1:
                return "PEGI 7";
            case 2:
                return "PEGI 12";
            case 3:
                return "PEGI 16";
            case 4:
                return "PEGI 18";
            default:
                return "PEGI 3";
        }
    }

    private void mostrarPegis() {
        System.out.println("\n(0) - PEGI 3\n(1) - PEGI 7\n(2) - PEGI 12\n(3) - PEGI 16"
                + "\n(4) - PEGI 18");
    }

    private Enum.Multijugador elegirMultijugador() {
        int valor;
        mostrarMultijugador();
        valor=sc.nextInt();
        switch (valor){
            case 0:
                return Enum.Multijugador.valueOf("ONLINE");
            case 1:
                return Enum.Multijugador.valueOf("LOCAL");
            case 2:
                return Enum.Multijugador.valueOf("NO");
            default:
                return Enum.Multijugador.valueOf("NO");
        }
    }

    private void mostrarMultijugador() {
        System.out.println("\n(0) - ONLINE\n(1) - LOCAL\n(2) - NO");
    }

    private boolean elegirStock() {
        System.out.println("\n¿Hay stock? (S/N)");
        if (sc.next().equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    private void eliminarVideojuego() {
        videojuegoAnt = videojuego;
        System.out.print("Introduce el id del videojuego que quieras eliminar: ");
        eliminarId=Integer.parseInt(sc.next());
        videojuego = new Videojuego();
        videojuego.setIdJuego(eliminarId);
    }

    private void buscarPorID() {
        System.out.print("\nIntroduce el ID del videojuego que quieres mostrar: ");
        busquedaId=Integer.parseInt(sc.next());
    }
    
    private void buscarPorTitulo(){
        System.out.print("\nIntroduce el titulo del videojuego que quieres mostrar: ");
        busquedaTitulo=sc.nextLine();
    }
    
    private void mostrarVideojuego(){
        System.out.println("ID: "+videojuego.getIdJuego());
        System.out.println("Titulo: "+videojuego.getTitulo());
        System.out.println("Genero: "+videojuego.getGenero());
        System.out.println("Consola: "+videojuego.getConsola().toString());
        System.out.println("Desarrollador :"+videojuego.getDesarrollador());
        System.out.println("Pegi: "+videojuego.getPegi());
        System.out.println("Multijugador :"+videojuego.getMultijugador());
        System.out.println("Año: "+videojuego.getAño().toString());
        System.out.println("Descripción: "+videojuego.getDescripcion());
        System.out.println("Precio: "+videojuego.getPrecio());
        System.out.println("¿Hay stock?: "+videojuego.isStock());
        System.out.println("\n");
        
    }
}