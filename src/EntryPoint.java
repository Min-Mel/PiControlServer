import server.Server;

public class EntryPoint
{
   public static void main(String[] args) {
	   int port = 3023;
	   if(args.length > 0)
		   try {
			   port = Integer.parseInt(args[0]);
		   } catch(NumberFormatException e) {
			   System.out.println("First argument (port) must be an integer");
		   }
       new Thread(new Server(port)).start();
   }
}
