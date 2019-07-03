/**
 *  Bu sinif kutuphane sistemdeki kullanicilarin ortak ozelliklerini tutuyor.
 * 
 *  @author KARACA
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class SystemUser implements ISystemUser{
	protected String name;
	protected String surname;
	protected String ID;
	protected boolean type;
	private String password;
	
	protected final static String SEPARATE = ";";
	protected static final String FILELIBRARY = "Library.csv";

	//protected ArrayList<String> books = new ArrayList<String>();
	protected String[] books;
	protected int bookSize =0;
	protected int bookCapacity=256;

	protected void reallocateBook(){
		String[] temp= new String[bookCapacity];
		for(int i=0;i < bookSize;i++ ){
			temp[i] = books[i];
		}
		bookCapacity *= 2;
		books = new String[bookCapacity];
		for(int i=0;i < bookSize;i++ ){
			books[i] = temp[i];
		}
	}

	/**
	 * books ArrayList ine gerekli bilgileri ekler.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void fillBooks() throws IOException,FileNotFoundException{
		FileReader readBook = new FileReader(new File(FILELIBRARY));
		BufferedReader bf = new BufferedReader(readBook);
			books = new String[bookCapacity];
		    bookSize=0;
			String str;
			while((str = bf.readLine()) != null){
				books[bookSize]=str;
				bookSize++;
				if(bookSize == bookCapacity)
					reallocateBook();
		}
			bf.close();
	}
	
	@Override
	public String toString(){
		return ID+SEPARATE+name+SEPARATE+surname+SEPARATE+type+SEPARATE+password;
	}

	abstract public boolean getType();
	abstract public void menu();

	/**
	 * Sistem kullanicisinin ismini geri dondurur.
	 */
	public String getName(){
		return name;
	};
	/**
	 * Sistem kullanicisinin soyismini geri dondurur.
	 */
	public String getSurname(){
		return surname;
	};

	/**
	 * Sistem kullanicisinin ID bilgisini geri dondurur.
	 */
	public String getID(){
		return ID;
	};

	
	// duzgun yazdir bunlari
	/**
	 * Kutuphane sistemine kayitli olan kitaplari ekrana basar.
	 */
	public void listBooks() throws IOException{
		FileReader bookReader = new FileReader(new File(FILELIBRARY));
		BufferedReader bufferedBook = new BufferedReader(bookReader);
		String bookLine;
		String bookLineParts[];
		if(type)
			System.out.println("ISBN\tName\tstatus\t  Barrowed");
		else
			System.out.println("ISBN\tName\tstatus");
		while((bookLine = bufferedBook.readLine()) != null){
			bookLineParts = bookLine.split(SEPARATE);
			if(type == true){
				System.out.println(bookLineParts[0]+ "\t\t" + bookLineParts[2]+
						"\t\t"+ bookLineParts[3] + "\t\t"+ bookLineParts[4]);
			}else{
				System.out.println(bookLineParts[0]+ "\t\t" + bookLineParts[2]+
						"\t\t"+ bookLineParts[3]);
			}
		}
		
		bufferedBook.close();
		bookReader.close();
	};

	/**
	 * Parametre olarak verilen id'ye sahip kitabin sistemde kayitli olop olmadigini
	 * kontrol eder.
	 * @param _ID kitap id'si
	 * @return Kitap var ise true yok ise false
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected boolean checkBook(String _ID) throws FileNotFoundException,IOException{
		FileReader reader = new FileReader(new File(FILELIBRARY));
		BufferedReader buffer = new BufferedReader(reader);
		String[] stringParts;
		String line;
		boolean check = false;
		
		while((line = buffer.readLine()) != null ){
			stringParts = line.split(";");
			if(stringParts[0].equals(_ID)){
				check = true;
			}
		}
		buffer.close();
		return check;
	}
	/**
	 * Parametre olarak array list alir ve icerdigi bilgiyi hedef dosyaya yazar.
	 * @param text Dosyaya yazilacak metin
	 * @param targetPath hedef dosya yolu/ismi
	 * @throws IOException
	 */
	protected void writeToFile(String[] text,String targetPath,int size) throws IOException{
		FileWriter fw = new FileWriter(new File(targetPath));
		PrintWriter pw = new PrintWriter(fw);
		
		for(int i=0;i<size ; i++){
			pw.println(text[i]);
		}
		pw.close();
	}
	/**
	 * Sistem kullanicisinin bilgilerini doldurur
	 * @param _ID Kullanici kimlik numarasi
	 * @param _name Kullanici adi
	 * @param _surname Kullanici soyadi
	 * @param _type Kullanici tipi True:yonetici False:Normal kullanici
	 * @param _password Hesap sifresi
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void fiilUserInformation(String _ID,String _name,String _surname,boolean _type,String _password) throws FileNotFoundException, IOException{
		name = _name;
		surname = _surname;
		ID = _ID;
		type = _type;
		password = _password;
		fillBooks();
	};
}