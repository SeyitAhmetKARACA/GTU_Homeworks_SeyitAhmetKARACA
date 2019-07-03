# ALgoritmamda N degerindeki 3e bolunme veya 5 e bolunme olayini dikkate almadan
# basamak degerini dikkate alarak kurdum.N degeri eger 3 ve 5 in kati ise
# sayi degerinin buyuk olmasi icin 3 tane 5i actigim listeye ekledim.
# sadece 5 in kati ise 5 tane 3u listeye ekledim.
# sadece 3 un kati ise listeye 3 tane 5 ekledim.
# eger ikisininde kati degil ise olusacak sayinin buyuk olmasi icin
# 3 tane 5 ekledim. ne kadar cok 5 olursa o kadar sayi buyuk olur diye.
# bu islemleri yaparken listeye kac basamak eklediysem N degerini o kadar azalttim
# N degeri 0 olur ise tam olarak 3 ve 5 leri dizmis oluyor.
# 0 olmaz ise eksi degerler olacak bu da olusmus sayi 5 e bolunen veya 3 e bolunen
# sayilardan olusmamis oluyor.
# en sonda listenin elemanlarini tam bir sayiya donusturup return ettim.

def decentNumber(parametre):
    liste = []
    while(parametre > 0):
        if(parametre %3 == 0 and parametre % 5 == 0):
            liste.append(5)
            liste.append(5)
            liste.append(5)
            parametre = parametre - 3
            
        elif(parametre %3 != 0 and parametre % 5 == 0):
            liste.append(3)
            liste.append(3)
            liste.append(3)
            liste.append(3)
            liste.append(3)
            parametre = parametre - 5

        elif(parametre %3 == 0 and parametre % 5 != 0):
            liste.append(5)
            liste.append(5)
            liste.append(5)
            parametre = parametre - 3
        
        elif(parametre %3 != 0 and parametre % 5 != 0):
            liste.append(5)
            liste.append(5)
            liste.append(5)
            parametre = parametre - 3
    i = 0
    katSayi = 1
    rv = 0
    liste = liste[::-1]
    while(len(liste)-i > 0):
        rv = liste[i] * katSayi + rv
        katSayi = katSayi * 10
        i = i + 1

    if(parametre == 0):
        return rv
    else:
        return -1
     
print(decentNumber(11))
