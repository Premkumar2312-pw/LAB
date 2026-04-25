import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class AddServiceImpl extends UnicastRemoteObject implements AddService {
    protected AddServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public int sub(int a, int b) throws RemoteException {
        return a - b;
    }

    @Override
    public int mul(int a, int b) throws RemoteException {
        return a * b;
    }

    @Override
    public double div(int a, int b) throws RemoteException {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (double) a / b;
    }
}
