
package Server;

import Chatexpress.SDrawInit;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author Halim
 */
class ServerHandler extends Thread {

    private ServerSocket sc = null;
    private Socket client = null;
    private int port;
    private String server_ip;
    
    public ServerHandler(String ip,int port) {
        this.port=port;
        this.server_ip=ip;
        start();
    }

    public void run(){
        Robot robot = null;
        Rectangle rect = null;

        try {
            System.out.println("Connecting to server......");
            sc = new ServerSocket(port);
            int clients = 0;
            while(clients==0){
                client = sc.accept();
                System.out.println("New client Connected to the server");
                clients++;
            }
            System.out.println("Connection Established.");
            
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd=gEnv.getDefaultScreenDevice();
            Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
            rect = new Rectangle(dimen);
            robot = new Robot(gd);
            new SDrawInit(server_ip);
            new ScreenSpyer(client,robot,rect);
            new ServerDriver(client,robot);
           
        } catch (AWTException e) {
                e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
     
   
   
}