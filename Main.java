import java.util.Scanner;

public class Main{
	public static void main(String[] args)
	{
		Calculator calc = new Calculator();
		do{
			try{
				String infixExpr = getInfixExpr();
				double result = calc.calculate(infixExpr);
				System.out.println("Result is: " + result + "\n");
				calc.clearCalc();
			}
			catch(Exception ex){
				calc.clearCalc();
				System.err.println("Error: " + ex.getMessage() );
			}
		}while(true);
	}
	public static String getInfixExpr()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter expresion: ");
		String infixExpr = keyboard.nextLine();
		return infixExpr;
	}

}