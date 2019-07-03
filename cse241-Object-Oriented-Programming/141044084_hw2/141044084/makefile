all: hello

hello: main.o
	g++ main.cpp -o prog

main.o: main.cpp
	g++ -c main.cpp

run0:
	./prog example.txt 0

run1:
	./prog example.txt 1

run2:
	./prog example.txt 2

clean:
	rm *.o prog
