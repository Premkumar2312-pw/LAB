import demo.Hello;
import demo.HelloHelper;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class HelloServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));

            rootpoa.the_POAManager().activate();
            HelloImpl helloImpl = new HelloImpl();
            helloImpl.setORB(orb);

            org.omg.CORBA.Object ref =
                    rootpoa.servant_to_reference(helloImpl);

            Hello href = HelloHelper.narrow(ref);

            String ior = orb.object_to_string(href);

            PrintWriter out = new PrintWriter(new FileWriter("hello.ior"));
            out.println(ior);
            out.close();
            System.out.println("Java CORBA Server started");
            System.out.println("IOR saved in hello.ior");

            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
