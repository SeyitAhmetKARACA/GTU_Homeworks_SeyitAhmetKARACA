package Part2.Server;

import Part2.GraphService;

import java.net.MalformedURLException;
import java.lang.reflect.Proxy;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            int portNumber = 1099;
            LocateRegistry.createRegistry(portNumber);
            BulutCizgeRegistrationService bulutCizgeRegistrationService = new BulutCizgeRegistrationService();
            GraphService graphService = new BulutCizgeGraphService();
            GraphService proxy = (GraphService) Proxy.newProxyInstance(graphService.getClass().getClassLoader(),
                    graphService.getClass().getInterfaces(),
                    new ServiceInvocationHandler(graphService, bulutCizgeRegistrationService));

            //Graph Service binding
            Naming.rebind("BulutCizge",  UnicastRemoteObject.exportObject(proxy, portNumber));
            //Registration service binding
            Naming.rebind("BulutCizgeRegistery",  UnicastRemoteObject.exportObject(bulutCizgeRegistrationService, portNumber));
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
