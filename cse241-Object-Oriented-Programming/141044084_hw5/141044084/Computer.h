#ifndef COMPUTER_H__
#define COMPUTER_H__

#include "CPU.h"
#include "CPUProgram.h"
#include "Memory.h"

using namespace std;

class Computer{
public:
	Computer(int option);
	Computer(const CPU& pCpu,const CPUProgram& pCpuprogram,const Memory& pMemory,const int pOption);
	void execute();
	
	void setCPU(const CPU& cpu){ cCpu = cpu;}
	void setCPUProgram(const CPUProgram& cp){ cCpuprogram = cp;}
	void setMemory(const Memory& m){ cMemory = m;}
	const CPU& getCPU(){return cCpu;}
	const Memory& getMemory(){ return cMemory; }
	const CPUProgram& getCPUProgram(){return cCpuprogram;}
private:
	int option;
	CPU cCpu;
	Memory cMemory;
	CPUProgram cCpuprogram;
};

#endif
