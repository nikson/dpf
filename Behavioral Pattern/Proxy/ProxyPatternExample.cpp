/*
    Proxy Pattern/Decorator or Adapter Pattern
*/

#include <iostream>

using namespace std;

class ITask
{
public:
    virtual void Start() = 0;       /* pure virtual function */
};

class Machine : public ITask
{
    public:
     void Start()
     {
         cout << "Started task...."   << endl;
     }
};

class ProxyMachine
{
private:
    Machine m;
public:
    bool started = false;
    bool getMachineStatus()
    {
        return started;
    }
    void Start()
    {
        if ( started)
        {
            m.Start();
        }
        else
        {
            cout << "Machine is not started yet!" << endl;
        }
    }
};

int main ()
{
    ProxyMachine pm;

    // pm working as a proxy of Machine instance
    pm.Start();
    pm.started = true;
    pm.Start();
    pm.Start();

    return 0;
}
