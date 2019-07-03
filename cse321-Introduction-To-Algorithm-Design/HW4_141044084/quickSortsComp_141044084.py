# iki algoritmada da ayni sayida comparison yapiliyor
# lomuto nun impelementi daha kolay.
# pivot secerken iki algoritmada da swap islemleri olsada
# Hoare partitionda her seferinde 3 kez swap olacagi kesinken
# lomuto da dongu icerisinde surekli bir swap islemi olabilir
# quickSortun worst case durumunda lomuto daha kotu sonuc verir.
# uzun olsada en kotu durumu gozeterek calistigimizdan
# performans gozetiyorsak Hoare partition kullanilmali.


def swap(arr,a,b):
    x = arr[b]
    arr[b] = arr[a]
    arr[a] = x

def Lomuto(arr,l,r):
    p = arr[l]
    s = l
    for i in range(l,r):
        if(arr[i] < p):
            s = s+1
            swap(arr,s,i)
        
    swap(arr,l,s)
    return s


def Hoare(arr,l,r):
    p= arr[l]
    i = l
    j = r+1
    
    while True:
        while(arr[i] <= p):
            i = i+1
        
        while(arr[j] > p):
            j = j-1
        
        swap(arr,i, j)
        
        if(i >= j):
            break

    swap(arr,i, j)
    swap(arr,l, j)
    return j

def qs(liste,p,r):
    if(p < r):
        q = Lomuto(liste,p,r)
        qs(liste,p,q)
        qs(liste,q+1,r)

def quickSort(arr):
    return qs(arr,0,len(arr)-2)


listeamk = [15,4,68,24,75,16,42]

print(quickSort(listeamk))
