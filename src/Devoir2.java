import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Devoir2
{

	public static void main(String[] args) 
	{
		if (args.length == 1)
			readFile(args[0]);
		else
			System.out.println("Argument invalide.");
	}
	
	public static void readFile(String path)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			int nbOfProblems = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < nbOfProblems; i++)
			{
				br.skip(2);
				String polygonStr = br.readLine();
				new Problem(polygonStr);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NumberFormatException e)
		{
			System.out.println("The input is in a wrong format");
			e.printStackTrace();
		}
	}

}


