//Reuben Orihuela
//CS 480 Mini Project
//2/7/19
package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Calculator extends JPanel implements ActionListener {
	
	JLabel display;
	JButton numButton;
	JButton clearButton;
	String displayContent = "";
	String[] numPadContent = {"1","2","3","+","4","5","6","-","7","8","9","*","C","0","⌫","/","(",")",".","="};
	ArrayList<JButton> buttonList;
	
	
	
	public Calculator(Container pane) {
		// size of keypad
		pane.setPreferredSize(new Dimension(330, 335));
		
		display = new JLabel(displayContent);
		display.setPreferredSize(new Dimension(320, 25));
		pane.add(display, BorderLayout.PAGE_START);
		
		buttonList = new ArrayList<JButton>(12);
		JPanel numberPanel = new JPanel();
		numberPanel.setLayout(new GridLayout(5,4,0,0));
		numberPanel.setPreferredSize(new Dimension(320,260));
		for (int i = 0; i < numPadContent.length; i++) {
			numButton = new JButton(numPadContent[i]);
			buttonList.add(numButton);
		}
		for (int n = 0; n < 20; n++) {
			buttonList.get(n).addActionListener(this);
			numberPanel.add(buttonList.get(n));
		}
		
		numberPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));
		pane.add(numberPanel, BorderLayout.LINE_END);
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String equation = display.getText();
		String additionalText = "";
		for (int a = 0; a < buttonList.size(); a++) {
			//Clear button functionality
			if (e.getSource().equals(buttonList.get(12))) {
				equation = "";
				break;
			}
			//backspace figure
			if (e.getSource().equals(buttonList.get(14))) {
				if (equation.isEmpty())
				break;
				else {
					equation = (equation.substring(0,equation.length()-1));
					break;
				}
			}
			//Equal sign functionality
			if (e.getSource().equals(buttonList.get(19))) {
				
				//add and subtract negative numbers functionality
				equation = equation.replaceAll("--", "+");
				equation = equation.replaceAll("\\+-", "-");
				equation = equation.replaceAll("-\\+", "-");				
				Object result = "";
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				try {
					result = engine.eval(equation);
					
				} catch (Exception f) {
					System.out.println("EXCEPTION");
				}
				equation = result + "";
				break;
			}
			//Add corresponding number or sign to equation
			if (e.getSource().equals(buttonList.get(a))) {
				additionalText = buttonList.get(a).getText();
			}
		}
		display.setText(equation.concat(additionalText));
	}
	
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new Calculator(frame));
        
		frame.pack();
		frame.setVisible(true);
	}
}
