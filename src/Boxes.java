import acm.graphics.*;
import acm.program.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Boxes extends GraphicsProgram {
	private static final int MAX_NAME = 25;
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	
	private HashMap<String,GObject> boxes;
	private JTextField nameField;
	private GObject currentObject;
	private GPoint last;	
		
		
	public void init() {
		boxes = new HashMap<String,GObject>();
		createController();
		addActionListeners();
		addMouseListeners();
	}
	
	private void createController() {
		nameField = new JTextField(MAX_NAME);
		nameField.addActionListener(this);
		
		add(new JLabel("Name"), SOUTH);
		add(nameField, SOUTH);
		add(new JButton("Add"), SOUTH);
		add(new JButton("Remove"), SOUTH);
		add(new JButton("Clear"), SOUTH);
	}
	
	private void addBox(String name) {
		GCompound box = new GCompound();
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(name);
		
		box.add(outline, -outline.getWidth() / 2, -outline.getHeight() / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		
		add(box, getWidth() / 2, getHeight() / 2);
		boxes.put(name, box);
	}
	
	private void removeBox(String name) {
		GObject box = boxes.get(name);
		if (box != null) {
		remove(box);
		}
	}
		
	private void removeContents() {
		for (String boxName: boxes.keySet()) {
			removeBox(boxName);
		}
		boxes.clear(); 
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add") || e.getSource() == nameField) {
			addBox(nameField.getText());
		} else if (e.getActionCommand().equals("Remove")) {
			removeBox(nameField.getText());
		} else if (e.getActionCommand().equals("Clear")) {
			removeContents();
		}
	}
	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentObject = getElementAt(last);
	}
	public void mouseDragged(MouseEvent e) {
		if (currentObject != null) {
			currentObject.move(e.getX() - last.getX(),
			e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}
	public void mouseClicked(MouseEvent e) {
		if (currentObject != null) {
			currentObject.sendToFront();
		}
	}
}