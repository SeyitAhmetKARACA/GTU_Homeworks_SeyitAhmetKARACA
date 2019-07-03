#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 27 15:06:45 2018

@author: sakaraca
"""
def alicePartyD(_K,_P):
    pairs = []
    pairsName = []
    invited = []
    for person in _P:
        pairlist = []
        pairName = []
        for ith in _K[person]:
            if ith not in invited and _P.index(person) not in invited:
                invited.append(ith)
                invited.append(_P.index(person))
                pairName.append(_P[ith])
                pairName.append(person)
                pairlist.append(ith)
                pairlist.append(_P.index(person))
                break
        if len(pairlist) > 0:
            pairsName.append(pairName)
            pairs.append(pairlist)
    return pairs,pairsName

def aliceParty(personList,friendList):
    K = {personList[0]:friendList[0],
         personList[1]:friendList[1],
         personList[2]:friendList[2],
         personList[3]:friendList[3],
         personList[4]:friendList[4],
         personList[5]:friendList[5],
         personList[6]:friendList[6],
         personList[7]:friendList[7],
         personList[8]:friendList[8], 
         personList[9]:friendList[9],
         personList[10]:friendList[10]}
    #print(K)
    return alicePartyD(K,personList)

P = ["seyit","aydın","safa","samet","yusuf","burki","furkan","cengo","fero","balli","sinan"]
PF = [[9,10,6,1,2],
      [6,7,8,9,10],
      [7,8,9,10,0],
      [10,0,1,2,7],
      [8,9,10,0,1],
      [0,1,2,3,4],
      [3,4,5,0,7],
      [5,6,3,8,9],
      [1,2,3,4,5],
      [4,5,6,7,8],
      [2,3,4,5,6]]


pairs , Names= aliceParty(P,PF)

print(pairs,Names,sep='\n')


