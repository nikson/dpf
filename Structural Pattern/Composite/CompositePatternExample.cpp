#include <iostream>
#include <vector>
using namespace std;

/*
	Composite: tree structure or hierarchies of object need a composite pattern (generally) 
*/


// Primary abstract class 
class Component 
{
public: 
	virtual int GetValue() = 0;
};

//  Child/leaf/part
class Child : public Component
{
	int netval;
public: 
	Child(int val) 
	{
		netval = val;
	}

	int GetValue() 
	{
		return netval;
	}
};

class Composite : public Component
{
	vector<Component*> item;
public:
	Composite() 
	{
		
	}
	void AddChild(Component *c)
	{
		item.push_back(c);
	}

	int GetValue() 
	{
		int temp = 0;
		for ( vector<Component*>::iterator it = item.begin(); it != item.end(); it++ )
		{
			temp += (*it)->GetValue();
		}

		return temp;
	}
};

int main() 
{
	Child *c1 = new Child(5);
	
	Composite *parent = new Composite();
	parent->AddChild(c1);
	parent->AddChild(new Child(20));

	cout << "Child value: " << c1->GetValue() << endl;
	cout << "Parent value: " << parent->GetValue() << endl;

	delete c1;
	delete parent;

	return 0;
}
