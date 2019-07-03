#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Dec 26 21:11:26 2018

@author: sakaraca
"""
import math

def optimalSequence(_hotel):
    penalty = [0]*len(_hotel)
    stopat = [0]*len(_hotel)
    finalStops = []
    for i in range(0,len(_hotel)):
        penalty[i] = math.pow((200 - _hotel[i]), 2)
        stopat[i] = 0
        for j in range(0,i):
            if penalty[j] + math.pow(200-(_hotel[i] - _hotel[j]),2) < penalty[i]:
                penalty[i] = penalty[j]+ math.pow(200-(_hotel[i] - _hotel[j]),2)
                stopat[i] = j + 1
    print("all penalties :", penalty)
    print("minimum penalty: ",penalty[-1])
  
    index = len(stopat) - 1
    while index >= 0:
        finalStops.append(index +1)
        index = stopat[index] - 1
    
    return finalStops[::-1]
    

inputHotel = [190, 220, 410, 580, 640, 770, 950, 1100, 1350]
optimalSopAtHotels = optimalSequence(inputHotel)

print("Optimum stop at hotels",optimalSopAtHotels)
    
    
    
