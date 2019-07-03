/**
 * SystemUser dan tureyen bu sinif normal kullanicinin ozelliklerine sahiptir.
 * Kutuphaneden kitap alabilri ve iade edebilir.
 *
 * 141044084
 * @autor Seyit KARACA
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public class User extends SystemUser{

	/**
	 * 
	 * @param _ID Kimlik numarasi
	 * @param _name isim
	 * @param _surname soyisim
	 * @param _type sistemdeki tipi
	 * @param _password sifre
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public User(String _ID, String _name, String _surname,boolean _type,String _password) throws FileNotFoundException, IOException {
		super.fiilUserInformation(_ID, _name, _surname, _type,_password);
	}
	
	/**
	 * Sistemdeki pozisyonunu belirlemek amaci ile tipini geri dondurur. 
	 */
	@Override
	public boolean getType(){
		return false;
	}
	
	/**
	 * Kullanicinin sistem uzerinde yapabilecekleri
	 */
	@Override
	public void menu(){
		System.out.println("[1] List books ");
		System.out.println("[2] Barrow book");
		System.out.println("[3] Return book");
	}
	
	/**
	 * Parametre ile gelen degere sahip kitap odunc verilir.
	 * @param bookID 
	 * @return
	 * @throws LibraryException 5: sistemde olmayan kitap ile ilgili islem yapilamaz
	 * 		   LibraryException 2: iadesi yapilmamis kitabi baskasi odunc alamaz
	 * @throws IOException
	 */
	public boolean barrowBook(String bookID) throws LibraryException, IOException{
		if(checkBook(bookID) == false)
			throw new LibraryException(5);
			
		String[] bookLineParts;
		String bookLine;
		
		for(int i=0;i < bookSize;i++){
			bookLineParts = books[i].split(";");
			if(bookLineParts[0].equals(bookID) && bookLineParts[4].equals("-")){

				bookLineParts[3] = "Not valid";
				bookLineParts[4] = ID;
				
				bookLine = bookLineParts[0]+";"+bookLineParts[1]+";";
				bookLine += bookLineParts[2]+";"+bookLineParts[3]+";";
				bookLine += bookLineParts[4];
				books[i] = bookLine;
			}else if(bookLineParts[0].equals(bookID) && bookLineParts[4].equals("-")==false){
				throw new LibraryException(2);
			}
		}

		writeToFile(books,FILELIBRARY,bookSize);
		return true;
	}
	/**
	 * 
	 * @param bookID kitap seri numarasi
	 * @return iade edildi ise true
	 * @throws LibraryException 5: sistemde olmayan kitap ile ilgili islem yapilamaz
	 * 		   LibraryException 3: kitabi sadece odunc alan iade edebilir
	 * @throws IOException
	 */
	public boolean returnBook(String bookID) throws LibraryException, IOException{
		if(checkBook(bookID) == false)
			throw new LibraryException(5);
		
		String[] bookLineParts;
		String bookLine;
		
		for(int i=0;i < bookSize;i++){
			bookLineParts = books[i].split(";");
			if(bookLineParts[0].equals(bookID) && bookLineParts[4].equals(ID)){
				bookLineParts[3] = "available";
				bookLineParts[4] = "-";
				
				bookLine = bookLineParts[0]+";"+bookLineParts[1]+";";
				bookLine += bookLineParts[2]+";"+bookLineParts[3]+";";
				bookLine += bookLineParts[4];
				books[i] = bookLine;
			}else if(bookLineParts[0].equals(bookID) && bookLineParts[4].equals(ID) == false){
				throw new LibraryException(3);
			}
		}
		
		writeToFile(books,FILELIBRARY,bookSize);
		return true;
	}
}