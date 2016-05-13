package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import adapter.AutoServer;
import adapter.BuildAuto;
import model.Automobile;

/**
 * @author-Rui Wang rw1
 */
public class DefaultSocketClient extends Thread{
    
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    private static int clientNum = 0;
    private static ServerSocket serverSocket;
    
    public DefaultSocketClient(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        clientNum++;
        handleSession();
    }
    
    public void handleSession() {
        try {
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            in = new ObjectInputStream(clientSocket.getInputStream());            
            while(true) {
                String input;
                    input = (String) in.readObject();
                    System.out.println("swich case"+input);
                    System.out.println("-------------");
//                    if(input == null)
//                        continue;
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    switch(input) {
                    case "0":   // quit
                        out.writeObject("Communication finished!");
                        out.flush();
                        System.out.println("Quit");
                        clientSocket.close();
                        return;
                    case "1":   // send to automobile to server
                        System.out.println("Recieve request from client.");
                        out.writeObject("Please send properties");
                        out.flush();
                        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());  
                        Properties properties = (Properties)inputStream.readObject();  // read properties
                        System.out.print("Read input data");
                        BuildCarModelOptions buildCarModelOptions = new BuildCarModelOptions();
                        Automobile auto = buildCarModelOptions.buildAutoOptions(properties);
                        System.out.println("Receive Automobile:" + auto.getModel());
                        out.writeObject("Automobile created successfully");  // write back successful information
                        out.flush();
                        System.out.print("write out input data");
                        break;
                    case "2":  // give the automobile list to user
                        AutoServer server = new BuildAuto();
                        out.writeObject(server.listAutomobileNames());
                        out.writeObject("");
                        out.flush();
                        System.out.println("Return list of Automobiles:\n" + server.listAutomobileNames());
                        break;
                    case "3":  // configure the automobiles
                        String modelName = (String) in.readObject();
                        System.out.println("Receive model name:" + modelName);
                        BuildAuto buildAuto = new BuildAuto();
                        Automobile automobile = buildAuto.getModel(modelName);
//                        ObjectOutputStream outAuto = new ObjectOutputStream(clientSocket.getOutputStream());
                        out.writeObject(automobile);
                        out.flush();
                        System.out.println("Return Automobile " + automobile.getModel());
                        break;
                     default:
                        out.writeObject("Please input valid number:0, 1, 2, or 3.");
                        out.flush();
                        System.out.println("Please input valid number:0, 1, 2, or 3.");
                        break;
                    }
            }
        } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(1);
        }
    }
}
