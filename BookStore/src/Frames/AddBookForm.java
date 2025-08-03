package Frames;

import Sql.SqlCon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddBookForm {

    public static void openAddBookForm() {
        JFrame addBookFrame = new JFrame("Add Book");
        addBookFrame.setSize(550, 530);
        addBookFrame.setDefaultCloseOperation(addBookFrame.getDefaultCloseOperation());
        addBookFrame.setLayout(null);
        addBookFrame.getContentPane().setBackground(new Color(0x172310));
        addBookFrame.setVisible(true);
        addBookFrame.setLocationRelativeTo(null);
        addBookFrame.setResizable(false);
        Image icon = new ImageIcon(MainFrame.class.getResource("/images/icon.png")).getImage();
        addBookFrame.setIconImage(icon);

        Color buttonColor = new Color(0x517c34);
        Font buttonFont = new Font("monospace", Font.PLAIN, 16);

        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField();
        JLabel authorsLabel = new JLabel("Authors id(comma separated):");
        JTextField authorsField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JLabel publisherLabel = new JLabel("Publisher Code:");
        JTextField publisherField = new JTextField();
        JLabel pagesLabel = new JLabel("Pages:");
        JTextField pagesField = new JTextField();
        JButton saveButton = new JButton("Save");

        addBookFrame.add(isbnLabel);
        addBookFrame.add(isbnField);
        addBookFrame.add(titleLabel);
        addBookFrame.add(titleField);
        addBookFrame.add(typeLabel);
        addBookFrame.add(typeField);
        addBookFrame.add(authorsLabel);
        addBookFrame.add(authorsField);
        addBookFrame.add(priceLabel);
        addBookFrame.add(priceField);
        addBookFrame.add(publisherLabel);
        addBookFrame.add(publisherField);
        addBookFrame.add(pagesLabel);
        addBookFrame.add(pagesField);
        addBookFrame.add(saveButton);

        isbnField.setBounds(290, 50, 200, 35);
        titleField.setBounds(290, 100, 200, 35);
        typeField.setBounds(290, 150, 200, 35);
        pagesField.setBounds(290, 200, 200, 35);
        priceField.setBounds(290, 250, 200, 35);
        authorsField.setBounds(290, 300, 200, 35);
        publisherField.setBounds(290, 350, 200, 35);

        titleField.setFont(new Font("monospace", Font.BOLD, 16));
        authorsField.setFont(new Font("monospace", Font.BOLD, 16));
        priceField.setFont(new Font("monospace", Font.BOLD, 16));
        publisherField.setFont(new Font("monospace", Font.BOLD, 16));
        pagesField.setFont(new Font("monospace", Font.BOLD, 16));
        isbnField.setFont(new Font("monospace", Font.BOLD, 16));
        typeField.setFont(new Font("monospace", Font.BOLD, 16));

        isbnLabel.setFont(new Font("monospace", Font.BOLD, 16));
        isbnLabel.setBounds(55, 50, 230, 35);
        isbnLabel.setForeground(new Color(0x95bf74));

        titleLabel.setFont(new Font("monospace", Font.BOLD, 16));
        titleLabel.setBounds(55, 100, 230, 35);
        titleLabel.setForeground(new Color(0x95bf74));

        typeLabel.setFont(new Font("monospace", Font.BOLD, 16));
        typeLabel.setBounds(55, 150, 230, 35);
        typeLabel.setForeground(new Color(0x95bf74));

        pagesLabel.setFont(new Font("monospace", Font.BOLD, 16));
        pagesLabel.setBounds(55, 200, 230, 35);
        pagesLabel.setForeground(new Color(0x95bf74));

        priceLabel.setFont(new Font("monospace", Font.BOLD, 16));
        priceLabel.setBounds(55, 250, 230, 35);
        priceLabel.setForeground(new Color(0x95bf74));

        authorsLabel.setFont(new Font("monospace", Font.BOLD, 16));
        authorsLabel.setBounds(55, 300, 260, 35);
        authorsLabel.setForeground(new Color(0x95bf74));

        publisherLabel.setFont(new Font("monospace", Font.BOLD, 16));
        publisherLabel.setBounds(55, 350, 230, 35);
        publisherLabel.setForeground(new Color(0x95bf74));

        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(buttonColor);
        saveButton.setFont(buttonFont);
        saveButton.setBounds(100, 420, 310, 35);

        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ea) {
                char c = ea.getKeyChar();
                // السماح فقط بالأرقام والفاصلة العشرية
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    ea.consume(); // منع الحرف من أن يظهر
                }
                // التأكد من وجود فاصلة واحدة فقط
                if (c == '.' && priceField.getText().contains(".")) {
                    ea.consume();
                }
            }
        });

        pagesField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ea) {
                char c = ea.getKeyChar();
                // السماح فقط بالأرقام والفاصلة العشرية
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    ea.consume(); // منع الحرف من أن يظهر
                }
            }

        });

        authorsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ea) {
                char c = ea.getKeyChar();
                // السماح فقط بالأرقام والفاصلة العشرية
                if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
                    ea.consume(); // منع الحرف من أن يظهر
                }
            }

        });

        addBookFrame.setVisible(true);
        saveButton.addActionListener(e -> {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String type = typeField.getText().trim();
            String pages = pagesField.getText().trim();
            String price = priceField.getText().trim();
            String authors = authorsField.getText().trim();
            String publisher = publisherField.getText().trim();

            if (title.isEmpty() || price.isEmpty() || publisher.isEmpty() || pages.isEmpty() || isbn.isEmpty() || type.isEmpty() || authors.isEmpty()) {
                JOptionPane.showMessageDialog(addBookFrame, "Please enter all data", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Integer.parseInt(pages) < 9) {
                JOptionPane.showMessageDialog(addBookFrame, "invalid pages count", "Erorr", JOptionPane.ERROR_MESSAGE);
            } else if (Float.parseFloat(price) <= 0) {
                JOptionPane.showMessageDialog(addBookFrame, "invalid price", "Erorr", JOptionPane.ERROR_MESSAGE);
            } else {
                // التحقق من تكرار الكتاب (نفس ISBN)
                Object[] checkBookParam = {isbn};
                boolean bookExists = false;
                try {
                    SqlCon.open();
                    PreparedStatement ps = SqlCon.con.prepareStatement("SELECT COUNT(*) FROM books WHERE isbn = ?");
                    ps.setObject(1, isbn);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        bookExists = rs.getInt(1) > 0;
                    }
                    SqlCon.close();
                } catch (Exception ea) {
                    SqlCon.lastError = ea.getMessage();
                    System.out.println(ea);
                    bookExists = false;
                }
                if (bookExists) {
                    JOptionPane.showMessageDialog(addBookFrame, "This book already exists!", "Duplicate Book", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean publisherExists = false;
                try {
                    SqlCon.open();
                    PreparedStatement ps = SqlCon.con.prepareStatement("SELECT COUNT(*) FROM publisher WHERE publisher_code = ?");
                    ps.setObject(1, publisher);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        publisherExists = rs.getInt(1) > 0;
                    }
                    SqlCon.close();
                } catch (Exception eb) {
                    SqlCon.lastError = eb.getMessage();
                    System.out.println(eb);
                    publisherExists = false;
                }
                if (!publisherExists) {
                    JOptionPane.showMessageDialog(addBookFrame, "Publisher ID is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] authorsList = authors.split(",");
                for (String author : authorsList) {
                    String authorTrimmed = author.trim();
                    boolean authorExists = false;
                    try {
                        SqlCon.open();
                        PreparedStatement ps = SqlCon.con.prepareStatement("SELECT COUNT(*) FROM author WHERE author_id = ?");
                        ps.setObject(1, authorTrimmed);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            authorExists = rs.getInt(1) > 0;
                        }
                        SqlCon.close();
                    } catch (Exception ec) {
                        SqlCon.lastError = ec.getMessage();
                        System.out.println(ec);
                        authorExists = false;
                    }

                    if (!authorExists) {
                        JOptionPane.showMessageDialog(addBookFrame, "Author ID '" + authorTrimmed + "' is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Object[] bookParameters = {isbn, title, type, price, pages, publisher};
                boolean result1 = SqlCon.Execute("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?);", bookParameters);

                if (!result1) {
                    JOptionPane.showMessageDialog(addBookFrame, "Error in adding book:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean authorError = false;
                for (String author : authorsList) {
                    String authorTrimmed = author.trim();
                    Object[] authorParameters = {authorTrimmed, isbn};
                    boolean result2 = SqlCon.Execute("INSERT INTO author_books VALUES (?, ?);", authorParameters);
                    if (!result2) {
                        authorError = true;
                        JOptionPane.showMessageDialog(addBookFrame, "Error linking author:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }

                if (!authorError) {
                    JOptionPane.showMessageDialog(addBookFrame, title + " was added successfully!");
                    addBookFrame.setVisible(false);
                }

            }
        });
    }
}
