#include <iostream>
#include <fstream>
#include <string>
#include <omniORB4/CORBA.h>
#include "Hello.hh"

int main(int argc, char** argv) {
    try {
        CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);
        std::ifstream fin("hello.ior");
        if (!fin) {
            std::cerr << "Cannot open hello.ior" << std::endl;
            return 1;
        }

        std::string ior;
        std::getline(fin, ior);
        fin.close();
        CORBA::Object_var obj =
            orb->string_to_object(ior.c_str());
        demo::Hello_var hello =
            demo::Hello::_narrow(obj);

        if (CORBA::is_nil(hello)) {
            std::cerr << "Invalid object reference" << std::endl;
            return 1;
        }
        CORBA::String_var res =
            hello->sayHello("Indira");
        std::cout << "Response: " << res.in() << std::endl;
        orb->destroy();
    }
    catch (const CORBA::Exception& e) {
        std::cerr << "CORBA Exception occurred" << std::endl;
    }

    return 0;
}
