
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    Thread WriterThread ;

    public static int KEY = 0;
    public static String COMMAND="[s]";
    public static String OLD_COMMAND;

    public Client(String host,int port){
        try{
            socket = new Socket(host,port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MessageReceiver implements Runnable{
        private static boolean isKeySend = false;
        private Socket socket;


        public MessageReceiver(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                PrintWriter printWriter1 = new PrintWriter(socket.getOutputStream());
                //String messageString1 = "[s]";
                while (true) {
                    if(!isKeySend){
                        printWriter1.write("(feb9945c4df3434aa9b8b202e7541b1e)");//feb9945c4df3434aa9b8b202e7541b1e
                        printWriter1.flush();
                        isKeySend = true;
                        System.out.println("key : "+KEY+" isKeySend : "+isKeySend);
                    }else {
                        synchronized ((Object) KEY){
                            if(KEY == 1){

                                System.out.println(Client.COMMAND);
                                printWriter1.write(Client.COMMAND);
                                printWriter1.flush();
                                KEY = 2;

                            }
                        }

                    }
                    //System.out.println("key : "+KEY);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }


        }

    }

    public void start(){

        WriterThread = new Thread(new MessageReceiver(socket));
        WriterThread.start();
        new Thread(new Listener(socket)).start();
//
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",9527);
        client.start();

    }
}
