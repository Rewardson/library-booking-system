import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Book {
    String title;
    String author;
    boolean isAvailable;
    int bookCode;

    public Book(String title, String author, boolean isAvailable, int bookCode) {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.bookCode = bookCode;
    }

    @Override
    public String toString() {
        return "Code: " + bookCode + " | " + title + " by " + author + (isAvailable ? " (Available)" : " (Not Available)");
    }
}

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
        // Pre-listing real-life book titles
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", true, 1));
        books.add(new Book("1984", "George Orwell", true, 2));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", true, 3));
        books.add(new Book("Moby Dick", "Herman Melville", true, 4));
        books.add(new Book("Pride and Prejudice", "Jane Austen", true, 5));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", true, 6));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", true, 7));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", true, 8));
        books.add(new Book("Brave New World", "Aldous Huxley", true, 9));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", true, 10));
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.title.toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.author.toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public boolean borrowBook(int bookCode) {
        for (Book book : books) {
            if (book.bookCode == bookCode && book.isAvailable) {
                book.isAvailable = false;
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(int bookCode) {
        for (Book book : books) {
            if (book.bookCode == bookCode && !book.isAvailable) {
                book.isAvailable = true;
                return true;
            }
        }
        return false;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int bookCode) {
        books.removeIf(book -> book.bookCode == bookCode);
    }

    public List<Book> getAllBooks() {
        return books;
    }
}

public class LibrarySystemGUI {
    private static Library library = new Library();
    private static JTextArea displayArea;
    private static JTextField searchField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Library System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout setup
        frame.setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchTitleButton = new JButton("Search by Title");
        JButton searchAuthorButton = new JButton("Search by Author");

        searchPanel.add(new JLabel("Enter Title or Author:"));
        searchPanel.add(searchField);
        searchPanel.add(searchTitleButton);
        searchPanel.add(searchAuthorButton);

        // Display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Action Listeners
        searchTitleButton.addActionListener(e -> searchBooksByTitle());
        searchAuthorButton.addActionListener(e -> searchBooksByAuthor());

        // Adding components to frame
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display all books when the program starts
        displayAllBooks();

        // Set frame visible
        frame.setVisible(true);
    }

    private static void searchBooksByTitle() {
        String title = searchField.getText();
        List<Book> foundBooks = library.searchByTitle(title);
        displayBooks(foundBooks);
    }

    private static void searchBooksByAuthor() {
        String author = searchField.getText();
        List<Book> foundBooks = library.searchByAuthor(author);
        displayBooks(foundBooks);
    }

    private static void displayBooks(List<Book> books) {
        displayArea.setText("");  // Clear previous results
        if (books.isEmpty()) {
            displayArea.append("No books found.\n");
        } else {
            for (Book book : books) {
                displayArea.append(book + "\n");
                // Add buttons to borrow/return books for each entry
                displayArea.append(createBorrowReturnButtons(book));
            }
        }
    }

    private static void displayAllBooks() {
        List<Book> allBooks = library.getAllBooks();
        displayArea.setText("");  // Clear previous results
        if (allBooks.isEmpty()) {
            displayArea.append("No books in the library.\n");
        } else {
            displayArea.append("List of All Books in the Library:\n");
            for (Book book : allBooks) {
                displayArea.append(book + "\n");
                // Add buttons to borrow/return books for each entry
                displayArea.append(createBorrowReturnButtons(book));
            }
        }
    }

    private static String createBorrowReturnButtons(Book book) {
        String borrowReturnButtons = "";
        if (book.isAvailable) {
            borrowReturnButtons = " [Borrow] ";
        } else {
            borrowReturnButtons = " [Return] ";
        }
        return borrowReturnButtons;
    }
}
