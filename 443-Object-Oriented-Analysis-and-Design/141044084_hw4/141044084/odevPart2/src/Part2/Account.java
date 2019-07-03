package Part2;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private String name;
    private String creditCard;

    /**
     *
     * @param name Account name
     * @param creditCard Credit card information
     */
    public Account(String name, String creditCard) {
        this.name = name;
        this.creditCard = creditCard;
    }

    public String getName() {
        return name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        else{
            Account account = (Account) o;
            return Objects.equals(name, account.name);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}