def th(disk,src,dst,aux):
  if ((src == 'SRC' and dst == 'DST') or (src == 'DST' and dst == 'SRC')):
    y = 2
  elif((src == 'SRC' and dst == 'AUX') or src == 'AUX' and dst == 'SRC'):
    y = 1
  elif((src == 'AUX' and dst == 'DST') or src == 'DST' and dst == 'AUX'):
    y = 1
  x = disk+1
  a[disk] = a[disk] + x*y
  
  if(disk == 0):
    print("disk ",disk+1,":",src," -> ",dst,"\n")
  else:
    th(disk-1,src,aux,dst);
    print("disk ",disk+1,":",src," -> ",dst,"\n")
    th(disk-1,aux,dst,src)


data = input("Enter input size")

print("input size is",data)
data2 = int(data)
a=[0]*(data2)
th(data2-1,'SRC','DST','AUX')

count = 1
for i in a:
  print("Elapsed time for disk ",count,":",i)
  count = count+1
