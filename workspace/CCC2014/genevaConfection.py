'''
Created on 2014-2-25

@author: linhz
'''
a=list()
a.reverse()
def check(seq,n):
    #print(sequence)
    i=1
    branch=list()
    while len(seq)>0:
        if len(seq)>0 and seq[0]==i:
            seq.pop(0)
            i+=1
        elif len(branch)>0 and branch[-1]==i:
            branch.pop()
            i+=1
        elif len(branch)==0 or seq[0]<branch[-1]:
            branch.append(seq.pop(0))
        else:
            return False
    return True
        
if __name__ == '__main__':
    T=int(input())
    for i in range(T):
        n=int(input())
        sequence=[int(input()) for j in range(n)]
        sequence.reverse()
        if check(sequence,n):
            print("Y")
        else:
            print("N")
'''
2
4
2
3
1
4
4
4
1
3
2
'''