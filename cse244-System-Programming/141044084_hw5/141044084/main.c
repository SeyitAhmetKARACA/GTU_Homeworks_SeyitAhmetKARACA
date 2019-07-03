#include <stdio.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <sys/wait.h>
#include <pthread.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <semaphore.h>
#include <time.h>
#include <sys/time.h>

#define MILLION 1000.0f
#define MAX_CHAR 255

typedef struct x{
    int totalNumberStringFound; /* ne kadar string bulundu.*/
    int numberDirectorySearched;/* kac klasor e girildi. +++++++*/
    int numberOfFilesSearched;  /* kac tane txt dosyasi bulundu ++++++*/
    int numberOfLinesSearched;  /* dosyalardaki satir sayisi - row +++++*/
    int cascadeThreads;         /* ??? +++++++++++++++ */
    int searchThreads;          /* +++++++++++  */
    int maxThreads;             /* +++++++++++  */
}oPut;

int maxTH;
int maxmax;

typedef struct yapi{
    char *targetStr;
    FILE *logPtr;
    char **fileNames;
    int fileNum;
    int *sharedValue;
}y1;

void sigintHandler(int);

sem_t semid;
key_t fkey;
int msgId;
int sigNumber;
struct timeval start,stop;
long timeDif;
oPut mainOutput;

void *funcTH(void* arg);
int isdirectory(char *path);
int rec(char *str,char* fname,FILE* lptr,int);
int isTxt(char *filename);

int main(int argc,char *argv[]){
    FILE* fptr=NULL;
    pid_t pid;
    fkey = getpid();
    msgId = msgget(fkey,IPC_CREAT | 0666);
    maxTH = 0;
    maxmax = 0;
    gettimeofday(&start,NULL);
    signal(SIGINT,sigintHandler);
    sigNumber = 0;
    if(argc != 3){
        fprintf(stderr,"Error :Usage is must be like this ' ./listdir string directoryName '\n");
    }else if(strcmp(argv[0],"./grepSh") != 0){
        fprintf(stderr,"Executable file name must be 'grepSh'\n");
    }else{
        if((fptr = fopen("log,txt","w")) != NULL) {
            pid = fork();
            if (pid == 0) {
                rec(argv[1], argv[2], fptr,msgId);
            }else{
                wait(NULL);

                msgrcv(msgId,&mainOutput,sizeof(oPut),0,0);
                msgctl(msgId,IPC_RMID,NULL);

                gettimeofday(&stop,NULL);
                timeDif = MILLION*(stop.tv_sec - start.tv_sec) + (stop.tv_usec - start.tv_usec)/MILLION;
                fprintf(fptr,"\n%d %s were found in total",mainOutput.totalNumberStringFound,argv[1]);
                printf("Total number of string founds : %d \n",mainOutput.totalNumberStringFound);
                printf("Number of directories searched : %d \n",mainOutput.numberDirectorySearched);
                printf("Number of files searched : %d \n",mainOutput.numberOfFilesSearched);
                printf("Number of lines searched : %d \n",mainOutput.numberOfLinesSearched);
                printf("Number of cascade threads created : %d \n",mainOutput.cascadeThreads);
                printf("Number of search threads created : %d \n",mainOutput.searchThreads);
                printf("Max # of threads running concurrently : %d \n",mainOutput.maxThreads);
                printf("Total run time,in miliseconds : %ld\n",timeDif);
                if(sigNumber == 0)
                    printf("Exit condition : normal\n");
                else
                    printf("Exit condition : signal no %d\n",sigNumber);
            }
        }
        fclose(fptr);
    }

    return 0;
}

int rec(char *str,char* fname,FILE* lname,int msgid){
    pid_t pid;
    y1 x;
    key_t shmKey = getpid();
    int shmId;
    void *sifir;
    int satirSayi =0;
    oPut *fOPut = (oPut*)malloc(sizeof(oPut));
    oPut *rcvOPut = (oPut*)malloc(sizeof(oPut));
    struct dirent *direntp=NULL;
    DIR *dirp=NULL;
    int lcv = 1;
    int i,fileCounter=0;
    pthread_t *tid = (pthread_t*)calloc(35,sizeof(pthread_t));
    x.fileNames = (char**)malloc(MAX_CHAR*sizeof(char*));
    /* dosya isimleri */

    for(i=0;i < MAX_CHAR ; i++ )
        x.fileNames[i] = (char*)malloc(MAX_CHAR*sizeof(char));

    fOPut->cascadeThreads = 0;
    fOPut->maxThreads = 0;
    fOPut->numberDirectorySearched = 0;
    fOPut->numberOfFilesSearched = 0;
    fOPut->searchThreads = 0;
    fOPut->totalNumberStringFound = 0;

    if((dirp = opendir(fname))!=NULL){
        chdir(fname);

        while( (direntp = readdir(dirp)) != NULL && lcv >= 1){
            if(isdirectory(direntp->d_name) != 0 &&
               strcmp(direntp->d_name,"..") != 0 && strcmp(direntp->d_name,".") != 0){
                if(isTxt(direntp->d_name)){
                    strcpy(x.fileNames[fileCounter],direntp->d_name);
                    fileCounter++;
                }
            }else if(strcmp(direntp->d_name,"..") != 0 && strcmp(direntp->d_name,".") != 0){
                pid = fork();
                if(pid == 0){
                    rec(str,direntp->d_name,lname,msgid);
                    exit(1);
                }else{
                    wait(NULL);
                    if(msgrcv(msgid,rcvOPut,sizeof(oPut),0,0) == -1){
                        fprintf(stderr,"xxxx");
                    }
                    fOPut->searchThreads += rcvOPut->searchThreads;
                    if(rcvOPut->maxThreads > fOPut->maxThreads)
                        fOPut->maxThreads = rcvOPut->maxThreads;

                    fOPut->numberOfFilesSearched += rcvOPut->numberOfFilesSearched;
                    fOPut->cascadeThreads += rcvOPut->cascadeThreads;
                    fOPut->totalNumberStringFound += rcvOPut->totalNumberStringFound;
                    fOPut->numberDirectorySearched += rcvOPut->numberDirectorySearched;
                    fOPut->numberOfLinesSearched += rcvOPut->numberOfLinesSearched;
                    /**/

                    lcv = 1;
                }
            }
        }
    }

    if(lcv == 1){
        x.sharedValue = NULL;
        sem_init(&semid,0,1);
        shmId = shmget(shmKey,sizeof(int)*fileCounter,IPC_CREAT | 0666);
        x.sharedValue = shmat(shmId,0,0);

        for(i = 0 ; i < fileCounter ; i++){
            x.logPtr = lname;
            x.targetStr = str;
            x.fileNum = i;
            pthread_create((tid+i),NULL,funcTH,&x);
            usleep(1000);
        }

        for(i = 0; i < fileCounter; i++){
            pthread_join(tid[i],&sifir);
            satirSayi += *(int*)sifir;
            fOPut->totalNumberStringFound += x.sharedValue[i];//x.sharedValue[i];
        }


        fOPut->searchThreads += fileCounter;
        if(maxmax > fOPut->maxThreads)
            fOPut->maxThreads += maxmax;

        fOPut->numberOfFilesSearched += fileCounter;
        fOPut->cascadeThreads += fileCounter;

        fOPut->numberDirectorySearched += 1;
        fOPut->numberOfLinesSearched += satirSayi;

        shmctl(shmId,IPC_RMID,NULL);
        shmdt(x.sharedValue);

        msgsnd(msgid,fOPut,sizeof(oPut),IPC_NOWAIT);
        sem_destroy(&semid);
    }



    for(i=0;i < MAX_CHAR; i++)
        free(x.fileNames[i]);
    free(x.fileNames);
    free(fOPut);
    free(rcvOPut);
    if(x.sharedValue == NULL)
        free(x.sharedValue);
    return 0;
}

void sigintHandler(int sigNo) {
    sigNumber = sigNo;
}

void *funcTH(void* arg) {
    /* basliyoo */
    struct yapi temp = *((struct yapi *)arg);
    char *string = temp.targetStr;
    int ii = temp.fileNum;
    FILE *logptr = temp.logPtr;
    char *filename = temp.fileNames[ii];
    FILE *fptr = NULL;
    int whiteSpaces, matchChar, col, lcv, sumOfFound;
    int row = 1;
    int srcStrLen = 0;
    char character = ' ';

    col = sumOfFound = 0;
    srcStrLen = strlen(string);

    maxTH++;
    sem_wait(&semid);
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
                        fprintf(logptr,"%d - %ld %s:  [%d,%d] %s first character is found. \n",getpid(),pthread_self(),filename,row,col,string);
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

    if(maxTH > maxmax)
        maxmax = maxTH;
    temp.sharedValue[ii] = sumOfFound;
    maxTH--;

    sem_post(&semid);
    pthread_exit(&row);
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

int isTxt(char *filename){
    int i = strlen(filename);
    i--;
    if(filename[i] == 't' && filename[i-1] == 'x' && filename[i-2] == 't'
       && filename[i-3] == '.')
        return 1;

    return 0;
}
