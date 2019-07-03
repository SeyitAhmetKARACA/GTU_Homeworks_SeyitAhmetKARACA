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

SUM: 	ds 50
newline: dw ""
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


	LXI H,SUM
	MVI C, 50
loopSon:
	MOV B,M
	MVI A, PRINT_B	
	call GTU_OS
	INX H
	DCR C
	JNZ loopSon
	hlt
