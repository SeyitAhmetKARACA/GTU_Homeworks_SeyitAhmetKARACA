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

	public static long timeAddBook() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("1","1");
		Staff staff = (Staff)su;

		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000 ; i++)
			staff.addBook(Integer.toString(i),"a","b");
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeRemoveBook() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("1","1");
		Staff staff = (Staff)su;


		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000 ; i++)
			staff.removeBook(Integer.toString(i));
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeAddUser() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("1","1");
		Staff staff = (Staff)su;

		startTime = System.currentTimeMillis();
		for(int i = 3; i < 1003 ; i++)
			staff.addUser(Integer.toString(i),"a","b",false,"0");
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeRemoveUser() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("1","1");
		Staff staff = (Staff)su;

		startTime = System.currentTimeMillis();
		for(int i = 3; i < 1003 ; i++)
			staff.removeUser(Integer.toString(i));
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeListBook() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("1","1");
		Staff staff = (Staff)su;

		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000 ; i++)
			staff.listBooks();
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeBarrowBook() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("2","2");
		User user = (User)su;

		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000 ; i++)
			user.barrowBook(Integer.toString(i));
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}

	public static long timeReturnBook() throws IOException, LibraryException {
		long startTime,stopTime;
		SystemUser su = userCreater.createUser("2","2");
		User user = (User)su;

		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000 ; i++)
			user.returnBook(Integer.toString(i));
		stopTime = System.currentTimeMillis();

		return stopTime-startTime;
	}


	public static void main(String[] args) throws IOException, LibraryException {
		// TODO Auto-generated method stub

		long avAdd=0 , avRm=0;
		avAdd += timeAddBook();
		avRm += timeRemoveBook();

		avAdd += timeAddBook();
		avRm += timeRemoveBook();

		avAdd += timeAddBook();
		avRm += timeRemoveBook();
		avAdd /= 3;
		avRm /= 3;
		System.out.println("Kitap ekleme suresi :"+avAdd);
		System.out.println("Kitap silme suresi :"+avRm);
		timeAddBook();

		avAdd=avRm=0;
		avAdd = timeAddUser();
		avRm = timeRemoveUser();

		avAdd += timeAddUser();
		avRm += timeRemoveUser();

		avAdd += timeAddUser();
		avRm += timeRemoveUser();
		avAdd /= 3;
		avRm /= 3;
		System.out.println("Kullanici ekleme suresi :"+avAdd);
		System.out.println("Kullanici silme suresi :"+avRm);

		avAdd = timeBarrowBook();
		avRm = timeReturnBook();

		avAdd += timeBarrowBook();
		avRm += timeReturnBook();

		avAdd += timeBarrowBook();
		avRm += timeReturnBook();
		avAdd /= 3;
		avRm /= 3;
		System.out.println("kitap odunc alma suresi :"+avAdd);
		System.out.println("kitap odunc verme suresi :"+avRm);

		timeRemoveBook();

		/*
		* Kitap ekleme suresi :726
		Kitap silme suresi :1046
		Kullanici ekleme suresi :2732
		Kullanici silme suresi :3030
		kitap odunc alma suresi :5810
		kitap odunc verme suresi :6874
		* */
	}
}