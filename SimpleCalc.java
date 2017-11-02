//John Ference, CS 401
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
	JButton[] digits = new JButton[12]; 
	JButton[] ops = new JButton[4];
	JTextField expression;
	JButton equals;
	JTextField result;
	String[] opCodes = { "+", "-", "*", "/" };
	public SimpleCalc()
	{
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();
		content.setLayout(new GridLayout(2,1)); // 2 row, 1 col
		ButtonListener listener = new ButtonListener();
		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3)); // 1 row, 3 col
		
		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");
		
		equals = new JButton("=");
		equals.setFont(new Font("verdana", Font.BOLD, 20 ));
		equals.addActionListener( listener ); 
		
		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(equals);
		topPanel.add(result);
						
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2)); // 1 row, 2 col
	
		JPanel  digitsPanel = new JPanel();
		digitsPanel.setLayout(new GridLayout(4,3));	
		
		for (int i=0 ; i<10 ; i++ )
		{
			digits[i] = new JButton( ""+i );
			digitsPanel.add( digits[i] );
			digits[i].addActionListener( listener ); 
		}
		digits[10] = new JButton( "C" );
		digitsPanel.add( digits[10] );
		digits[10].addActionListener( listener ); 

		digits[11] = new JButton( "CE" );
		digitsPanel.add( digits[11] );
		digits[11].addActionListener( listener ); 		
	
		JPanel opsPanel = new JPanel();
		opsPanel.setLayout(new GridLayout(4,1));
		for (int i=0 ; i<4 ; i++ )
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
			for (int i=0 ; i<10 ; i++ )
				if (whichButton == digits[i])
					expression.setText( expression.getText() + i );


			if(whichButton == digits[10]){
				expression.setText("");
			}
			if(whichButton == digits[11]){
				String curExp = expression.getText();
				curExp = curExp.substring(0, curExp.length()-1);
			expression.setText(curExp);
			}

			for(int j=0; j<4; j++){
				if(whichButton == ops[j])
					expression.setText(expression.getText()+opCodes[j]);
			}

			if(whichButton == equals){
				evaluate();	
			}
			
		}
	}
	public void evaluate(){
		String newExpression = expression.getText();
		
		 ArrayList<String> operators = new ArrayList<String>();
		 ArrayList<Double> operands = new ArrayList<Double>();

		StringTokenizer st = new StringTokenizer(newExpression, "+-*/",true);
		while(st.hasMoreTokens()){
			String token = st.nextToken();

			if("+-*/".contains(token))
					operators.add(token);
			else
					operands.add(Double.parseDouble(token));
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
		if(operators.size() == 0)
				result.setText(finalRes);
		else
				result.setText("Bogus");
	}

	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}

