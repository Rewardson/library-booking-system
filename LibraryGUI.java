import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

class Book {
    private final String code;
    private final String title;
    private final String author;
    private boolean isAvailable;
    private String borrowedBy;
    private LocalDate dueDate;

    public Book(String code, String title, String author) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.dueDate = null;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowedBy() { return borrowedBy; }
    public LocalDate getDueDate() { return dueDate; }

    public void borrowBook(String borrowerName) {
        if (isAvailable) {
            isAvailable = false;
            borrowedBy = borrowerName;
            dueDate = LocalDate.now().plusDays(5); // 5 days borrowing period
        }
    }

    public void returnBook() {
        isAvailable = true;
        borrowedBy = null;
        dueDate = null;
    }

    public long getDaysRemaining() {
        if (dueDate == null) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    @Override
    public String toString() {
        if (isAvailable) {
            return String.format("[%s] %s by %s - Available", code, title, author);
        } else {
            return String.format("[%s] %s by %s - Borrowed by: %s (Due in %d days)", 
                   code, title, author, borrowedBy, getDaysRemaining());
        }
    }
}

class Library {
    private final ArrayList<Book> books;
    private final Map<String, Book> bookCodeMap;

    public Library() {
        books = new ArrayList<>();
        bookCodeMap = new HashMap<>();
        initializeLibrary();
    }

    private void initializeLibrary() {
        addBook("BK001", "1984", "George Orwell");
        addBook("BK002", "A Tale of Two Cities", "Charles Dickens");
        addBook("BK003", "Animal Farm", "George Orwell");
        addBook("BK004", "Brave New World", "Aldous Huxley");
        addBook("BK005", "Crime and Punishment", "Fyodor Dostoevsky");
        addBook("BK006", "Don Quixote", "Miguel de Cervantes");
        addBook("BK007", "Frankenstein", "Mary Shelley");
        addBook("BK008", "Great Expectations", "Charles Dickens");
        addBook("BK009", "Jane Eyre", "Charlotte Brontë");
        addBook("BK010", "Moby Dick", "Herman Melville");
        addBook("BK011", "Pride and Prejudice", "Jane Austen");
        addBook("BK012", "The Catcher in the Rye", "J.D. Salinger");
        addBook("BK013", "The Great Gatsby", "F. Scott Fitzgerald");
        addBook("BK014", "The Hobbit", "J.R.R. Tolkien");
        addBook("BK015", "The Lord of the Rings", "J.R.R. Tolkien");
        addBook("BK016", "The Picture of Dorian Gray", "Oscar Wilde");
        addBook("BK017", "To Kill a Mockingbird", "Harper Lee");
        addBook("BK018", "Ulysses", "James Joyce");
        addBook("BK019", "War and Peace", "Leo Tolstoy");
        addBook("BK020", "Wuthering Heights", "Emily Brontë");
    }

    public void addBook(String code, String title, String author) {
        Book book = new Book(code, title, author);
        books.add(book);
        bookCodeMap.put(code, book);
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> available = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    public ArrayList<Book> getBorrowedBooks() {
        ArrayList<Book> borrowed = new ArrayList<>();
        for (Book book : books) {
            if (!book.isAvailable()) {
                borrowed.add(book);
            }
        }
        return borrowed;
    }

    public Book findBookByCode(String code) {
        return bookCodeMap.get(code);
    }
}

public class LibraryGUI extends JFrame {
    private final Library library;
    private final JTextArea displayArea;
    private JPanel inputPanel;
    private JTextField borrowerField;
    private JTextField bookCodeField;

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(30, 30, 30)); // Dark black
        button.setForeground(new Color(0, 255, 127)); // Neon green
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 127), 1));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50)); // Slightly lighter black
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30)); // Reset to original color
            }
        });

        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(0, 255, 127)); // Neon green
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    public LibraryGUI() {
        library = new Library();

        setTitle("Modern Library System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set modern black theme
        getContentPane().setBackground(new Color(30, 30, 30)); // Dark black

        // Create components
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(30, 30, 30)); // Dark black
        displayArea.setForeground(new Color(0, 255, 127)); // Neon green
        displayArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 127), 1));

        // Create empty input panel (will be populated when needed)
        inputPanel = new JPanel();
        inputPanel.setBackground(new Color(30, 30, 30)); // Dark black
        inputPanel.setVisible(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.setBackground(new Color(30, 30, 30)); // Dark black

        JButton showAllBtn = createStyledButton("Show All Books");
        JButton showAvailableBtn = createStyledButton("Show Available");
        JButton showBorrowedBtn = createStyledButton("Show Borrowed");
        JButton borrowBtn = createStyledButton("Borrow Book");
        JButton returnBtn = createStyledButton("Return Book");
        JButton addBookBtn = createStyledButton("Add New Book");

        // Add components to panels
        buttonPanel.add(showAllBtn);
        buttonPanel.add(showAvailableBtn);
        buttonPanel.add(showBorrowedBtn);
        buttonPanel.add(borrowBtn);
        buttonPanel.add(returnBtn);
        buttonPanel.add(addBookBtn);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add event listeners
        showAllBtn.addActionListener(e -> {
            inputPanel.setVisible(false);
            displayAllBooks();
        });
        showAvailableBtn.addActionListener(e -> {
            inputPanel.setVisible(false);
            displayAvailableBooks();
        });
        showBorrowedBtn.addActionListener(e -> {
            inputPanel.setVisible(false);
            displayBorrowedBooks();
        });
        borrowBtn.addActionListener(e -> showBorrowPanel());
        returnBtn.addActionListener(e -> showReturnPanel());
        addBookBtn.addActionListener(e -> addNewBook());

        // Display all books initially
        displayAllBooks();
    }

    private void showBorrowPanel() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridLayout(2, 1));
        
        JPanel borrowerPanel = new JPanel(new FlowLayout());
        borrowerPanel.setBackground(new Color(30, 30, 30)); // Dark black
        borrowerField = new JTextField(20);
        borrowerPanel.add(createStyledLabel("Your Name:"));
        borrowerPanel.add(borrowerField);
        
        JPanel bookCodePanel = new JPanel(new FlowLayout());
        bookCodePanel.setBackground(new Color(30, 30, 30)); // Dark black
        bookCodeField = new JTextField(10);
        bookCodePanel.add(createStyledLabel("Book Code:"));
        bookCodePanel.add(bookCodeField);
        
        JButton submitBtn = createStyledButton("Submit Borrow");
        submitBtn.addActionListener(e -> borrowBook());
        bookCodePanel.add(submitBtn);
        
        inputPanel.add(borrowerPanel);
        inputPanel.add(bookCodePanel);
        inputPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void showReturnPanel() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridLayout(2, 1));
        
        JPanel borrowerPanel = new JPanel(new FlowLayout());
        borrowerPanel.setBackground(new Color(30, 30, 30)); // Dark black
        borrowerField = new JTextField(20);
        borrowerPanel.add(createStyledLabel("Your Name:"));
        borrowerPanel.add(borrowerField);
        
        JPanel bookCodePanel = new JPanel(new FlowLayout());
        bookCodePanel.setBackground(new Color(30, 30, 30)); // Dark black
        bookCodeField = new JTextField(10);
        bookCodePanel.add(createStyledLabel("Book Code:"));
        bookCodePanel.add(bookCodeField);
        
        JButton submitBtn = createStyledButton("Submit Return");
        submitBtn.addActionListener(e -> returnBook());
        bookCodePanel.add(submitBtn);
        
        inputPanel.add(borrowerPanel);
        inputPanel.add(bookCodePanel);
        inputPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void displayBooks(ArrayList<Book> books) {
        displayArea.setText("");
        if (books.isEmpty()) {
            displayArea.append("No books found.\n");
            return;
        }
        for (Book book : books) {
            displayArea.append(book.toString() + "\n\n");
        }
    }

    private void displayAllBooks() {
        displayBooks(library.getAllBooks());
    }

    private void displayAvailableBooks() {
        displayBooks(library.getAvailableBooks());
    }

    private void displayBorrowedBooks() {
        displayBooks(library.getBorrowedBooks());
    }

    private void borrowBook() {
        String borrowerName = borrowerField.getText().trim();
        if (borrowerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name");
            return;
        }

        String code = bookCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a book code");
            return;
        }

        Book book = library.findBookByCode(code);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found. Please check the code.");
            return;
        }

        if (book.isAvailable()) {
            book.borrowBook(borrowerName);
            JOptionPane.showMessageDialog(this, 
                """
                Book borrowed successfully!
                Title: %s
                Due Date: %s
                Days Remaining: %d
                """.formatted(book.getTitle(), book.getDueDate(), book.getDaysRemaining()));
            inputPanel.setVisible(false);
            displayAllBooks();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Book is already borrowed by " + book.getBorrowedBy() + "\n" +
                "Due in " + book.getDaysRemaining() + " days");
        }
    }

    private void returnBook() {
        String borrowerName = borrowerField.getText().trim();
        if (borrowerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name");
            return;
        }

        String code = bookCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a book code");
            return;
        }

        Book book = library.findBookByCode(code);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found. Please check the code.");
            return;
        }

        if (book.isAvailable()) {
            JOptionPane.showMessageDialog(this, "This book is already available");
            return;
        }

        if (!borrowerName.equals(book.getBorrowedBy())) {
            JOptionPane.showMessageDialog(this, 
                "Only the borrower can return this book\n" +
                "Current borrower: " + book.getBorrowedBy());
            return;
        }

        book.returnBook();
        JOptionPane.showMessageDialog(this, "Book returned successfully!");
        inputPanel.setVisible(false);
        displayAllBooks();
    }

    private void addNewBook() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField codeField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        panel.add(createStyledLabel("Book Code:"));
        panel.add(codeField);
        panel.add(createStyledLabel("Title:"));
        panel.add(titleField);
        panel.add(createStyledLabel("Author:"));
        panel.add(authorField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Add New Book", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String code = codeField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            
            if (code.isEmpty() || title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }
            
            if (library.findBookByCode(code) != null) {
                JOptionPane.showMessageDialog(this, "A book with this code already exists");
                return;
            }
            
            library.addBook(code, title, author);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            displayAllBooks();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}