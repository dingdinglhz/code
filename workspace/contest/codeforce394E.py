
n=0
m=0
class Point:
	"""oedered pair representing a dot"""
	def __init__(self,x=0,y=0):
		self.x = x
		self.y = y
	def disSqr(self):
		return self.x*self.x+self.y*self.y
	def __add__(a,b):
		return Point(a.x+b.x,a.y+b.y)
	def __sub__(a,b):
		return Point(a.x-b.x,a.y-b.y)
	def __mul__(a,c):
		return Point(a.x*c,a.y*c)
	def __truediv__(a,c):
		return Point(a.x/c,a.y/c)
	def __repr__(self):
		return object.__repr__(self)+"point({0},{1})".format(self.x,self.y)
	def crossProduct(o,a,b):
		oa=a-o
		ob=b-o
		return ob.x*oa.y-oa.x*ob.y
	def dotProduct(o,a,b):
		oa=a-o
		ob=b-o
		return ob.x*oa.x+oa.y*ob.y
generator=list()
vertex=list()
def main():
	n=int(input())
	for i in range(n):
		(x,y)=[int(tmp) for tmp in input().split()]
		generator.append(Point(x,y))
	m=int(input())
	for i in range(m):
		(x,y)=[int(tmp) for tmp in input().split()]
		vertex.append(Point(x,y))


'''               

Point generator[MAX_N];
Point vertex[MAX_M];
Point center;
double centerDisSqr;
void input(){
	cin>>n;
	for (int i = 0; i < n; ++i){
		cin>>generator[i].x>>generator[i].y;
	}
	cin>>m;
	for (int i = 0; i < m; ++i){
		cin>>vertex[i].x>>vertex[i].y;
	}
	/*for (int i = 0; i < n; ++i){
		cout<<"generator:( "<<generator[i].x<<" , "<<generator[i].y<<" ) "<<endl;
	}
	for (int i = 0; i < m; ++i){
		cout<<"vertex:( "<<vertex[i].x<<" , "<<vertex[i].y<<" ) "<<endl;
	}*/
}
Point findCenter(){
	Point tmp;
	for (int i = 0; i < n; ++i){
		tmp=tmp+generator[i];
		//cout<<"tmpcm:( "<<tmp.x<<" , "<<tmp.y<<" ) "<<endl;
	}
	tmp=tmp/double(n);
	return tmp;
}
void findCenterDIsSqr(){
	centerDisSqr=0;
	for (int i = 0; i < n; ++i){
		Point tmp=generator[i]-center;
		centerDisSqr+=tmp.disSqr();
	}
}
bool checkInside(){
	Point lastP=vertex[m-1];
	for (int i = 0; i < m; ++i){
		if(crossProduct(lastP,vertex[i],center)<0){
			return false;
		}
		lastP=vertex[i];
	}
	return true;
}
double findMinDis(){
	double minDis1=MAX_DIS;
	double minDis2=MAX_DIS;
	Point tmp,minP1,minP2;
	for (int i = 0; i < m; ++i){
		tmp=vertex[i]-center;
		if (tmp.disSqr()<minDis2){
			if (tmp.disSqr()<minDis1){
				minDis2=minDis1;
				minP2=minP1;
				minDis1=tmp.disSqr();
				minP1=vertex[i];
			}
			else{
				minDis2=tmp.disSqr();
				minP2=vertex[i];
			}
		}
	}
	//cout<<"minP1:( "<<minP1.x<<" , "<<minP1.y<<" ) dis^2= "<<minDis1<<endl;
	//cout<<"minP2:( "<<minP2.x<<" , "<<minP2.y<<" ) dis^2= "<<minDis2<<endl;
	if (dotProduct(minP1,minP2,center)>0 & dotProduct(minP2,minP1,center)>0){//if both angle less than 90, the perpendicular line is mimimum
		//cout<<"perpendicular line";
		double dotResult=crossProduct(minP1,minP2,center);
		Point base=minP2-minP1;
		double ans=dotResult*dotResult/(base.disSqr());
		return ans;
	}
	else{return minDis1;}
}
int main(int argc, char const *argv[])
{
	cout.precision(100);
	input();
	center=findCenter();
	findCenterDIsSqr();
	//cout<<"center:( "<<center.x<<" , "<<center.y<<" ) dis^2= "<<centerDisSqr<<endl;
	if(checkInside()){
		cout<<centerDisSqr;
	}
	else{
		double minDisSqr=findMinDis();
		double ans=centerDisSqr+n*minDisSqr;
		cout<<ans;
	}
	//system("pause");
	return 0;
}
/*
4
3 2
3 4
5 4
5 2
4
3 3
4 4
5 3
4 2
*************
8.00000000
*****************
5
2 8
-6 -1
-6 -3
-3 4
2 -10
6
0 0
10 10
20 16
30 6
36 -4
26 -14
*************
279.00000000
*****************
11
-74 63
66 92
29 66
-24 -75
100 79
4 73
-6 -69
-72 42
37 -77
-25 2
62 -76
26
0 0
100 72
200 141
300 203
400 243
500 270
600 293
700 312
772 212
841 112
903 12
943 -88
970 -188
993 -288
1012 -388
912 -460
812 -529
712 -591
612 -631
512 -658
412 -681
312 -700
240 -600
171 -500
109 -400
69 -300
***************
82527.18402146
*/
'''
