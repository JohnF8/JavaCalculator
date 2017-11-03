//John Ference
/*
 * Modifying a calculator program created in CS401
 *
 * Added functionality will include:
 *
 *
 * To Do:
 * Add the ability to not modify the text in the main area. Make sure the equals is in the right spot
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class SimpleCalc
{
	JFrame window;  
	Container content ;
	JButton[] digits = new JButton[13]; 
	JButton[] ops = new JButton[5];
	JTextField expression;
	JTextField result;
	String[] opCodes = { "+", "-", "*", "/", "%" };
	int NUM_OPS=5;
	public SimpleCalc()
	{
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		content.setLayout(new GridLayout(2,1)); 
		ButtonListener listener = new ButtonListener();
		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,1)); 
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");

		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(result);
						
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));  
	
		JPanel  digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(5,3));	
		
		for (int i=1 ; i<10 ; i++ )
		{
			digits[i-1] = new JButton( ""+i );
			digitsPanel.add( digits[i-1] );
			digits[i-1].addActionListener( listener );
		}
		digits[9] = new JButton( "C" );
		digitsPanel.add( digits[9] );
		digits[9].addActionListener( listener ); 

		digits[10]= new JButton("0");
		digitsPanel.add(digits[10]);
		digits[10].addActionListener(listener);

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		

		digits[12] = new JButton("Enter");
		digitsPanel.add(digits[12]);
		digits[12].addActionListener(listener);

		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(5,1));
		for (int i=0 ; i<5 ; i++ )
		{
			ops[i] = new JButton( opCodes[i] );
			opsPanel.add( ops[i] );
			ops[i].addActionListener( listener ); 
		}
		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );
		
		content.add( topPanel );
		content.add( bottomPanel );
	
		window.setSize( 640,480);
		window.setVisible( true );
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component whichButton = (Component) e.getSource();
			for (int i=1 ; i<10 ; i++ )
				if (whichButton == digits[i-1])
					expression.setText( expression.getText() + i );

			if(whichButton == digits[9]){
				expression.setText("");
			}

			if(whichButton == digits[10]){
				expression.setText(expression.getText() +0);
			}
			if(whichButton == digits[11]){
				String curExp = expression.getText();
				curExp = curExp.substring(0, curExp.length()-1);
			expression.setText(curExp);
			}

			for(int j=0; j<NUM_OPS; j++){
				if(whichButton == ops[j])
					expression.setText(expression.getText()+opCodes[j]);
			}

			if(whichButton == digits[12]){
				evaluate();	
			}
			
		}
	}
	public void evaluate(){
		String newExpression = expression.getText();
		
		 ArrayList<String> operators = new ArrayList<String>();
		 ArrayList<Double> operands = new ArrayList<Double>();

		StringTokenizer st = new StringTokenizer(newExpression, "+-*/%^",true);
		while(st.hasMoreTokens()){
			String token = st.nextToken();

			if("+-*/%^".contains(token))
					operators.add(token);
			else
					operands.add(Double.parseDouble(token));
		}

		for(int i=0; i<operators.size(); i++){
			if(operators.get(i).equals("^")){
				operands.set(i, Math.pow(operands.get(i), operands.get(i+1)));
				operands.remove(i+1);
				operators.remove(i);
				i--;
			}
		}
		for(int i=0; i<operators.size(); i++){
			if(operators.get(i).equals("*")){
				operands.set(i, operands.get(i) * operands.get(i+1));
				operands.remove(i+1);
				operators.remove(i);
				i--;
			}
			
			else if(operators.get(i).equals("/")){
				operands.set(i, operands.get(i) / operands.get(i+1));
				operands.remove(i+1);
				operators.remove(i);
				i--;
			}

			else if(operators.get(i).equals("%")){
					operands.set(i, operands.get(i) % operands.get(i+1));
					operands.remove(i+1);
					operators.remove(i);
					i--;
			}
		}

		for(int x=0; x<operators.size(); x++){
			if(operators.get(x).equals("+")){
				operands.set(x, operands.get(x) + operands.get(x+1));
				operands.remove(x+1);
				operators.remove(x);
				x--;
			}
			else if(operators.get(x).equals("-")){
				operands.set(x, operands.get(x) - operands.get(x+1));
				operands.remove(x+1);
				operators.remove(x);
				x--;
			}
		}
		String finalRes = operands.get(0).toString();
		if(operators.size() == 0 || operands.size() == 1)
				result.setText(finalRes);
		else
				result.setText("Bogus");
	}

	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}

