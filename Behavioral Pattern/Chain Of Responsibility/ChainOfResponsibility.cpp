#include <iostream>
using namespace std;

/*
    When more than one object has a chance to handle a request, try to avoid coupling the sender of a request to it's receiver
    Chain the receiving object and pass the request
*/

class HelpHandler
{
private:
HelpHandler *successor = NULL;         // keep track of predecessor
int help_exist = 0;

public:
    HelpHandler(HelpHandler *r, bool hashelp)
    {
        successor = r;
        help_exist = hashelp;
    }

    void setHelpHandler(HelpHandler *r) { successor = r; }

    virtual void HandleHelp()
    {
        // delegate to next object
        if ( successor )
        {
            successor->HandleHelp();
        }
    }

    virtual bool HasHelp() { return (help_exist == true); }
};

class Widget: public HelpHandler
{
    Widget *parent;
public:
    Widget (Widget *w, bool hashelp) : HelpHandler(w, hashelp)
    {
        parent = w;
    }

    virtual void HandleHelp()
     {
         if ( HasHelp() )
         {
             cout << "Showing widget help...." << endl;
         }
         else
         {
             cout << "Widget: forwarding help request to parent..." << endl;

             HelpHandler::HandleHelp();
         }
     }
};

class Button : public Widget
{
 public:
     Button(Widget *w, bool hashelp) : Widget(w, hashelp) { }

     virtual void HandleHelp()
     {
         if ( HasHelp() )
         {
             cout << "Showing button help...." << endl;
         }
         else
         {
             cout << "Button: forwarding help request to parent..." << endl;

             HelpHandler::HandleHelp();
         }
     }
};

class Dialog : public Widget
{
 public:
     Dialog(HelpHandler *h, bool hashelp) : Widget(0, hashelp)
     {
         setHelpHandler(h);
     }

     virtual void HandleHelp()
     {
         if ( HasHelp() )
         {
             cout << "Showing dialog help...." << endl;
         }
         else
         {
             cout << "Dialog: forwarding help request to parent..." << endl;

             HelpHandler::HandleHelp();
         }
     }
};

// Application not widget it's direct from HelpHandler
class App : public HelpHandler
{
public:
    App(bool hashelp) : HelpHandler(0, hashelp) { }

    virtual void HandleHelp()
    {
         if ( HasHelp() )
         {
             cout << "Showing app help...." << endl;
         }
         else
         {
             HelpHandler::HandleHelp();
         }
     }
};

int main()
{
    App *a = new App(true);
    Dialog *d = new Dialog(a, false);
    Button *b = new Button(d, false);

    b->HandleHelp();

    cout << endl;

    a = new App(false);
    d = new Dialog(a, true);
    b = new Button(d, false);
    b->HandleHelp();

    return 0;
}
