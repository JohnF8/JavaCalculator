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
import java.lang.*;

public class SimpleCalc
{
	JFrame window;  
	Container content ;
	JTextField expression;
	JTextField result;

	JButton add;
	JButton subtract;
	JButton multiply;
	JButton divide;
	JButton lParen;
	JButton rParen;
	JButton exponent;
	JButton exp;
	JButton ln;
	JButton log;
	JButton sqrt;
	JButton cos;
	JButton sin;
	JButton tan;
	JButton clear;
	JButton enter;


	ArrayList<String> operators;
	ArrayList<String> operands;
	ArrayList<Double> finalOperands;
	String[] quick= {"sin","cos","tan"};
	public SimpleCalc()
	{
		//Setting up the frame
		window = new JFrame( "Simple Calc");
		content = window.getContentPane();

		//Two windows are within content
		content.setLayout(new GridLayout(2,1)); 
		ButtonListener listener = new ButtonListener();
		
		//Top panel		
		JPanel topPanel = new JPanel(); //Top panel that will hold the input and output
		topPanel.setLayout(new GridLayout(2,1));

		expression = new JTextField();
		expression.setFont(new Font("verdana", Font.BOLD, 16));
		expression.setText("");

		result = new JTextField();
		result.setFont(new Font("verdana", Font.BOLD, 16));
		result.setText("");
		
		topPanel.add(expression);
		topPanel.add(result);
	
		//Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(4,4));

		//Numerous Components of Bottom Panel
		add = new JButton();
		add.setText("+");
		add.addActionListener(listener);

		subtract = new JButton();
		subtract.setText("-");
		subtract.addActionListener(listener);

		multiply = new JButton();
		multiply.setText("*");
		multiply.addActionListener(listener);

		divide = new JButton();
		divide.setText("/");
		divide.addActionListener(listener);
		
		lParen = new JButton();
		lParen.setText("(");
		lParen.addActionListener(listener);
		
		rParen = new JButton();
		rParen.setText(")");
		rParen.addActionListener(listener);
		
		exponent = new JButton();
		exponent.setText("^");
		exponent.addActionListener(listener);

		exp = new JButton();
		exp.setText("e");
		exp.addActionListener(listener);
		
		ln = new JButton();
		ln.setText("ln");
		ln.addActionListener(listener);

		log = new JButton();
		log.setText("log");
		log.addActionListener(listener);

		sqrt = new JButton();
		sqrt.setText("sqrt");
		sqrt.addActionListener(listener);

		cos = new JButton();
		cos.setText("cos");
		cos.addActionListener(listener);
		
		sin = new JButton();
		sin.setText("sin");
		sin.addActionListener(listener);

		tan = new JButton();
		tan.setText("tan");
		tan.addActionListener(listener);
		
		clear = new JButton();
		clear.setText("CE");
		clear.addActionListener(listener);

		enter = new JButton();
		enter.setText("enter");
		enter.addActionListener(listener);
		
		bottomPanel.add(add);
		bottomPanel.add(subtract);
		bottomPanel.add(multiply);
		bottomPanel.add(divide);
		
		bottomPanel.add(lParen);
		bottomPanel.add(rParen);
		bottomPanel.add(exponent);
		bottomPanel.add(exp);


		bottomPanel.add(ln);
		bottomPanel.add(log);
		bottomPanel.add(sqrt);
		bottomPanel.add(cos);
		
		bottomPanel.add(sin);
		bottomPanel.add(tan);
		bottomPanel.add(clear);
		bottomPanel.add(enter);
		
		content.add(topPanel);
		content.add(bottomPanel);
		window.setSize( 640,480);
		window.setVisible( true );
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component whichButton = (Component) e.getSource();

			if(whichButton == cos){
				expression.setText(expression.getText() + "cos( ");
			}

			if(whichButton == sin){
				expression.setText(expression.getText() + "sin( ");
			}

			if(whichButton == tan){
				expression.setText(expression.getText()+ "tan( ");
			}

			if(whichButton == clear){
				expression.setText("");
			}

			if(whichButton == enter){
				evaluate();	
			}
			
		}
	}
	public void evaluate(){
		String newExpression = expression.getText();
		
		 operators = new ArrayList<String>();
		 operands = new ArrayList<String>();
		 finalOperands= new ArrayList<Double>();

		StringTokenizer st = new StringTokenizer(newExpression, "+-*/%^",true);
		while(st.hasMoreTokens()){
			String token = st.nextToken();

			//[sin(0),(,5,6,),10]
			//Evaluate individuals that can be easily evaluated
			//Take out the parentheses and recruse through the evaluation func
			//
			System.out.println("The token" + token);
			if("+-*/%^".contains(token))
					operators.add(token);
			else{
				operands.add(token);
				quickEvaluation(token);
			}
		}
		System.out.println(operands);
		System.out.println(finalOperands);
		System.out.println("-----");
		newParens();
		basicEval(0,operands.size()-1);
		String finalRes = operands.get(0).toString();
		if(operators.size() == 0 || operands.size() == 1)
				result.setText(finalRes);
		else
				result.setText("Bogus");
	
	}
	//Work
	//Need to include handling for operations within parentheses.Recursion?
	public void basicEval(int start, int end){
		System.out.println("start: " + start + " ends "+end);
		double result=0;
		for(int i=start; i<end; i++){
			if(operators.get(i).equals("*")){
				result=finalOperands.get(i) * finalOperands.get(i+1);
				finalOperands.set(i, result);
				operands.set(i,Double.toString(result)); 
				operators.remove(i);
				finalOperands.remove(i+1);
				operands.remove(i+1);
				i--;
				end--;
			}

			else if(operators.get(i).equals("/")){
				result=finalOperands.get(i) / finalOperands.get(i+1);
				finalOperands.set(i, result);
				operands.set(i, Double.toString(result));
				operators.remove(i);
				finalOperands.remove(i+1);
				operands.remove(i+1);
				i--;
				end--;
			}
		}

		for(int i=start; i<end; i++){
			if(operators.get(i).equals("+")){
				result=finalOperands.get(i) + finalOperands.get(i+1);
				finalOperands.set(i,result);
				operands.set(i,Double.toString(result));
				operators.remove(i);
				finalOperands.remove(i+1);
				operands.remove(i+1);
				i--;
				end--;
			}
		
			else if(operators.get(i).equals("-")){
				result=finalOperands.get(i) - finalOperands.get(i+1);
				finalOperands.set(i,result);
				operands.set(i,Double.toString(result));
				operators.remove(i);
				finalOperands.remove(i+1);
				operands.remove(i+1);
				i--;
				end--;
			}
		}
	System.out.println("After going through evaluation "+ finalOperands);		
	System.out.println("The operands " +operands);
	}


	public void newParens(){
		int start=-1;
		int end=-1;

		for(int i=0; i<operands.size(); i++){
			if(operands.get(i).startsWith("("))
				start=i;
			//May want to make this an else if
			if(operands.get(i).endsWith(")"))
				end=i;
			if(start != -1 && end != -1){
				basicEval(start,end);
				start=-1;
				end=-1;
			}
		}
		System.out.println("Final " + finalOperands);
		System.out.println("Normal " + operands);
	}

	public void quickEvaluation(String token){
		double num=0;
		int i;
		for(i=0;i<quick.length; i++){
			if(token.startsWith(quick[i])){
				num=Double.parseDouble(token.substring(4,token.length()-1));
				System.out.println(num);
			}
		}
				if(i==0)
					finalOperands.add(Math.sin(num));
				else if(i==1)
					finalOperands.add(Math.cos(num));
				else if(i==2)
					finalOperands.add(Math.tan(num));
				else{
					token = token.replaceAll("[()]","");
					System.out.println("The new token "+token);
					try{
						finalOperands.add(Double.parseDouble(token));//Fill finalOperands to be the same size
					}
					catch(NumberFormatException e){
						finalOperands.add(1.0);
					}
					
				}
			
			
	}

	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}

