/**
 * SystemUser sinifindan turemis sinif.
 * Sitemde personelin yapabilecegi kullanici kaydetme/silme
 * kitap kaydetme/silme yetkilerine sahip.
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Staff extends SystemUser{


	public Staff(String _ID,String _name,String _surname,boolean _type,String _password) throws FileNotFoundException, IOException{
		super.fiilUserInformation(_ID, _name, _surname, _type,_password);
	}


	private int userCapacity = 256;
	private String[] users;
	private int userSize=0;

	private void reallocateUser(){
		String[] temp= new String[userCapacity];
		for(int i=0;i < userSize;i++ ){
			temp[i] = users[i];
		}
		userCapacity *= 2;
		users = new String[userCapacity];
		for(int i=0;i < userSize;i++ ){
			users[i] = temp[i];
		}
	}

	private static final String FILEUSER = "SystemUser.csv";
	
	/**
	 * Sistemdeki pozisyon bilgisini verir
	 * True personel
	 */
	@Override
	public boolean getType(){
		return true;
	}

	@Override
	public String toString(){
		return "Admin of library";
	}

	/**
	 * Kutuphanede kitap ekleme islemini yapar
	 * @param _ID kitap seri numarasi
	 * @param _name kitap ismi
	 * @param _autor kitap yazar ismi
	 * @return
	 * @throws IOException
	 * @throws LibraryException 4 : Kitap zaten sistemde kayitli
	 */
	public boolean addBook(String _ID,String _name,String _autor) throws IOException,LibraryException{
		if(checkBook(_ID) == true)
			throw new LibraryException(4);

		Book book = new Book(_ID,_name,_autor);
		writeToFile(book.toString()+SEPARATE+"available"+SEPARATE+"-",FILELIBRARY);
		return true;
	}
	/**
	 * Kutuphaneden kitap siler.
	 * @param _ID kitap seri numarasi
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws LibraryException 5 : sistemde olmayan kitap silinemez
	 * 		   LibraryException 6 : Iade edilmemis kitap silinemez
	 */
	public boolean removeBook(String _ID) throws FileNotFoundException, IOException, LibraryException{
		if(checkBook(_ID) == false)
			throw new LibraryException(5);
	
		String[] bookLineParts;
		
		for(int i=0;i < bookSize;i++){
			bookLineParts = books[i].split(";");
			if(bookLineParts[0].equals(_ID) && bookLineParts[4].toString().equals("-")){
				books[i] = books[bookSize-1];
				books[bookSize-1] = "";
				bookSize--;
			}else if(bookLineParts[0].equals(_ID) && bookLineParts[4].toString().equals("-") == false){
				throw new LibraryException(6);
			}
		}
		writeToFile(books,FILELIBRARY,bookSize);
		return true;
	}

	private void fiilUsers() throws IOException {
		FileReader fReadUser = new FileReader(new File(FILEUSER));
		BufferedReader readUser = new BufferedReader(fReadUser);
		String userLine;
		userSize=0;
		users = new String[userCapacity];
		while((userLine = readUser.readLine()) != null ){
			users[userSize] = userLine;
			userSize++;
			if(userSize == userCapacity)
				reallocateUser();
		}
		readUser.close();
		fReadUser.close();
	}
	/**
	 * Sisteme kullanici ekler
	 * @param _ID kullanici kimlik numarasi
	 * @param _name kullanici adi
	 * @param _surname kullanici soyadi
	 * @param _type kullanici tipi
	 * @param _passowrd kullanici sifresi
	 * @return
	 * @throws IOException 
	 * @throws LibraryException 9:sistemde olan kullanici eklemek
	 */
	public boolean addUser(String _ID,String _name,String _surname,boolean _type,String _passowrd) throws IOException, LibraryException{

			String userLineParts[];
			
			for(int i=0;i< userSize ; i++){
				userLineParts = users[i].split(";");
				if(userLineParts[0].toString().equals(_ID)){
					throw new LibraryException(9);
				}
			}
			User user = new User(_ID,_name,_surname,_type,_passowrd);

			writeToFile(user.toString(),FILEUSER);
			return true;
	}
	/**
	 * 
	 * @param _ID
	 * @return
	 * @throws LibraryException 7:Personel kendisini silemez
	 * 		   LibraryException 8:Kayitli olmayan kullanici silenemez
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public boolean removeUser(String _ID)throws LibraryException, FileNotFoundException,IOException{
		if(_ID.toString().equals(ID)){
			throw new LibraryException(7);
		}
		fiilUsers();
		boolean check = true;
		String userLineParts[];

		for(int i=0;i< userSize ; i++){
			userLineParts = users[i].split(";");
			if(userLineParts[0].toString().equals(_ID)){
				check = false;
				users[i] = users[userSize-1];
				userSize--;
			}
		}

		if(check){
			throw new LibraryException(8);
		}

		writeToFile(users,FILEUSER,userSize);

		return true;
	}

	/**
	 * Parametre olarak gelen texti parametre olarak gelen hedef dosyaya yazar.
	 * @param text yazilacak metin
	 * @param targetPath hedef dosya
	 * @throws IOException
	 */
	protected void writeToFile(String text,String targetPath) throws IOException{
		FileWriter Writer = new FileWriter(new File(targetPath),true);
		PrintWriter Output = new PrintWriter(Writer);
		
		Output.println(text);
		
		Output.close();
		Writer.close();
		
	}

	/**
	 * Sistemdeki personelin yapabileceklei.
	 */
	@Override
	public void menu(){
		System.out.println("[1] List books ");
		System.out.println("[2] Add book");
		System.out.println("[3] Remove book");
		System.out.println("[4] Add user");
		System.out.println("[5] Remove user");
	}
}
