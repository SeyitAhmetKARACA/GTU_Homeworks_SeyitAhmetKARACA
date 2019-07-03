package Part2.Server;

import Part2.Account;
import Part2.GraphService;
import Part2.NoEnoughCreditException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceInvocationHandler implements InvocationHandler {
    private GraphService service;
    private BulutCizgeRegistrationService bulutCizgeRegistrationService;

    public ServiceInvocationHandler(GraphService service, BulutCizgeRegistrationService bulutCizgeRegistrationService) {
        this.service = service;
        this.bulutCizgeRegistrationService = bulutCizgeRegistrationService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, NoEnoughCreditException {
        Account a = (Account) args[0];

        //credit control
        if (!bulutCizgeRegistrationService.hasCredit(a))
            throw new NoEnoughCreditException();

        bulutCizgeRegistrationService.decreaseCredit(a);

        //print method invocation time
        synchronized (this) {
            System.out.println(method.getName() + " "
                    + "invoked at " + new SimpleDateFormat("hh:mm:ss").format(new Date()) + " "
                    + "(Account: " + a.getName() + ") (Credit left: " + bulutCizgeRegistrationService.getCredit(a) + ") "
                    + "(Thread " + Thread.currentThread().getId() + ")"
            );
        }

        //invoke method
        long t0 = System.nanoTime();
        Object retval = method.invoke(service, args);
        long t1 = System.nanoTime();

        //print runtime of the method
        synchronized (this) {
            System.out.println(method.getName() + " "
                    + "tooks " + String.format("%.3f", ((t1 - t0) * 1e-6)) + " ms" + " "
                    + "(Account: " + a.getName() + ") "
                    + "(Thread " + Thread.currentThread().getId() + ")\n ------------------------------\n\n"
            );
        }

        return retval;
    }
}
