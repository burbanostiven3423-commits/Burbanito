/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.model;

public class Producto {

    private int id;
    private String nombre;
    private double precio;

    // cantidad y stock apuntan al mismo valor
    private int cantidad;

    public Producto() {
    }

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(int id, String nombre, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // ===== ID =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ===== NOMBRE =====
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ===== PRECIO =====
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // ===== CANTIDAD =====
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // ===== STOCK =====
    // Compatibilidad con el nuevo servlet

    public int getStock() {
        return cantidad;
    }

    public void setStock(int stock) {
        this.cantidad = stock;
    }

    @Override
    public String toString() {

        return "{"
                + "\"id\":" + id + ","
                + "\"nombre\":\"" + nombre + "\","
                + "\"precio\":" + precio + ","
                + "\"cantidad\":" + cantidad
                + "}";
    }
}