package emailAddressPackage;
import java.util.Iterator;

public class PersonalEmailAddress extends EmailAddressAbstact {
    String name;
    String email;

    public PersonalEmailAddress(String _name, String _email) {
        name = _name;
        email = _email;
    }

    /**
     * getName
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * getEmail
     * @return
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Personel iteratör
     * @return NullIterator
     */
    public Iterator<EmailAddressAbstact> createIterator() {
        return new NullIterator();
    }

    /**
     * Personel print
     */
    public void print() {
        System.out.print(" " + getName());
        System.out.println("-" + getEmail());
    }
}