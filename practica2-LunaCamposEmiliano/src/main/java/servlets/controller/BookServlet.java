/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets.controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.dao.BookDAO;
import servlets.model.Book;

/**
 *
 * @author emicosmic
 */
@WebServlet("/books")
public class BookServlet extends HttpServlet {
    
    private  BookDAO bookDAO;
    
    @Override
    public void init(){
        bookDAO=new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException{
        
        String searchQuery = request.getParameter("search");
         List<Book> books;
    
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            books = bookDAO.buscarBooks(searchQuery);
        } else {
            books = bookDAO.getAllBooks();
        }

        request.setAttribute("libreria", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("libreria.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException{
          try {
            String title = request.getParameter("titulo");
            String author = request.getParameter("autor");
            String priceStr = request.getParameter("precio");
            
            double price = Double.parseDouble(priceStr);
            Book newBook = new Book(title.trim(), author.trim(), price);            
            boolean success = bookDAO.addBook(newBook);
            
            if (success) {
                response.sendRedirect("books?added=true");
            } else {
                response.sendRedirect("books?error=no_guardado");
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir precio: " + e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
