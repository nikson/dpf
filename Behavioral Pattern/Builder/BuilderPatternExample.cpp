#include<iostream>

using namespace std;

class Car
{
public:
    string modelName;
    int makeYear;
    string color;
};


class CarBuilder
{
private:
    Car car;
public:
    CarBuilder& SetModel(string name);
    CarBuilder& SetMakeYear(int year)
    {
        car.makeYear = year;
        return *this;
    }

    Car build()
    {
        return car;
    }
};

CarBuilder& CarBuilder::SetModel(string model)
{
    car.modelName = model;
    return *this;
}


int main()
{
    CarBuilder cb;
    // Method chaining with builder pattern

    Car c = cb.SetMakeYear(1999).SetModel("BMW").build();
    cout << c.modelName  << " " << c.makeYear << endl;

    return 0;
}
