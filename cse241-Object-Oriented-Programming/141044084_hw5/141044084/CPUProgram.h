
#ifndef CPUPROGRAM_H__
#define CPUPROGRAM_H__

#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

class CPUProgram{
public:
	/*Not # operator+ ile gelen string (instruction) leri CPU class inda
	  kontrol ediyorum.O yuzden burda kontrol yapmadan direkt eklemesine
	  musade ettim .*/
	/* CPUProgram - CPUProgram iki parametre alan operator+
	   overload'u */
	friend const CPUProgram operator+(CPUProgram& c1,CPUProgram& c2);
	/*overload cout + CPUProgram */
	friend ostream& operator<<(ostream& a,const CPUProgram& o);
	/*CPUProgram - string overload*/
	friend CPUProgram operator+(CPUProgram&,string);

	CPUProgram(int);
	CPUProgram();
	/*Copy constructor*/
	CPUProgram(const CPUProgram&);

	const int size()const{ return sizeOfFile;}
	
	void ReadFile(string);
	string toUpper(string);

	const string getLine(const int);
	/*overloads*/
	/* operator[] , dosya satir sayisinden buyuk ve -(eksi) index
	   verilirse bos string, degilse satir sayisini return ediyor*/
	const string operator[](int);
	/* operator+= operator+(CPUProgram,string) i calistiriyor*/
	const CPUProgram& operator+=(string);
	/* operator== gelen parametre CPUProgram ile base CPUProgram'in
	   satir sayilari esit ise true , degilse false*/
	const bool operator==(const CPUProgram& o);
	/* operator!= gelen parametre CPUProgram ile base CPUProgram'in
	   satir sayilari esit degil ise true, esit ise false	*/
	const bool operator!=(const CPUProgram&);
	/* operator< parametre olarak gelen CPUProgram in satir sayisi 
	base CPUProgramin satir sayisinden buyuk ise true ,degil ise false
	*/
	const bool operator< (const CPUProgram&);
	/* operator< parametre olarak gelen CPUProgram in satir sayisi 
	base CPUProgramin satir sayisinden kucuk ise true ,degil ise false
	*/
	const bool operator> (const CPUProgram&);
	/* operator< parametre olarak gelen CPUProgram in satir sayisi 
       base CPUProgramin satir sayisinden buyuk esit ise true,degil ise false*/
	const bool operator<=(const CPUProgram&);
	/* operator< parametre olarak gelen CPUProgram in satir sayisi 
       base CPUProgramin satir sayisinden kucuk esit ise true,degil ise false*/
	const bool operator>=(const CPUProgram&);
	/* operator--() (pre decrement) CPUProgram daki son instruction ini silip
	   return ediyor.Hata durumu size 0 ise silmeden kendisini return
	    ediyor. */
	const CPUProgram& operator--();
	/* operator--(int) (post decrement) CPUProgram daki son instruction ini
	 silip degismemis halini return ediyor.
	 Hata durumu size 0 ise silmeden kendisini return ediyor. */
	const CPUProgram operator--(int);
	/* operator() function call : gelen 2 int degeri arasindaki satir
	   sayilarini yeni bir nesne olusturup return ediyor
   	   Hata : 2.parametre 1.den buyuk olmasi,negatif deger almalari,
	  satir sayisindan buyuk parametre verilmesi. 
	   return : kendisini return ediyor degisiklik yapmadan*/
	const CPUProgram operator()(int,int);
	/* operator= assignment , member wise copy yapildi*/
	const CPUProgram& operator=(const CPUProgram&);

private:
	/*instructionlari tutan yapi*/
	vector<string> instructions;
	int option;
	int sizeOfFile; /* satir sayisini tutan degisken*/
	string fname;   /* dosya ismini tutan degisken.*/
};

#endif
