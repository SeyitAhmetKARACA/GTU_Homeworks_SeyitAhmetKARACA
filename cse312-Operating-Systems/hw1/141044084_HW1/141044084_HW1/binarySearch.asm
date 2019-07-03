        ; 8080 assembler code
        .hexfile exe.hex
        .binfile exe.com
        ; try "hex" for downloading in hex format
        .download bin
        .objcopy gobjcopy
        .postbuild echo "OK!"
        ;.nodump

	; OS call list
PRINT_B		equ 1
PRINT_MEM	equ 2
READ_B		equ 3
READ_MEM	equ 4
PRINT_STR	equ 5
READ_STR	equ 6
GET_RND		equ 7

	; Position for stack pointer
stack   equ 0F000h

	org 000H
	jmp begin

	; Start of our Operating System
GTU_OS:	PUSH D
	push D
	push H
	push psw
	nop	; This is where we run our OS in C++, see the CPU8080::isSystemCall()
		; function for the detail.
	pop psw
	pop h
	pop d
	pop D
	ret
	; ---------------------------------------------------------------
	; YOU SHOULD NOT CHANGE ANYTHING ABOVE THIS LINE

	;This program adds numbers from 0 to 10. The result is stored at variable
	; sum. The results is also printed on the screen.

NOTFOUND:	dw "error",00AH,00H ; null terminated string

SUM:	ds 50 ; will keep the sum
INDEX:	ds 2
        ; ds ile yer alabiliyoruz
begin:
	LXI SP,stack 	; always initialize the stack pointer
	LXI H,SUM
	MVI C, 50

loop:
	MVI A, GET_RND
	call GTU_OS
	MOV M,B
	INX H
	DCR C
	JNZ loop

	
	MVI E, 50
outLoop:
	MVI C, 49
	LXI H,SUM
innerLoop:
	MOV D,M ; D = MEM[X]
	INX H   ; MEM++
	MOV A,M ; A = MEM[X]
	SUB D   ; A = A - MEM[X+1]
	JC swap; If C, PC <­ address
	JMP skip 
        ; carry 1 ise adrese git.
swap:MOV A,M
	MOV M,D
	DCX H
	MOV M,A
	INX H
	JMP skip

skip:	DCR C
	JNZ innerLoop
	DCR E
	JNZ outLoop
    ;sort end

     	MVI A,READ_B; the number wil search
	call GTU_OS
	MOV D,B  ; the number wil search
    
	;;; generate first mid
	LXI H,SUM
	MVI E, 0	; init C with 10
	MVI B, 50
	MOV A,B
	DCR A
	STC 
	CMC
	RAR
	MOV C,A
blbs:   ;begin loop binary search
	INX H
	DCR C
	JNZ blbs
    
   

	; BINARY SEARCH !!!!
    	MOV C,A     ; 
loopBS:
	MOV A,M
	SUB D
	JZ bulundu
	JC saga
	JNC sola
	JMP bitis
    

saga: ; C DE MID VAR !!!!!
	INR C
	MOV E, C
	MOV A, B
	SUB E
	STC 
	CMC
	RAR
	ADD E
	MOV C, A
	LXI H, SUM
loopSag:
	INX H
	DCR A
	JNZ loopSag
	
	MOV A,B ;;if (l > r)
	SUB E
	JC ERR
	JMP loopBS
    
sola:
	DCR C
	MOV B, C
	MOV A, B
	SUB E
	STC 
	CMC
	RAR
	ADD E
	MOV C, A
    
	LXI H, SUM
loopSol:
	INX H
	DCR A
	JNZ loopSol

	MOV A,B
	SUB E
	JC ERR
    
    JMP loopBS
    
ERR:
	LXI B,NOTFOUND
	MVI A,PRINT_STR
	call GTU_OS
	JMP bitis
    
bulundu:
	MOV B, C
	MVI A, PRINT_B
	call GTU_OS
  
	JMP bitis

bitis:

	hlt		; end program
