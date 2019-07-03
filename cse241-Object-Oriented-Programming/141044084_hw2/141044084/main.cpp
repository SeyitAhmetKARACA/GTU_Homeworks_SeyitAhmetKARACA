#include <iostream>
#include <fstream>
#include <string>
#include <limits> /* int'in max degeri icin*/

using namespace std;

/* parametre olarak gelen string karakterlerinin hepsini
   buyuk harfe ceviriyor 		*/
string toUpper(string);

/* string param: stringin icerigi tamamen rakam ise
	return true			*/
bool isInt(string param);

/* string param : string icerigin sagindaki ve solundaki
   bosluklari siliyor.			*/
void clearWSpace(string& param);

/* satirdaki virgul sayisini geri donduruyor.
   yok ise -1 geri donduruyor		*/
int virgulIndex(string line);

/* butun isi bu yaptıgi icin ismi slave
   string st: getline ile gelen string
   int *arr : register lari tuttugum int dizisi
   int *arrM: memory leri tutan int dizi
   int size : dizi boyutu
   int sizeMemory : memory dizi boyutu
   fstream& : jmp'a gondermek icin gerekli
   int&     : hata satir sayisi icin gerekli
   int&     : hlt sayisi
   int 	    : dosya satir sayisi
   int 	    : 3.arguman degeri 
   int&	    : int in max alabilecegi sayi tutuluyor*/
bool slave(string st,int* arr,unsigned int* arrM,const int size,
       const int sizeMemory,fstream&,int&,int&,const int,const int,const int&);

/* gelen stringi integer a cevirip return ediyor.
  isInt fonksiyonu ile kullanilir.	*/
int stringToInt(string line);

/* gelen parametreye gore bir deger geri donduruyor
   Null(bosluk) :0 	Register :1	constant :2 memory :3	hata :-1   */
int R_N_C(string param);

/* 2.parametre tipine gore calisir (p2type), 2 ise p2iC 'i dizinin p1i'nci
   adresine atar. p2type 1 ise dizinin p1i'nci elemani p2iC'nci elemana kopyalr
   p1i : p1 index , p2iC: p2 index veya Constant
   p2type , 2.parametrenin tipinide gonderyior. memory icin
*/
void MOV(int* arr,unsigned int* arrM,int p1i,int p2iC,int p1type,int p2type);

/* 2.parametre tipine gore calisir (p2type), 2 ise p2iC ile dizinin p1i'nci
   adresindeki elemanla toplar p1i'nci elemana yazar.
   p2type 1 ise dizinin p1i'nci elemani p2iC'nci elemanla toplar p1i'nci elmna
   yazar
   p1i : p1 index , p2iC: p2 index veya Constant		*/
void ADD(int* arr,unsigned int* arrM,int p1i,int p2iC,int p2type);

/* 2.parametre tipine gore calisir (p2type), 2 ise p2iC ile dizinin p1i'nci
   adresindeki elemanla cikarir p1i'nci elemana yazar.
   p2type 1 ise dizinin p1i'nci elemani p2iC'nci elemanla cikarir p1i'nci elmna
   yazar
   p1i : p1 index , p2iC: p2 index veya Constant 		*/
void SUB(int* arr,unsigned int* arrM,int p1i,int p2iC,int p2type);

/* gelen parametredeki degeri ekrana basar */
void PRN(int input);

/* fstream& : dosya basina gelmek icin gerekli
   int : dosya basindan kac satir ilerleyecek. */
bool JMP(fstream&,int);

/* programi sonlandirmak icin gerekli fonksiyon
   sonlandirirken butun registerlari ekrana basar.
   int *: register dizisi icin. int : size icin*/
bool HLT(int*,int);

int main(int argc, char **argv)
{
	int err=0; /* hata satir sayisi icin */
	const int numberOfRegs = 5;
	const int numberOfMemory = 10; /*3.arguman 2 olunca ekrana
					basarken cok kotu duruyor.
					ilk 10 taneyi kullandim zaten
					50 tane isterseniz bunu 50 yapin */
	fstream is;
	string line="";
	int R[numberOfRegs];	//register dizi
	unsigned int M[numberOfMemory]; // hafiza dizi
	int numberOfHlt=0; /* hlt komutu sayisi. */
	int lof =0; /* line of file */
	bool wc = true; /* while control */
	int arg = 0;  /* argv[2] den gelen deger */
	int intMaxValue = numeric_limits<int>::max();
	for(int i=0;i<numberOfMemory;i++)	
		M[i] = 0;
	for(int i=0;i <numberOfRegs ; i++)
		R[i] =0;

	if(argc != 3){
		cerr<<"Error Error : too few arguments to run"<<endl;
		return 0;
	}else if(isInt(argv[2])){
		arg = stringToInt(argv[2]);
		if(arg > 2 || arg < 0){
			cerr<<"third argument must be '0' or '1' or '2'"<<endl;
			return 0;
		}
	}else if(isInt(argv[2]) == false){
		cerr<<"third argument must be '0' or '1' or '2'"<<endl;
		return 0;
	}

	is.open(argv[1]);
/*	dosyadaki satir sayisi - acildi */
	if(is.is_open()){
		while(!is.eof()){
			getline(is,line);
			if(line != ""){
				lof++;
			}	
		}
		is.close();
/*	dosya satir sayisi - kapandi */

	if( lof > 200){
		cerr<<"Number of lines can not higher than 200";
		return 0;
	}

		is.open(argv[1]);
		while(!is.eof() && wc){
			err++;
			getline(is,line);
			line = toUpper(line);
			if(line != "")
				wc = slave(line,R,M,numberOfRegs,numberOfMemory
				      ,is,err,numberOfHlt,lof,arg,intMaxValue);
		}
		if(numberOfHlt == 0)
		     cerr<<"Error Error : Instruction HLT was not found"<<endl;

		is.close();
	}else
		cerr<<"Error Error : The file was not found"<<endl;


	return 0;
}

bool slave(string fLine,int* arr,unsigned int* arrM,const int size,const int sizeMemory,
	fstream& fs,int& errLine,int& numberHlt,const int lineOfFile,
						const int arg,const int& mInt){
	string mod,p2="",p3=""; /*mod: instruction p2-3:parametre 2-3 */
	int ip2,ip3=-1; /* i = int 				 */
	int p2t,p3t; /* parametre tipi				*/
	int vs = 0;  /* virgul sayisi				*/
	int imod;    /* index mod,inst. tan sonraki ilk virgul indexi	*/
	clearWSpace(fLine);
	imod = fLine.find_first_of(" ");
	mod = fLine.substr(0,imod);
	fLine.erase(0,imod);

	vs = virgulIndex(fLine);
	if(virgulIndex(fLine) >= 0){ /* 1 tane virgul var		*/
		int i = virgulIndex(fLine);


		p2 = fLine.substr(0,i);
		clearWSpace(p2);
	
		fLine.erase(0,i+1);

		p3 = fLine;
		clearWSpace(p3);

		p3t = R_N_C(p3);
		p2t = R_N_C(p2);

		/* parametre 2 icin RNCE
		 R ise index al
		 C ise constant'i al
		 E ise error
		 N , null*/
		if(p2t == 1)
			ip2 = stringToInt(&p2[1])-1;
		else if(p2t == 2)
			ip2 = stringToInt(p2);
		else if(p2t == 0)
			ip2 = 0;
		else if(p2t == -1 ){
		   	cerr<<"Error Line :"<<errLine;
			cerr<<" Check first parameter"<<endl;
			return false;
		}else if(p2t == 3){
			ip2 = stringToInt(&p2[1]);
		}
		
		
		if(p3t == 1 ){
			ip3 = stringToInt(&p3[1])-1;
		}else if(p3t == 2)
			ip3 = stringToInt(p3);
		else if(p3t == 0)
			ip3 = 0;
		else if(p3t == -1){
			cerr<<"Error Line :"<<errLine;
			cerr<<" Check second parameter"<<endl;
			return false;
		}else if(p3t == 3){
			ip3 = stringToInt(&p3[1]);
		}
	}else if(virgulIndex(fLine) == -1){ /* virgul yok demek.		*/
		p2 = fLine;
		clearWSpace(p2);
		
		p2t = R_N_C(p2);
		p3t = 0;
		if(p2t == 1)
			ip2 = stringToInt(&p2[1])-1;
		else if(p2t == 2)
			ip2 = stringToInt(p2);
		else if(p2t == 3){
			ip2 = stringToInt(&p2[1]);
		}else if(mod != "HLT"){
			cerr<<"Error Line :"<<errLine;
			cerr<<" Check the parameters"<<endl;
			return false;
		}
	}
	/* arraylerin indexlerin disina gecmemesi icin */
	if((p2t == 1 && ip2 > size) || (p2t == 3 && ip2 > sizeMemory)){
		cerr<<"Error Line "<<errLine;
		cerr<<" : entered unknown register"<<endl;
		return false;
	}else if(p3t == 1 && ip3 > size || (p3t == 3 && ip3 > sizeMemory)){
		cerr<<"Error Line "<<errLine;
		cerr<<" : entered unknown register"<<endl;
		return false;
	}

	/*unsigned int - int sorunlarini engellemek icin*/
	if(mod == "MOV" && (p2t == 1 || p2t == 3) && ip2 != -1){
		if((p2t == 1 && p3t == 3 && arr[ip2] <=0 )
			 || (p2t == 3 && p3t == 1 && arrM[ip2] >= mInt)){
			cerr<<"Error Line "<<errLine;
			cerr<<" : Couldn't converted signed integer to ";
			cerr<<"unsigned integer"<<endl;
			return false;
		}else
			MOV(arr,arrM,ip2,ip3,p2t,p3t);
	}else if(mod == "ADD" && p2t == 1)
		ADD(arr,arrM,ip2,ip3,p3t);
	else if(mod == "SUB" && p2t == 1)
		SUB(arr,arrM,ip2,ip3,p3t);
	else if(mod == "JMP"){
		if(p2t == 2 && p3t == 0){/*sabit sayi geldiginde.	*/
			if(JMP(fs,ip2) == false || ip2 > lineOfFile){
				cerr<<"Error Line :"<<errLine<<
				" Wrong parameter"<<endl;
				cerr<<"The value that is given to JMP "<<
				"instruction can not be higher than ";
				cerr<<"file line size"<<endl;
				return false;
			}
		errLine = ip2;
		}
		else if(p2t == 1 && p3t == 2){
			if(arr[ip2] == 0){
				if(JMP(fs,ip3) == false || ip3 > lineOfFile){
					cerr<<"Error Line :"<<errLine<<
					" Wrong parameter"<<endl;
					cerr<<"The value that is given to JMP "<<
					"instruction can not be higher than ";
					cerr<<"file line size"<<endl;
					return false;
				}
				errLine = ip3-1;
			}
		}else{
			cerr<<"Error Line :"<<errLine<<" wrong parameters"<<endl;
			return false;
		}
	}else if(mod == "JPN" && p2t == 1 && p3t == 2){ // JPN
		if(arr[ip2] <= 0){
			if(JMP(fs,ip3) == false || ip3 > lineOfFile){
				cerr<<"Error Line :"<<errLine<<
				" Wrong parameter"<<endl;
				cerr<<"The value that is given to JMP "<<
				"instruction can not be higher than ";
				cerr<<"file line size"<<endl;
				return false;
			}
			errLine = ip3-1;
		}
	}else if(mod == "PRN" && vs == -1){
		if(p2t == 2)
			PRN(ip2);
		else if(p2t == 1)
			PRN(arr[ip2]);
		else if(p2t == 3)
			PRN(arrM[ip2]);
	}else if(mod == "HLT" && vs == -1 && p3 =="" && p2t == 0){
		numberHlt++;
		if(HLT(arr,size))
			return false;
	}else{
		cerr<<"Error Line :"<<errLine<<" wrong parameters"<<endl;
		return false;
	}

	if(arg == 1){
		if(mod == "PRN" || mod == "JMP" || mod == "SUB")
			cout<<mod<<" "<<p2<<" "<<p3<<"   \t";
		else
			cout<<mod<<" "<<p2<<" "<<p3<<"\t";
		for(int i=0;i< size;i++){
			cout<<"R"<< i+1<<"="<<arr[i];
			if(i != size-1)
				cout<<",";
		}
		cout<<endl;
	}else if(arg == 2){
		if(mod == "PRN" || mod == "JMP" || mod == "SUB")
			cout<<mod<<" "<<p2<<" "<<p3<<"   \t";
		else
			cout<<mod<<" "<<p2<<" "<<p3<<"\t";
		for(int i=0;i< sizeMemory;i++){
			cout<<"#"<< i<<"="<<arrM[i];
			if(i != size-1)
				cout<<",";
		}
		cout<<endl;
	}


	return true;
}


int stringToInt(string param){
	const int powerx = 10;
	int power = 1,lcv=0,sum=0;
	string st="";
	int i=param.size();;
	int sign = 1;

	if(param[0] == '-'){
		st = &param[1];
		sign = -1;
		i = st.size();
	}else
		st = param;

	while(i>0){
		sum += power * (st[i-1] - '0');
		power *= powerx;
		i--;
	}
	
	return sum*sign;
}
/*
	 0 : Null
	 1 : Register
	 2 : Constant
	-1 : Error
*/

int R_N_C(string st){
	int rv;

	clearWSpace(st);
	if(st[0] == 'R' && st[1] != '-')
	{
		if(isInt(&st[1]))
			rv = 1;
		else
			rv =-1;
	}else if(st[0] == '#' && st[1] != '-')
	{
		if(isInt(&st[1]))
			rv = 3;
		else
			rv =-1;
	}else if(st == "")
		rv = 0;
	else if(isInt(st))
		rv = 2;
	else
		rv = -1;

	return rv;
}

/*
	-1 : virgul yok
	-2 : birden fazla virgul var
	 x : virgulun oldugu indexi verir
*/

int virgulIndex(string st)
{
	int i=0,j=0;
	int b=0;
	while (i != string::npos){
		
		i= st.find_first_of(',',i+1);
		if(i != -1)
			j =i;
		b++;
	}

	if( b-1 == 0)
		j = -1;
	else if(b-1 > 1)
		j = -2;

	return j;
}

void clearWSpace(string& param1){
	int i=0,j=0;
	bool lcv = true;
	int k =0;

	k = param1.find_first_of(';');		
	if( k != -1)				/* varsa noktali virgul ve  */
		param1.erase(k,param1.size()-k);/* sonrasini yok et !	    */

	while(lcv){
		if(param1[i] == ' ' || param1[i] == '	')
			i++;
		else
			lcv = false;
	}
	param1.erase(0,i);

	i=0;
	lcv = true;
	while(lcv){
		if(param1[param1.size()-(i+1)] == ' ' 
				|| param1[param1.size()-(i+1)] == '	' )
			j++;
		else
			lcv = false;
		i++;
	}
	param1.erase(param1.size()-j,j);

	return;
}

bool isInt(string st){
	int lcv=0;
	if(st == "")
		return false;

	if(st[0] == '-')
		st = &st[1];

	while(st.size() > lcv){ /* int e cevrilmiyor.	*/
		if(st[lcv] < '0' || st[lcv] > '9')
			return false;
		lcv++;
	}

	return true;
}

void MOV(int* arr,unsigned int* arrM,int p1,int p2,int p1type,int p2type){
	if(p1type == 1 && p2type == 1) // reg - reg
		arr[p2] = arr[p1];	
	else if(p1type == 1 && p2type == 2) // reg- const
		arr[p1] = p2;		//++
	else if(p1type == 1 && p2type == 3) // reg - memory
		arrM[p2] = arr[p1];
	else if(p1type == 3 && p2type == 1) // memory - reg
		arr[p2] = arrM[p1];
	else if(p1type == 3 && p2type == 2) // memory - const
		arrM[p1] = p2;

	return;
}

void ADD(int* arr,unsigned int* arrM,int p1,int p2,int p2type){
	if(p2type == 1)
		arr[p1] = arr[p1] + arr[p2];	
	else if(p2type == 2)
		arr[p1] += p2;
	else if(p2type == 3)
		arr[p1] = arr[p1] + arrM[p2];

	return;
}

void SUB(int* arr,unsigned int* arrM,int p1,int p2,int p2type){
	if(p2type == 1)
		arr[p1] = arr[p1] - arr[p2];	
	else if(p2type == 2)
		arr[p1] -= p2;
	else if(p2type == 3)
		arr[p1] = arr[p1] - arrM[p2];

	return;
}

void PRN(int p1){
	cout <<p1<<endl;	
	return;
}

bool JMP(fstream& is,int q)
{
	string st;
	if(q>0){
		is.seekg(0, is.beg);
		while(q-1 > 0){
			getline(is,st);
			q--;
		}
	}else
		return false;
	return true;
}


bool HLT(int* arr,int m){
	for(int i=0;i<m;i++)
		cout<<"R"<< i+1<<"="<<arr[i]<<"\t";
	cout<<endl;
	return true;
}

string toUpper(string param){
	for(int i=0;i< param.size();i++){
		if(param[i] >= 'a' && param[i] <= 'z')
			param[i] += 'A' - 'a';
	}
	return param;	
}
