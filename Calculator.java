import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class Calculator{

	public static void main(String[] args)
	{
		Stack<Character> opStack = new Stack<Character>();
		String postfixExpr = "";
		Map<Character, Integer> map = new HashMap<Character, Integer>();

		map.put('+', 1);
		map.put('-', 1);
		map.put('*', 2);
		map.put('/', 2);


		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter expresion: ");
		String infixExpr = keyboard.nextLine();

		char[] tokens = new char[infixExpr.length()];

		for(int i = 0; i < infixExpr.length(); i++)
		{
			tokens[i] = infixExpr.charAt(i);
		}

		for(int i = 0; i < infixExpr.length(); i++)
		{
			System.out.println("Token " + i + ": " + tokens[i]);
			if( isOperand(tokens[i]) )
			{
				if(opStack.empty() )
					opStack.push(tokens[i]);
				else
				{
					boolean topStackPrecHigher = true;
					boolean tokenPushed = false;

					do{
						if(map.get(opStack.peek()) < map.get(tokens[i]))
						{
							opStack.push(tokens[i]);
							topStackPrecHigher = false;
							tokenPushed = true;
						}
						else //top stack higher precdecne
							postfixExpr += opStack.pop();
							
					}while(topStackPrecHigher && !opStack.empty());
					
					if(!tokenPushed)	
						opStack.push(tokens[i]);
				}
			}
			else
				postfixExpr += tokens[i];
		}

		while(!opStack.empty()) //pop remaining tokens
			postfixExpr += opStack.pop();

		System.out.println("post fix expresion is: " + postfixExpr);


	}
	public static void populateMap()
	{
		
	}
	public static boolean comparePrecedence()
	{
		return false;
	}
	public static boolean isOperand(char c)
	{
		 return (c == '+' || c == '-' || c == '*' || c == '/');
	}

}