all: hello

hello: main.o cpuprogram.o memory.o cpu.o computer.o
	g++ --std=c++11 CPUProgram.cpp Memory.cpp Computer.cpp CPU.cpp _mainTester.cpp -o pro

main.o: _mainTester.cpp
	g++ --std=c++11 -c _mainTester.cpp

cpuprogram.o:CPUProgram.cpp
	g++ --std=c++11 -c CPUProgram.cpp

cpu.o: CPU.cpp
	g++ --std=c++11 -c CPU.cpp

memory.o: Memory.cpp
	g++ --std=c++11 -c Memory.cpp

computer.o: Computer.cpp
	g++ --std=c++11 -c Memory.cpp

clean:
	rm *.o pro

run0:
	./pro instructions.txt 0

run1:
	./pro instructions.txt 1
