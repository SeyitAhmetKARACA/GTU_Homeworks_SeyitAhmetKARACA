#include <stdio.h>

int len(char *);

int main(int argc, char *argv[]){
	FILE *fptr = NULL;
	int whiteSpaces,matchChar,col,lcv,sumOfFound;
	int row=1;
	int srcStrLen;
	char character;
	whiteSpaces=matchChar=col=lcv=sumOfFound=0;
	srcStrLen = len(argv[1]);

	if(argc != 3){
		printf("Hata ! kullanim : ./list string filename\n");
	}else if(paramCheck(argv[0]) == 0){
		printf("calistirilabilir dosya ismi 'list' veya 'exe' olmalidir!\n");
	}else if((fptr = fopen(argv[2],"r")) != NULL){
		while(!feof(fptr)){
			matchChar= whiteSpaces= lcv= 0;
			character= fgetc(fptr);
			col++;
			if(character == '\n'){
				row++;
				col =0;
			}
			while(lcv == 0){
				if(argv[1][matchChar] == character || character == '\n' || character == ' ' || character == '\t'){
					if(character == '\n' || character == ' ' || character == '\t'){
						whiteSpaces++;
					}else{
						matchChar++;
					}

					if(matchChar == srcStrLen){
						printf("[%d, %d] konumunda ilk karakter bulundu.\n",row,col);
						lcv = 1;
						sumOfFound++;
						fseek(fptr,-matchChar-whiteSpaces,SEEK_CUR);

					}
					if(matchChar == 0 && whiteSpaces == 1){
						lcv = 1;
					}else{
						character = fgetc(fptr);
						if(character == -1)
							lcv = 1;
					}
					}else{
						lcv = 1;
						if(matchChar > 0){
							fseek(fptr,-matchChar-whiteSpaces,SEEK_CUR);
						}else if(whiteSpaces> 0){
							fseek(fptr,-matchChar-whiteSpaces,SEEK_CUR);
						}
				}
			}

		}
		fclose(fptr);
		if(sumOfFound > 0){
			printf("%d adet %s bulundu.\n",sumOfFound,argv[1]);
		}else
			printf("Aranan dosyada '%s' bulunamadi.\n",argv[1]);
	}else{
		printf("Belirtilen dosya bulunamadi.\n");
	}
	return 0;
}

int paramCheck(char *word){
	int check=0;
	if(len(word) == 6){
		if(word[0] == '.' && word[1] == '/' &&
		   word[2] == 'l' && word[3] == 'i' &&
		   word[4] == 's' && word[5] == 't')
		check = 1;
	}else if(len(word) == 5){
		if(word[0] == '.' && word[1] == '/' &&
		   word[2] == 'e' && word[3] == 'x' &&
		   word[4] == 'e'){
			check = 1;
		}
	}

	return check;
}

int len(char *word){
	int count=0;
	while(word[count] != '\0'){
		count++;
	}
	return count;
}
