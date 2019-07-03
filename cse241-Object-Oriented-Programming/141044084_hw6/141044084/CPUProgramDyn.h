
#ifndef CPUPROGRAMDYN_H
#define CPUPROGRAMDYN_H

#include <iostream>
#include <string>
#include <fstream>

using namespace std;

namespace dyn{
	class CPUProgramDyn{
	public:
		/* CPUProgramDyn - CPUProgram iki parametre alan operator+
		   overload'u */
		friend const CPUProgramDyn operator+(CPUProgramDyn& c1,
							CPUProgramDyn& c2);
		/*overload cout + CPUProgramDyn */
		friend ostream& operator<<(ostream& a,const CPUProgramDyn& o);
		/*CPUProgramDyn - string overload*/
		
		friend CPUProgramDyn operator+(CPUProgramDyn&,string);

		CPUProgramDyn(int);//++
		CPUProgramDyn();//++
		/*Copy constructor*/
		CPUProgramDyn(const CPUProgramDyn&);

		const int size()const{ return sizeOfFile;}
	
		void ReadFile(string);
		string toUpper(string);

		const string getLine(const int)const;

		/*overloads*/
		/* operator[] , dosya satir sayisinden buyuk ve -(eksi) index
		   verilirse bos string, degilse satir sayisini return ediyor*/
		const string operator[](int);
		
		/* operator+= operator+(CPUProgramDyn,string) i calistiriyor*/
		const CPUProgramDyn& operator+=(string);

		/* operator== gelen parametre CPUProgramDyn ile base 
		CPUProgramDyn'in satir sayilari esit ise true , degilse false*/
		const bool operator==(const CPUProgramDyn& o);

		/* operator!= gelen parametre CPUProgramDyn ile base 
		CPUProgramDyn'in satir sayilari esit degil ise true,
		esit ise false	*/
		const bool operator!=(const CPUProgramDyn&);

		/* operator< parametre olarak gelen CPUProgramDyn in satir sayisi 
		base CPUProgramDyn satir sayisinden buyuk ise true 
		,degil ise false */
		const bool operator< (const CPUProgramDyn&);

		/* operator< parametre olarak gelen CPUProgramDyn in satir
		sayisi base CPUProgramDyn satir sayisinden kucuk ise true 
		,degil ise false*/
		const bool operator> (const CPUProgramDyn&);

		/* operator< parametre olarak gelen CPUProgramDyn in satir 
		sayisi base CPUProgramDyn satir sayisinden buyuk esit ise true,
		degil ise false*/
		const bool operator<=(const CPUProgramDyn&);

		/* operator< parametre olarak gelen CPUProgramDyn in satir
		sayisi base CPUProgramDyn satir sayisinden kucuk esit ise true,
		degil ise false*/
		const bool operator>=(const CPUProgramDyn&);

		/* operator--() (pre decrement) CPUProgramDyn daki son 
		instruction ini silip return ediyor.Hata durumu size 0 ise 
		silmeden kendisini return ediyor. */
		const CPUProgramDyn& operator--();

		/* operator--(int) (post decrement) CPUProgramDyn daki 
		son instruction ini silip degismemis halini return ediyor.
		 Hata durumu size 0 ise silmeden kendisini return ediyor. */
		const CPUProgramDyn operator--(int);

		/* operator() function call : gelen 2 int degeri arasindaki 
		satir sayilarini yeni bir nesne olusturup return ediyor
	   	   Hata : 2.parametre 1.den buyuk olmasi,negatif deger almalari,
		  satir sayisindan buyuk parametre verilmesi. 
		   return : kendisini return ediyor degisiklik yapmadan*/
		const CPUProgramDyn operator()(int,int);

		/* operator= assignment , member wise copy yapildi*/
		const CPUProgramDyn operator=(const CPUProgramDyn&);
		
		~CPUProgramDyn();
	private:
		string* instructions;
		int option;
		int sizeOfFile; /* satir sayisini tutan degisken*/
		string fname;   /* dosya ismini tutan degisken.*/
		void setSize(string* inst);
	};
}

#endif
