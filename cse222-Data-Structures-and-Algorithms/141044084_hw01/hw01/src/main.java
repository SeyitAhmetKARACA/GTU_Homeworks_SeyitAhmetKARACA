/**
 * Kutuphane sistemini kullanan sinif.
 * Kullanicidan kimlik ve sifre bilgilerini alip ona uygun olarak
 * sisteme giris yapmasini ve kullanici turune gore sistemde yapabileceklerin
 * islemleri uygulanmasini saglar.
 * 
 * @author KARACA
 */
import java.io.IOException;
import java.util.Scanner;

public class main {
	
	final public static boolean STAFF = true; 
	final public static boolean USER = false; 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		String userID,password;
		int choose=0;
		System.out.print("Please enter your ID :");
		userID = scanner.next();
		System.out.print("Please enter your password :");
		password = scanner.next();
		try{
			SystemUser su = userCreater.createUser(userID,password);
			System.out.println(su.getName()+" "+su.getSurname()+" login the system\n");
			
			if(su.getType() == STAFF){
				while(choose != -1){
					su.menu();
					System.out.println("[-1] Exit");
					System.out.print("    Please select operation : ");
					Staff staff = (Staff) su;
					
					choose = scanner.nextInt();
					switch(choose){
					case 1:
						System.out.println("");
						staff.listBooks();
						System.out.println("");
						break;
					case 2:
						String ISBN,bookName,autorName;
						System.out.println("\n\tPlease enter book serial number,name and autor name to add book.");
						System.out.print("\tSerial number : ");
						ISBN = scanner.next();
						System.out.print("\tName : "); 
						bookName = scanner.next();
						System.out.print("\tAutor name : "); 
						autorName = scanner.next();
						staff.addBook(ISBN, bookName, autorName);
						System.out.println("");
						break;
					case 3:
						System.out.print("\nPlease enter a serial number to remove book from book :");
						String removeISBN;
						removeISBN =scanner.next();
						staff.removeBook(removeISBN);
						break;
					case 4:
						String addUserID, addUserName, addUserSurname,addUserPassword;
						System.out.println("\n\tPlease enter ID, name , surname and password to add user");
						System.out.print("\tUser ID : "); addUserID = scanner.next();
						System.out.print("\tUser name : "); addUserName = scanner.next();
						System.out.print("\tUser surmame : "); addUserSurname = scanner.next();
						System.out.print("\tUser password : "); addUserPassword = scanner.next();
						staff.addUser(addUserID, addUserName, addUserSurname , false,addUserPassword);
						break;
					case 5:
						System.out.print("\n\tPlease enter ID to remove user from database");
						String removeUser = scanner.next();
						staff.removeUser(removeUser);
						break;
					case -1:
						break;
					default:System.err.println("\nYour choose did not found in menu");
					}

				}
			}else if(su.getType() == USER){
				while(choose != -1){
					su.menu();
					System.out.println("[-1] Exit");
					System.out.print("    Please select operation : ");
					User user = (User)su;
					choose = scanner.nextInt();
					switch(choose){
					case 1:
						System.out.println("\n\n");
						user.listBooks();
						System.out.println("");
						break;
					case 2:
						String bookISBN;
						System.out.print("\n\tEnter book serial number to barrow :");
						bookISBN = scanner.next();
						user.barrowBook(bookISBN);
						break;
					case 3:
						String bookReturnISBN;
						System.out.print("\n\tEnter book serial number to return :");
						bookReturnISBN = scanner.next();
						user.returnBook(bookReturnISBN);
						break;
					case -1:
						break;
					default:System.err.println("Your choose did not found in menu");
					}

				}
			}
		}catch(LibraryException e){
			System.err.println("Error :" + e.getMessage());
		}catch(Exception e){
			System.err.println("Error :" + e.getMessage());
		}
	}
}