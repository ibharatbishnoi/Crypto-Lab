import java.util.*;
import java.net.URL;

class Main{
    public static void main (String args[] ) throws Exception{
        Scanner s = new Scanner (new URL("https://gfg1.tiiny.site/").openStream());
        while (s.hasNextLine()){
            System.out.println(s.nextLine());
            
        }
        s.close();
}
}