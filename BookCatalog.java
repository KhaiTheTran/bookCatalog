
package bookCatalog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/*Assignment : #4 Book Cataloging System
 * Name: Khai Tran
 * Date: 06/09/2017
 * Course: CSC&143
 * Instructor: Ravi Gandham  
 * File Name: BookCatalog.java
 * Program Description: Write a program to manage a Book Catalog. The catalog would contain a collection of
books, with following fields of information: book code (unique for each book; must be
valid – see ISBN validation rules in this assignment for more info), author’s last name,
author’s first name, book title, year of publication and price. The book catalog should
use a file for storing books.
 */
public class BookCatalog {
	private ListNote noteHead;
	private LogNode logchangeNode;
	private int counterRow=1;
	private Book bookOb;
	private int countDulicate=0;
	/**
	 * 
	 */
	
	// read all data from the file to catalog except the duplicate one
	public void validBooklist(String fileName) throws FileNotFoundException{
		
		Scanner data = new Scanner(new File(fileName));
		while(data.hasNextLine()){
			String[] valid =data.nextLine().split("\t");
			
				bookOb= new Book(valid);
				if(bookOb.toString().equalsIgnoreCase("error")){
					System.out.println("There is a invalid book in the file with the ISBN: "+valid[0]+" and author name: "+valid[1]+" not in the catalog!");
					System.out.println();
				} else {if(!add(bookOb)){
					addLogchange("Duplicate deleted from catalog with ISBN-> "+bookOb.toString());// save log
						System.out.println("This book is duplicated ISBN: "+valid[0]+" from "+valid[1]+ " was found!");	
					}
				}
		}
		data.close();
		System.out.println("Deleted duplicate in the catalog is: "+countDulicate);
	}
	// add a book to catalog except the duplicate one
	public boolean add(Book dataB){
		//generate note
		boolean validate =false;
		String[] checkDulicate= dataB.toString().split("\t");
		if(noteHead ==null){
			noteHead= new ListNote(dataB);
			validate = true;
		}else{
			ListNote curStanding = noteHead;
			while(curStanding.next !=null){
				curStanding= curStanding.next;
			}
			// ignore dulicate data and save to the catalog
			if(searchBook(checkDulicate[0].trim(),"a").equalsIgnoreCase("no")) {
				curStanding.next= new ListNote(dataB);
				counterRow++;// count the number of book
				validate = true;
			} else{
				countDulicate++;
				validate= false;
				}	
		}
		return validate;
	}
	
	// tracking log change from catalog
	public void addLogchange(String dataLog){
		if(logchangeNode ==null){
			logchangeNode= new LogNode(dataLog);
		}else{
			LogNode curStanding = logchangeNode;
			while(curStanding.next !=null){
				curStanding= curStanding.next;
			}
			curStanding.next= new LogNode(dataLog);
		}
	}
	// looking a book from catalog
	public String searchBook(String ISBN,String condtion){

		String dulicateRecord="no";
		ListNote curStanding = noteHead;// call book catalog
		
		while(curStanding !=null){
			
			String[] dataISBN = curStanding.data.toString().split("\t");
			//search ISBN
			if(condtion.equalsIgnoreCase("a")){
				if(dataISBN[0].trim().equalsIgnoreCase(ISBN)){ 
					return dulicateRecord = curStanding.data.toString();
				}	
			}else
			//Search last name
			if(condtion.equalsIgnoreCase("b")){
				if(dataISBN[1].trim().equalsIgnoreCase(ISBN)) {
					return	dulicateRecord = curStanding.data.toString();
				}
			}else
			//Search first name
			if(condtion.equalsIgnoreCase("c")){
				if(dataISBN[2].trim().equalsIgnoreCase(ISBN)) {
				return	dulicateRecord = curStanding.data.toString();
				}
			}
			curStanding= curStanding.next;
		}
		return dulicateRecord;
	}
	//pre: 0<= i <  index
	//post: return a reference to the note with giving index
	private ListNote noteAtIndex(int index){
		ListNote curStanding = noteHead;
		for(int i=0; i<index-1;i++){
			curStanding= curStanding.next;
		}
		return curStanding;
	}
	
	//remove book by ISBN
	// remove with the number of duplicate and stop if there is only one ISBN of that book
	public boolean removeCatalogData(String ISBN){
		ISBN= ISBN.trim();
		ListNote curStanding= noteHead;
		int index=0;
		while(curStanding !=null){
			String[] dataISBN = curStanding.data.toString().split("\t");
			if(dataISBN[0].equalsIgnoreCase(ISBN)){
				curStanding= noteAtIndex(index);
				
				if(index==0){
					addLogchange("A book was deleted from the catalog with ISBN-> "+curStanding.data.toString());// deleted log
					noteHead = curStanding.next; // delete node root
				}else{
					addLogchange("A book was deleted from the catalog with ISBN-> "+curStanding.next.data.toString());// deleted log
					curStanding.next= curStanding.next.next;
				}
				counterRow--;
				return true;
			}
			index++;
			curStanding= curStanding.next;
		}
		return false;
	}
	// save catalog to the file
	public boolean saveFile() throws FileNotFoundException{
		PrintStream write_catalog = new PrintStream(new File("src/booklist.txt"));
		if(noteHead !=null){
			ListNote curStanding = noteHead;
			while(curStanding !=null){
				write_catalog.println(curStanding.data.toString());
				curStanding= curStanding.next;
			}
			write_catalog.close();
			return true;
		}
		return false;
	}
	// read all the data from the catalog
	public void readCatalogData(){
		if(noteHead !=null){
			ListNote curStanding = noteHead;
			while(curStanding !=null){
				System.out.println(curStanding.data.toString());
				curStanding= curStanding.next;
			}
		}
	}
	// Save log change from catalog
	public boolean saveLogchangeFile() throws FileNotFoundException{
		PrintStream write_catalog = new PrintStream(new File("src/logCatalogchange.txt"));
		if(logchangeNode !=null){
			LogNode curStanding = logchangeNode;
			while(curStanding !=null){
				write_catalog.println(curStanding.datalog.toString());
				curStanding= curStanding.next;
			}
			write_catalog.close();
			return true;
		}
		return false;
	}
	//read all data from log change
	public void readlogChange(){
		if(logchangeNode !=null){
			LogNode curStanding = logchangeNode;
			while(curStanding !=null){
				System.out.println(curStanding.datalog.toString());
				curStanding= curStanding.next;
			}
		}
	}
	// return number of data
	public int getcounterRow(){
		return this.counterRow;
	}
// the node for book catalog
public class ListNote{
	// data will be store in listNote
	public Book data;
	// reference the ListNote together
	public ListNote next;
	
	public ListNote(ListNote next){
		this.next=next;
	}
	// constructor
	public ListNote(Book datain){
		next = null;
		this.data= datain;
	}
	// store data to the note
	public ListNote(Book dataIn, ListNote next) {
		// TODO Auto-generated constructor stub
		this.data = dataIn;
		this.next= next;
	}
}
//the node for book catalog log change
public class LogNode{
	// data will be store in LogNode
	public String datalog;
	// reference the LogNode together
	public LogNode next;
	
	public LogNode(){
		this(null);
	}
	// constructor
	public LogNode(String datain){
		next = null;
		this.datalog= datain;
	}
	// store data to the note
	public LogNode(String dataIn, LogNode next) {
		// TODO Auto-generated constructor stub
		this.datalog = dataIn;
		this.next= next;
	}
}

}


