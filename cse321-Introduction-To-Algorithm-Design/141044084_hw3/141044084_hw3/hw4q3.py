import math

def q3Helper(liste,lmin,lmax):
	mid = math.floor((lmax + lmin)/2)
	if mid == liste[mid]:
		return mid
	elif mid >= lmax or mid <= lmin:
		return -1
	else:
		
		s = q3Helper(liste,lmin,mid)
		b = q3Helper(liste,mid,lmax)
		if(s >= b):
			return s
		else:
			return b

def q3(listeq3):
	return q3Helper(listeq3,0,len(listeq3))

a = [0,2,4,6,8]
b = [-1,1,4,6,8,10]
c = [-1,1,2,10]
d = [-4,-2,0,3,8,10]
e = [-1,0,1,2,4,10]
f = [-1,0,1,2,3,5]
g = [10,20,30,40,50,60]

for i in a,b,c,d,e,f,g:
	print(q3(i))
