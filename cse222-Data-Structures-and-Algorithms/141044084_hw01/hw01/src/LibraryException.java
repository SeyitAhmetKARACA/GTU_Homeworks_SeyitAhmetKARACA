/**
 * Bu sinif Exception sinifindan tureyip odevde sadece kutuphaneyi sistemini
 * ilgilendiren hata yakalama olaylarini iceriyor.
 * 
 * @author KARACA
 *
 */
final public class LibraryException extends Exception{

	private static int errorNo;
	/**
	 * 
	 * @param _errorNo parametre olarak gelen hata kodu ile mesaj bilgisi belirleniyor.
	 */
	public LibraryException(int _errorNo) {
		errorNo = _errorNo;
	}

	@Override
	public String getMessage(){
		if(errorNo == 1){
			return "You entered wrong ID or password.";
		}else if(errorNo == 2){
			return "The book unreturned does not barrow by anyone.";
		}else if(errorNo == 3){
			return "The book returns by only who barrowed it.";
		}else if(errorNo == 4){
			return "The book you want to add the database already exist.";
		}else if(errorNo == 5){
			return "Can not any operation for the book doesn't exist in database.";
		}else if(errorNo == 6){
			return "Can not remove the book not returned.";
		}else if(errorNo == 7){
			return "Staff can not remove yourself.";
		}else if(errorNo == 8){
			return "Can not remove the user inexistent in system database.";
		}else if(errorNo == 9){
			return "The user you want to add to system already exist.";
		}else
			return "";
	}
}
