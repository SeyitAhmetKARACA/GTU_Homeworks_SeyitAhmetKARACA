#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 27 13:09:18 2018

@author: sakaraca
"""

def mergeSort(arr): 
    if len(arr) >1: 
        mid = len(arr)//2
        L = arr[:mid]
        R = arr[mid:]
  
        mergeSort(L)
        mergeSort(R) 
  
        i = j = k = 0
          
        while i < len(L) and j < len(R): 
            if L[i] < R[j]: 
                arr[k] = L[i] 
                i+=1
            else: 
                arr[k] = R[j] 
                j+=1
            k+=1
          
        while i < len(L): 
            arr[k] = L[i] 
            i+=1
            k+=1
          
        while j < len(R): 
            arr[k] = R[j] 
            j+=1
            k+=1
 
def question3(ksa):
    arr = []
    for item in ksa:
        for iteminner in item:
            arr.append(iteminner)
    return arr


ksorted = [[8,9,10,11,23],
       [1,2,3,6,23],
       [7,9,10,11,23],
       [6,9,10,11,23]]


print(question3(ksorted))
  





