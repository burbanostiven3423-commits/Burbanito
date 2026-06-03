/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject3.api;

import com.mycompany.mavenproject3.model.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            ArrayList<Producto> estudiantes = new ArrayList<>();

            estudiantes.add(new Producto("Laptop", 2500.0, 5));
            estudiantes.add(new Producto("Mouse", 50.0, 20));
            estudiantes.add(new Producto("Teclado", 120.0, 0));

            out.print("{");
            out.print("\"mensaje\":\"Listado basico de estudiantes\",");
            out.print("\"total\":" + estudiantes.size() + ",");
            out.print("\"estudiantes\":[");

            for (int i = 0; i < estudiantes.size(); i++) {

                Producto estudiante = estudiantes.get(i);

                out.print("{");
                out.print("\"nombre\":\"" + estudiante.getNombre() + "\",");
                out.print("\"edad\":" + estudiante.getPrecio() + ",");
                out.print("\"nota\":" + estudiante.getCantidad() + ",");

                out.print("}");

                if (i < estudiantes.size() - 1) {
                    out.print(",");
                }
            }

            out.print("]");
            out.print("}");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

}