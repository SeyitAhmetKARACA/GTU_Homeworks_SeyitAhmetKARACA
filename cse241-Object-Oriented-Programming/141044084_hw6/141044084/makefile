all: hello

hello: main.o cpuprogramdyn.o memory.o cpu.o computer.o
	g++ --std=c++11 CPUProgramDyn.cpp Memory.cpp Computer.cpp CPU.cpp _mainTester.cpp -o pro

main.o: _mainTester.cpp
	g++ --std=c++11 -c _mainTester.cpp

cpuprogramdyn.o:CPUProgramDyn.cpp
	g++ --std=c++11 -c CPUProgramDyn.cpp

cpu.o: CPU.cpp
	g++ --std=c++11 -c CPU.cpp

memory.o: Memory.cpp
	g++ --std=c++11 -c Memory.cpp

computer.o: Computer.cpp
	g++ --std=c++11 -c Memory.cpp

clean:
	rm *.o pro

run0:
	./pro instruction.txt 0

run1:
	./pro instruction.txt 1

run2:
	./pro instruction.txt 2
