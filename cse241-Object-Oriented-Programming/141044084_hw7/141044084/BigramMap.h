#ifndef BIGRAMMAP
#define BIGRAMMAP

#include <string>
#include <map>
#include "Bigram.h"
#include <vector>
#include <fstream>

using namespace std;
template <class T>
class BigramMap : public Bigram<T>{
public:
	/* int parametre alan contructor */
	BigramMap(int dataType){
	}

	BigramMap(){}
	/* Dosyadaki verileri pair member degiskene okuyor */
	void readFile(const char* fileName)throw(Exception) override;
	/* operator<< icin gerekli override fonksiyon */	
	void fcout()const override;
	/* Bigram sayisini return ediyor */
	virtual int numGrams()const override;
	/* parametre olarak gelen degiskenlerin gram sayisini donduruyor */	
	virtual int numOfGrams(T,T)const override;
	/* en fazla tekrar eden pair i donduruyor */	
	virtual pair<T,T> maxGrams()const override;
	virtual ~BigramMap()override{
		;
	}
private:
	map <int,pair<T,T> > bgMap;
	/*dosyadaki verileri bgMap te tuttum.
	int : index olarak kullanabilmek icin
	pair<T,T> Bigram lari tutuyor*/
};

#endif
