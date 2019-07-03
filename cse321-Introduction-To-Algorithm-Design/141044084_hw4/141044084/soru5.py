#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 27 01:09:57 2018

@author: sakaraca
"""

def question5(_equations):
    nf = []
    myset = set()
    myset.add(equations[0][0])
    x = len(myset)
    for i in equations:
        nft = []
        for j in i:
            myset.add(j)
            if x != len(myset):
                nft.append(equations.index(i))
                nft.append(i.index(j))
            x = len(myset)
            
        if len(nft) > 0:
            nf.append(nft)
    return nf

equations=[
        [1,1,1,1,1],
        [1,1,1,2,1],
        [1,1,1,3,1]
        ]


print(question5(equations))