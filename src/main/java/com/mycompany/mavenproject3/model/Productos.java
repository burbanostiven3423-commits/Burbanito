/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.model;

/**
 *
 * @author USUARIO
 */
public class Productos {
    
     private String nombre;
    private int cantidad;
    private double precio;

    public Productos(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    Productos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    // Estado del producto según el precio
    public String getEstado() {
        if (precio >= 1000) {
            return "Producto costoso";
        }
        return "Producto económico";
    }

    public String getResumen() {
        return "Nombre: " + nombre + " | Cantidad: " + cantidad + " | Precio: " + precio;
    }
}
