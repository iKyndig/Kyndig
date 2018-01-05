//	This is the currency calculator program

// imports
// scanner
import java.util.Scanner;
// input Output
import java.io.*;
import java.nio.file.*;
// to format exchange result
import java.text.DecimalFormat;

public class extendedCurrencyCalculator
{
	// string array for currency code
	static String[] currencyCode;
	// double array for rates
	static double[] currencyRate;
	// integer to count the currencies
	static int currencyCount = 0;
	// create 0.00 format for exchange result to output in cash format 
	static DecimalFormat formatCash = new DecimalFormat("0.00"), formatRates = new DecimalFormat("0.00000");
	// declare the default size of the arrays as 200, as there are only 180 currencies in the world.  allows for the possible creation of new currencies 
	static int arraySize = 200;
	// create a global user input Scanner
	static Scanner userInput = new Scanner(System.in); 
 
	// create function to read files
	public static void ReadFile()
 	{
	 	// initialise both arrays
	 	currencyCode = new String[arraySize];
	 	currencyRate = new double[arraySize];
		// counter for currencies
		currencyCount = 0;
	 
	 	// set the file location
	 	File readFile = new File("src/fileIO/rates.txt");
	 
	 	// create an input string to hold the file contents
	 	String readLine = "";
	 
	 	// integer to control the code / rate iteration
	 	int i = 0; 
	 	try
	 	{
		 	// create scanner to read the file
		 	Scanner scanFile = new Scanner(readFile); 
		 
		 	// while loop to read the file to the arrays
		 	while (scanFile.hasNextLine())
		 	{
			 	// read lines one by one
			 	readLine = scanFile.nextLine(); 
			 	// currency code is three letters, use i, but do not increment, so as to ensure alignment of both arrays
			 	currencyCode[i] = readLine.substring(0,3).trim();
			 	// rate is to five decimals, increment i+1, so as to move to next row of arrays 
			 	currencyRate[i++] = Double.valueOf(readLine.substring(4,11).trim()); 
			 	// increment currencyCount to count currencies
			 	currencyCount++;
		 	}
		 	// close Scanner
		 	scanFile.close(); 
	 	} catch (FileNotFoundException e) // handle exception
	 	{ 
	 		// output error message if file cannot be read
		 	System.out.println("ReadFile error, please check location of rates.txt is src/fileIO/rates.txt");
		 	System.exit(1);
	 	} 
 	}
	
	// function to output array contents
	public static void ListArray()
	{
		// print the table header in format
			 	System.out.println("\n---------------" + "\nCode:\tRate:" + "\n---------------"); 
			 	
			 	// loop to print the arrays in line with the header format and
			 	// use formatRates to ensure 5 decimal places are shown
			 	for (int i = 0; i<currencyCount; i++)
			 	{ 
				 	System.out.println(currencyCode[i] + "\t" + formatRates.format(currencyRate[i]) + "\n---------------");  
			 	} 
	}

	// function to identify the array index of a code
 	public static int FindArrayIndex(String Code)
 	{
 		// variable for return value, -1 will never be an array index
	 	int arrayIndex = -1;
	 	
	 	// loop to iterate array
	 	for (int i = 0; i < currencyCode.length; i++)
	 	{
	 		// check against the array contents
		 	if (currencyCode[i].equals(Code))
		 	{
		 		// assign array index to return value
			 	arrayIndex = i;
			 	// break prevents the loop from continuing once found
			 	break;
		 	}
	 	}
	 	// return the array index for the code
	 	return arrayIndex;
 	}

 	// function to confirm if user enters correct code when changing a rate
 	public static int SearchArray(String Code)
 	{
 		// loop to iterate through 
	 	for (int i = 0; i < currencyCode.length; i++)
	 	{
	 		// check against array contents
		 	if (currencyCode[i].equals(Code))
		 	{
		 		// return success if code found
			 	return 1;
		 	}
	 	}
	 	// return fail if code not found
	 	return 2;
 	}
 	
 	// function for a user to change a rate
 	public static void ChangeRates()
 	{
 		// local variables
 		String oldLine = "", newLine = "", oldCode = "", userRate = "", userSave = "";
 		double newRate = 0;
		Boolean confirmRate = false;
 		
		// local variable
		int codeFound = 0;
 				
		// do while loop to request user code and check against array contents
		do
		{
			System.out.println("\nPlease enter the currency code: ");
			String userCode = userInput.nextLine();
			oldCode = userCode.toUpperCase();
 					
			// implement function to search array
			codeFound = SearchArray(oldCode);
		}while(codeFound == 2);
 			
		// do while loop to request rate and check if can be turned into a double
		do
		{
			// request new rate
			System.out.println("\nPlease enter the new rate: ");
			userRate = userInput.nextLine();
			
			// confirm input can be converted to double
			newRate = Double.valueOf(userRate);
 		 			
			// get the user input rate length
			int userRateLength = userRate.length();

			// if, else if to pad user input
			if(userRateLength == 1)
			{
				userRate = (userRate + ".00000");
				confirmRate = true;
			}
			else if(userRateLength == 3)
			{
				userRate = (userRate + "0000");
				confirmRate = true;
			}
			else if(userRateLength == 4)
			{
				userRate = (userRate + "000");
				confirmRate = true;
			}
			else if(userRateLength == 5)
			{
				userRate = (userRate + "00");
				confirmRate = true;
			}
			else if(userRateLength == 6)
			{
				userRate = (userRate + "0");
				confirmRate = true;
			}
			else if(userRateLength == 7)
			{
				//newRate = Double.valueOf(userRate);
				confirmRate = true;
			}
			// else if to ensure only 5 decimals are accepted
			else if(userRateLength >= 8)
			{
				// convert string to int
				userRate = userRate.substring(0, 7).trim();
				confirmRate = true;
			}
		}while(confirmRate == false && newRate == Double.valueOf(userRate));

		// variable for the old rate string
 		String oldRateString = "";
 				
		// identify array position of currency code
		int arrayPosition = FindArrayIndex(oldCode);

 				
		// variable to hold the old rate for the replacement process
		double oldRate = currencyRate[arrayPosition];
		
		// ensure the old rate string has 5 decimals
		oldRateString = formatRates.format(oldRate);
 		
		// get the old rate length
		int oldRateLength = oldRateString.length();

		// if, else if to pad the old rate string to 5 decimals
		if(oldRateLength == 1)
		{
			oldRateString = (oldRateString + ".00000");
		}
		else if(oldRateLength == 3)
		{
			oldRateString = (oldRateString + "0000");
		}
		else if(oldRateLength == 4)
		{
			oldRateString = (oldRateString + "000");
		}
		else if(oldRateLength == 5)
		{
			oldRateString = (oldRateString + "00");
		}
		else if(oldRateLength == 6)
		{
			oldRateString = (oldRateString + "0");
		}
		// else if to ensure only 5 decimals are included
		else if(oldRateLength >= 8)
		{
			oldRateString = (oldRateString.substring(0, 6).trim());
		}
 				
		// create a string duplicating the file line to be replaced
		oldLine = (oldCode + " " + oldRateString);
		// create a string holding the replacement line
		newLine = (oldCode + " " + userRate);

		do
		{
		// ask user if they want to save
		System.out.println("\nWould you like to save the new rate?\n(Y)es, (N)o.");
		userSave = userInput.nextLine();
		
			if(!userSave.equalsIgnoreCase("Y") && !userSave.equalsIgnoreCase("N"))
			{
				System.out.println("Sorry, that wasn't recognised!");
			}
	
		}while(!userSave.equalsIgnoreCase("Y") && !userSave.equalsIgnoreCase("N"));
		
		// if user enters y
		if(userSave.equalsIgnoreCase("Y"))
		{
			// try catch block to call the function to update the file	
			try
			{
				// call the function to replace the line in the file
				ReplaceRate("src/fileIO/rates.txt", oldLine, newLine);
				ReadFile();
				ListArray();
			}catch(Exception e) // handle exceptions
			{
				System.out.println("Oops, something went wrong. Please restart and try again.");
				System.exit(1);
			}
		}
		// in case the user doesn't want to save, just update the loaded array without saving to file
		if(userSave.equalsIgnoreCase("N"))
		{
			newRate = Double.valueOf(userRate);
			currencyRate[arrayPosition] = newRate;
			ListArray();
		}
 	 }
 	
 	// function to replace a file line which must be passed three variables
 	static void ReplaceRate(String filePath, String oldLine, String newLine)
    {
 		// accept the first variable
        File fileToChange = new File(filePath);
        
        // create string for old content
        String oldContent = "";
        
        // create reader
        BufferedReader reader = null;
        
        // create writer
        FileWriter writer = null;
        
        // try catch finally block for line replacement
        try
        {
        	// create a reader for the file modification
            reader = new BufferedReader(new FileReader(fileToChange));
             
            //Reading all the lines of input text file into oldContent     
            String fileLine = reader.readLine();
            
            // ensure null lines are not read
            while (fileLine != null) 
            {
            	// properly format the line when read
                oldContent = oldContent + fileLine + System.lineSeparator();
                
                // read lines with content
                fileLine = reader.readLine();
            }
             
            // replace the old line with the new line
            String newContent = oldContent.replaceAll(oldLine, newLine);
             
            // set the file writer
            writer = new FileWriter(fileToChange);
            
            // write the new content to the file
            writer.write(newContent);
        }catch (IOException e) // handle replace error
        {
            System.out.println("Could not write to file. Please restart and try again!");
        }
        // last part of replace function
        finally
        {
        	// try catch to close the reader and writer
            try
            {
                // close the reader and writer
                reader.close();
                writer.close();
            } 
            catch (IOException e) // handle IO errors
            {
                System.out.println("Something went wrong.  Please restart the program and try again!");
                System.exit(1);
            }
        }
    }
 	
 	// function to calculate the exchange
 	public static void ConvertCurrency()
 	{
 		// declare the local variables
		int codeFound = 0, inGBP = 0;
		String oldCode = "", userCode = "", changeGBP = "";
 		
		// do while loop to only accept codes found in the array
 		do
			{
 	 			// request code from user
 	 			System.out.println("\nPlease enter the code of the currency being bought: ");
 	 			userCode = userInput.nextLine();
 	 			
 	 			// ensure code is upper case
				oldCode = userCode.toUpperCase();
				
				// implement function to search array for the code from user
				codeFound = SearchArray(oldCode);
			}while(codeFound == 0);

 		// do while loop to only accept an input which can be converted to double
 		do
 		{
 			// request GBP
 			System.out.println("\nPlease enter the amount of GBP to be converted: ");
 			changeGBP = userInput.nextLine();
 			
 			 // convert string to int
 			inGBP = Integer.valueOf(changeGBP);
 		}while(inGBP != Integer.valueOf(changeGBP));
 		 			
 			// find the array index of the code
 			int arrayPosition = FindArrayIndex(oldCode);	

 			// use array index of code to select rate from same index
 			double rate = currencyRate[arrayPosition];
 			
 			// output the exchange results
 			System.out.println("\n" + inGBP + " GBP is " + userCode + " @ " + currencyRate[arrayPosition] + " for " + formatCash.format(inGBP * rate) + " " + currencyCode[arrayPosition] + ".");
 		
 		// show the arrays again
 		ListArray();
 	}
 	
 	// function for a user to change a rate, if Y is sent from main
 	public static void AddRates()
 	{
 		// local variables
 		String userRate = "", newCode = "", newRate = "", newLine = "";
 		double addRate = 0;
		int threeChar = 0, codeIsFound = -1;
		boolean rateIsCorrect = false, codeIsCorrect = false;
 				
		// do while loop to request user code and check against array contents
		do
		{
			System.out.println("\nPlease enter the new currency code: ");
			String userCode = userInput.nextLine();
			newCode = userCode.toUpperCase();
			threeChar = newCode.length();
			if(threeChar == 3)
			{
				codeIsFound = SearchArray(newCode);
				if(codeIsFound == 0)
				{
					codeIsCorrect = true;
					System.out.println("That code is already in use, please use the change rate function!!");
				}
			}
			else if(threeChar != 3)
			{
				System.out.println("Please use a three letter currency code!");
			}
		}while(codeIsCorrect == false);
 			
		// do while loop to request rate and check if can be turned into a double
		do
		{
			// request new rate
			System.out.println("\nPlease enter the new rate: ");
			userRate = userInput.nextLine();
			newRate = userRate;
			
			// confirm input can be converted to double
			addRate = Double.valueOf(userRate);
 		 			
			// get the user input rate length
			int userRateLength = userRate.length();

			// if, else if to pad user input
			if(userRateLength == 1)
			{
				newRate = (newRate + ".00000");
				rateIsCorrect = true;
			}
			else if(userRateLength == 3)
			{
				newRate = (newRate + "0000");
				rateIsCorrect = true;
			}
			else if(userRateLength == 4)
			{
				newRate = (newRate + "000");
				rateIsCorrect = true;
			}
			else if(userRateLength == 5)
			{
				newRate = (newRate + "00");
				rateIsCorrect = true;
			}
			else if(userRateLength == 6)
			{
				newRate = (newRate + "0");
				rateIsCorrect = true;
			}
			else if(userRateLength == 7)
			{
				//newRate = Double.valueOf(userRate);
				rateIsCorrect = true;
			}
			// else if to ensure only 5 decimals are accepted
			else if(userRateLength >= 8)
			{
				// convert string to int
				newRate = userRate.substring(0, 7).trim();
				rateIsCorrect = true;
			}
		}while(rateIsCorrect == false && addRate == Double.valueOf(userRate));

		// try catch block to call the function to update the file	
		try {
			newLine = (newCode + " " + newRate + System.lineSeparator());
			Files.write(Paths.get("src/fileIO/rates.txt"), newLine.getBytes(), StandardOpenOption.APPEND);
			ReadFile();
			ListArray();
		}catch(Exception e) // handle exceptions
		{
			System.out.println("Oops, something went wrong. Please restart and try again.");
			System.exit(1);
		}
 	}


 // main function to control program flow
 	public static void main(String[] args)
 	{ 
 		// welcome message
 		System.out.println("Lets convert some currency!"); 
 		String userString = " "; 

 		// first read the file
 		ReadFile();
 		
 		// display the codes and rates
 		ListArray();
 		
 		// do while loop to accept correct or reject incorrect user input
 		do
 		{
 			// offer options to the user
 			System.out.println("\nWould you like to change an exchange rate, add a new currency and rate, exchange some currency or quit? "); 
 			System.out.println("C(hange), A(dd), E(xchange), Q(uit)");                                                                           
 			userString = userInput.nextLine();
 		
 			// only accept specified options
 			if(!(userString.equalsIgnoreCase("C") || !userString.equalsIgnoreCase("A") || !userString.equalsIgnoreCase("E") || !userString.equalsIgnoreCase("Q") || userString != " "))
 			{
 				System.out.println("\nInput not recognised, please try again!");
 			}
 		
 		}while(!(userString.equalsIgnoreCase("C") || userString.equalsIgnoreCase("A") || userString.equalsIgnoreCase("E") || userString.equalsIgnoreCase("Q") || userString != " "));
 		
 		// do while loop to act upon recognised input. repeat process unless user quits
 		do
 		{ 
 			try
 			{

 				// action for changing rates
 				if (userString.equalsIgnoreCase("C")) 
 					ChangeRates();

 				// action for adding a new rate
 				else if (userString.equalsIgnoreCase("A")) 
 					AddRates();
 				
 				// action for exchanging currency
 				else if (userString.equalsIgnoreCase("E")) 
 					ConvertCurrency(); 
 				
 				// hidden option to read file and display arrays (for testing option of not saving)
 				else if (userString.equalsIgnoreCase("RFL"))
 				{
 					ReadFile();
 					ListArray();
 				}
 								
 				// quit if requested to do so
 				else if(userString.equalsIgnoreCase("Q"))
 					break;
 				
 				// reject incorrect input
 				else if (!userString.equalsIgnoreCase("Q")) 
 					System.out.println("Sorry, I didn't recognise that!");
 				
 				// this is to control further program use
 				System.out.println("\nWould you like to change an exchange rate, add a new currency and rate, exchange some currency or quit? "); 
 				System.out.println("C(hange), A(dd), E(xchange), Q(uit)");                                                                           
 				userString = userInput.nextLine();

 			}catch (Exception e) // fail-safe to handle incorrect input
 			{ 
 				System.out.println("Exception e error! Sorry, I didn't recognise that!"); 
 			} 
 		} while (!userString.equalsIgnoreCase("Q")); 
 	  
 		// close input
 		userInput.close(); 
 	  
 		// exit message
 		System.out.println("Thank you for using the currency convertor!");
 		System.exit(1);
 	} 
 }