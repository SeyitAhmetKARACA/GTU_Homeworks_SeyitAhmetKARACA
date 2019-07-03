#ifndef CPU_H__
#define CPU_H__

#include <iostream>
#include <string>
#include "Memory.h"
#define NUMBER_OF_REGS 5

using namespace std;
class CPU{
public:
	CPU(); /* parametresiz c. argc = 0 */
	CPU(int); /* argc yi parametre olarak aliyor */
	void execute(string,Memory&);	
	bool Halted(){ return haltedValue;}//Halted fonksiyonu
	void setReg(int ,int);
	int getReg(int)const;
	int getPc()const;
	void print()const;
	void setSize(int);
	void setOption(int);
private:
	int size;
	int arg;
	int R[NUMBER_OF_REGS];
	bool haltedValue;/*Halted fonksiyonunun ciktisi*/
	int PC; /*Program Counter*/
	void setPc(int);
	int R_N_C(const string param);
	/*bunlar bu classin degil friend yapmamak icin
	burdalar static te olabilirlerdi aslinda*/
	void clearWSpace(string& param);
	int commaIndex(const string line);
	int stringToInt(string line);
	bool isInt(string);
	/* --------------------------------- */
	void MOV(Memory& Mem,int p1i,int p2iC,int p1type,int p2type);
	void ADD(Memory& Mem,int p1i,int p2iC,int p2type);
	void SUB(Memory& Mem,int p1i,int p2iC,int p2type);
	void PRN(const int input);
	bool JMP(const int);
	void HLT();
};

#endif
