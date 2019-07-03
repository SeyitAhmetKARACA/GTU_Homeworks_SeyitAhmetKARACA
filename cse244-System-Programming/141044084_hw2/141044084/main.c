#include <stdio.h>
#include <unistd.h>
#include <dirent.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/wait.h>

#define MAX_CHAR 255

int len(char *word);
void oku(char *string,char *filename,FILE* logptr);
int isdirectory(char *path);
int rec(char *str,char* fname,FILE* lptr);
int paramCheck(char *word);
int isTxt(char *filename);

int main(int argc,char *argv[]){
	FILE* fptr=NULL;
	FILE* fptrAppend=NULL;
	int mpid = getppid();
	int lines=0;
	int checkFile = 0;
	char ch = ' ';
	if(argc != 3){
		fprintf(stderr,"Error :Usage is must be like this ' ./listdir string directoryName '\n");
	}else if(paramCheck(argv[0]) == 0){
		fprintf(stderr,"Executable file name must be 'listdir' or 'exe'\n");
	}else{
		if((fptr = fopen("log,log","w")) != NULL){
			checkFile = rec(argv[1],argv[2],fptr);
		}
		fclose(fptr);
		while( wait(NULL) != -1 ){
		}
		if(checkFile == 0){
			if(mpid == getppid()){
				chdir("..");
				if((fptrAppend = fopen("log,log","a+")) != NULL){
					while(!feof(fptrAppend)){
						ch = fgetc(fptrAppend);
						if(ch == '\n')
							lines++;
					}		
					fprintf(fptrAppend,"\n%d %s were found in total.",lines,argv[1]);
				}
				fclose(fptrAppend);
			}
		}else{
			unlink("log.log");
			fprintf(stderr,"Directory not found.\n");
		}
	}
	return 0;
}

int rec(char *str,char* fname,FILE* lname){
	int status=0;
	pid_t pid=1;
	struct dirent *direntp=NULL;
	DIR *dirp=NULL;
	int lcv = 1;
	if((dirp = opendir(fname))!=NULL){
		chdir(fname);
		while( (direntp = readdir(dirp)) != NULL && lcv == 1){

			pid = fork();
			if(pid == 0){
				if(isdirectory(direntp->d_name) != 0 &&
				strcmp(direntp->d_name,"..") != 0 && strcmp(direntp->d_name,".") != 0){
					if(isTxt(direntp->d_name))
						oku(str,direntp->d_name,lname);
				}else if(strcmp(direntp->d_name,"..") != 0 && strcmp(direntp->d_name,".") != 0){
					rec(str,direntp->d_name,lname);
				}
				lcv = 0;
			}
		}
	}else{
		closedir(dirp);
		return 1;	
	}
	closedir(dirp);
	return 0;
}

void oku(char *string,char *filename,FILE *logptr){
	FILE *fptr = NULL;
	int whiteSpaces,matchChar,col,lcv,sumOfFound;
	int row=1;
	int srcStrLen=0;
	char character=' ';
	whiteSpaces=matchChar=col=lcv=sumOfFound=0;
	srcStrLen = len(string);

	if((fptr = fopen(filename,"r")) != NULL){
		while(!feof(fptr)){
			matchChar= whiteSpaces= lcv= 0;
			character= fgetc(fptr);
			col++;
			if(character == '\n'){
				row++;
				col =0;
			}
			while(lcv == 0){
				if(string[matchChar] == character || character == '\n' || character == ' ' || character == '\t'){
					if(character == '\n' || character == ' ' || character == '\t'){
						whiteSpaces++;
					}else{
						matchChar++;
					}

					if(matchChar == srcStrLen){
						fprintf(logptr,"%s:  [%d,%d] ece first character is found.\n",filename,row,col);
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
	}
	return;
}


int isdirectory(char *path){
	DIR* directory = opendir(path);
	if(directory){
		closedir(directory);
		return 0;
	}
	closedir(directory);
	return -1;
}

int paramCheck(char *word){
	int check=0;
	if(len(word) == 9){
		if(word[0] == '.' && word[1] == '/' &&
		   word[2] == 'l' && word[3] == 'i' &&
		   word[4] == 's' && word[5] == 't' &&
		   word[6] == 'd' && word[7] == 'i' &&
		   word[8] == 'r')
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

int isTxt(char *filename){
	int i = len(filename);
	i--;
	if(filename[i] == 't' && filename[i-1] == 'x' && filename[i-2] == 't'
		&& filename[i-3] == '.')
		return 1;

	return 0;
}
