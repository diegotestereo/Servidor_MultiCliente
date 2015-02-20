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
	 
	  static class ServerThread implements Runnable {
		    Socket client = null;
		    
		    public ServerThread(Socket c) {
		        this.client = c;
		    }
		    public void run() {
		    	String NombreCliente;
		    	boolean BoolCliente=true;
		        try {
		        	NombreCliente=client.getInetAddress().getHostName();
		            System.out.println("Connected to client : "+NombreCliente);
		            
		            do{
		            	BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
		            	PrintWriter salida= new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
		                
		                 String datos = entrada.readLine();
		                 if (datos.equals(null)){
		                	 BoolCliente=false;
		                	 System.out.println("dato nulo");
		  		              
		                 }else{
		                  System.out.println("Dispositivo '"+ NombreCliente+"' : "+datos);
		                  salida.println("Servidor -> "+NombreCliente+" : "+datos);
		                 }
		                 
		                 }while(BoolCliente);
		        } catch (Exception e) {
		            System.err.println(e.getMessage());
		            BoolCliente=false;
		        }
		    }
		    }
	 
	 
	 
	 public static void main(String args[]) {
			 
		 
		 Boolean Serv=true;
		int Puerto=9001;
		   try {
		          sk = new ServerSocket(Puerto);
		          System.out.println();  
	        	     System.out.println("*****************************************************");
			        System.out.println("************    ServidorMulticliente      ***********");
	                  System.out.println("************    IP: "+InetAddress.getLocalHost().getHostAddress()+":"+Puerto+"     **********");
	                 System.out.println("*****************************************************");
		                 
	                 System.out.println();
		          while (Serv) { 
		        	  
		                 Socket cliente = sk.accept();
		                 new Thread(new ServerThread(cliente)).start();
		              
		          }
		   } catch (IOException e) {
		          System.out.println(e);
		   }
		  }
		

}
