package Part2;

import java.rmi.Remote;
        import java.rmi.RemoteException;
import java.util.Map;

public interface RegistrationService extends Remote {
    /**
     * register new account
     * @param a account
     * @param credit credit
     * @return true on success, false otherwise
     * @throws RemoteException
     */
    boolean register(Account a, int credit) throws RemoteException;

    /**
     * Adds credit ,which comes with parameter, to current ceredit.
     * @param a Account
     * @param credit Credit
     * @return true; success, false;otherwise
     * @throws RemoteException
     */
    boolean loadCredit(Account a, int credit) throws RemoteException;

    /**
     * It gives accounts
     * @return Map include accounts
     * @throws RemoteException
     */
    Map getAccounts() throws RemoteException;

    /**
     * It gives specific account's credit
     * @param acc which account
     * @return current credit
     * @throws RemoteException
     */
    int getCredit(Account acc) throws  RemoteException;
}