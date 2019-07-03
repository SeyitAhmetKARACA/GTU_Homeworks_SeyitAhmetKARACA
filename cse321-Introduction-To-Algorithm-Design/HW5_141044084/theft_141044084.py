# algoritmamda best case veya worst case yok.
# her turlu n2 calisiyor.
# verilen dizideki her satirdan giriyor.
# stunlari dolasiyor.
# n satir , m stun var ise 
# O(n*m) comlexity de calisiyor.
# Algoritmamda ilk basta satir ve stun degerlerini aldim.
# satir satir her turlu normal gezicegi icin for loop ile hallettim onu.
# (greedy olmadigi icin hepsine bakmam gerek diye dusundum.)
# stunlari gezerken girdigi ilk satiri val degiskeninde tutarak
# saga gittikte en buyuk olan sayinin indexine baktim.
# eger en buyuk (0) dedigim sayidan buyuk olan hangisi ise
# j+1 , j , j-1 bunlardan biri buyuk ise en buyuk olarak tanidim ve
# val degiskenine ekledim bunu.
# saga giderken satirlar arasindaki degisikligi tuttugum temp i degiskeniyle hallettim.
# dongunun basinda hep yedegini alip bu tempi duruma bagli olarak r degiskeninde tuttum.
# dongu sonunda gecici i degerine ekleyerek en son kaldigim satiri buldum.
# en sagdan ciktigimda ise bir listeye attim.
# listenin en buyuk elemanini return ettim.

# n row
# m column
def theft(liste):
    x = len(liste)
    y = len(liste[0])
    rvListe = []
    count = 0  # stunlari istedigim gibi gezebilmek icin actim bunu.
    temp = liste
    
    eb = 0 
    j = 0
    for i in range(0,x):
        ti = i # temp i , listenin satirlarinde gezmek icin
        r = 0 # +1 0 -1 icin
        val = liste[i][j]  # val en buyuk olan sayilari toplamak icin
        while(count < y-1 ):
            
            if(ti+1 < x and liste[ti+1][j+1] > eb):
                eb = liste[ti+1][j+1]
                r = 1
                
            if(liste[ti][j+1] > eb):
                eb = liste[ti][j+1]
                r = 0
                
            if(ti-1 >= 0 and liste[ti-1][j+1] > eb):
                eb = liste[ti-1][j+1]
                r= -1

            ti = ti + r
            val = val + eb
            eb = 0
            j = j +1
            count = count +1
        j = 0
        count = 0
        
        rvListe.append(val)
 
    
    return max(rvListe)

amountOfMoneyInLand = [[1,3,1,5],[2,2,4,1],[5,0,2,3],[0,6,1,2]]

res = theft(amountOfMoneyInLand)
print(res)
