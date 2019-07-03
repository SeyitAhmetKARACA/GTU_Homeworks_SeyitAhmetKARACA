
#ifndef CPUPROGRAM_H__
#define CPUPROGRAM_H__

#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

class Cpuprogram{
public:
	Cpuprogram(string);/*dosya adini aliyor*/
	Cpuprogram();  /* dosya adini bos birakiyor*/
	static int size(){ return sizeOfFile;}
	void readFile(string);
	string toUpper(string);
	//readfile status get-set

	//getline
	const string getLine(const int);
	//size
private:
	vector<string> instructions; /*dosyayi tutuyor*/
	fstream fs;

	static int sizeOfFile;
};

#endif
