package emailAddressPackage;
import java.util.ArrayList;
import java.util.Iterator;

public class GroupEmailAddress extends EmailAddressAbstact {

    private String name;
    private String email;
    private ArrayList<EmailAddressAbstact> emailAddressComponents = new ArrayList<EmailAddressAbstact>();
    private Iterator<EmailAddressAbstact> iterator = null;

    public GroupEmailAddress(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void add(EmailAddressAbstact emailAddressComponent) {
        emailAddressComponents.add(emailAddressComponent);
    }

    public void remove(EmailAddressAbstact emailAddressComponent) {
        emailAddressComponents.remove(emailAddressComponent);
    }

    /**
     * GetMame
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
     * grup iterator
     * @return
     */
    public Iterator<EmailAddressAbstact> createIterator() {
        if (iterator == null) {
            iterator = new CompositeIterator(emailAddressComponents.iterator());
        }
        return iterator;
    }

    /**
     * grup print
     */
    public void print() {
        System.out.println("\n"+getName()+"\n");
        System.out.println(getEmail()+" ");
        System.out.println("- - - - - - - - - - -");
        Iterator<EmailAddressAbstact> iterator = emailAddressComponents.iterator();
        while (iterator.hasNext()) {
            EmailAddressAbstact emailAddressComponent = iterator.next();
            emailAddressComponent.print();
        }
    }
}