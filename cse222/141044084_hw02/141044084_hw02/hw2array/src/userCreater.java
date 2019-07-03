/**
 * Bu sinif bir methoda sahip olup methoda verilen kimlik numarasi ve
 * sifre bilgisine gore sistem kullanici geri dondurur.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class userCreater {
	private static final String FILELIBRARY = "Library.csv";
	private static final String FILEUSER = "SystemUser.csv";
	/**
	 * 
	 * @param _ID Sisteme giris yapan kullanici kimlik numarasi
	 * @param _password Sisteme giris yapan kullanici sifresi.
	 * @return girilen parametrelere gore User veya Staff nesneleri verir.
	 * @throws IOException
	 * @throws LibraryException yanlis kimlik numarasi veya sifresi girilmis
	 */
	public static SystemUser createUser(String _ID,String _password) throws IOException,LibraryException{
		createFiles();
		FileReader fReadUser = new FileReader(new File(FILEUSER));
		BufferedReader readUser = new BufferedReader(fReadUser);
		String fileLine;
		String[] fileLineParts;
		
		while((fileLine = readUser.readLine()) != null){
			fileLineParts = fileLine.split(";");
			if(fileLineParts[0].equals(_ID) && fileLineParts[4].equals(_password)){
				if(fileLineParts[3].equals("true")){
					Staff staff = new Staff(fileLineParts[0],fileLineParts[1],fileLineParts[2],true,fileLineParts[4]);
					readUser.close();

					return staff;
				}else if(fileLineParts[3].equals("false")){
					User user = new User(fileLineParts[0],fileLineParts[1],fileLineParts[2],false,fileLineParts[4]);
					readUser.close();
					return user;					
				}
			}
		}
		readUser.close();
		throw new LibraryException(1);
	}
	private static void createFiles() throws IOException{
		File fLibrary = new File(FILELIBRARY);
		File fSystemUser = new File(FILEUSER);
		if(!fLibrary.exists()){
			fLibrary.createNewFile();
		}
		if(!fSystemUser.exists()){
			fSystemUser.createNewFile();
		}
	}
	
}