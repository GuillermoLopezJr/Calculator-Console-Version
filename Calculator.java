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
	}
	public static double evaluate()
	{
		Stack<String> stack = new Stack<String>();

		for(int i = 0; i < tokens.size(); i++)
		{
			if(!isOperator(tokens.get(i)))
				stack.push(tokens.get(i));
			else
			{
				//if(isBinaryOperator() );
				try{
					double num2 = Double.parseDouble(stack.pop());
					double num1 = Double.parseDouble(stack.pop());
					char op = tokens.get(i).charAt(0);
					double result = getResult(num1, num2, op);
					stack.push(""+result);
				}
				catch(Exception ex)
				{
					System.out.println("Invalid expression");
				}
				
			}
		}

		if(stack.size() == 1)
			return Double.parseDouble(stack.pop());
		else
			return -1; // error

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
			if( isOperator(tokens.get(i)) )
			{
				if(opStack.empty() )
					opStack.push(tokens.get(i));
				else
				{
					boolean topStackPrecHigher = true;
					boolean tokenPushed = false;

					do{
						if(precedenceTable.get(opStack.peek()) < precedenceTable.get(tokens.get(i)))
						{
							opStack.push(tokens.get(i));
							topStackPrecHigher = false;
							tokenPushed = true;
						}
						else //top stack higher precdecne
							postfixExpr += " " + opStack.pop();
							
					}while(topStackPrecHigher && !opStack.empty());
					
					if(!tokenPushed)	
						opStack.push(tokens.get(i));
				}
			}
			else
				postfixExpr += " " + tokens.get(i);
		}

		while(!opStack.empty()) //pop remaining tokens
			postfixExpr += " " + opStack.pop();

		return postfixExpr;
	}
	public static boolean comparePrecedence()
	{
		return false;
	}
	public static boolean isOperator(String str)
	{
		 return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") );
	}

}