package com.mycompany.mavenproject3.api;

import com.mycompany.mavenproject3.dao.ProductoDAO;
import com.mycompany.mavenproject3.model.Producto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductoBDApiServlet", urlPatterns = {"/api/productos-bd"})
public class ProductoBDApiServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    private void configurarHeaders(HttpServletRequest request,
            HttpServletResponse response)
            throws java.io.UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(request, response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(request, response);

        try {

            List<Producto> lista = dao.listar();

            StringBuilder respuesta = new StringBuilder();

            respuesta.append("{");
            respuesta.append("\"mensaje\":\"Listado de productos desde MySQL\",");
            respuesta.append("\"total\":").append(lista.size()).append(",");
            respuesta.append("\"productos\":[");

            for (int i = 0; i < lista.size(); i++) {

                if (i > 0) {
                    respuesta.append(",");
                }

                respuesta.append(lista.get(i));
            }

            respuesta.append("]}");

            try (PrintWriter out = response.getWriter()) {
                out.print(respuesta.toString());
            }

        } catch (SQLException e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + escaparJson(e.getMessage()) + "\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(request, response);

        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        try {

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            Producto nuevo = new Producto(nombre, precio, stock);

            int idGenerado = dao.insertar(nuevo);

            nuevo.setId(idGenerado);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"mensaje\":\"Producto guardado\",\"producto\":" + nuevo + "}");
            }

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + escaparJson(e.getMessage()) + "\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(request, response);

        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Producto producto = new Producto(id, nombre, precio, stock);

            dao.actualizar(id, producto);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"mensaje\":\"Producto actualizado\"}");
            }

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + escaparJson(e.getMessage()) + "\"}");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(request, response);

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            dao.eliminar(id);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"mensaje\":\"Producto eliminado\"}");
            }

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + escaparJson(e.getMessage()) + "\"}");
            }
        }
    }

    private String escaparJson(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ");
    }
}