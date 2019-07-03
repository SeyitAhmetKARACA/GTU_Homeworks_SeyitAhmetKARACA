#ifndef COMPUTER_H__
#define COMPUTER_H__

#include "CPU.h"
#include "CPUProgramDyn.h"
#include "Memory.h"

using namespace dyn;
using namespace std;

class Computer{
public:
	Computer(int option);
	Computer(const CPU& pCpu,const CPUProgramDyn& pCpuprogram,const Memory& pMemory,const int pOption);
	void execute();
	
	void setCPU(const CPU& cpu){ cCpu = cpu;}
	void setCPUProgram(const CPUProgramDyn& cp){ cCpuprogram = cp;}
	void setMemory(const Memory& m){ cMemory = m;}
	const CPU& getCPU(){return cCpu;}
	const Memory& getMemory(){ return cMemory; }
	const CPUProgramDyn& getCPUProgram(){return cCpuprogram;}
private:
	int option;
	CPU cCpu;
	Memory cMemory;
	CPUProgramDyn cCpuprogram;
};

#endif
