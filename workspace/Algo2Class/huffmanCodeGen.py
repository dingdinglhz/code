import sys,os  # @UnusedImport
class Symbol:
    def __init__(self,c=' ',p=0.0):
        self.c=c
        self.p=p
    def __lt__(self,y):
        return self.p<y.p
    def __rt__(self,y):
        return self.p>y.p
class SubTree:
    def __init__(self,lc=None,rc=None,p=0.0):
        self.lc=lc
        self.rc=rc
        self.p=p
def show(prefix,t):
    if type(t)==Symbol:
        print("Symbol {0} Code {1} p={2}".format(t.c,prefix,t.p))
    else:
        show(prefix+'0',t.lc)
        show(prefix+'1',t.rc)
def popOut():
    if len(q)==0:
        return s.pop(0)
    elif len(s)==0:
        return q.pop(0)
    elif q[0].p<s[0].p:
        return q.pop(0)
    else:
        return s.pop(0)
if __name__ == '__main__':
    n=int(input())
    s=list()
    q=list()
    for i in range(n):
        stmp=input().split()
        s.append(Symbol(stmp[0],float(stmp[1])))
    s.sort()
    
    for i in range(n-1):
        tmpa=popOut()
        tmpb=popOut()
        q.append(SubTree(tmpa,tmpb,tmpa.p+tmpb.p))
    show("",q.pop(0))
        
'''
DATA
7
A 0.05 
B 0.4
C 0.08 
D 0.04 
E 0.1 
F 0.1 
G 0.23
5
a 0.32
b 0.25
c 0.2
d 0.18
e 0.05
'''