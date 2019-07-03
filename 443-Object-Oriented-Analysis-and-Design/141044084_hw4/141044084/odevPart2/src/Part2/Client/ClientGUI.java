package Part2.Client;

import Part2.*;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientGUI {
    private JTextField usernameField;
    private JTextField cardNumberTextField;
    private JButton registerButton;
    private JTextArea responseArea;
    private JTextField pathField;
    private JButton constructGraphButton;
    private JButton minimumSpanningTreeButton;
    private JButton incidenceMatrixButton;
    private JPanel card;
    private JTextField creditField;
    private JButton loadCreditButton;
    private JButton showGraphButton;
    private JButton GetCreditButton;
    private JTextField userCredit;
    private JButton listAccountsButton;
    private JComboBox pathFieldComboBox;

    private static GraphService graphService;
    private static RegistrationService registrationService;
    private Graph graph;

    public ClientGUI() throws RemoteException {
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Account account = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());
                try {
                    if (registrationService.register(account, 20) == true)
                        responseArea.setText("Account registered");
                    else
                        responseArea.setText("Account couldn't registered");
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
        });
        listAccountsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    Map accs =  registrationService.getAccounts();
                    List<Account> list = new ArrayList<Account>(accs.keySet());
                    String AccountList = "";
                    for(int i = 0 ; i < list.size();i++){
                        AccountList += list.get(i).getName()+" "+ list.get(i).getCreditCard() + "\n";
                    }
                    responseArea.setText(AccountList);
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
        });

        GetCreditButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    getCreditMethod();
            }
        });

        loadCreditButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account account = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());
                try {
                    if (registrationService.loadCredit(account, Integer.parseInt(creditField.getText().trim())) == true) {
                        responseArea.setText("Credit loaded successfully");
                        getCreditMethod();
                    }
                    else
                        responseArea.setText("Credit couldn't loaded");
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
        });
        constructGraphButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account account = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());
                List<Edge> edges = new ArrayList<>();
                String selected = (String) pathFieldComboBox.getSelectedItem();
                File f = new File(selected);
                try {

                    Scanner scanner = new Scanner(f);

                    int V = scanner.nextInt();
                    while (scanner.hasNext()) {
                        int from = scanner.nextInt();
                        int to = scanner.nextInt();
                        double w = scanner.nextDouble();

                        edges.add(new Edge(from, to, w));
                    }

                    scanner.close();

                    graph = graphService.buildGraph(account, edges.toArray(new Edge[edges.size()]), V);
                    getCreditMethod();
                    responseArea.setText("Graph builded");
                } catch (RemoteException re) {
                    re.printStackTrace();
                } catch (NoEnoughCreditException nece) {
                    responseArea.setText("User don't have enough credit to build graph.");
                } catch (FileNotFoundException fne) {
                    responseArea.setText("File " + pathField.getText().trim() + " not found");
                }
            }
        });
        showGraphButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account account = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());

                try {
                    responseArea.setText(graphService.graphToString(account, graph));
                    getCreditMethod();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (NoEnoughCreditException nece) {
                    responseArea.setText("User don't have enough credit to show graph.");
                }
            }
        });
        incidenceMatrixButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account account = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());

                try {
                    double[][] retval = graphService.getIncidenceMatrix(account, graph);
                    StringBuilder builder = new StringBuilder();
                    for (double[] row : retval) {
                        for (double d : row)
                            builder.append(String.format("%3.2f ", d));
                        builder.append("\n");
                    }
                    responseArea.setText(builder.toString());
                    getCreditMethod();
                } catch (RemoteException re) {
                    re.printStackTrace();
                } catch (NoEnoughCreditException nece) {
                    responseArea.setText("User don't have enough credit to retrieve the matrix.");
                }
            }
        });
        minimumSpanningTreeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Account a = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());

                try {
                    Graph g = graphService.getMinimumSpanningTree(a, graph);
                    responseArea.setText(g.toString());
                    getCreditMethod();
                } catch (RemoteException re) {
                    re.printStackTrace();
                } catch (NoEnoughCreditException nece) {
                    responseArea.setText("User don't have enough credit to retrieve the matrix.");
                }
            }
        });
        pathFieldComboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File[] txtfiles = finder("./");
                ArrayList al = new ArrayList();
                for(int i= 0 ; i < txtfiles.length;i++)
                    al.add(txtfiles[i].toString());
                pathFieldComboBox.setModel(new DefaultComboBoxModel(al.toArray()));
            }
        });
        cardNumberTextField.addFocusListener(new FocusAdapter() {
            String exString = cardNumberTextField.getText();
            @Override
            public void focusGained(FocusEvent e) {
                cardNumberTextField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(cardNumberTextField.getText().length() == 0)
                    cardNumberTextField.setText(exString);
            }
        });
        usernameField.addFocusListener(new FocusAdapter() {
            String exString = usernameField.getText();
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(usernameField.getText().length() == 0)
                    usernameField.setText(exString);
            }
        });
    }
    private void getCreditMethod() {
        try {
            Account a = new Account(usernameField.getText().trim(), cardNumberTextField.getText().trim());
            Map accs = registrationService.getAccounts();
            userCredit.setText(accs.get(a).toString());
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public File[] finder( String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );

    }

    public static void main(String[] args) throws RemoteException {
        String host = "localhost";

        if (args.length == 1)
            host = args[0];

        try {
            graphService = (GraphService) Naming.lookup("rmi://" + host + "/BulutCizge");
            registrationService =
                    (RegistrationService)  Naming.lookup("rmi://" + host + "/BulutCizgeRegistery");
            JFrame frame = new JFrame("Client");
            frame.setContentPane(new ClientGUI().card);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(1000,500);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sunucuya ulaşılamadı.\nLütfen sunucunun " +
                    "açık olduğundan emin olduktan sonra tekrar deneyiniz.");
        }
    }
}
