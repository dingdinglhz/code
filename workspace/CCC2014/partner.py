'''
Created on 2014-2-25

@author: linhz
'''
def judge(nameA,nameB,i):
    if(nameA[i]==nameB[i]):
        return False
    tmp=nameB.index(nameA[i])
    if nameA[tmp]==nameB[i]:
        return True
    else:
        return False

if __name__ == '__main__':
    n=int(input())
    nameA=input().split()
    nameB=input().split()
    flag=True
    for i in range(n):
        if not judge(nameA,nameB,i):
            print("bad")
            flag=False
            break
    if flag:
        print("good")