#include <iostream>
using namespace std;

class Base {
public:
    void process()
    {
        // Template method
        step_one();
        step_two();
        step_three();
        cout << "Templete method: process() finish..." << endl;
    }
protected:
    virtual void step_one() = 0;        // abstract methods
    virtual void step_two() = 0;
    virtual void step_three()
    {
        cout << "Base" << ": " << "step_three() is invoked!" << endl;
    }
};

class Client1 : public Base
{
private:
    string name = "Client1";
protected:
    // Hook methods
    void step_one()
    {
        cout << name << ": " << "step_one() is invoked!" << endl;
        // work for step one
    }

    void step_two()
    {
        cout << name << ": " << "step_two() is invoked!" << endl;
        // work for step two
    }

};

int main()
{
    Base *t = new Client1();

    // Calling Template method, which will call the all hook methods of subclass
    t->process();

    return 0;
}
