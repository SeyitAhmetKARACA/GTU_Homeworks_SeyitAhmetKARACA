.data
buffer:
	.space 255
endl:
	.asciiz "\n"
strt0:
	.asciiz "\nt0 : "
strt1:
	.asciiz "\nt1 : "
strt2:
	.asciiz "\nt2 : "
strt3:
	.asciiz "\nt3 : "
strt4:
	.asciiz "\nt4 : "
dot:
	.asciiz "."
sifir:
	.asciiz "0"

.text
main:
	jal parser	#parser
	jal fillVariables	#a.b operator c.d
	addi $sp, $sp, -24
	lw $s0,20($sp)	# 1.tam
	lw $s1,16($sp)	# 1.ondalik
	lw $s2,12($sp)	# 2.tam
	lw $s3,8($sp)	# 2.ondalik
	lw $s4,4($sp)	# operator
	lw $s5,0($sp)	# . dan sonraki basamak sayisi
	addi $sp, $sp, 24


	move $t3,$s5	# t3 = .dan sonraki basamak sayisi
	
	li $t4,10 	#. dan sonraki bas. sayi bolmek icin

	mult $s0,$s5	
	mflo $t0
	add $t0, $t0, $s1

	mult $s2,$s5
	mflo $t1
	add $t1, $t1, $s3
	beq $s4, '+', toplama
	beq $s4, '-', cikarma
	beq $s4, '*', carpma
toplama:	
	div $t3,$t4
	mflo $t3
	add $s6, $t0, $t1
	div $s6, $s5
	mflo $t0
	mfhi $t1
	j son
carpma:	mult $t3,$t3
	mflo $t3
	div $t3,$t4
	mflo $t3
	
	mult $t0, $t1
	mflo $s6
	mult $s5, $s5
	mflo $s5
	div $s6,$s5
	mflo $t0
	mfhi $t1
	j son
cikarma:	
	div $t3,$t4
	mflo $t3
	sub $s6, $t0, $t1
	div $s6, $s5
	mflo $t0
	mfhi $t1
	slt $s5,$t1,$zero
	bne $s5, $zero , inverse
	j son
inverse:
	li $s7, -1
	mult $t1,$s7
	mflo $t1
	j son
son:
	move $a0, $t0	#Sonuc tam kismi
	li $v0,1
	syscall
	
	la $a0,dot
	li $v0,4
	syscall
	
	# 1.001 + 2.001 = 3._00_2
	# _ arasindaki 0 lar icin dongu
dot0Loop:	slt $t5, $t1,$t3
	beq $t5, $zero,dot0LoopEnd
	div $t3,$t4
	mflo $t3
	
	la $a0,sifir	
	li $v0,4
	syscall
	j dot0Loop
dot0LoopEnd:	

	move $a0, $t1	# Sonuc ondalik kisim
	li $v0,1
	syscall
		
	li $v0, 10  #return 0
	syscall

# PARSER variable doldurma
fillVariables:addi $sp, $sp, -20
	lw $s0,16($sp)
	lw $s1,12($sp)
	lw $s2,8($sp)
	lw $s3,4($sp)
	lw $s4,0($sp)
	addi $sp, $sp, 20

	# !!!! s6 ve s8 bos !!!!!  s6 ilk ondalik icin t9 iknci ondalik icin
	move $s6, $s1
	move $t9, $s3
		
	la $s5, buffer	# s5 e stringi atadik
	li $t1, 0		# genel toplam 0 yapildi ilk tam sayi icin
	li $t4, 10	# /10 icin
	# toplamlar t0 da olucak
	# t1 de ara toplamlar olucak
	# t2 basamak degeri bulmak icin 10 ve katlari olacak.
	# t3 okudugum karakterler .
# !!!!!	# t6 variable dongu degeri
	li $s7, 0 	# ikinci ondalik kisim
	li $t6, 0		# ilk ondalik
	li $t8, 0		# ikinci tam
	li $t3, 1		#carpma icin 1 yaptik
	li $t0, 0		# ilk tam kisim

	move $t7, $s0
	addi $t7,$t7,-1	# 10^(sayinin basamak sayisi)
on:	beq $t7,$zero ,v1	# for t>0
	addi $t7,$t7,-1	# t7 --
	mult $t4,$t3	# t2*t3
	mflo $t3		# t3

	j on
	# v1 , v2 , v3 , v4 = string -> int
v1:	beq $s0, $zero , v1e	#
	addi $s0, $s0, -1
	lb $t2, ($s5)		# karakteri aldik
	addi $s5, $s5, 1		# bir sonraki index
	addi $t2, $t2, -48		#	SAYILARIN 10* OLARAK DEGERÝ HESAPLANIYOR T0 DA TOPLANIYOR
	mult $t2,$t3		# basamaktaki sayi * 10x
	mflo $t1			# cevabi t2 ye aldik
	add $t0,$t0,$t1		# t1 = t1+ t2
	div $t3,$t4		# t3/10
	mflo $t3			# t3= t3/10
	j v1
v1e:
	addi $s5, $s5, 1	# noktayi gec
	
	# ÝLK ondalik kisim !!!
	li $t3, 1		#carpma icin 1 yaptik
	move $t7, $s1
	addi $t7,$t7,-1	#
on1:	beq $t7,$zero ,v2	# if (t7 == 0) v2 ye git.
	addi $t7,$t7,-1	# t7 --
	mult $t4,$t3	# 
	mflo $t3		# t3 = t4*t3
	
	j on1
	
v2:	beq $s1, $zero, v2e	# t7 de dongy degiskeni olucak. s0--
	addi $s1, $s1, -1
	lb $t2, ($s5)		# ilk karakteri aldik
	addi $s5, $s5, 1		# bir sonraki index
	addi $t2, $t2, -48		#	SAYILARIN 10* OLARAK DEGERÝ HESAPLANIYOR T0 DA TOPLANIYOR
	mult $t2,$t3		# basamaktaki sayi * 10x
	mflo $t1			# cevabi t2 ye aldik
	add $t6,$t6,$t1		# t1 = t1+ t2
	div $t3,$t4
	mflo $t3
	
	j v2
v2e:
	addi $s5,$s5,1
	addi $s5,$s5,1
	addi $s5,$s5,1	

		# ikinci tam kisim !!!
	li $t3, 1		#carpma icin 1 yaptik
	move $t7, $s2
	addi $t7,$t7,-1	#
on2:	beq $t7,$zero ,v3	# for t>0
	addi $t7,$t7,-1	# t7 --
	mult $t4,$t3	# t2*t3
	mflo $t3		# t3
	
	j on2
	
v3:	beq $s2, $zero, v3e		# t7 de dongy degiskeni olucak. s0--
	addi $s2, $s2, -1
	lb $t2, ($s5)		# ilk karakteri aldik
	addi $s5, $s5, 1		# bir sonraki index
	addi $t2, $t2, -48		#	SAYILARIN 10* OLARAK DEGERÝ HESAPLANIYOR T0 DA TOPLANIYOR
	mult $t2,$t3		# basamaktaki sayi * 10x
	mflo $t1			# cevabi t2 ye aldik
	add $t8,$t8,$t1		# t1 = t1+ t2
	div $t3,$t4
	mflo $t3
	
	j v3
v3e:
	addi $s5, $s5, 1 # noktayi atladi
		# ikinci tam kisim !!!
	li $t3, 1		#carpma icin 1 yaptik
	move $t7, $s3
	addi $t7,$t7,-1	#
on3:	beq $t7,$zero ,v4	# for t>0
	addi $t7,$t7,-1	# t7 --
	mult $t4,$t3	# t2*t3
	mflo $t3		# t3
	
	j on3
	
v4:	beq $s3, $zero, v4e		# t7 de dongy degiskeni olucak. s0--
	addi $s3, $s3, -1
	lb $t2, ($s5)		# ilk karakteri aldik
	addi $s5, $s5, 1		# bir sonraki index
	addi $t2, $t2, -48		#	SAYILARIN 10* OLARAK DEGERÝ HESAPLANIYOR T0 DA TOPLANIYOR
	mult $t2,$t3		# basamaktaki sayi * 10x
	mflo $t1			# cevabi t2 ye aldik
	add $s7,$s7,$t1		# t1 = t1+ t2
	div $t3,$t4
	mflo $t3
	
	j v4
v4e:
	# s6 ve t9
	# . dan sonraki basamak
	# sayilarini karsilastir
	# buyuk olani al
	slt $t1, $s6, $t9
	beq $t1, $zero, opE1
	bne $t1, $zero, opE2
opE1:
	move $t1,$s6
	sub $s6, $s6, $t9	
	li $t3, 1
	li $t4, 10
on4:	beq $s6,$zero ,sayiTam1
	addi $s6, $s6, -1
	mult $t4,$t3
	mflo $t3
	j on4
sayiTam1:
	mult $t3,$s7
	mflo $s7
	j gogo
opE2:
	move $t1,$t9
	sub $s6, $t9, $s6	
	li $t3, 1
	li $t4, 10
on5:	beq $s6,$zero ,sayiTam2
	addi $s6, $s6, -1
	mult $t4,$t3
	mflo $t3
	j on5
sayiTam2:
	mult $t3,$t6
	mflo $t6
	j gogo
gogo:
	li $t3, 1
	li $t4, 10
on6:	beq $t1,$zero ,sayiExit
	addi $t1, $t1, -1
	mult $t4,$t3
	mflo $t3
	j on6

sayiExit: # degiskenler hazirlandi stacke atiliyor
	addi $sp, $sp, -24
	sw $t0,20($sp)	
	sw $t6,16($sp)
	sw $t8,12($sp)
	sw $s7,8($sp)
	sw $s4,4($sp)
	sw $t3,0($sp)
	addi $sp, $sp, 24

	jr $ra

parser:	# STRING girisi !!
	la $a0, buffer
	li $a1, 255
	li $v0, 8
	syscall

	move $s2,$zero # s2 = 0
	li $t0,0
	li $t1,0
	li $t2,0
	li $t3,0
	li $t4,0
	
	move $s1, $a0
	li $s3 , 0

mainLoop:lb $s4, ($s1)
	beq $s4 , '\n' , exit
	addi $s3, $s3, 1
	addi $s1, $s1, 1
	beq $s4, '.',nokta
	beq $s4, ' ',bosluk
	beq $s4, '+',plus
	beq $s4, '-',minus
	beq $s4, '*',multi
cont:	j mainLoop

exit:	move $t3,$s3
	addi $t0, $t0, -1
	addi $t1, $t1, -1
	
	addi $t2, $t2, -1
	
	addi $sp, $sp, -20
	sw $t0,16($sp)
	sw $t1,12($sp)
	sw $t2,8($sp)
	sw $t3,4($sp)
	sw $t4,0($sp)
	addi $sp, $sp, 20
	jr $ra
	
nokta:	bne $s2,$zero, n2
	move $t0,$s3
	addi $s3, $zero, 0
	addi $s2, $s2, 1
	j cont
n2:	move $t2,$s3
	addi $s3, $zero, 0
	j cont

plus:	li $t4, '+'
	addi $s1, $s1, 1
	addi $s3, $zero, 0
	j cont

minus:	li $t4, '-'
	addi $s1, $s1, 1
	addi $s3, $zero, 0
	j cont

multi:	li $t4, '*'
	addi $s1, $s1, 1
	addi $s3, $zero, 0
	j cont

bosluk:	move $t1,$s3
	j cont
