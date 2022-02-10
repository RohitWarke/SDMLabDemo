package tester;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static com.app.core.Category.*;
import com.app.core.Book;
import custom_exception.*;
import static utils.IOUtils.*;
import static utils.CollectionUtils.*;
import static java.time.LocalDate.*;

//code addded by 
//java code added by kanad
//code for serilization added by kanad

public class Tester {

	public static void main(String[] args) {
		
		try(Scanner sc = new Scanner(System.in))
		{
			
			String s = "book.ser";
//			storeBooksDetails(populateMapFromList(populateBooks()), s);
//			System.out.println("Done dana Done");
//			
			
//			HashMap<String, Book> bookMap = new HashMap<>(populateMapFromList(populateBooks()));
			Map<String, Book> bookMap = restoreBookDetails(s);
			Collection<Book> book = bookMap.values();
			boolean flag = false;
			
			while(!flag) {
				
				System.out.println("Option 1.Add Book 2.Display all Books"
						+"3.Issue a book 4.Return a book 5.Remove old Book"
						+"6.Exit");
				try {
				switch (sc.nextInt()) {
				case 1:
					System.out.println("Enter BookName to add in library");
					String bookName = sc.next();
					if(bookMap.containsKey(bookName)) {
						System.out.println("Enter book Quantity to enter in library");
						book.stream()
						.filter(i -> i.getTitle().equals(bookName))
						.forEach(i -> i.setQuantity(i.getQuantity()+sc.nextInt()));
					}else {
						System.out.println("Adding new book");
						System.out.println("Enter Details  type price publishDate authorName quantity" );
											
						Book b1 = new Book(bookName, valueOf(sc.next().toUpperCase()), sc.nextDouble(),parse(sc.next()) , sc.next(), sc.nextInt());
						bookMap.put(bookName, b1);
					}


					break;
				case 2:System.out.println("Displaying Availble Books");
					book.stream()
					.filter(i -> i.getQuantity()>0)
					.forEach(System.out::println);
					
//					bookMap.forEach((k,v) -> System.out.println(v));
					break;
					
				case 3:
					System.out.println("Enter Book Name");
					String issue = sc.next();
				
					if(bookMap.containsKey(issue)) {
						Book book2 = bookMap.get(issue);
						if(book2.getQuantity() > 0) {
						book.stream()
						.filter(i-> i.getTitle().equals(issue))
						.forEach(i -> i.setQuantity(i.getQuantity()-1));
						System.out.println("Return book in 10 days");
						}else {
							throw new BookNotAvailableException("You will have to wait");
						}
					}else {
						throw new InvalidBookEntryException("Entered book is not found enter valid name");
					}

					
					break;
				case 4:
					System.out.println("Enter book name to return");
					String returnBook = sc.next();
					if(bookMap.containsKey(returnBook)) {
						book.stream()
						.filter(i-> i.getTitle().equals(returnBook))
						.forEach(i -> i.setQuantity(i.getQuantity()+1));
						System.out.println("Thanks for returning");
					}else {
						throw new BookNotFoundException("Entered book is found enter valid name");
					}

					break;
				case 5:
					System.out.println("removing old books");
					System.out.println("enter date");
					LocalDate d1 = parse(sc.next());
					book.removeIf(i -> i.getPublishDate().isBefore(d1));

					break;
				case 6:
		
					storeBooksDetails(bookMap, s);
//					restoreBookDetails(s).forEach((k,v)->System.out.println(v));
					flag = true;
					break;
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			sc.nextLine();	
			}
	
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
