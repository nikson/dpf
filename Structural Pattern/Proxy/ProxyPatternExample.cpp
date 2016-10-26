#include <iostream>
using namespace std;

/*
#### 4 type of proxy
1. Virtual proxy: placeholder of an expensive object
2. Remote proxy: local representation of resides in different address space. ex: stub in rpc/rmi
3. Protective proxy: access control of sensitive object
4. Smart proxy: interposes additional action (counting reference of main object, loading a persisting object into memory
*/

class Image
{
public:
    virtual void loadImage() = 0;       /* pure virtual function */
};

class RealImage : public Image
{
    string path;
public:
    RealImage(string imagepath)
    {
        path = imagepath;
        loadImage();
    }

    void loadImage()
    {
        // Load the image into system memory
        cout << "Image loadded: " << path << endl;
    }
};

class ProxyImage : public Image
{
    string path;
    RealImage *rimage = NULL;
public:
    ProxyImage(string imagepath)
    {
        path = imagepath;
    }

    ~ProxyImage()
    {
        delete rimage;
    }

    void loadImage()
    {
        if ( !rimage )
        {
            rimage = new RealImage(path);
        }
        else
        {
            rimage->loadImage();
        }
    }
};

int main ()
{
    // Image is not loaded until called loadImage method of proxy
    Image *r1 = new ProxyImage("1.jpg");
    Image *r2 = new ProxyImage("2.jpg");

    r1->loadImage();
    r2->loadImage();

    // Image is loaded into memory while object instantiate
    Image *r3 = new RealImage("3.jpg");

    return 0;
}
