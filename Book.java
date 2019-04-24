
package bookCatalog;

/*Assignment : #4 Book Cataloging System
 * Name: Khai Tran
 * Date: 06/09/2017
 * Course: CSC&143
 * Instructor: Ravi Gandham  
 * File Name: Book.java
 * Program Description: Write a program to manage a Book Catalog. The catalog would contain a collection of
books, with following fields of information: book code (unique for each book; must be
valid – see ISBN validation rules in this assignment for more info), author’s last name,
author’s first name, book title, year of publication and price. The book catalog should
use a file for storing books.
 */
public class Book {
private String bookCode="";
private String authorLastname = "";
private String authorFirstname="";
private String bookTitle ="";
private String yearPublication= "";
private String price = "";
private String numValid="0123456789";
private String errorData;
	/**
	 * 
	 */
	public Book(String[] bookData) {
		// TODO Auto-generated constructor stub
		errorData = "error";
		setBookcode(bookData[0].trim());
		setAuthorFirstname(bookData[1].trim());
		setAuthorLastname(bookData[2].trim());
		setbookTitle(bookData[3].trim());
		setyearPublication(bookData[4].trim());
		setPrice(bookData[5].trim());		
	}
	
//valid the ISBN
public boolean validBookcode(String ISBN){
	
	String checkISBN= "0123456789-X";
	String isbnNumx="";
	ISBN= ISBN.trim(); // remove white space
	String isbnNum = ISBN.replaceAll("(?:\\-|(?:X))", "");// cut dash to check all number
	if(isbnNum.length()<9)  return false;//013-1134-0 (too few digits)
	
	String lastNum = ISBN.substring(ISBN.length()-1,ISBN.length());
	// check if ISBN has X or not
	if(lastNum.equals("X")) {
	isbnNumx = isbnNum;//.substring(0, isbnNum.length());
	}else isbnNumx = isbnNum.substring(0, isbnNum.length()-1);
	
	//System.out.println(isbnNumx);
	// check valid -013-117334-0 (beginning or ending dash)
	if(ISBN.substring(0,1).equals("-") ||lastNum.equals("-"))  return false;
	
	if(ISBN.length()>13) return false; //0-201-88337-63 (too many digits) 
	//if(isbnNumx.length()!=9)  return false;//013-1134-0 (too few digits)
	
	//check char
	int checksum=0;
	int count=0;
	for(int i=0; i<ISBN.length();i++){
		// check  (bad digit) and ISBN contains "0123456789-X" or not
		if(!checkISBN.contains(ISBN.substring(i, i+1))) return false;
		if(ISBN.substring(i, i+1).equals("-")) count++;
		//157231--866-X (sequential dashes) 
		if(ISBN.substring(i, i+1).equals("-") && ISBN.substring(i+1, i+2).equals("-")) return false;
		if(count > 4) return false;//0-201-88-337-6 (too many dashes)
	}
	//count sum
	for(int i=0; i< isbnNumx.length();i++){
	checksum = checksum + Integer.parseInt(isbnNumx.substring(i, i+1))*(i+1);
	}
	
	// taking modulo 11 (the remainder after dividing by 11)
	//0-201-88337-3 (wrong check sum) 
	if(!lastNum.equals("X")){
	if((checksum%11) != Integer.parseInt(isbnNum.trim().substring(isbnNum.length()-1,isbnNum.length()))) return false;
	// The letter X corresponds to a value of 10.
	}else if(lastNum.equals("X") && (checksum%11) != 10) return false; 
	
	if(count ==3 || count ==0){
		return true;//has exactly three dashes  (not enough dashes) System.out.println("wrong check sum");//
	}
	else {
	if(count>0)	return false;
	}
	return true;	
}

// Valid name of book
public boolean validName(String nameB){
	// name can't start with number
	if(nameB.trim().isEmpty() || numValid.contains(nameB.trim().substring(0, 1)))
	return false;
	
	return true;
}
// Valid title of book
public boolean validTitle(String title){
	if(title.trim().isEmpty())
	return false;
	
	return true;
}
//Valid year of book
public boolean validyear(String yearB){
	if(yearB.isEmpty()) return false;
	for(int i=0; i<yearB.length();i++){
		if(!numValid.contains(yearB.substring(i, i+1))) return false;
	}
	if(Integer.parseInt(yearB)<2013)  return true;
	
	return false;
	
}
// Valid price of book
public boolean validPrice(String priceB){
	if(priceB.isEmpty()) return false;
	String numBook="0123456789.";
	for(int i=0; i<priceB.length();i++){
		if(!numBook.contains(priceB.substring(i, i+1))) return false;
	}
	if(Double.valueOf(priceB)>0) return true;
	return false;
}

/**
 *  mutator (set)
 */
//set Book code
public void setBookcode(String ISBN){
	if(validBookcode(ISBN)) this.bookCode=ISBN;
	else errorData= "The book is not correct! It should follow the ISBN validation rules.";
}
//set Author First name
public void setAuthorFirstname(String nameFBook){
	if(validName(nameFBook)) this.authorFirstname=nameFBook;
	else errorData= "Last name can not start with number and be empty!";
}
//set Author Last name
public void setAuthorLastname(String nameLbook){
	if(validName(nameLbook)) this.authorLastname=nameLbook;
	else errorData= "First name can not start with number and be empty!";
}
// set book Title
public void setbookTitle(String bTitle ){
	if(validTitle(bTitle)) this.bookTitle=bTitle;
	else errorData= "Title can not be empty!";
}
public void setyearPublication(String year){
	if(validyear(year)) this.yearPublication=year;
	else errorData= "Year should be digits and not be greater than 2013!";
}
// return set Price
public void setPrice(String priceBook){
	if (validPrice(priceBook)) this.price=priceBook;
	else errorData= "Price is not negative or zero!";
}
/**
 * accessor (get)
 */
//get Book code
public String getBookcode(){
	return this.bookCode;
}
//get Author First name
public String getAuthorFirstname(){
	return this.authorFirstname;
}
//get Author Last name
public String getAuthorLastname(){
	return this.authorLastname;
}
//get book Title
public String getbookTitle(){
	return this.bookTitle;
}
//get year Publication
public String getyearPublication(){
	return this.yearPublication;
}
// return get Price
public String getPrice(){
	return this.price;
}

public String toString(){
	//TODO
	//System.out.println(0);
	if(errorData.equalsIgnoreCase("error")) return bookCode+"\t"+authorFirstname+"\t"+authorLastname+"\t"+bookTitle+"\t"+yearPublication+"\t"+price;
	return errorData;
}
}
