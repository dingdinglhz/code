'''
Created on 2013-8-27

@author: linhz
'''
import matplotlib.pyplot as plt
from matplotlib.colors import BoundaryNorm
from matplotlib.ticker import MaxNLocator
import numpy as np
import sys
import getopt

def showSet(x,y,z,cmap,norm):
    plt.pcolormesh(x, y, z, cmap=cmap, norm=norm)
    plt.colorbar()
    # set the limits of the plot to the limits of the data
    plt.axis([x.min(), x.max(), y.min(), y.max()])
    plt.show()
    
def juliaSet(side=4,lx=-2,ly=-2,cx=-0.4,cy=0.6,resolution=400,iteration=10,showStep=0):
    dx=dy=side/resolution
    y, x = np.mgrid[slice(ly,ly+side+dy,dy),
                slice(lx,lx+side+dx,dx)]
    cmap = plt.get_cmap('hsv')
    levels = MaxNLocator(nbins=200).tick_values(0,2)
    norm = BoundaryNorm(levels, ncolors=cmap.N, clip=True)
    xi,yi=x,y
    for i in range(0,iteration):
        z=np.ma.masked_greater_equal(np.sqrt(xi*xi+yi*yi), 100)
        xi=np.ma.masked_array(xi,mask=z.mask)
        yi=np.ma.masked_array(yi,mask=z.mask)
        (xi,yi)=( (xi*xi-yi*yi+cx) , (2*xi*yi+cy) )
        #print(z)
        if(showStep):
            if(i%showStep==0):
                showSet(x,y,z,cmap,norm)
    showSet(x,y,z,cmap,norm)
   
        
if __name__ == "__main__":
    side=4
    (lx,ly)=(-2,-2)
    (cx,cy)=(-0.4,0.6)
    it=10
    print(sys.argv)
    opts,args=getopt.getopt(sys.argv[1:],"s:",["lx=","ly=","cx=","cy=","it="])
    for(op,value)in opts:
        if op=="-s":
            side=float(value)
        elif op=="--lx":
            lx=float(value)
        elif op=="--ly":
            ly=float(value)
        elif op=="--cx":
            cx=float(value)
        elif op=="--cy":
            cy=float(value)
        elif op=="--it":
            it=int(value)
        print("op:"+op+" value:"+value)          
    juliaSet(side,lx,ly,cx,cy,400,it,showStep=0)