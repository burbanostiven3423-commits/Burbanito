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

@WebServlet(name = "ProductoApiServlet", urlPatterns = {"/api/productos"})
public class ProductoApiServlet extends HttpServlet {

    private static final ArrayList<Producto> productos =
            new ArrayList<>();

    static {

        productos.add(new Producto("Laptop", 2500.0, 5));
        productos.add(new Producto("Mouse", 50.0, 20));
        productos.add(new Producto("Teclado", 120.0, 10));
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder respuesta = new StringBuilder();

        respuesta.append("{");
        respuesta.append("\"mensaje\":\"Listado basico de productos\",");
        respuesta.append("\"total\":")
                .append(productos.size())
                .append(",");
        respuesta.append("\"productos\":[");

        for (int i = 0; i < productos.size(); i++) {

            if (i > 0) {
                respuesta.append(",");
            }

            respuesta.append(productos.get(i).toString());
        }

        respuesta.append("]");
        respuesta.append("}");

        try (PrintWriter out = response.getWriter()) {

            out.print(respuesta.toString());
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String nombre =
                request.getParameter("nombre");

        String precioTexto =
                request.getParameter("precio");

        String cantidadTexto =
                request.getParameter("cantidad");

        if (nombre == null || nombre.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {

                out.print(
                    "{\"mensaje\":\"El nombre es obligatorio\"}"
                );
            }

            return;
        }

        for (Producto p : productos) {

            if (p.getNombre()
                    .equalsIgnoreCase(nombre.trim())) {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST);

                try (PrintWriter out =
                             response.getWriter()) {

                    out.print(
                        "{\"mensaje\":\"El producto ya existe\"}"
                    );
                }

                return;
            }
        }

        try {

            double precio =
                    Double.parseDouble(precioTexto);

            int cantidad =
                    Integer.parseInt(cantidadTexto);

            Producto producto =
                    new Producto(
                            nombre.trim(),
                            precio,
                            cantidad
                    );

            productos.add(producto);

            StringBuilder respuesta =
                    new StringBuilder();

            respuesta.append("{");
            respuesta.append(
                    "\"mensaje\":\"Producto guardado correctamente\","
            );
            respuesta.append("\"producto\":");
            respuesta.append(producto.toString());
            respuesta.append("}");

            try (PrintWriter out =
                         response.getWriter()) {

                out.print(respuesta.toString());
            }

        } catch (NumberFormatException ex) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out =
                         response.getWriter()) {

                out.print(
                    "{\"mensaje\":\"Precio y cantidad deben ser numericos\"}"
                );
            }
        }
    }

    @Override
    protected void doPut(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String nombre =
                request.getParameter("nombre");

        String precioTexto =
                request.getParameter("precio");

        String cantidadTexto =
                request.getParameter("cantidad");

        try {

            double precio =
                    Double.parseDouble(precioTexto);

            int cantidad =
                    Integer.parseInt(cantidadTexto);

            for (Producto p : productos) {

                if (p.getNombre()
                        .equalsIgnoreCase(nombre.trim())) {

                    p.setPrecio(precio);
                    p.setCantidad(cantidad);

                    try (PrintWriter out =
                                 response.getWriter()) {

                        out.print(
                            "{\"mensaje\":\"Producto actualizado\"}"
                        );
                    }

                    return;
                }
            }

            response.setStatus(
                    HttpServletResponse.SC_NOT_FOUND);

            try (PrintWriter out =
                         response.getWriter()) {

                out.print(
                    "{\"mensaje\":\"Producto no encontrado\"}"
                );
            }

        } catch (NumberFormatException ex) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out =
                         response.getWriter()) {

                out.print(
                    "{\"mensaje\":\"Datos invalidos\"}"
                );
            }
        }
    }

    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String nombre =
                request.getParameter("nombre");

        for (int i = 0; i < productos.size(); i++) {

            Producto p = productos.get(i);

            if (p.getNombre()
                    .equalsIgnoreCase(nombre.trim())) {

                productos.remove(i);

                try (PrintWriter out =
                             response.getWriter()) {

                    out.print(
                        "{\"mensaje\":\"Producto eliminado\"}"
                    );
                }

                return;
            }
        }

        response.setStatus(
                HttpServletResponse.SC_NOT_FOUND);

        try (PrintWriter out =
                     response.getWriter()) {

            out.print(
                "{\"mensaje\":\"Producto no encontrado\"}"
            );
        }
    }
}