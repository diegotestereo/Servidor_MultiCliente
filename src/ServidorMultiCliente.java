import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorMultiCliente {

	 static ServerSocket sk;
	 static final int Puerto=9001;
		
	  static class HiloServidor implements Runnable {
		    Socket client = null;
		    
		    // constructor y parametro
		    public HiloServidor(Socket c) {
		        this.client = c;
		    }
		    
		    public void run() {
		    	
		    	String NombreCliente;
		    	boolean BoolCliente=true;
		    	
		        try {
		        	
		        	NombreCliente=client.getInetAddress().getHostName();
		            System.out.println("Connected to client : "+NombreCliente);
		            
		            do{
		            	//lee o que envia el cliente
		            	BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
		            	// escribe la salida del servidor
		            	PrintWriter salida= new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
		                
		            	//formatea el dato de entrada o caste a aString
		                String datos = entrada.readLine();
		                System.out.println("datos: "+datos);
			         
		                if (datos==null){
		                	 System.out.println("dato nulo");
		                	 BoolCliente=false;
		                     
		                 }else{
		                  System.out.println("Dispositivo '"+ NombreCliente+"' : "+datos);
		                  salida.println("Servidor -> "+NombreCliente+" : "+datos);
		                 }
		                 
		                 }while(BoolCliente);//cuando el clientees
		        } catch (Exception e) {
		            System.err.println(e.getMessage());
		            BoolCliente=false;
		        }
		    }
		    }
	 
	 
	 
	 public static void main(String args[]) {
			 
		 
		 Boolean Serv=true;
		   try {
		          sk = new ServerSocket(Puerto);
		          System.out.println();  
	        	     System.out.println("*****************************************************");
			        System.out.println("************    ServidorMulticliente      ***********");
	                  System.out.println("************    IP: "+InetAddress.getLocalHost().getHostAddress()+":"+Puerto+"     **********");
	                 System.out.println("*****************************************************");
		                 
	                 System.out.println();
		          while (Serv) { 
		        	  
		                 Socket cliente = sk.accept();// se queda a la espera de un cliente
		                 new Thread(new HiloServidor(cliente)).start();// crea un hilo con un nuevo cliente.
		                 //y le paso como parametro el Socket cliente ingresado.
		              
		          }
		   } catch (IOException e) {
		          System.out.println(e);
		   }
		  }
		

}
