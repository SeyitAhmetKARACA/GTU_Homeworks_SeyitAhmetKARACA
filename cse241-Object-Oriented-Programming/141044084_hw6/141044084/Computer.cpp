#include "Computer.h"
#include "CPU.h"
#include "CPUProgramDyn.h"
#include "Memory.h"

using namespace dyn;
using namespace std;

Computer::Computer(int pOption){
	option=pOption;
}

Computer::Computer(const CPU& pCpu,const CPUProgramDyn& pCpuprogram,const Memory& pMemory,const int pOption){
	setCPU(pCpu);
	setCPUProgram(pCpuprogram);
	setMemory(pMemory);
	option=pOption;
}

void Computer::execute(){
	cCpu.setSize(cCpuprogram.size());
	cCpu.setOption(option);
	while(!cCpu.Halted()){
		cCpu.execute(cCpuprogram.getLine(cCpu.getPc()),cMemory);
	}
	return;
}
