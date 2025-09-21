<%-- 
    Document   : libreria
    Created on : 14 sept 2025, 20:54:17
    Author     : emicosmic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="servlets.model.Book"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión de Libros</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f5f5f5;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h1, h2 {
                color: #333;
            }
            .form-section, .list-section {
                margin-bottom: 30px;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            input[type="text"], input[type="number"] {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }
            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-right: 10px;
            }
            button:hover {
                background-color: #45a049;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #f5f5f5;
            }
            .search-form {
                display: flex;
                margin-bottom: 20px;
            }
            .search-form input {
                flex: 1;
                margin-right: 10px;
            }
            .message {
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 4px;
            }
            .success {
                background-color: #dff0d8;
                color: #3c763d;
                border: 1px solid #d6e9c6;
            }
        </style>
    </head>
    
    <body>
         <div class="container">
            <h1>Sistema de Gestión de Libros</h1>
            <% if (request.getParameter("added") != null) { %>
    <div class="message success">
        Libro añadido correctamente.
    </div>
<% } %>

   
            <!-- Sección para añadir libros -->
            <div class="form-section">
                <h2>Añadir Nuevo Libro</h2>
                <form action="books" method="POST">
                    <div class="form-group">
                        <label for="titulo">Título:</label>
                        <input type="text" id="titulo" name="titulo" required>
                    </div>
                    <div class="form-group">
                        <label for="autor">Autor:</label>
                        <input type="text" id="autor" name="autor" required>
                    </div>
                    <div class="form-group">
                        <label for="precio">Precio:</label>
                        <input type="number" id="precio" name="precio" step="0.01" min="0" required>
                    </div>
                    <button type="submit">Añadir Libro</button>
                </form>
            </div>
            
            <!-- Sección para buscar libros -->
            <div class="search-section">
                <h2>Buscar Libros</h2>
                <form action="books" method="GET" class="search-form">
                    <input type="text" name="search" placeholder="Buscar por título o autor..." 
                           value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
                    <button type="submit">Buscar</button>
                    <button type="button" onclick="window.location.href='books'">Ver Todos</button>
                </form>
            </div>
            
            <!-- Sección para listar libros -->
            <div class="list-section">
                <h2>Lista de Libros</h2>
                <%
                    List<Book> books = (List<Book>) request.getAttribute("libreria");
                    if (books != null && !books.isEmpty()) {
                %>
                    <table>
                        <thead>
                            <tr>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>Precio</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Book book : books) { %>
                                <tr>
                                    <td><%= book.getNombre() %></td>
                                    <td><%= book.getAutor() %></td>
                                    <td>$<%= String.format("%.2f", book.getPrecio()) %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p>No se encontraron libros.</p>
                <% } %>
            </div>
        </div>
    </body>
</html>
