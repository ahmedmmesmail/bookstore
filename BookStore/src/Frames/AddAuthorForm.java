package Frames;

import Sql.SqlCon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddAuthorForm {

	public static void openAddAuthorForm() {
		JFrame addAuthorFrame = new JFrame("Add Author");
		addAuthorFrame.setSize(500, 450);
		addAuthorFrame.setDefaultCloseOperation(addAuthorFrame.getDefaultCloseOperation());
		addAuthorFrame.setLayout(null);
		addAuthorFrame.getContentPane().setBackground(new Color(0x172310));
		addAuthorFrame.setVisible(true);
		addAuthorFrame.setLocationRelativeTo(null);
		addAuthorFrame.setResizable(false);
		Image icon = new ImageIcon(MainFrame.class.getResource("/images/icon.png")).getImage();
		addAuthorFrame.setIconImage(icon);

		Color buttonColor = new Color(0x517c34);
		Font buttonFont = new Font("monospace", Font.PLAIN, 16);

		JLabel idLabel = new JLabel("id:");
		JTextField idField = new JTextField();
		JLabel firstNameLabel = new JLabel("First Name:");
		JTextField firstNameField = new JTextField();
		JLabel lastNameLabel = new JLabel("Last name:");
		JTextField lastNameField = new JTextField();
		JLabel cityLabel = new JLabel("City:");
		JTextField cityField = new JTextField();
		JLabel emailLabel = new JLabel("Email:");
		JTextField emailField = new JTextField();
		JButton saveButton = new JButton("Save");

		addAuthorFrame.add(idLabel);
		addAuthorFrame.add(idField);
		addAuthorFrame.add(firstNameLabel);
		addAuthorFrame.add(firstNameField);
		addAuthorFrame.add(lastNameLabel);
		addAuthorFrame.add(lastNameField);
		addAuthorFrame.add(cityLabel);
		addAuthorFrame.add(cityField);
		addAuthorFrame.add(emailLabel);
		addAuthorFrame.add(emailField);
		addAuthorFrame.add(saveButton);

		idField.setBounds(195, 50, 200, 35);
		firstNameField.setBounds(195, 100, 200, 35);
		lastNameField.setBounds(195, 150, 200, 35);
		cityField.setBounds(195, 200, 200, 35);
		emailField.setBounds(195, 250, 200, 35);

		idField.setFont(new Font("monospace", Font.BOLD, 16));
		firstNameField.setFont(new Font("monospace", Font.BOLD, 16));
		lastNameField.setFont(new Font("monospace", Font.BOLD, 16));
		cityField.setFont(new Font("monospace", Font.BOLD, 16));
		emailField.setFont(new Font("monospace", Font.BOLD, 16));

		idLabel.setFont(new Font("monospace", Font.BOLD, 16));
		idLabel.setBounds(90, 50, 230, 35);
		idLabel.setForeground(new Color(0x95bf74));

		firstNameLabel.setFont(new Font("monospace", Font.BOLD, 16));
		firstNameLabel.setBounds(90, 100, 230, 35);
		firstNameLabel.setForeground(new Color(0x95bf74));

		lastNameLabel.setFont(new Font("monospace", Font.BOLD, 16));
		lastNameLabel.setBounds(90, 150, 230, 35);
		lastNameLabel.setForeground(new Color(0x95bf74));

		cityLabel.setFont(new Font("monospace", Font.BOLD, 16));
		cityLabel.setBounds(90, 200, 230, 35);
		cityLabel.setForeground(new Color(0x95bf74));

		emailLabel.setFont(new Font("monospace", Font.BOLD, 16));
		emailLabel.setBounds(90, 250, 230, 35);
		emailLabel.setForeground(new Color(0x95bf74));

		saveButton.setForeground(Color.WHITE);
		saveButton.setBackground(buttonColor);
		saveButton.setFont(buttonFont);
		saveButton.setBounds(80, 320, 330, 35);

		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ea) {
				char c = ea.getKeyChar();
				// السماح فقط بالأرقام والفاصلة العشرية
				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
					ea.consume(); // منع الحرف من أن يظهر
				}
			}
		});

		addAuthorFrame.setVisible(true);
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String firstName = firstNameField.getText().trim();
				String lastName = lastNameField.getText().trim();
				String city = cityField.getText().trim();
				String email = emailField.getText().trim();
				String id = idField.getText().trim();
				if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty() || city.isEmpty()) {
					JOptionPane.showMessageDialog(addAuthorFrame, "Please enter all data", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (Integer.parseInt(id) < 1) {
					JOptionPane.showMessageDialog(addAuthorFrame, "invalid id", "Erorr", JOptionPane.ERROR_MESSAGE);
				} else {
				boolean authorExists = false;
				try {
					SqlCon.open();
					PreparedStatement ps = SqlCon.con.prepareStatement("SELECT COUNT(*) FROM author WHERE author_id = ?");
					ps.setObject(1, id);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						authorExists = rs.getInt(1) > 0;
					}
					SqlCon.close();
				} catch (Exception eb) {
					SqlCon.lastError = eb.getMessage();
					System.out.println(eb);
					authorExists = false;
				}
				if (authorExists) {
					JOptionPane.showMessageDialog(addAuthorFrame, "this author is already exist", "Duplicate author", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!authorExists) {	
					Object[] authorParameters = {id, city, firstName, lastName, email};
					boolean result = SqlCon.Execute("insert into author values(?, ?, ?, ?, ?)", authorParameters);
					if (!result) {
						JOptionPane.showMessageDialog(addAuthorFrame, "Error in data entry:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(addAuthorFrame, firstName + " was added successfully!");
						addAuthorFrame.hide();
					}}
				}
			}
		});

	}
}
