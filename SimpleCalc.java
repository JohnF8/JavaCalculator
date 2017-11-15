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
		sin.setText("sin)");
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

			if("+-*/%^".contains(token))
					operators.add(token);
			else{
				operands.add(token);
				try{
					finalOperands.add(Double.parseDouble(token));//Fill finalOperands to be the same size
				}
				catch(NumberFormatException e){
					finalOperands.add(1.0);
				}
			}
		}
		evaluateIndividuals();
		System.out.println(finalOperands);
		evaluateParens();
		System.out.println(operators);
		System.out.println(operands);
		System.out.println(finalOperands);
		/*
		String finalRes = operands.get(0).toString();
		if(operators.size() == 0 || operands.size() == 1)
				result.setText(finalRes);
		else
				result.setText("Bogus");
	*/
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
	System.out.println(finalOperands);		
	}

	public void evaluateParens(){
		int start=0;
		int end=0;
		for(int i=0; i<operands.size(); i++){
			if(operands.get(i).startsWith("("))
				start=i;
			if(operands.get(i).endsWith(")"))
				end=i;
			if(end != 0){
				basicEval(start,end);
				start=0;
				end=0;
			}
		}
	}

	public void evaluateIndividuals(){
		double num=0;
		String def="";
		for(int i=0; i<operands.size(); i++){
			if(operands.get(i).startsWith("cos")){
				def = operands.get(i);
				num=Double.parseDouble(def.substring(4,def.length()-1));
				num=Math.cos(num);
				finalOperands.set(i,num);
			}

			else if(operands.get(i).startsWith("sin")){
				def=operands.get(i);
				num=Double.parseDouble(def.substring(4,def.length()-1));
				num = Math.sin(num);
				finalOperands.set(i,num);
			}

			else if(operands.get(i).startsWith("tan")){
				def=operands.get(i);
				num=Double.parseDouble(def.substring(4,def.length()-1));
				num=Math.tan(num);
				finalOperands.set(i,num);
			}

			else if(operands.get(i).startsWith("(")){
				def=operands.get(i);
				num=Double.parseDouble(def.substring(1,def.length()));
				finalOperands.set(i,num);
			}

			else if(operands.get(i).startsWith(")")){
				def=operands.get(i);
				num=Double.parseDouble(def.substring(0,def.length()+1));
				finalOperands.set(i,num);
			}
		}
	}


	public static void main(String [] args)
	{
		new SimpleCalc();
	}
}

