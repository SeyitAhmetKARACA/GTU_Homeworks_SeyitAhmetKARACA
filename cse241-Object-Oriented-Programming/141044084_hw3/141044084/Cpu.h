#ifndef CPU_H__
#define CPU_H__

#include <iostream>
#include <string>

#define NUMBER_OF_REGS 5

using namespace std;
class Cpu{
public:
	Cpu(); /* parametresiz c. argc = 0 */
	Cpu(int); /* argc yi parametre olarak aliyor */
	void execute(string);		
	bool Halted(){ return haltedValue;}
	void setReg(int ,int);
	int getReg(int)const;
	int getPc()const;
	void print()const;
private:
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
	void MOV(const int p1i,const int p2iC,const int p2type);
	void ADD(const int p1i,const int p2iC,const int p2type);
	void SUB(const int p1i,const int p2iC,const int p2type);
	void PRN(const int input);
	bool JMP(const int);
	void HLT();
};

#endif
