# longest_comment_postfix metodunda listeyi surekli en ufak parca kalana kadar
# boluyorum.Recursive olarak sag ve sol kisimlarini cagirdigimdan
# tek kalan kelimelerin biri l 'ye gideri r'ye geliyor.
# bu kelimeleri helper metoduna veriyorum.
# bu metod sondan geri dogru kelimeleri karsilasitiryor
# ayni olan kismini return ediyor.
# bu sekilde her ikililerden ayni kisimlari alarak
# sondan basa dogru ayni yerleri vere vere gidiyor.

# worst case analizinde ise butun kelimeler ayni olursa en kotu durum olusuyor.
# kelimenin uzunlugu m , dizideki eleman sayisi n olursa
# helper metodu kelimelerin tamamini taragidi icin, ordan bi m geliyor.
# divide&conquer da olsa cunku
# bir usttekilerle karsilasitiriyor ve toplam olarak n-1 defa helper calisiyor.
# yani worst case : O(n*m)

liste = ["absorptivity", "circularity","electricity", "importunity", "humanity","fareity"]

def helper(w1,w2):
  print("x   ",w1,w2)
  if(len(w1) > len(w2)):
    minlen = len(w2)
  else:
    minlen = len(w1)
  
  rW1 = w1[::-1]
  rW2 = w2[::-1]

  count = 0
  while(count < minlen and rW1[count] == rW2[count]):
    count = count + 1
  rl = rW1[0:count]
  return rl[::-1]

def longest_comment_postfix(param_liste):
  if(len(param_liste) == 1):
    return param_liste[0]
  else:
    m = (int)(len(param_liste)/2)
    l = longest_comment_postfix(param_liste[0:m])
    r = longest_comment_postfix(param_liste[m:len(param_liste)])
    return helper(l,r)

print(longest_comment_postfix(liste))






