package social;
//controller

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class ControllerGui implements KeyListener {
	
	private SocialGui view;
	private Social model;

	public ControllerGui(SocialGui v, Social m) {
		this.view=v;
		this.model=m;
		
		view.id.addKeyListener(this);
		view.login.addActionListener(e -> {
			try {
				if(model.getPerson(view.id.getText())!="") {
					view.selected.setText(view.id.getText());
					view.friends.setListData(model.listOfFriends(view.id.getText()).toArray(String[]::new));
					
				}
			} catch (NoSuchCodeException e1) {
				JOptionPane.showMessageDialog(view, "The user code is invalid!", "Login error", JOptionPane.ERROR_MESSAGE );
			}
		});
		
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			try {
				if(model.getPerson(view.id.getText())!="") {
					view.selected.setText(view.id.getText());
					view.friends.setListData(model.listOfFriends(view.id.getText()).toArray(String[]::new));
				}
			} catch (NoSuchCodeException e1) {
				JOptionPane.showMessageDialog(view, "The user code is invalid!", "Login error", JOptionPane.ERROR_MESSAGE );
			}
		
	}

}
