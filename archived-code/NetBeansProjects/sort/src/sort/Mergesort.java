/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

/**
 *
 * @author linhz
 */
public class Mergesort {
    public int data[][];
    public int n;

    Mergesort(int length) {
        n=length;
        data=new int[2][n];
    }
    /**
     *
     */
    public void Mergesort(){
        data=new int[2][];
    }
    public void Mergesort(int length){
        n=length;
        data=new int[2][n];
    }
    
    public void msort(int l,int r,int s){
	if(l<r){
		//test(l,r,s);
		int mid=(l+r)/2;
		msort(l,mid,s+1);
		msort(mid+1,r,s+1);
		//printf("after loading\n");
		//test(l,r,s);
		int i=l,j=mid+1,po=l;
		while(i<=mid && j<=r){
			if(data[(s+1)%2][i]<data[(s+1)%2][j]){
				data[s%2][po]=data[(s+1)%2][i];
				++i;
			}
			else{
				data[s%2][po]=data[(s+1)%2][j];
				++j;
			}
			++po;
		}
		//printf("after picking i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
		if(i<=mid){
			for (;i<=mid;++i,++po){
				data[s%2][po]=data[(s+1)%2][i];}
			}
		if(j<=r){
			for (;j<=r;++j,++po){
				data[s%2][po]=data[(s+1)%2][j];} 
			}
		//printf("after changing i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
		}
	}
	/*public static void main(String[] args) {
	scanf("%d",&n);
	int nn;
	for (int i = 0; i < n; ++i)
	{
		scanf("%d",&nn);
		data[0][i]=nn;
		data[1][i]=nn;
	}
	mergesort(0,n-1,0);
	test(-1,-1,0);
	//system("pause");
	return 0;
}*/
}
/*
8
1 3 8 4 5 7 6 2
 17
13 2 9 4 6 8 8 8 12 5 10 11 11 11 1 7 3 
17
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
17
17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
*/

    

