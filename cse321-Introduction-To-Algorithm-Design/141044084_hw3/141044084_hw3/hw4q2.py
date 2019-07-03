import random

def nim( _n , _m , player):
	if _n <= 0:
		if player == 1:
			return 2
		else:
			return 1	
	takenChip = random.randint(1,_m)
	print("n :",_n,"  player ",player," takes ",takenChip," chip")
	if player == 1:
		return nim(_n - takenChip, takenChip , 2)
	elif player == 2 :
		return nim(_n - takenChip, takenChip , 1)
	



n = 10 # total chips
m = 3  # how many chips players takes own turn
whoSturn = 1 # which player is start the game

if n%(m+1) != 0:
	print(nim(n ,m ,whoSturn )) # shows the winner
else:
	print("An error occured where initialize n and m.")
