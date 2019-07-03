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

sakString: dw "hello world", 00AH ,00H
begin:
	LXI SP,stack 	; always initialize the stack pointer
	LXI H,SUM
	MVI C, 50

	;; PRINT_B
	MVI B,10
	MVI A, PRINT_B
	call GTU_OS

	;; READ_B
	MVI A, READ_B
	call GTU_OS

	;; READ_B PROVE
	MVI A, PRINT_B
	call GTU_OS

	MVI A, READ_MEM
	call GTU_OS

	MVI A, PRINT_MEM
	call GTU_OS	

	LXI B, sakString
	MVI A, PRINT_STR
	call GTU_OS

	
	MVI A,READ_sTR
	call GTU_OS

	MVI A, PRINT_STR
	call GTU_OS


	MVI A,GET_RND
	call GTU_OS

	MVI A,PRINT_B
	call GTU_OS
	hlt
