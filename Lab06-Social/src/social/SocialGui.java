package social;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//view

public class SocialGui extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	// The following components are declared public
	// in order to allow testing the user interface

	/**
	 * The code of the person to log in
	 */
	public JTextField id ;

	/**
	 * The button to perform login
	 */
	public JButton login ;

	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */

	public JLabel name ;
	public JLabel selected ;

	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;

	private Social model;

	private ControllerGui controller;

	public SocialGui(Social m){
		
		this.model=m;
		
		
		setTitle("My facebook");

		setSize(600, 500);
		setLayout(new BorderLayout());
		JPanel upper = new JPanel();
		JPanel footer = new JPanel();

		id = new JTextField(20);
		login = new JButton("Login");
		name = new JLabel("ID");
		selected = new JLabel("");
		friends = new JList<>();
		
		upper.setLayout(new FlowLayout());
		upper.add(name);
		upper.add(id);
		upper.add(login);
		
		footer.setLayout(new BorderLayout());
		footer.add(selected, BorderLayout.NORTH);
		footer.add(friends, BorderLayout.CENTER);
		
		
		add(upper, BorderLayout.NORTH);
		add(footer, BorderLayout.CENTER);
		

		this.controller = new ControllerGui(this, m);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

}
