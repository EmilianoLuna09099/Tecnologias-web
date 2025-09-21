/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import servlets.model.Book;

/**
 *
 * @author emicosmic
 */
public class BookDAO {        
    private final String jdbcURL="jdbc:mysql://localhost:3306/bookdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String jdbcUsername="root";
    private final String jdbcPassword="arodar09";
    
    private static final String SELECT_ALL = "SELECT * FROM books";
    private static final String INSERT_BOOK = "INSERT INTO books (titulo, autor, price) VALUES (?, ?, ?)";
    
    protected Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");            
            Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            if (conn != null && !conn.isClosed()) {
                System.out.println("La conexión está activa y válida");
                return conn;
            } else {
                throw new SQLException("La conexión es nula o está cerrada");
            }
            
        } catch(ClassNotFoundException e){
            throw new SQLException("Error al cargar el driver MySQL", e);
            
        } catch(SQLException e) {
            throw e;
        }
    }
    
    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();
        try (Connection conn = getConnection(); 
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL)){
           
            ResultSet rs = ps.executeQuery();
            
            int count = 0;
            while (rs.next()){ 
                count++;
                String title = rs.getString("titulo");
                String author = rs.getString("autor");
                double price = rs.getDouble("price");
                books.add(new Book(title, author, price));
                System.out.println("Libro " + count + ": " + title + " - " + author + " - $" + price);
            }
            System.out.println("Total de libros obtenidos: " + count);
            
        }catch (SQLException e){
            System.err.println("ERROR al obtener libros: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }
    
    public boolean addBook(Book book) throws SQLException {
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_BOOK)){ 
            ps.setString(1, book.getNombre());
            ps.setString(2, book.getAutor());
            ps.setDouble(3, book.getPrecio());
            
            int rowsAffected = ps.executeUpdate(); 
            if (rowsAffected > 0) {
                System.out.println("¡Libro insertado correctamente!");
                return true;
            } else {
                System.err.println("ADVERTENCIA: No se insertaron filas");
                return false;
            }
            
        } catch(SQLException e) {
            throw e;
        }
    }
    
    public List<Book> buscarBooks(String query) {  
        List<Book> books = new ArrayList<>();
        String SEARCH_QUERY = "SELECT * FROM books WHERE titulo LIKE ? OR autor LIKE ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(SEARCH_QUERY)) {
            
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            
            int count = 0;
            while (rs.next()){ 
                count++;
                String title = rs.getString("titulo");
                String author = rs.getString("autor");
                double price = rs.getDouble("price");
                books.add(new Book(title, author, price));
                System.out.println("Encontrado " + count + ": " + title + " - " + author + " - $" + price);
            }
            System.out.println("Total de libros encontrados: " + count);
            
        } catch (SQLException e) {
            System.err.println("ERROR al buscar libros: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }
    
}
