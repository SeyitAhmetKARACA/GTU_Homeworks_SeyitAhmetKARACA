# Recursive olarak listeyi surekli bolerek tek elemana kadar getiriyor
# indiriyor.Secilen elemandan itibaren saga ve sola giderek bir liste 
# olusturuyor.Bu listenin elemanlarinin toplamı digerlerinden kucuk ise
# return ediliyor.

# Divide&conquer olsa bile butun elemanlara baktıgından
# n kere rList metodu calisacak.
# Liste elemanlari hepsi negatif degerler olursa
# butun elemanlar icin liste surekli taranacak.
# bu yuzden worst case: O(n*n)


liste = [1,-4,-7,5,-13,2,-23,-1,88]

def rList(listex,i): # liste donduren metod
    bound = float('inf')
    tempList = []
    _sum = 0
    j = i+1
    
    while(i > -1 and bound > _sum + listex[i]):
        _sum = _sum + listex[i]
        bound = _sum
        tempList.append(listex[i])
        i = i-1
        
    tempList = tempList[::-1]
    while(j < len(listex) and bound > _sum + listex[j]):
        _sum = _sum + listex[j]
        bound = _sum
        tempList.append(listex[j])
        j = j+1
        
    return tempList
  
def min_subarray_finder(param_liste,l,r):
    if(r-l == 1):
        return rList(liste,l)
    else:
        m = (int)((l+r)/2)
        l = min_subarray_finder(param_liste,l,m)
        r = min_subarray_finder(param_liste,m,r)
    
        if(sum(l) >= sum(r)):
            return r
        else:
            return l

print(min_subarray_finder(liste,0,len(liste)))
print(sum(min_subarray_finder(liste,0,len(liste))))
