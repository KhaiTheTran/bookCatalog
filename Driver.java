
package bookCatalog;

import java.io.FileNotFoundException;
import java.util.Scanner;
/*Assignment : #4 Book Cataloging System
 * Name: Khai Tran
 * Date: 06/09/2017
 * Course: CSC&143
 * Instructor: Ravi Gandham  
 * File Name: Driver.java
 * Program Description: Write a program to manage a Book Catalog. The catalog would contain a collection of
books, with following fields of information: book code (unique for each book; must be
valid – see ISBN validation rules in this assignment for more info), author’s last name,
author’s first name, book title, year of publication and price. The book catalog should
use a file for storing books.
 */
public class Driver {
	
private static BookCatalog bookcatalog;
private static Book bookValid;
private static Scanner console;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bookcatalog = new BookCatalog();
		console= new Scanner(System.in);
		 String checkCondition= "booklist.txt";
		
		 int choice =0;
		
		try {
			bookcatalog.validBooklist("src/"+checkCondition);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("There is something wrong with the data of the "+checkCondition+" or The "+checkCondition+" is not found!");
			
		}
		//initial data
		readCatalogMain();
		
		while(!checkCondition.equalsIgnoreCase("Close")){
			checkCondition="in";
			System.out.println();
			System.out.println("Please, choose a number to do: ");
			System.out.println("\n1. Add a new book to the catalog.");
			System.out.println("\n2. Find a book from catalog.");
			System.out.println("\n3. Delete an existing book.");
			System.out.println("\nTo close the program enter 'Close'!");
			checkCondition=console.nextLine();// waiting for choice
			// to go to the save file and exit the program
						if(checkCondition.equalsIgnoreCase("Close")) {
							checkCondition="Exit";
						}
						// integer exception
						try {
							choice = Integer.parseInt(checkCondition); 
						} catch (Exception e) {
							// TODO: handle exception
							if(!checkCondition.equalsIgnoreCase("Exit")) System.out.println("You should enter a digit! The program will direct to default choice!"); 
							System.out.println();
							choice=999;
						}
			
			// main console working program
			int count=0;
			while(!checkCondition.equalsIgnoreCase("Exit")){
				String ISBN ="";
				
				if(choice ==1) { 
					//book code
					String[] str_bookCatalog= {"a","b","b","b","b","b"};
					bookValid= new Book(str_bookCatalog);
					System.out.println("Please, enter the book code with correct the ISBN rules:");
					str_bookCatalog[0]=console.nextLine();
					checkCondition="good";
					
					//author's Last name
					if(bookValid.validBookcode(str_bookCatalog[0].trim())){
					System.out.println("Please, enter the author's last name with (can't start with numbers):");
					str_bookCatalog[1]=console.nextLine();
					} else{
						
						if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("The book is not correct! It should follow the ISBN validation rules.");
						checkCondition="Error";
					}
					
					//author's first name
					if(bookValid.validName(str_bookCatalog[1].trim())&& !checkCondition.equalsIgnoreCase("Error")){	System.out.println("Please, enter the author's first name (can't start with numbers):");
					str_bookCatalog[2]=console.nextLine();
					}else{
						if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("Last name can not start with number and be empty!");
						checkCondition="Error";
					}
					//book title
					if(bookValid.validName(str_bookCatalog[2].trim())&& !checkCondition.equalsIgnoreCase("Error")){	System.out.println("Please, enter the book title:");
					str_bookCatalog[3]=console.nextLine();
					}else{
						
						if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("First name can not start with number and be empty!");
						checkCondition="Error";
					}
					//book year of publication
					if(bookValid.validTitle(str_bookCatalog[3].trim())&& !checkCondition.equalsIgnoreCase("Error")){	System.out.println("Please, enter the year of publication (less than 2013):");
					str_bookCatalog[4]=console.nextLine();
					}else{
						
						if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("Title can not be empty!");
						checkCondition="Error";
					}
					//book price
					if(bookValid.validyear(str_bookCatalog[4].trim())&& !checkCondition.equalsIgnoreCase("Error")){	System.out.println("Please, enter the book price (no negative or zero):");
					str_bookCatalog[5]=console.nextLine();
					}else{
						
					if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("Year should be digits and not be greater than 2013!");
						checkCondition="Error";
					}
					if(!bookValid.validPrice(str_bookCatalog[5].trim()) && checkCondition.equalsIgnoreCase("Error")){
							if(!checkCondition.equalsIgnoreCase("Error")) System.out.println("Price is not negative or zero!");
							checkCondition="Error";
						}
					
					// validate all book data
					bookValid= new Book(str_bookCatalog);
					String[] saveB= bookValid.toString().split("\t");
					// saving data to catalog
					if(saveB[0].equalsIgnoreCase(str_bookCatalog[0].trim()) && bookcatalog.add(bookValid)){
						
						readCatalogMain();// read data from catalog
						bookcatalog.addLogchange("New book was saved to the catalog with ISBN-> "+bookValid.toString());// save log
						System.out.println();
						System.out.println("The new book has been saved to the catalog!");
						checkCondition="Exit";
					}else {
						if(!checkCondition.equalsIgnoreCase("Error")){System.out.println("Duplicated Error! (or) "+bookValid.toString());
						System.out.println("The new book has not been saved!");}
						System.out.println();
					}
	
				}else
				//Finding book
				if(choice == 2){
					System.out.println("Please, enter letter 'A' to find the a book by ISBN in catalog!");
					System.out.println("Please, enter letter 'B' to find the a book by Last Name in catalog!");
					System.out.println("Please, enter letter 'C' to find the a book by First Name in catalog!");
					String findB= console.nextLine().trim();
					
					//find by ISBN
					if(findB.equalsIgnoreCase("A")){
						
						findbookCalog(ISBN,findB);
					// find by First Name		
					}else if(findB.equalsIgnoreCase("B")){
						
						findbookCalog(ISBN,findB);
						// find by last Name
					} else if(findB.equalsIgnoreCase("C")){
						findbookCalog(ISBN,findB);
					}else{
						if(count !=3){System.out.println("Please, enter 'Yes' to continue or 'Exit' to exit finding!");
						checkCondition= console.nextLine();}
					}
				// delete an existing book	
				}else if(choice == 3){
					System.out.println("Please, enter the ISBN of book to delete!");
					ISBN= valibookISBN(ISBN,"a");
				if(bookcatalog.removeCatalogData(ISBN)){
					readCatalogMain();
					System.out.println();
					System.out.println("The book with "+ISBN+" was deleted!");
					System.out.println();
					if(count !=3){System.out.println("Please, enter 'Yes' to continue or 'Exit' to exit deleting!");
					checkCondition= console.nextLine();}
					}else {
					System.out.println();
					System.out.println("Deleting  not successful!");
					System.out.println("The ISBN was not matched!");
					System.out.println();
					}
				}
				if(count ==3){
					count=0;
					System.out.println();
					System.out.println("Please, enter 'Yes' to continue or 'Exit' to go to the main menu!");
					console= new Scanner(System.in);
					checkCondition= console.nextLine();
					}
					count++;
			}
			if(choice==999){
				System.out.println("Would you like to save the catalog to file! Yes or No");
				checkCondition=console.nextLine().trim();
				if(checkCondition.equalsIgnoreCase("Yes")){
				try {
					if(bookcatalog.saveFile()) {
						System.out.println("The catalog was Saved!");
						System.out.println();
						System.out.println("Have a wonderful day!");
						System.out.println();
						if(bookcatalog.saveLogchangeFile()) System.out.println("The changed log below was saved to 'src/logCatalogchange.txt'");
						System.out.println();
						bookcatalog.readlogChange();
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("The catalog was not Saved!");
					System.out.println();
					System.out.println("The changed log was not Saved!");
				}
				}else {
					System.out.println("The catalog was not Saved!");
					System.out.println();
					System.out.println("Have a wonderful day!");
				}
				checkCondition="Close";
				
			}
		}
	}
	
	// finding book return result
	public static void findbookCalog(String ISBN, String findB){
		ISBN= valibookISBN(ISBN,findB).trim();
		String a= bookcatalog.searchBook(ISBN, findB);
		if(a.equalsIgnoreCase("no")){
			System.out.println("Sorry, we can not find the book!");
			System.out.println();
		} else {
			System.out.println();
			System.out.println(a);
			System.out.println();
		}
	}
	
	// finding book
	public static String valibookISBN(String ISBNb, String findB){
			if(findB.equalsIgnoreCase("a")){
				System.out.println("Please, enter the correct rules of book ISBN!");
			}else if (findB.equalsIgnoreCase("b")){
				System.out.println("Please, enter the valid author's Last Name!");
			} else System.out.println("Please, enter the valid author's First Name!");
			console= new Scanner(System.in);
			ISBNb=console.nextLine();
			
		return ISBNb;
	}
	// read and rewrite the catalog
	public static void readCatalogMain(){
		System.out.println();
		System.out.println("The Catalog Book below is: "+bookcatalog.getcounterRow()+" row(s)!");
		System.out.println();
		bookcatalog.readCatalogData();	
	}
	
}
