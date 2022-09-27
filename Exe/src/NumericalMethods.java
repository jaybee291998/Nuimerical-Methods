import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

public class NumericalMethods
{
	private DoubleEvaluator eval;
	private StaticVariableSet<Double> variables;
	private String expression;
	public NumericalMethods(String expression)
	{
		this.expression = expression;
		eval = new DoubleEvaluator();
		variables = new StaticVariableSet<Double>();
	}
	/**
		computes the approximate integral of a the function using the trapezoidal rule
		@param minimumLimit the start of the interval
		@param maximumLimit the end of the interval
		@param n the number of subintervals
		@return  the approximate integral of of the function
	*/
	public double trapezoidalMethod(double minimumLimit, double maximumLimit, int n) throws IllegalArgumentException
	{
		double deltaX = (maximumLimit - minimumLimit) / n; //the length of the sub interval
		variables.set("x", minimumLimit);

		double total = eval.evaluate(expression, variables); //the value of f at the minimumLimit
		for(double xi = minimumLimit + deltaX; xi < maximumLimit; xi += deltaX)
		{
			variables.set("x", xi);
			total += 2 * eval.evaluate(expression, variables); 
		}
		variables.set("x", maximumLimit);
		total += eval.evaluate(expression, variables); //the value of the function at the end of the interval

		double approximateIntegral = 0.5 * deltaX * total; // the approximate integral

		return approximateIntegral;
	}

	/**
		finds the approximate interval where a  root exist in the function
		@param minimumLimit the start of the interval
		@param maximumLimit the end of the interval
		@param depth the maximum iteration of halving the interval before we stop searching
		@return the smallest interval, 0,0 if the interval where the root exist is not found
	*/
	public String bisectionMethod(double minimumLimit, double maximumLimit, int depth) throws IllegalArgumentException
	{
		double a = minimumLimit;
		double b =maximumLimit;
		double mid = a + (b-a)/2;
		boolean doesntExist = false;
		boolean moreThanOneRoot = false;
		String result = "";

		int i = 0;
		while(i < depth && !doesntExist)
		{
			variables.set("x", a);
			double valA = eval.evaluate(expression, variables);
			variables.set("x", mid);
			double valMid = eval.evaluate(expression, variables);
			variables.set("x", b);
			double valB = eval.evaluate(expression, variables);
			if(checkNumSign(valA, valMid) && checkNumSign(valMid, valB))
			{
				moreThanOneRoot = true;
				break;
			}
			else if(checkNumSign(valA, valMid))
			{
				b = mid;
				mid = a + (b-a)/2;
			}
			else if(checkNumSign(valMid, valB))
			{
				a = mid;
				mid = a + (b-a)/2;
			}
			else
			{
				doesntExist = true;
			}
			i++;
			//System.out.println(i);
		}

		if(doesntExist)
		{
			variables.set("x", 0.0);
			double resultAtZero = eval.evaluate(expression, variables);
			if(resultAtZero == 0.0)
			{
				result = "The root is at 0.0";
			}
			else
			{
				result = "A root doest exist on the interval [" + minimumLimit + ", " + maximumLimit + "].";
			}
		}
		else if(moreThanOneRoot)
		{
			result = "There more than one root on the interval [" + minimumLimit + ", " + maximumLimit + "].";
		}
		else
		{
			result = "The root is between the interval [" + a + ", " + b + "].";
		}
		return result;

	}

	/**
		checks wether 2 given number has a different sign
		@param firstNum 
		@param secondNum
		@return true if they have different signs
	*/
	public boolean checkNumSign(double firstNum, double secondNum)
	{
		boolean result = false;

		if(firstNum >= 0)
		{
			if(secondNum < 0)
			{
				result = true;
			}
		}
		else
		{
			if(secondNum >= 0)
			{
				return true;
			}
		}

		return result;
	}
}