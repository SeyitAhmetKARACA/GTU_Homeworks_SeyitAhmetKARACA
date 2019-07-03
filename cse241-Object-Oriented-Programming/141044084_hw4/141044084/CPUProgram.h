
#ifndef CPUPROGRAM_H__
#define CPUPROGRAM_H__

#include <iostream>
#include <vector>
#include <fstream>
#include <limits>

using namespace std;

class CPUProgram{
public:
	CPUProgram(int);
	CPUProgram();
	int size(){ return sizeOfFile;} /*dosyada kac satir oldugu bilgisi*/
	void ReadFile(string);
	string toUpper(string);
	/* parametre olarak gelen sayinin oldugu satiri donduruyor */
	const string getLine(const int);

private:
	vector<string> instructions; /*dosyayi tutuyor*/
	int option;
	int sizeOfFile;
};

#endif
