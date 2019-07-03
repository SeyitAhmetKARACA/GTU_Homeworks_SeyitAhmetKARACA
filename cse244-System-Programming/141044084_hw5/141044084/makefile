all: exe

exe: main.o
	gcc main.o -pthread -o grepSh

main.o: main.c
	gcc -c main.c

clean:
	rm *.o grepSh
