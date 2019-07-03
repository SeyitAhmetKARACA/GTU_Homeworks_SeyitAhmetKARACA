all: hello

hello: main.o
	g++ main.cpp -o prog

main.o: main.cpp
	g++ -c main.cpp

run0:
	./prog e2.txt 0

run1:
	./prog e2.txt 1

clean:
	rm *.o prog
