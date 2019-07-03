package emailAddressPackage;

import java.util.Iterator;

public abstract class EmailAddressAbstact {
    /**
     * email iterator
     * @return
     */
    public abstract Iterator<EmailAddressAbstact> createIterator();

    /**
     * email Add
     * @param emailAddressComponent email grup veya email
     */
    public void add(EmailAddressAbstact emailAddressComponent) {
        throw new UnsupportedOperationException();
    }
    public EmailAddressAbstact getChild(int i) {
        throw new UnsupportedOperationException();
    }
    public void remove(EmailAddressAbstact emailAddressComponent) {
        throw new UnsupportedOperationException();
    }
    public String getName() {
        throw new UnsupportedOperationException();
    }
    public String getEmail() {
        throw new UnsupportedOperationException();
    }

    public void print() {
        throw new UnsupportedOperationException();
    }
}