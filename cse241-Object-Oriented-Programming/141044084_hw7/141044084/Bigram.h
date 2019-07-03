#ifndef BIGRAM
#define BIGRAM
#include <string>
using namespace std;
#define ERRORFILEOPEN 0
#define ERRORFILEREAD 1
#define ERRORDATATYPE 3
#define ERRORCLASSTYPE 4

using std::pair;
using std::ostream;
using std::string;

template <class T>
class Bigram{
public:	
	/* ostream turetilmis sinifta override yapilamicagi icin burda
	doldurdum.icerisindeki print ile polymorphism ile turemis siniflara
	ulasiyor. */
	friend ostream& operator<<(ostream& str,const Bigram& data){
		data.fcout();
		return str;
	}	
	/* Pure virtual fonksiyon prototipleri */
	virtual void readFile(const char* fileName)=0;
	virtual void fcout()const = 0;	
	virtual int numGrams() const = 0;
	virtual int numOfGrams(const T , const T)const = 0;
	virtual pair<T,T> maxGrams()const =0;
	virtual ~Bigram(){
		;
	}
};

/* exception */

class Exception: public exception{
public:
	Exception(int e){
		eType = e;
	};
	/* override what fonksiyonu */
	virtual const char* what() const throw(){
		if(eType == ERRORFILEOPEN)
			return "File couldn't open";
		else if(eType == ERRORFILEREAD )
			return "Data couldn't read from file";
		else if(eType == ERRORCLASSTYPE)
			return "Check the classtype parameter";
		else if(eType == ERRORDATATYPE)
			return "Check the datatype parameter";
	}
private:
	int eType = 0;
};
#endif
