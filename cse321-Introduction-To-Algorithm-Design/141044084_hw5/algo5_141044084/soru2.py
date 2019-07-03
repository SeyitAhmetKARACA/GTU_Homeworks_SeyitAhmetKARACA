#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 27 17:42:33 2018

@author: sakaraca
"""


def optimalCost(_n, _m ,costn , costs):
    if _n > len(costn) and len(costs):
        return -1
    OPTn = [0]*len(N)
    OPTs = [0]*len(S)

    for i in range(0, _n):
       OPTn[i] = costn[i] + min(OPTn[i-1], _m + OPTs[i-1])  # ilk iterasyon icin OPTx[-1] = 0
       OPTs[i] = costs[i] + min(OPTs[i-1], _m + OPTn[i-1])

    print("Optimum reuslts for NY", OPTn)
    print("Optimum reuslts for SF", OPTs)
    return OPTn[-1] if OPTn[-1] < OPTs[-1] else OPTs[-1]

# N : Costs of NY
# S : Costs of SF
# M : costs of moving between cities
# n : number of month


N = [1, 3, 1, 8]  # [1,3,20,30]
S = [20, 1, 20, 5]  # [50,20,2,4]
M = 20
n = len(N)

print("Optimal result is", optimalCost(n, M, N, S))


