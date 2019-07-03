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
import java.util.LinkedList;

public class Staff extends SystemUser{


	public Staff(String _ID,String _name,String _surname,boolean _type,String _password) throws FileNotFoundException, IOException{
		super.fiilUserInformation(_ID, _name, _surname, _type,_password);
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
		
		for(int i=0;i < books.size();i++){
			bookLineParts = books.get(i).split(";");
			if(bookLineParts[0].equals(_ID) && bookLineParts[4].toString().equals("-")){
				books.remove(i);
			}else if(bookLineParts[0].equals(_ID) && bookLineParts[4].toString().equals("-") == false){
				throw new LibraryException(6);
			}
		}
		writeToFile(books,FILELIBRARY);
		return true;
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
			//
			FileReader fReadUser = new FileReader(new File(FILEUSER));
			BufferedReader readUser = new BufferedReader(fReadUser);
			LinkedList<String> users = new LinkedList<String>();
			String userLineParts[];
			String userLine;
			while((userLine = readUser.readLine()) != null ){
				users.add(userLine);
			}
			
			for(int i=0;i< users.size() ; i++){
				userLineParts = users.get(i).split(";");
				if(userLineParts[0].toString().equals(_ID)){
					readUser.close();
					throw new LibraryException(9);
				}
			}
			readUser.close();
			User user = new User(_ID,_name,_surname,_type,_passowrd);
			//
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
		
		FileReader fReadUser = new FileReader(new File(FILEUSER));
		BufferedReader readUser = new BufferedReader(fReadUser);
		boolean check = true;
		LinkedList<String> users = new LinkedList<String>();
		String userLineParts[];
		String userLine;
		while((userLine = readUser.readLine()) != null ){
			users.add(userLine);
		}
		
		for(int i=0;i< users.size() ; i++){
			userLineParts = users.get(i).split(";");
			if(userLineParts[0].toString().equals(_ID)){
				check = false;
				users.remove(i);
			}
		}
		readUser.close();
		if(check){
			throw new LibraryException(8);
		}

		writeToFile(users,FILEUSER);
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
