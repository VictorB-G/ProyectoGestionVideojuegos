package modelos;
import clases.Enum.*;
import java.io.Serializable;
import java.time.Year;
public class Videojuego implements Comparable<Object>, Serializable{
    private int idJuego;
    private double precio;
    private String titulo, genero, desarrollador, pegi, descripcion;
    private Multijugador multijugador;
    private boolean stock;
    private Consola consola;
    private Year año;
    
    public Videojuego(){}
    
    @Override
    public int compareTo(Object o) {
        Videojuego aux = (Videojuego)o;  
        return getIdJuego()-aux.getIdJuego();
    }

    @Override
    public boolean equals(Object o) {
        Videojuego aux=(Videojuego)o;
        return getIdJuego()==aux.getIdJuego();
    }
    
    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }
    
    public int getIdJuego() {
        return idJuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public Multijugador getMultijugador() {
        return multijugador;
    }

    public void setMultijugador(Multijugador multijugador) {
        this.multijugador = multijugador;
    }

    public Consola getConsola() {
        return consola;
    }

    public void setConsola(Consola consola) {
        this.consola = consola;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Year getAño() {
        return año;
    }

    public void setAño(Year año) {
        this.año = año;
    }
    
    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        return "#"+idJuego+"#"+titulo+"#"+genero+"#"+desarrollador+"#"+multijugador+"#"+
           consola.name()+"#"+pegi+"#"+descripcion+"#"+año.getValue()+"#"+stock+"#"+precio;
    }
}