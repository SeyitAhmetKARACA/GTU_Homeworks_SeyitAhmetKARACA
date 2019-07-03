#include "requiredIncs.h"

/* ilk parametreyi degistrdim referans aliyor */
template <class T>
void testFuncD(BigramDyn<T> &bgd, const char* filename, T p1, T p2)
{
	/* Bigram pointer a parametre olarak gelen fonksiyonun adresi atandi */
	Bigram<T> *bg = &bgd;
	//////////////////////////////////////////////////////////////////////////
	//readFile
	bg->readFile(filename);
	
	//numGrams
	cout << bg->numGrams() << endl;

	//numOfGrams
	cout << bg->numOfGrams(p1, p2) << endl;

	//maxGrams
	pair<T, T> retVal = bg->maxGrams();
	cout << retVal.first << "   " << retVal.second << endl;
	
	//operator<<
	cout << *bg << endl;
	//////////////////////////////////////////////////////////////////////////
}

template <class T>
void testFuncM(BigramMap<T>& bgm, const char* filename, T p1, T p2)
{
	Bigram<T> *bg = &bgm;
	//////////////////////////////////////////////////////////////////////////
	//readFile
	bg->readFile(filename);
	
	//numGrams
	cout << bg->numGrams() << endl;

	//numOfGrams
	cout << bg->numOfGrams(p1, p2) << endl;

	//maxGrams
	pair<T, T> retVal = bg->maxGrams();
	cout << retVal.first << "   " << retVal.second << endl;
	
	//operator<<
	cout << *bg << endl;
	//////////////////////////////////////////////////////////////////////////
}



int main(int argc, char** argv){

	//////////////////////////////////////////////////////////////////////////
	//command line parameters
	const char* filename = argv[1];
	int dataType = atoi(argv[2]);
	int classType = atoi(argv[3]);
	//////////////////////////////////////////////////////////////////////////
	/* sizin tester fonksiyonundan farklarinin ustlerindeki satirlara
	   yorum biraktim. */

	try
	{
		//////////////////////////////////////////////////////////////////////////
		//Testing class BigramMap
		if (classType == 1)
		{
			if (1 == dataType){

				BigramMap<int> myBg(dataType);
				testFuncM<int>(myBg, filename, 3, 4);		
			}

			else if (2 == dataType){
				BigramMap<string> myBg(dataType);
				testFuncM<string>(myBg , filename, "qwe", "asd");
			}
			else if (3 == dataType){

				BigramMap<double> myBg(dataType);
				testFuncM<double>(myBg, filename, 3.0, 4.0);	
			}else{
				throw (Exception(3));
			}
		}
		else if(classType == 2){
			if (1 == dataType){
				/* myBg yi referans olarak gonderdim. */
				BigramDyn<int> myBg(dataType);
				testFuncD<int>(myBg, filename, 3, 4);
			}else if (2 == dataType){

				BigramDyn<string> myBg(dataType);
				testFuncD<string>(myBg, filename, "qwe", "asd");
			}else if (3 == dataType){

				BigramDyn<double> myBg(dataType);
				testFuncD<double>(myBg, filename, 3.0, 4.0);
			}else{/* datatype farkli girilir ise */
				throw (Exception(3));
			}
		}else{ /* classtype uygun olmayan birsey girilir ise  */
			throw (Exception(4));
		}
	}
	catch (exception& e)
	{
		cout << "Exception: " << e.what() << endl;
	}

	return 0;
}
