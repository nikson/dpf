#include <iostream>
using namespace std;

class User
{
protected:
    string username;
    string password;

public:
    // interface methods
    virtual void setName(string name) = 0;
    virtual void setPassword(string pwd) = 0;

    // Template method
    bool Login()
    {
        cout << "Login user: " << username << endl;
        return true;
    }
};

class Student : public User
{
public:
    void setName(string name)
    {
        username = name;
    }

    void setPassword(string pwd)
    {
        password = pwd;
    }

    // Student has limited properties
};

class Employee : public User
{
public:
    void setName(string name)
    {
        username = name;
    }

    void setPassword(string pwd)
    {
        password = pwd;
    }

    // emplyee can hold many other properties and privilege
};

int main()
{
    User *data[2] = {  new Student(), new Employee() };

    data[0]->setName("Student 1");
    data[1]->setName("Employee 1");

    for ( int i = 0; i < 2; i++)
    {
        data[i]->Login();
    }

    return 0;
}
