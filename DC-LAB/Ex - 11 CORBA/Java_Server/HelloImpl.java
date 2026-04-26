import demo.HelloPOA;
import org.omg.CORBA.ORB;

public class HelloImpl extends HelloPOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public String sayHello(String name) {
        return "Hello " + name + " from Java CORBA Server";
    }
}
