/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject3.dao;

import com.mycompany.mavenproject3.db.DatabaseConfig;
import com.mycompany.mavenproject3.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() throws SQLException {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (
                Connection con = DatabaseConfig.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );

                lista.add(p);
            }
        }

        return lista;
    }

    public int insertar(Producto producto) throws SQLException {

        String sql = "INSERT INTO productos(nombre, precio, stock) VALUES (?, ?, ?)";

        try (
                Connection con = DatabaseConfig.getConexion();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    public boolean actualizar(int id, Producto producto) throws SQLException {

        String sql = "UPDATE productos SET nombre=?, precio=?, stock=? WHERE id=?";

        try (
                Connection con = DatabaseConfig.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setInt(4, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {

        String sql = "DELETE FROM productos WHERE id=?";

        try (
                Connection con = DatabaseConfig.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}
