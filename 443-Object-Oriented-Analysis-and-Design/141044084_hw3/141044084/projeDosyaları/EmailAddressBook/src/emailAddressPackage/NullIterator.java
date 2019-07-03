package emailAddressPackage;
import java.util.Iterator;

public class NullIterator implements Iterator<EmailAddressAbstact> {
    public EmailAddressAbstact next() {
        return null;
    }

    /**
     * Nulliterator
     * @return
     */
    public boolean hasNext() {
        return false;
    }
}