package Frames;

import Sql.SqlCon;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainFrame {
	// إنشاء النافذة الرئيسية

	public static void openMainFrame() {
		JFrame frame = new JFrame("Book store");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		Image icon = new ImageIcon(MainFrame.class.getResource("/images/icon.png")).getImage();
		frame.setIconImage(icon);

		JLabel isbnLabel = new JLabel("Enter book ISBN:");
		isbnLabel.setFont(new Font("monospace", Font.BOLD, 18));
		isbnLabel.setForeground(new Color(0x95bf74));
		isbnLabel.setBounds(200, 200, 180, 30);

		JTextField isbnField = new JTextField();
		isbnField.setFont(new Font("monospace", Font.BOLD, 16));
		isbnField.setBounds(365, 200, 230, 35);

		JButton addAuthorButton = new JButton("Add author");
		JButton addPubButton = new JButton("Add publisher");
		JButton addBookButton = new JButton("Add book");
		JButton searchButton = new JButton("Search for book");
		JButton deleteButton = new JButton("Delete book");
		JButton updateButton = new JButton("Update book");

		Color buttonColor = new Color(0x517c34);
		Font buttonFont = new Font("Arial", Font.PLAIN, 16);
		Dimension buttonSize = new Dimension(170, 35);

		//row
		addAuthorButton.setForeground(new Color(0xf3f8ed));
		addAuthorButton.setBackground(buttonColor);
		addAuthorButton.setFont(buttonFont);
		addAuthorButton.setBounds(120, 350, buttonSize.width, buttonSize.height);

		addPubButton.setForeground(new Color(0xf3f8ed));
		addPubButton.setBackground(buttonColor);
		addPubButton.setFont(buttonFont);
		addPubButton.setBounds(320, 350, buttonSize.width, buttonSize.height);

		addBookButton.setForeground(new Color(0xf3f8ed));
		addBookButton.setBackground(buttonColor);
		addBookButton.setFont(buttonFont);
		addBookButton.setBounds(520, 350, buttonSize.width, buttonSize.height);

		//row
		searchButton.setForeground(new Color(0xf3f8ed));
		searchButton.setBackground(buttonColor);
		searchButton.setFont(buttonFont);
		searchButton.setBounds(320, 280, buttonSize.width, buttonSize.height);

		deleteButton.setForeground(new Color(0xf3f8ed));
		deleteButton.setBackground(buttonColor);
		deleteButton.setFont(buttonFont);
		deleteButton.setBounds(120, 280, buttonSize.width, buttonSize.height);

		updateButton.setForeground(new Color(0xf3f8ed));
		updateButton.setBackground(buttonColor);
		updateButton.setFont(buttonFont);
		updateButton.setBounds(520, 280, buttonSize.width, buttonSize.height);

		frame.add(isbnLabel);
		frame.add(isbnField);
		frame.add(addAuthorButton);
		frame.add(addBookButton);
		frame.add(addPubButton);
		frame.add(searchButton);
		frame.add(deleteButton);
		frame.add(updateButton);

		frame.getContentPane().setBackground(new Color(0x172310));
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		// إضافة أحداث الأزرار مع التحقق من كون الحقل فارغًا
		searchButton.addActionListener(e -> {
			String isbn = isbnField.getText().trim();
			if (isbn.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "The ISBN field is empty! Please enter the book ISBN", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					SqlCon.open();
					Connection con = SqlCon.con;

					String sql = "SELECT b.title, b.type, b.price, b.page_count, "
							+ "p.name AS publisher_name, p.city AS publisher_city, p.phone AS publisher_phone, p.p_email, "
							+ "a.first_name, a.last_name, a.city AS author_city, a.a_email "
							+ "FROM books b "
							+ "JOIN publisher p ON b.Publisher_code = p.publisher_code "
							+ "JOIN author_books ab ON b.isbn = ab.isbn "
							+ "JOIN author a ON ab.author_id = a.author_id "
							+ "WHERE b.isbn = ?";

					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1, isbn);
					ResultSet rs = stmt.executeQuery();

					StringBuilder authorsInfo = new StringBuilder();
					String title = "", type = "", price = "", pages = "";
					String publisherName = "", publisherCity = "", publisherPhone = "", publisherEmail = "";
					boolean found = false;

					while (rs.next()) {
						found = true;

						// بيانات الكتاب والناشر تحفظ مرة واحدة
						if (title.isEmpty()) {
							title = rs.getString("title");
							type = rs.getString("type");
							price = rs.getString("price");
							pages = rs.getString("page_count");

							publisherName = rs.getString("publisher_name");
							publisherCity = rs.getString("publisher_city");
							publisherPhone = rs.getString("publisher_phone");
							publisherEmail = rs.getString("p_email");
						}

						// بيانات المؤلف تتكرر
						String authorName = rs.getString("first_name") + " " + rs.getString("last_name");
						String authorCity = rs.getString("author_city");
						String authorEmail = rs.getString("a_email");

						authorsInfo.append("\n\nAuthor info:")
								.append("\nName: ").append(authorName)
								.append("\nCity: ").append(authorCity)
								.append("\nEmail: ").append(authorEmail);
					}

					if (found) {
						String message = "ISBN: " + isbn
								+ "\nTitle: " + title
								+ "\nType: " + type
								+ "\nPrice: " + price
								+ "\nPages: " + pages
								+ "\n\nPublisher Info:"
								+ "\nName: " + publisherName
								+ "\nCity: " + publisherCity
								+ "\nPhone: " + publisherPhone
								+ "\nEmail: " + publisherEmail
								+ "\n" + authorsInfo.toString();

						JOptionPane.showMessageDialog(frame, message, "Book Found", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame, "No book found with ISBN: " + isbn, "Not Found", JOptionPane.ERROR_MESSAGE);
					}

					rs.close();
					stmt.close();
					SqlCon.close();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		deleteButton.addActionListener(e -> {
			String isbn = isbnField.getText().trim();
			if (isbnField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "The ISBN field is empty! Please enter the book ISBN", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String sql = "DELETE FROM author_books WHERE isbn = ?";
				String sql2 = "DELETE FROM books WHERE isbn = ?";
				Object[] params = {isbn};
				int affectedRows = SqlCon.ExecuteEdit(sql, params);
				int affectedRows2 = SqlCon.ExecuteEdit(sql2, params);
				if (affectedRows == 0 && affectedRows2 == 0) {
					JOptionPane.showMessageDialog(frame, "No book found with this ISBN. delete failed.", "delete Error", JOptionPane.ERROR_MESSAGE);
				} else if (affectedRows > 0 && affectedRows2 > 0) {
					JOptionPane.showMessageDialog(frame, isbn + " was successfully deleted!");
				} else {
					JOptionPane.showMessageDialog(frame, "Error in data entry:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateButton.addActionListener(e -> UpdateBookForm.openUpdateBookForm());
		addAuthorButton.addActionListener(e -> AddAuthorForm.openAddAuthorForm());
		addBookButton.addActionListener(e -> AddBookForm.openAddBookForm());
		addPubButton.addActionListener(e -> AddPubForm.openAddPubForm());

	}
}
