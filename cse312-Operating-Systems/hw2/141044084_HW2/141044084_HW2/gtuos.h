#ifndef H_GTUOS
#define H_GTUOS

#include "8080emuCPP.h"
#include <vector>

typedef struct thread{
	State8080 state;
	long threadID;
	unsigned int startTimeOfThread;
	int totalThread;
	int threadStatus;
	int functionAddress;
	int stackEmptySpace;
	bool checkBeginFunction;
}thread;

using namespace std;
class GTUOS{
	public:
		GTUOS();
		uint64_t handleCall(CPU8080 & cpu,unsigned int & cycle);
		int PRINT_B(const CPU8080 & cpu);
		int PRINT_MEM(const CPU8080 & cpu);
		int READ_B(const CPU8080 & cpu);
		int READ_MEM(const CPU8080 & cpu);
		int PRINT_STR(const CPU8080 & cpu);
		int READ_STR(const CPU8080 & cpu);
		int GET_RND(const CPU8080 & cpu);
		void writeMemory(const CPU8080 & cpu);
                int TExit(CPU8080 & cpu);
                int TJoin(const CPU8080 & cpu);
                int TYield(CPU8080 & cpu);
                int TCreate(const CPU8080 & cpu,unsigned int & cycle);
		void roundRobin(unsigned int & cycle,CPU8080 & cpu);
	private:
		int threadCount;
		vector<thread> threadTable;
		int threadindex;
};


#endif
