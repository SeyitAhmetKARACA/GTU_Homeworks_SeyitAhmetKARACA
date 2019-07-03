all: exe

exe: seeWhat.o showResult.o timeServer.o
	gcc seeWhat.o -o sWhat
	gcc showResult.o -o sResult
	gcc timeServer.o -o tServer

seeWhat.o: seeWhat.c
	gcc -c seeWhat.c

showResult.o: showResult.c
	gcc -c showResult.c

timeServer.o: timeServer.c
	gcc -c timeServer.c

clean:
	rm *.o 
