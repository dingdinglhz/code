'''
Created on 2013-9-1

@author: linhz
'''
import sys, os
class RedirectStdinTo:
    def __init__(self, in_new):
        self.in_new = in_new

    def __enter__(self):
        self.in_old = sys.stdin
        sys.stdin = self.in_new

    def __exit__(self, *args):
        sys.stdin = self.in_old
        
class job:
    def __init__(self, weight, length):
        self.weight = weight
        self.length = length
        self.key = length / weight
    def __str__(self):
        # return "weight: "+str(self.weight)+" length: "+str(self.length)+" key: "+str(self.key)
        return "weight: %d length: %d key: %d" % (self.weight, self.length, self.key)
    def __lt__(self, y):
        if self.key == y.key:
            return self.weight > y.weight
        return self.key < y.key


if __name__ == '__main__':
    with open("jobs.txt", encoding='utf-8') as inFile, RedirectStdinTo(inFile):
        num = int(input())
        jobList = []
        for i in range(0, num):
            inputTmp = input().split()
            jobi = job(int(inputTmp[0]), int(inputTmp[1]))
            jobList.append(jobi)
        jobList.sort()
        time = 0
        result = 0
        for i in jobList:
            # print(i)
            time += i.length
            result += i.weight * time;
            # print("time %d result %d"%(time,result))
            # os.system("pause")
        print(result)
        
'''
small scale data
6
30 90
10 30
20 40
30 50
20 60
10 50 
'''
