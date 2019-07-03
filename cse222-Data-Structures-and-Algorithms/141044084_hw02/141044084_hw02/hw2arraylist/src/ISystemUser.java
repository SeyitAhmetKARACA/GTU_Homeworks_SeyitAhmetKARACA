import java.io.IOException;

public interface ISystemUser {
	String getName();
	String getSurname();
	String getID();
	void listBooks() throws IOException;
	boolean getType();
}
