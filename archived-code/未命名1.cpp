#include <iostream> 
#include <sstream> 
using namespace std; 
int main()   
{ 
	istringstream istr;
    string s="1 \n 56.7";
	istr.str(s); 
    //上述两个过程可以简单写成 istringstream istr("1 56.7"); 
    cout << istr.str()<<endl; 
    int a; 
    float b; 
	istr>>a; 
    cout<<a<<endl; 
	istr>>b; 
    cout<<b<<endl; 
    s="23 \n 56.7";
    
    istr>>a; 
    cout<<a<<endl; 
	istr>>b; 
    cout<<b<<endl; 
	system("pause"); 
	ostringstream ostr; 
    ostr.str("abc");//如果构造的时候设置了字符串参数,那么增长操作的时候不会从结        尾开始增加,而是修改原有数据,超出的部分增长 
    ostr.put('d'); 
    ostr.put('e'); 
    ostr<<"fg"; 

    string gstr = ostr.str(); 
    cout<<gstr; 
    system("pause"); 
stringstream sstr("ccc"); 
sstr.put('d'); 
sstr.put('e'); 
sstr<<"fg"; 
 gstr = sstr.str(); 
cout<<gstr<<endl; 
system("pause"); 
char ch;
sstr>>ch; 
cout<<ch<<endl;
cout<<sstr.str()<<endl;
sstr<<"hij";
cout<<sstr.str()<<endl;
sstr>>ch; 
cout<<ch<<endl;

system("pause"); 
}

