
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener implements Runnable{
    Socket socket;
    InputStreamReader inputStreamReader;

    static private int flag=0;
    MapInfoBase mapInfo;

    public Listener(Socket s){
        this.socket=s;
        try {
            this.inputStreamReader=new InputStreamReader(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapInfo=new MapInfoBase();
    }

    public boolean isInputStreamReady(){
        try{
            return this.inputStreamReader.ready();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void run() {
        System.out.println("Listener.run");
        int messageCount=1;
        String string;
        while(true){
            if(isInputStreamReady()) {

                readFromStream();
                System.out.println("Read Completed " + messageCount + " flag " + flag);
                messageCount++;
                if (flag == 2) {
                    string = mapInfo.getNextCommand();
                    Client.KEY = 1;
                    Client.COMMAND = string;

                }
            }
        }
    }

    public void readFromStream(){
        System.out.println("readFromStream");
        try{
            int ch = inputStreamReader.read();
            StringBuffer stringBuffer=new StringBuffer();
            char ch1 = (char)ch;
            char ch2;
            stringBuffer.append(ch1);
            switch (ch1){
                case (char)79:
                    ch2=(char)inputStreamReader.read();
                    stringBuffer.append(ch2);
                    flag = 1;
                    break;
                case (char)91:
                    for(int i=0;i<=225;i++){
                        ch2=(char)inputStreamReader.read();
                        stringBuffer.append(ch2);
                    }
                    flag = 2;
                    break;
                default:
                    while(isInputStreamReady()){
                        stringBuffer.append((char)inputStreamReader.read());
                    }
                    System.exit(0);
                    flag = 3;
                    break;
            }
            System.out.println(stringBuffer.toString());
            if(flag==2){
                mapInfo.createFromString(stringBuffer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
