public interface AddService extends java.rmi.Remote {

    int add(int a, int b) throws java.rmi.RemoteException;

    int sub(int a, int b) throws java.rmi.RemoteException;

    int mul(int a, int b) throws java.rmi.RemoteException;

    double div(int a, int b) throws java.rmi.RemoteException;
}
