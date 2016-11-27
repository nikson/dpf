#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

// Flyweight: interface
class Card
{
public:
	virtual int Status() const = 0;
	virtual void Move(int x, int y) = 0;		// extrinsic state
};

// Concrete Flyweight
class RealCard : public Card
{
    // intrinsic state of object
	int _id;
	int color;
	int state;
	int cx, cy;

public:
	RealCard(int id)
	{
		_id = id;
		state = _id%2;   	// [0,1]
	}

	virtual void Move(int x, int y)
	{
		// move around the board
	}

	virtual int Status() const { return state; }

    bool operator==(const RealCard &rhs) const
    {
        return (this->_id == rhs._id);
    }
};

// Flyweight Factory
class CardFactory
{
private:
	static CardFactory *instance;       // Singleton
	vector<RealCard> data;
	CardFactory() { }

public:
	static CardFactory* Instance()
	{
		if ( !instance ) instance = new CardFactory();
		return instance;
	}

	// getFlyweight() : Flyweight
	Card* getCard(int id)
	{
		RealCard *c = new RealCard(id);

		vector<RealCard>::iterator it = find( data.begin(), data.end(), *c) ;
		if( it != data.end() ) {
		 	c =  &(*it);
			cout << "Return existing data..." << endl;
		}
		else
		{
			data.push_back(*c);
		}
		// cout << "Data len: " << data.size() << endl;

		return c;
	}
};

// Initialize static members
CardFactory* CardFactory::instance = NULL;

// Client
int main ()
{

	Card *c1 = CardFactory::Instance()->getCard(1);
    Card *c2 = CardFactory::Instance()->getCard(2);

	cout << "C1 status: " << c1->Status() << endl;
	cout << "C2 status: " << c2->Status() << endl;

	c1 = CardFactory::Instance()->getCard(1);
	cout << "C1 status: " << c1->Status() << endl;

	return 0;
}
