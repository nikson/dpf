#include <iostream>
using namespace std;

/*
	Decorator: Attach additional responsibility to an object dynamically
*/


class View 
{
public:
	virtual void Draw() = 0;
};

class TextView : public View 
{
	// Core class "Is A" relationship
	int w, h;

public:
	TextView(int w, int h) 
	{
		this->w = w;
		this->h = h;
	}

	void Draw() 
	{
		cout << "TextView: w=" << w << ", h=" << h << endl;	
	}
};

class Decorator : public View 
{
	// "Has A" relationship
	View *view;

public:
	Decorator(View *v) 
	{
		view = v;
	}

	virtual void Draw()
	{
		view->Draw();
	}
};

class BorderDecorator : public Decorator
{
public:
	BorderDecorator(View *v) : Decorator(v) { }
	
	void Draw()
	{
		Decorator::Draw();
		cout << "	BorderBox" << endl;
		cout << "	Add here additional operation on this object" << endl;
	}
};

class ScrollDecorator : public Decorator
{
public:
	ScrollDecorator(View *v) : Decorator(v) { }
	
	void Draw()
	{
		Decorator::Draw();
		cout << "	ScrollBox" << endl;
		cout << "	Add here additional operation on this object" << endl;
	}
};

int main() 
{
	View *view = new ScrollDecorator(new BorderDecorator(new TextView(20, 20)));
	view->Draw();

	return 0;
}
	
