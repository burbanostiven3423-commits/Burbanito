package com.mycompany.mavenproject3.api;

import com.mycompany.mavenproject3.dao.ProductoDAO;
import com.mycompany.mavenproject3.model.Producto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util
        .List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API REST de productos usando MySQL
 */
@WebServlet(name = "ProductoBDApiServlet", urlPatterns = {"/api/productos-bd"})
public class ProductoBDApiServlet extends HttpServlet {

    // Conexion con el DAO
    private final ProductoDAO dao = new ProductoDAO();

    /**
     * LISTAR PRODUCTOS
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

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

                out.print("{\"error\":\"Error al consultar productos: "
                        + escaparJson(e.getMessage()) + "\"}");
            }
        }
    }

    /**
     * GUARDAR PRODUCTO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"El nombre es obligatorio\"}");
            }

            return;
        }

        try {

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            Producto nuevo = new Producto(nombre.trim(), precio, stock);

            int idGenerado = dao.insertar(nuevo);

            nuevo.setId(idGenerado);

            try (PrintWriter out = response.getWriter()) {

                out.print("{");
                out.print("\"mensaje\":\"Producto guardado correctamente\",");
                out.print("\"producto\":" + nuevo);
                out.print("}");
            }

        } catch (NumberFormatException ex) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Precio y stock deben ser numericos\"}");
            }

        } catch (SQLException ex) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {

                out.print("{\"error\":\"Error al guardar producto: "
                        + escaparJson(ex.getMessage()) + "\"}");
            }
        }
    }

    /**
     * ACTUALIZAR PRODUCTO
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        try {

            int id = Integer.parseInt(idStr);
            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            if (nombre == null || nombre.trim().isEmpty()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"error\":\"El nombre es obligatorio\"}");
                }

                return;
            }

            Producto actualizado = new Producto(id, nombre.trim(), precio, stock);

            boolean exito = dao.actualizar(id, actualizado);

            if (!exito) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"error\":\"No existe un producto con ese id\"}");
                }

                return;
            }

            try (PrintWriter out = response.getWriter()) {

                out.print("{");
                out.print("\"mensaje\":\"Producto actualizado correctamente\",");
                out.print("\"producto\":" + actualizado);
                out.print("}");
            }

        } catch (NumberFormatException ex) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {

                out.print("{\"error\":\"Id, precio y stock deben ser numericos\"}");
            }

        } catch (SQLException ex) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {

                out.print("{\"error\":\"Error al actualizar producto: "
                        + escaparJson(ex.getMessage()) + "\"}");
            }
        }
    }

    /**
     * ELIMINAR PRODUCTO
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idStr = request.getParameter("id");

        try {

            int id = Integer.parseInt(idStr);

            boolean exito = dao.eliminar(id);

            if (!exito) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"error\":\"No existe un producto con ese id\"}");
                }

                return;
            }

            try (PrintWriter out = response.getWriter()) {

                out.print("{");
                out.print("\"mensaje\":\"Producto eliminado correctamente\",");
                out.print("\"id\":" + id);
                out.print("}");
            }

        } catch (NumberFormatException ex) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {

                out.print("{\"error\":\"El id debe ser numerico\"}");
            }

        } catch (SQLException ex) {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {

                out.print("{\"error\":\"Error al eliminar producto: "
                        + escaparJson(ex.getMessage()) + "\"}");
            }
        }
    }

    /**
     * Escapar caracteres especiales JSON
     */
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