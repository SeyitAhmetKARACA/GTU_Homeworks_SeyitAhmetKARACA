package emailAddressPackage;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<EmailAddressAbstact> {
    Stack<Iterator<EmailAddressAbstact>> stack = new Stack<Iterator<EmailAddressAbstact>>();

    public CompositeIterator(Iterator<EmailAddressAbstact> iterator) {
        stack.push(iterator);
    }

    /**
     *
     * @return
     */
    public EmailAddressAbstact next() {
        if (hasNext()) {
            Iterator<EmailAddressAbstact> iterator = stack.peek();
            EmailAddressAbstact component = iterator.next();
            stack.push(component.createIterator());
            return component;
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<EmailAddressAbstact> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }
}
