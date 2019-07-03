all: hello

hello: main.o cpu.o cpuprogram.o Memory.o Computer.o
	g++ main.cpp CPU.cpp CPUProgram.cpp Memory.cpp Computer.cpp -o pro

main.o: main.cpp
	g++ -c main.cpp

cpu.o: CPU.cpp
	g++ -c CPU.cpp

cpuprogram.o:CPUProgram.cpp
	g++ -c CPUProgram.cpp

Memory.o:Memory.cpp
	g++ -c Memory.cpp

Computer.o:Computer.cpp
	g++ -c Computer.cpp

clean:
	rm *.o pro

run0:
	./pro example2.txt 0

run1:
	./pro example2.txt 1
