#ifndef BIGRAMDYN
#define BIGRAMDYN

#include <string>
#include <map>
#include "Bigram.h"
#include <vector>
#include <fstream>

using namespace std;

template <class T>
class BigramDyn: public Bigram<T>{
public:
	/* int parametre alan contructor */
	BigramDyn(int dataType){
		size = 0;
		pDyn = nullptr;
	}
	/* Dosyadaki verileri pair member degiskene okuyor */
	void readFile(const char* fileName)throw(Exception)override;
	/* operator<< icin gerekli override fonksiyon */
	void fcout()const override;
	/* Bigram sayisini return ediyor */
	virtual int numGrams()const override;
	/* parametre olarak gelen degiskenlerin gram sayisini donduruyor */
	virtual int numOfGrams(const T,const T)const override;
	/* en fazla tekrar eden pair i donduruyor */
	virtual pair<T,T> maxGrams()const override;
	virtual ~BigramDyn()override{
		if(size > 0)
			delete [] pDyn;
	}
private:
	pair<T,T> *pDyn;/* dinamik olarak verilerin tutuldugu degisken*/
	int size;/* pDyn 'nin size ini tutuyor */
};

#endif
