#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Dec 27 17:12:27 2018

@author: sakaraca
"""


def minimumWeightSumOfCompletionTime(_jobs):
	for i in range(0, len(jobs)):
		for j in range(0, i):
			if _jobs[i][0]/_jobs[i][1] > _jobs[j][0]/_jobs[j][1]:
				_jobs[i], _jobs[j] = _jobs[j], _jobs[i]

	sumOfweight = 0

	hour = 0
	for job in _jobs:
		hour = hour + job[1]
		sumOfweight = sumOfweight + job[0]*hour
	return sumOfweight


# Weight ,Time
jobs = [[5, 1000],
		[4, 10],
		[4, 10]]

print(minimumWeightSumOfCompletionTime(jobs))
