package Frames;

import Sql.SqlCon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateBookForm {

	public static void openUpdateBookForm() {
		JFrame UpdateBookFrame = new JFrame("Update Book");
		UpdateBookFrame.setSize(520, 490);
		UpdateBookFrame.setDefaultCloseOperation(UpdateBookFrame.getDefaultCloseOperation());
		UpdateBookFrame.setLayout(null);
		UpdateBookFrame.getContentPane().setBackground(new Color(0x172310));
		UpdateBookFrame.setVisible(true);
		UpdateBookFrame.setLocationRelativeTo(null);
		UpdateBookFrame.setResizable(false);
		Image icon = new ImageIcon(MainFrame.class.getResource("/images/icon.png")).getImage();
		UpdateBookFrame.setIconImage(icon);

		Color buttonColor = new Color(0x517c34);
		Font buttonFont = new Font("monospace", Font.PLAIN, 16);

		JLabel isbnLabel = new JLabel("ISBN:");
		JTextField isbnField = new JTextField();
		JLabel titleLabel = new JLabel("Title:");
		JTextField titleField = new JTextField();
		JLabel typeLabel = new JLabel("Type:");
		JTextField typeField = new JTextField();
		JLabel priceLabel = new JLabel("Price:");
		JTextField priceField = new JTextField();
		JLabel pagesLabel = new JLabel("Pages:");
		JTextField pagesField = new JTextField();
		JButton saveButton = new JButton("Save");

		UpdateBookFrame.add(isbnLabel);
		UpdateBookFrame.add(isbnField);
		UpdateBookFrame.add(titleLabel);
		UpdateBookFrame.add(titleField);
		UpdateBookFrame.add(typeLabel);
		UpdateBookFrame.add(typeField);
		UpdateBookFrame.add(priceLabel);
		UpdateBookFrame.add(priceField);
		UpdateBookFrame.add(pagesLabel);
		UpdateBookFrame.add(pagesField);
		UpdateBookFrame.add(saveButton);

		isbnField.setBounds(200, 50, 200, 35);
		titleField.setBounds(200, 100, 200, 35);
		typeField.setBounds(200, 150, 200, 35);
		pagesField.setBounds(200, 200, 200, 35);
		priceField.setBounds(200, 250, 200, 35);

		titleField.setFont(new Font("monospace", Font.BOLD, 16));
		priceField.setFont(new Font("monospace", Font.BOLD, 16));
		pagesField.setFont(new Font("monospace", Font.BOLD, 16));
		isbnField.setFont(new Font("monospace", Font.BOLD, 16));
		typeField.setFont(new Font("monospace", Font.BOLD, 16));

		isbnLabel.setFont(new Font("monospace", Font.BOLD, 16));
		isbnLabel.setBounds(140, 50, 230, 35);
		isbnLabel.setForeground(new Color(0x95bf74));

		titleLabel.setFont(new Font("monospace", Font.BOLD, 16));
		titleLabel.setBounds(140, 100, 230, 35);
		titleLabel.setForeground(new Color(0x95bf74));

		typeLabel.setFont(new Font("monospace", Font.BOLD, 16));
		typeLabel.setBounds(140, 150, 230, 35);
		typeLabel.setForeground(new Color(0x95bf74));

		pagesLabel.setFont(new Font("monospace", Font.BOLD, 16));
		pagesLabel.setBounds(140, 200, 230, 35);
		pagesLabel.setForeground(new Color(0x95bf74));

		priceLabel.setFont(new Font("monospace", Font.BOLD, 16));
		priceLabel.setBounds(140, 250, 230, 35);
		priceLabel.setForeground(new Color(0x95bf74));

		saveButton.setForeground(Color.WHITE);
		saveButton.setBackground(buttonColor);
		saveButton.setFont(buttonFont);
		saveButton.setBounds(120, 320, 300, 35);

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

		UpdateBookFrame.setVisible(true);
		saveButton.addActionListener(e -> {
			String isbn = isbnField.getText().trim();
			String title = titleField.getText().trim();
			String type = typeField.getText().trim();
			String pages = pagesField.getText().trim();
			String price = priceField.getText().trim();

			if (isbn.isEmpty() || title.isEmpty() || type.isEmpty() || pages.isEmpty() || price.isEmpty()) {
				JOptionPane.showMessageDialog(UpdateBookFrame, "Please enter all data", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (Integer.parseInt(pages) < 9) {
				JOptionPane.showMessageDialog(UpdateBookFrame, "invalid pages count", "Erorr", JOptionPane.ERROR_MESSAGE);
			} else if (Float.parseFloat(price) <= 0) {
				JOptionPane.showMessageDialog(UpdateBookFrame, "invalid price", "Erorr", JOptionPane.ERROR_MESSAGE);
			} else {
				String sql = "UPDATE books SET title = ?, type = ?, page_count = ?, price = ? WHERE isbn = ?";
				Object[] params = {title, type, pages, price, isbn};

				int affectedRows = SqlCon.ExecuteEdit(sql, params);

				if (affectedRows == 0) {
					JOptionPane.showMessageDialog(UpdateBookFrame, "No book found with this ISBN. Update failed.", "Update Error", JOptionPane.ERROR_MESSAGE);
				} else if (affectedRows > 0) {
					JOptionPane.showMessageDialog(UpdateBookFrame, title + " was updated successfully!");
				} else {
					JOptionPane.showMessageDialog(UpdateBookFrame, "Error in data entry:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

	}
}
