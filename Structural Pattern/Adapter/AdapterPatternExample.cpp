#include <iostream>
using namespace std;


class Shape 
{
public:
	virtual void Draw() = 0;
};

class TextView 
{
public: 
	TextView() 
	{
		//cout << "TextView..." << endl;
	}
	virtual void Show() 
	{
		cout << "Show the textview..." << endl;
	}
};


// class adapter 
class RectangleTextViewAdapter: public Shape, private TextView
{
public: 
	RectangleTextViewAdapter()
	{
		cout << "Rectangle-TextView Adapter..." << endl;
	}

	virtual void Draw() 
	{
		cout << "Drawing rectangle...." << endl;
		// call adaptee (textview) method 
		TextView::Show();
	}
};

int main() 
{
	Shape *s = new RectangleTextViewAdapter();
	s->Draw();


	return 0;
}

