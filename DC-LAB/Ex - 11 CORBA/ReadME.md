# CORBA Java–C++ Interoperability

## Aim
To implement CORBA communication between Java server and C++ client using IDL.

---

## Steps

### 1. Compile IDL
idlj -fall Hello.idl
omniidl -bcxx Hello.idl

---

### 2. Compile Java
javac demo/*.java HelloImpl.java HelloServer.java

---

### 3. Run Java Server
java HelloServer

---

### 4. Compile C++
g++ HelloClient.cpp HelloSK.cc -o client `pkg-config --cflags --libs omniORB4`

---

### 5. Run Client
./client
