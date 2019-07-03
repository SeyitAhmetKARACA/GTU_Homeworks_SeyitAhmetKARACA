/**
 * Bu sinif kitap bilgileri tutar. Yapici fonksiyonu ile olusturulabilen sinif
 * methotlari ile sadece bilgileri elde edebilirsiniz.
 * 
 */
public class Book {
	private String ID;
	private String name;
	private String autor;
	private static String SEPARATE = ";";
	
	/**
	 * Kurucu fonksiyon
	 * @param _ID Olusturulacak kitabin kendine ozgu numarasi
	 * @param _name Kitabin ismi bilgisi
	 * @param _autor Kitabin yazarinin ismi bilgisi
	 */
	public Book(String _ID,String _name,String _autor) {
		ID = _ID;
		name = _name;
		autor = _autor;
	}
	/**
	 * Kitabin ID bilgisini kullaniciya verir.
	 * @return ID
	 */
	public String getBookID(){
		return ID;
	}
	/**
	 * Kitabin adi bilgisini kullaniciya verir.
	 * @return name
	 */
	public String getBookName(){
		return name;
	}
	
	/**
	 * Kitabin yazar adi bilgisini kullaniciya verir.
	 * @return
	 */
	public String getAutor(){
		return autor;
	}
	
	@Override
	public String toString(){
		return ID+SEPARATE+name+SEPARATE+autor;
	}
}