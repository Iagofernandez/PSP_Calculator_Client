package psp_calculadora;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;

public class MetodosCliente {

    boolean recibido = false;

    static Socket clienteSocket;
    int conn;

   
    public void crearConexion(JTextField port, JButton boton, JTextField field) throws IOException {
        clienteSocket = new Socket();
        // Se crea el socket del cliente:
        System.out.println("Creando socket");
        
// Se estable la dirección del socket:
        System.out.println("Connected");
        conn = Integer.parseInt(port.getText());
        InetSocketAddress addr = new InetSocketAddress("localhost", conn);
        clienteSocket.connect(addr);
        boton.setEnabled(false);
        field.setEnabled(false);
    }

    public void connect() {
        try {
            //Creamos el socket
            clienteSocket = new Socket();
            //Establecemos conexión
            InetSocketAddress addr = new InetSocketAddress("localhost", conn);
            clienteSocket.connect(addr);
        } catch (IOException ex) {
            Logger.getLogger(MetodosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public void enviarMensaje(String mensaje, JTextField resultado) {
        String resul = "0";
        try {
            //Conectamos
            connect();
            //Creamos outputStream para escribir el mensaje
            OutputStream os = clienteSocket.getOutputStream();
            //Escribimos el mensaje
            os.write(mensaje.getBytes());
            System.out.println("Mensaje");

          
            InputStream is = clienteSocket.getInputStream();
           
            byte[] mensajeRecibido = new byte[25];
           
            is.read(mensajeRecibido);
           
            resultado.setText(String.format("%.2f",Float.parseFloat(new String(mensajeRecibido))));
           
            System.out.println("Mensaje " + new String(mensajeRecibido));
           
            is.close();
            os.close();
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        } finally {
            cerrarConexion();
        }
    }

    public void cerrarConexion() {
        try {
            //Cerramos el socket
            System.out.println("Cerrando el socket");
            clienteSocket.close();
            
        } catch (IOException ex) {
            System.out.println("Error conexión");
        }
    }
}
