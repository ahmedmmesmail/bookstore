package Frames;

import Sql.SqlCon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddPubForm {

	public static void openAddPubForm() {
		JFrame addPubFrame = new JFrame("Add Publisher");
		addPubFrame.setSize(500, 450);
		addPubFrame.setDefaultCloseOperation(addPubFrame.getDefaultCloseOperation());
		addPubFrame.setLayout(null);
		addPubFrame.getContentPane().setBackground(new Color(0x172310));
		addPubFrame.setVisible(true);
		addPubFrame.setLocationRelativeTo(null);
		addPubFrame.setResizable(false);
		Image icon = new ImageIcon(MainFrame.class.getResource("/images/icon.png")).getImage();
		addPubFrame.setIconImage(icon);

		Color buttonColor = new Color(0x517c34);
		Font buttonFont = new Font("monospace", Font.PLAIN, 16);

		JLabel codeLabel = new JLabel("code:");
		JTextField codeField = new JTextField();
		JLabel NameLabel = new JLabel("full Name:");
		JTextField NameField = new JTextField();
		JLabel phoneLabel = new JLabel("Phone number:");
		JTextField phoneField = new JTextField();
		JLabel cityLabel = new JLabel("City:");
		JTextField cityField = new JTextField();
		JLabel emailLabel = new JLabel("Email:");
		JTextField emailField = new JTextField();
		JButton saveButton = new JButton("Save");

		addPubFrame.add(codeLabel);
		addPubFrame.add(codeField);
		addPubFrame.add(NameLabel);
		addPubFrame.add(NameField);
		addPubFrame.add(phoneLabel);
		addPubFrame.add(phoneField);
		addPubFrame.add(cityLabel);
		addPubFrame.add(cityField);
		addPubFrame.add(emailLabel);
		addPubFrame.add(emailField);
		addPubFrame.add(saveButton);

		codeField.setBounds(200, 50, 200, 35);
		NameField.setBounds(200, 100, 200, 35);
		phoneField.setBounds(200, 150, 200, 35);
		cityField.setBounds(200, 200, 200, 35);
		emailField.setBounds(200, 250, 200, 35);

		codeField.setFont(new Font("monospace", Font.BOLD, 16));
		NameField.setFont(new Font("monospace", Font.BOLD, 16));
		phoneField.setFont(new Font("monospace", Font.BOLD, 16));
		cityField.setFont(new Font("monospace", Font.BOLD, 16));
		emailField.setFont(new Font("monospace", Font.BOLD, 16));

		codeLabel.setFont(new Font("monospace", Font.BOLD, 16));
		codeLabel.setBounds(75, 50, 230, 35);
		codeLabel.setForeground(new Color(0x95bf74));

		NameLabel.setFont(new Font("monospace", Font.BOLD, 16));
		NameLabel.setBounds(75, 100, 230, 35);
		NameLabel.setForeground(new Color(0x95bf74));

		phoneLabel.setFont(new Font("monospace", Font.BOLD, 16));
		phoneLabel.setBounds(75, 150, 230, 35);
		phoneLabel.setForeground(new Color(0x95bf74));

		cityLabel.setFont(new Font("monospace", Font.BOLD, 16));
		cityLabel.setBounds(75, 200, 230, 35);
		cityLabel.setForeground(new Color(0x95bf74));

		emailLabel.setFont(new Font("monospace", Font.BOLD, 16));
		emailLabel.setBounds(75, 250, 230, 35);
		emailLabel.setForeground(new Color(0x95bf74));

		saveButton.setForeground(Color.WHITE);
		saveButton.setBackground(buttonColor);
		saveButton.setFont(buttonFont);
		saveButton.setBounds(75, 320, 350, 35);

		phoneField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ea) {
				char c = ea.getKeyChar();
				// السماح فقط بالأرقام
				if (!Character.isDigit(c) && c != '+' && c != KeyEvent.VK_BACK_SPACE) {
					ea.consume(); // منع الحرف من أن يظهر
				}
			}
		});
		addPubFrame.setVisible(true);
		saveButton.addActionListener(e -> {
			String code = codeField.getText().trim();
			String Name = NameField.getText().trim();
			String phone = phoneField.getText().trim();
			String city = cityField.getText().trim();
			String email = emailField.getText().trim();
			if (code.isEmpty() || Name.isEmpty() || phone.isEmpty() || city.isEmpty()) {
				JOptionPane.showMessageDialog(addPubFrame, "Please enter all data", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (15 < phone.length() && phone.length() < 9) {
				JOptionPane.showMessageDialog(addPubFrame, "invalid phone number", "Erorr", JOptionPane.ERROR_MESSAGE);
			} else {
				boolean publisherExists = false;
				try {
					SqlCon.open();
					PreparedStatement ps = SqlCon.con.prepareStatement("SELECT COUNT(*) FROM publisher WHERE publisher_code = ?");
					ps.setObject(1, code);
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
				if (publisherExists) {
					JOptionPane.showMessageDialog(addPubFrame, "this Publisher is already exist", "Duplicate publisher", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!publisherExists) {
					Object[] pubParameters = {code, Name, city, phone, email};
					boolean result = SqlCon.Execute("insert into publisher values(?, ?, ?, ?, ?)", pubParameters);
					if (!result) {
						JOptionPane.showMessageDialog(addPubFrame, "Error in data entry:\n" + SqlCon.lastError, "Database Error", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(addPubFrame, Name + " was added successfully!");
						addPubFrame.hide();
					}
				}
			}
		});

	}
}
