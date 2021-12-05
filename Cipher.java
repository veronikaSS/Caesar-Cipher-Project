/* Written in Java
 * Copryright 2021, Veronika Schuermann, all rights reserved
 * 
 * Caesar Cipher
 * This program allows users to encrypt a given text file using a caesar cipher.
 * A caesar cipher encrypts text by "shifting" each letter a certain number of
 * places backwards or forwards in the alphabet. The amount the letters are shifted
 * is called the shift of the cipher.
 * In this program, once the user has provided the name of the file to be encrypted
 * and the shift amount, a new file containing the encrypted version of the given
 * file will be generated. Additionally, the encrypted text will be printed to output.
*/


import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;


public class Cipher {
	
	
	
	public static void main(String[] args)
	{		
		Scanner fileIn = null;
		Scanner keyboard = new Scanner(System.in);
		boolean validInput;
		String cipherName = null;
		int shift = 0;

		
		
		System.out.println("Welcome to this Caeser Cipher program!");
		System.out.println("How much of a shift will this cipher be using?");
		
		//Prompts the user for the shift for this cipher. Loops until a valid response is provided.
		do
		{
			validInput = true;
			
			
			//attempts to parse the user's response to a valid shift amount
			try
			{
				System.out.println("Please, provide a non-zero number between -13 and 13.");
				shift = Integer.parseInt(keyboard.nextLine());
				
				//checks that the shift is in bounds of the specified range
				if(shift < -13 || shift > 13 || shift == 0)
					throw new NumberFormatException();
			}
			
			//catches the exception if the user's response was invalid
			catch(NumberFormatException e)
			{
				System.out.println("Invalid response.");
				validInput = false;
			}
		}
		while(!validInput);
		
		
		
		System.out.println("Please, provide the name of the text file you would like to cipher:");
		
		//Prompts the user for the name of the file to be ciphered. Loops until a valid file name is provided.
		do
		{
			validInput = true;
			
			
			//Attempts to create the cipher if the given file name is valid
			try
			{
				//gets the file name and creates a scanner to read from the file
				String fileName = keyboard.nextLine();
				fileIn = new Scanner(new File(fileName));
				
				
				//creates the new cipher file where the cipher will be written
				int dotIndex = fileName.lastIndexOf(".");
				cipherName = fileName.substring(0, dotIndex) + "_CIPHER" + shift + fileName.substring(dotIndex);
				File cipherFile = new File(cipherName);
				
				//creates the new cipher file, checking if it already exists
				if(!cipherFile.createNewFile())
					throw new Exception();
					
				FileWriter cipherWriter = new FileWriter(cipherFile);
				

				System.out.println("\nCIPHER:");
				
				//ciphers each line of the original file and writes the ciphered line to the cipher file
				while(fileIn.hasNextLine())
				{
					String line = fileIn.nextLine();
					
					//ciphers each character of the line
					for(int i = 0; i < line.length(); i++)
					{
						int newASCIIval = line.charAt(i);
						
						
						//only applies the cipher if the given character is a letter
						if(Character.isLetter(newASCIIval))
						{
							//attempts to calculate the new ASCII value of the letter
							newASCIIval += shift;
							int aVal, zVal;
							
							
							//gets the ASCII values for a and z if the character is lowercase
							if(Character.isLowerCase(line.charAt(i)))
							{
								aVal = 'a';
								zVal = 'z';
							}
							
							//gets the ASCII values for A and Z if the character is uppercase
							else
							{
								aVal = 'A';
								zVal = 'Z';
							}
							
							//given there is a negative shift, checks if the cipher loops around
							//to the other end of the alphabet
							if(shift < 0 && newASCIIval < aVal)
								newASCIIval = (zVal + 1) - (aVal - newASCIIval);

							//given there is a positive shift, checks if the cipher loops around
							//to the other end of the alphabet
							else if(shift > 0 && newASCIIval > zVal)
								newASCIIval = (aVal - 1) + (newASCIIval - zVal);
						}
		
						
						//adds the ciphered character to the cipher file and prints it to output
						char cipherChar = (char)newASCIIval;
						cipherWriter.write(cipherChar);
						System.out.print(cipherChar);
					}
					
					cipherWriter.write("\n");
					System.out.println();
				}
				
				
				cipherWriter.close();
				fileIn.close();
				
				System.out.println("\nYour file has been encrypted under the name " + cipherName);
			}
			
			//Checks if the given file name cannot be found
			catch(FileNotFoundException e)
			{
				System.out.println("Error: That file cannot be found. Please, provide a valid file name.");
				validInput = false;
			}
			
			//Checks if the cipher file could be created successfully
			catch(Exception e)
			{
				System.out.println("The shift " + shift + " cipher for that file already exists under the name " + cipherName);
			}
		}
		while(!validInput);
		
		
		
		System.out.println("\nGoodbye!");
	}

}
