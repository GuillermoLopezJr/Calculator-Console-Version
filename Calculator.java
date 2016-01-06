import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Calculator{
	
	public static ArrayList<String> tokens;
	public static Map<String, Integer> precedenceTable;
	public static Stack<String> opStack; 
	public static final String delimiters = "()+-/* ";

	public static void main(String[] args)
	{
		opStack = new Stack<String>();
		tokens = new ArrayList<String>();
		precedenceTable = new HashMap<String, Integer>();
		populatePrecedenceTable();

		String infixExpr = getInfixExpr();
		getTokens(infixExpr);
		
        String postfixExpr = getPostFixExpre();
		System.out.println("post fix expresion is: " + postfixExpr);
		
		tokens.clear();
		getTokens(postfixExpr);

		double result = evaluate();
		System.out.println("Result is: " + result);
	}

	public static void getTokens(String expr)
	{
		StringTokenizer st = new StringTokenizer(expr, delimiters, true);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
           	
            if(!token.equals(" "))
            	tokens.add(token);
         }
	}

	public static void populatePrecedenceTable()
	{
		precedenceTable.put("+", 1);
		precedenceTable.put("-", 1);
		precedenceTable.put("*", 2);
		precedenceTable.put("/", 2);
		precedenceTable.put("(", 0);
		precedenceTable.put(")", 100);
	}

	public static double evaluate()
	{
		Stack<String> stack = new Stack<String>();

		for(int i = 0; i < tokens.size(); i++)
		{
			String curToken = tokens.get(i);
			if (isOperand(curToken))
				stack.push(curToken);
			else if (isOperator(curToken))
			{
				//if(isBinaryOperator() );
				try{
					double num2 = Double.parseDouble(stack.pop());
					double num1 = Double.parseDouble(stack.pop());
					char op = curToken.charAt(0);
					double result = getResult(num1, num2, op);
					stack.push(""+result);
				}
				catch(Exception ex){
					System.out.println("Invalid expression");
				}
			}
		}

		if(stack.size() == 1)
			return Double.parseDouble(stack.pop());
		else
			return -1;
			//throw new ComputationException("invalid Expression");
			//throw ComputationException("Invalid Expression");
	}

	public static double getResult(double num1, double num2, char op)
	{
		double result = 0;

		if(op == '+')
			result = num1 + num2;
		else if(op == '-')
			result = num1 - num2;
		else if (op == '/')
			result = num1 / num2;
		else if (op == '*')
			result = num1 * num2;
		return result;
	}

	public static String getInfixExpr()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter expresion: ");
		String infixExpr = keyboard.nextLine();
		return infixExpr;
	}

	public static String getPostFixExpre()
	{
		String postfixExpr = "";

		for(int i = 0; i < tokens.size(); i++)
		{
			String curToken = tokens.get(i);

			if ( isOperand(curToken) )
			{
				postfixExpr += " " + curToken;
			}
			else if (isOperator(curToken) )
			{
				while( !opStack.empty() && !hasHigherPrecedence(curToken, opStack.peek()) && !isLeftParen(opStack.peek()) )
					postfixExpr += opStack.pop();

				opStack.push(curToken);
			}
			else if ( isLeftParen(curToken) )
			{
				opStack.push(curToken);
			}
			else if ( isRightParen(curToken) )
			{
				while( !opStack.empty() && !isLeftParen(opStack.peek()) )
					postfixExpr += " " + opStack.pop();
				opStack.pop(); //pop the right paren
			}
		}

		while(!opStack.empty()) //pop remaining tokens
			postfixExpr += " " + opStack.pop();

		return postfixExpr;
	}

	public static boolean isLeftParen(String str)
	{
		return (str.equals("("));
	}
	public static boolean isRightParen(String str)
	{
		return (str.equals(")")); 
	}
	public static boolean hasHigherPrecedence(String op1, String op2)
	{
		return (precedenceTable.get(op1) > precedenceTable.get(op2) );	
	}
	public static boolean isOperand(String str)
	{
		try{
			double num = Double.parseDouble(str);
		}
		catch(NumberFormatException ex){
			return false;
		}
		return true;
	}
	public static boolean isOperator(String str)
	{
		 return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") );
	}
}