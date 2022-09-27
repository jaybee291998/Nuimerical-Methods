import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

public class NumericalMethodsDemo
{
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) 
	{
		selection();
	}

	public static void functionEvaluator()
	{
		DoubleEvaluator eval = new DoubleEvaluator();
		StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		String expression = "";
		System.out.println("Function Evaluator");
		System.out.print("f(x) = ");
		try
		{
			expression = in.next();
			variables.set("x", 0.0);
			eval.evaluate(expression, variables);
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("Invalid Expression");
			functionEvaluator();
			return;
		}
		System.out.println("Enter -999999 to quit");
		double input = 0;
		while(input != -999999)
		{
			System.out.print("x = ");
			input = in.nextDouble();
			variables.set("x", input);
			double result = eval.evaluate(expression, variables);
			System.out.println("f(" + input + ") = " + result);
		}
	}

	public static void selection()
	{
		int choice = 1;
		while(choice != 0)
		{
			System.out.println("Choose a program: ");
			System.out.println("1.) Trapezoidal Method");
			System.out.println("2.) Bisection Method");
			System.out.println("3.) Function Evaluator");
			System.out.println("Choose 0 to quit");
			choice = 0;
			try
			{
				choice = in.nextInt();
			}
			catch(InputMismatchException e)
			{
				//selection();
				return;
			}

			switch(choice)
			{
				case 0:
					break;
				case 1:
					trapezoidalMethod();
					break;
				case 2:
					bisectionMethod();
					break;
				case 3:
					functionEvaluator();
				default:
					System.out.println("No Program Selected, choose 0 to quit");
					selection();
					return;
			}
		}
	}

	public static void bisectionMethod()
	{
		String answer = "yes";

		while(answer.toLowerCase().equals("yes"))
		{
			
			System.out.println("Bisection Method");
			System.out.print("f(x) = ");
			String expression = in.next();
			NumericalMethods bisection = new NumericalMethods(expression);
			System.out.println();

			double a = 0;
			double b = 0;
			try
			{
				System.out.print("a = ");
				a = in.nextDouble();
				System.out.println();

				System.out.print("b = ");
				b = in.nextDouble();
				System.out.println();		
			}
			catch(InputMismatchException e)
			{
				System.out.println("a and b should be a number");
				bisectionMethod();
				return;
			}



			String result = "";
			try
			{
				result = bisection.bisectionMethod(a, b, 1000);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Invalid Expression: make sure it is single variable(x)");
				bisectionMethod();
				return;
			}
			System.out.println(result);

			System.out.print("Evaluate another function?[yes/no]: ");
			answer = in.next();
			System.out.println();
		}
	}

	public static void trapezoidalMethod()
	{
		String answer = "yes";
		Scanner in = new Scanner(System.in);

		while(answer.toLowerCase().equals("yes"))
		{
			
			System.out.println("Trapezoidal Method");
			System.out.print("f(x) = ");
			String expression = in.next();
			NumericalMethods trap = new NumericalMethods(expression);
			System.out.println();
			double a = 0;
			double b = 0;
			int n = 0;
			try
			{
				System.out.print("a = ");
				a = in.nextDouble();
				System.out.println();

				System.out.print("b = ");
				b = in.nextDouble();
				System.out.println();

				System.out.print("Number of sub-intervals: ");
				n = in.nextInt();
				System.out.println();
			}
			catch(InputMismatchException e)
			{
				System.out.println("a, b, and number of sub-intervals should be a number");
				trapezoidalMethod();
				return;
			}

			double result = 0;
			try
			{
				result = trap.trapezoidalMethod(a, b, n);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Invalid Expression: make sure it is single variable(x)");
				trapezoidalMethod();
				return;
			}
			System.out.println("The approximate integral is " + result);

			System.out.print("Evaluate another function?[yes/no]: ");
			answer = in.next();
			System.out.println();
		}
	}	
}