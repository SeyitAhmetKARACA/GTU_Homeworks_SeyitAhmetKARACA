#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Dec 26 22:27:00 2018

@author: sakaraca
"""
#


def ReconstituteHelper(s1, d1):
    for item in d1:
        if len(item) <= len(s1) and item in s1[0:len(item)]:
            return d1.index(item)
    return -1

def Reconstitute(s, d):
    R = []
    for i in range(len(s)-1,-1,-1):
        cv = ReconstituteHelper(s[i:len(s)], d)
        if cv != -1:
            for qw in range(0, len(d[cv])):
                R.append(cv)
    return R[::-1]


dic = ["it", "was", "the", "best", "of", "times"]
cumle = "itwasthebestoftimes"

x = Reconstitute(cumle, dic)
print(x)
if len(x) == len(cumle):
    print("True")
else:
    print("False")