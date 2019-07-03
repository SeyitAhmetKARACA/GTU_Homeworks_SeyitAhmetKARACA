all: hello

hello: main.o cpu.o cpuprogram.o
	g++ main.o Cpu.o Cpuprogram.o -o pro

main.o: main.cpp
	g++ -c main.cpp

cpu.o: Cpu.cpp
	g++ -c Cpu.cpp

cpuprogram.o:Cpuprogram.cpp
	g++ -c Cpuprogram.cpp

clean:
	rm *.o pro

run0:
	./pro e2.txt 0

run1:
	./pro e2.txt 1

rune:
	./pro e.txt 0
