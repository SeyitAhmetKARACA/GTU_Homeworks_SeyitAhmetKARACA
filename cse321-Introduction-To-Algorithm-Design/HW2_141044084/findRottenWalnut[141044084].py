liste = [1,1,1,0.5,1,1,1,1,1,1,1,1,1]

def compareScales (leftScaleList, rightScaleList):
    result = sum(leftScaleList) - sum(rightScaleList)
    if result < 0:
        return 1
    elif result > 0:
        return -1
    else:
        return 0

def rec(myList,i,j):
    mid1 = (int)((i+j)/2)
    if(i+j % 2 == 0):
      mid2 = mid1
    else:
      mid2 = mid1 +1
      
    x = compareScales(myList[i:mid2] , myList[mid1:j])

    if(x == -1):
        return rec(myList,mid1,j)
    elif(x == 1):
        return rec(myList,i,mid2)
    else:
        return mid1

print("rotten walnut index is : ",rec(liste,0,len(liste)))
