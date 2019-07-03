#ifndef H_GTUOS
#define H_GTUOS

#include "8080emuCPP.h"

using namespace std;
class GTUOS{
	public:
		uint64_t handleCall(const CPU8080 & cpu);
        int PRINT_B(const CPU8080 & cpu);
        int PRINT_MEM(const CPU8080 & cpu);
        int READ_B(const CPU8080 & cpu);
        int READ_MEM(const CPU8080 & cpu);
        int PRINT_STR(const CPU8080 & cpu);
        int READ_STR(const CPU8080 & cpu);
        int GET_RND(const CPU8080 & cpu);
        void writeMemory(const CPU8080 & cpu);
};


#endif
