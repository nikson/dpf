#include <iostream>
using namespace std;

/*
	Bridge pattern: decouple an abstraction from it's implementation so that
	the two can vary independently.
	Bridge uses encapsulation, aggregation and inheritance to separate responsiblity into diferent classes.
	links: https://en.wikipedia.org/wiki/Bridge_pattern#Java
*/


class XWindowImp;   // forward declaration doesn't work here because of object instantiation, so I had to reorder classes

// dummy class
class View { };

class WindowImp
{
public:
	virtual void SetOrigin() = 0;
	virtual void Draw() = 0;
protected:
	WindowImp() { };
};

class XWindowImp : public WindowImp
{
public:
	XWindowImp() { }

	virtual void SetOrigin() { }

	virtual void Draw()
	{
		// Concrete implementation of drawing
		// which will invoke by abstractor
		cout << "Calling Draw function of XWindow..." << endl;
	}
};

class Window
{
public:
	Window(View* v) { }

	// request handled by window
	virtual void DrawContents() = 0;

	// request forwarded to implementation
	virtual void SetOrigin() = 0;
	virtual void Draw() = 0;

protected:
	WindowImp* _imp = NULL;
	WindowImp* GetWindowImp()
	{
		// Use abstract factory to get exact implementation
		if ( _imp == NULL )
		{
			// _imp = WindowImpFactory.Instance() -> GetWindowImp();
			_imp =  new XWindowImp();           // Temporary
		}

		return _imp;
	}

	View* GetView() { return new View(); }
};


// Subclass of window define the different kinds of windows the application might use

class ApplicationWindow : public Window
{
public:
	ApplicationWindow (View* view) : Window(view) { }

	virtual void DrawContents()
	{
		// here is the body of method
	}

	virtual void SetOrigin() { }

	// Implementor's functions
	virtual void Draw()
	{
	    cout << "Called Drawing function... " << endl;
		WindowImp* imp = Window::GetWindowImp();
		// call implementor class method
		if ( imp != NULL ) imp->Draw();

	}
};

class IconWindow : public Window
{
public:
	virtual void DrawContents()
	{
		// abstractor methods implementation
	}

	void SetOrigin() { }

	// Implementor's functions
	void Draw() { }
};


class PMWindowImp : public Window
{
public:
	void SetOrigin() { }
	void Draw() {}
};

int main()
{
	Window *win = new ApplicationWindow(new View());

	win->Draw();

	return 0;
};

