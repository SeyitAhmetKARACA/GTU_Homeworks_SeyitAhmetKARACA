; Denemelerin bazilari
(defvar tokens1 '(
("operator" "(") ("integer" "1")
 ("integer" "2") ("integer" "3") ("integer" "4") ("operator" ")")
))

(defvar tokens2 '(
("operator" "(") ("keyword" "deffun") ("ID" "topla") ("operator" "(")
 ("ID" "Q") ("operator" ")") ("operator" "(") ("integer" "1")
 ("integer" "2") ("integer" "3") ("integer" "4") ("operator" ")") ("ID" "Q")
 ("operator" ")")
))

(defvar tokens3 '(
("operator" "(") ("keyword" "deffun") ("identifier" "check_coord") ("operator" "(")
 ("identifier" "x") ("identifier" "y") ("operator" ")") ("operator" "(") ("keyword" "if")
 ("operator" "(") ("keyword" "and") ("operator" "(") ("keyword" "equal")
 ("identifier" "x") ("integer" "1") ("operator" ")") ("operator" "(")
 ("keyword" "equal") ("ID" "y") ("integer" "2") ("operator" ")")
 ("operator" ")") ("integer" "1") ("integer" "0") ("operator" ")")
 ("operator" ")")
))

; Expression listesi. input olarak gelen listedeki komutlari bu liste anlamdirmaktadir.
(defvar expi '("VALUES" ("while" "EXPB" "EXPLISTI") ("/" "EXPI" "EXPI") ("*" "EXPI" "EXPI") ("+" "EXPI" "EXPI") ("-" "EXPI" "EXPI") "ID" "integer" ("if" "EXPB" "EXPLISTI" "EXPLISTI") ("if" "EXPB" "EXPLISTI") ("deffun" "ID" "IDLIST" "EXPLISTI") ("and" "EXPB" "EXPB") ("or" "EXPB" "EXPB") ("not" "EXPB") ("equal" "EXPI" "EXPI") ("equal" "EXPB" "EXPB") "BinaryValue" ("concat" "EXPLISTI" "EXPLISTI") ("append" "EXPI" "EXPLISTI") "null" "keyword" ("for" "ID" "EXPI" "EXPI" "EXPLISTI") ("defvar" "ID" "EXPI") ("set" "ID" "EXPI")
))

; Bu fonksiyon input olarak gelen liste icerisinde en distaki parantezin icerigini kontrol etmektedir.
; Karsilasilan ilk acma parantezi nerede kapandi ise kapandigi indexi return etmektedir.
(defun getir(getir-liste getir-check)
	(if (not (car getir-liste))
		0
		(if (string= (car (cdr (car getir-liste))) "(")
			(+ 1 (getir (cdr getir-liste) (+ 1 getir-check)))
			(if (string= (car (cdr (car getir-liste))) ")")
				(if (= (- getir-check 1) 0)
					1
					(+ 1 (getir (cdr getir-liste) (- getir-check 1)))
				)
				(+ 1 (getir (cdr getir-liste) getir-check))
			)
		)
	)
)


; match-xp-list'te kullanicidan gelen kodlar bulunmaktadir.
; expiL ile karsilastirilip tokenlari belirlenmektedir.
; getir isimli fonkisyon ile istenilen parantez icerisindeki kodlar alinip
; hangi pattern'e ait olduklari belirlenir.
(defun match-xp(match-xp-list expiL) ; expi = expressionlari tutan liste checklist;(+ x 5) olan liste
	(if (not (car expiL))
		nil
		(if (listp (car expiL))
			(if (equal (car (car expiL)) (car (cdr (car match-xp-list))))
				(car expiL)
				(match-xp match-xp-list (cdr expiL))
			)
			(if (string= (car expiL) (car (cdr (car match-xp-list))))
				(car expiL)
				(match-xp match-xp-list (cdr expiL))
			)

		)
	)	
)

; Bu method bir liste ve string almaktadir.
; string , listenin en icteki elemani ile eslesir ise true
; eslesmez ise nil return etmektedir.
(defun checklistornot(cnlliste cnlstring)
	(if (not (car cnlliste))
		nil
		(if (listp (car cnlliste))
			(checklistornot (cdr cnlliste) cnlstring)
			(if (string= (car cnlliste) cnlstring)
				t
				(checklistornot (cdr cnlliste) cnlstring)
			)
		)
	)
)

; Bu method agac basabilmek icin gerekli listeyi olusturmaktadir.
; kendisine gelen kodlari (clist) expiression listesine göre bir liste olusturmaktadir.
; listedeki root-child iliskisi bu fonksiyonda kurulmaktadir.
;
(defun slave(clist res &optional (rResult '())) ;clist:sublist , res;match-xp'den gelen result (+ expi expi)	
	(if (or (string= (car (cdr (car clist))) "(" ) (string= (car (cdr (car clist))) "'" ) )
		(progn
			(if (string= "IDLIST" (car res))
				(progn
					(setq mouse '("IDLIST"))
					(append rResult (slave (subseq clist (getir clist 0) (length clist)) (cdr res) (list (basic clist mouse))))
				)
				(if (string= "EXPLISTI" (car res))
					(progn
						(setq mouse '("EXPLISTI"))	
						(append rResult (slave (subseq clist (getir clist 0) (length clist)) (cdr res) (list (basic  clist mouse))))
					)
					(if (string= "EXPB" (car res))
						(progn
							(setq mouse '("EXPB"))
							(append rResult (slave (subseq clist (getir clist 0) (length clist)) (cdr res) (list (basic  clist mouse))))
						)
						(if (and (string= "VALUES" (car res)) (string= (car (cdr (car clist))) "'" ))
							(progn
								(setq mouse '("VALUES"))
								(append rResult (slave (subseq clist (getir clist 0) (length clist)) (cdr res) (list (basic  clist  mouse))))
							)
							(progn
								(append rResult (slave (cdr clist) res (list (car clist))))
							)
						)

					)
				)
			)
		)
		(if (not (car clist))
			rResult
			(if (checklistornot expi (car (car clist)))
				(progn
					(if (string= "EXPLISTI" (car res))
						(progn
							(if (string= "integer" (car (car clist)))
(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list (append (list "EXPI" (append '("VALUES")(list (car clist))))) "EXPLISTI")))) ))
								(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list (car clist)) '("EXPLISTI"))))))
							)
						)
						;BinaryValue
						(if (string= "EXPB" (car res))
							(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list (car clist)) '("EXPB"))))))
							(if (string= "VALUES" (car res))
								(append rResult (slave (cdr clist) res (list (reverse (append (list (car clist)) '("VALUES"))))))
								(if (string= (car res) "EXPI")
									(if (string= "integer" (car (car clist)))
										(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list  (append  '("VALUES")(list (car clist))) "EXPI"))))))
										(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list (car clist)) '("EXPI"))))))
									)
									;(append rResult (slave (cdr clist) (cdr res) (list (reverse (append (list (car clist)) '("expi"))))))
									(append rResult (slave (cdr clist) (cdr res) (list (car clist))))
								)
							)
						)
					)
				)
				(progn
					(append rResult (slave (cdr clist) (cdr res) (append (list (car clist)) )))
					

				)
			)
		)
	)
)

; Bu fonksiyon istenilen paran icerisindeki kodun hangi yapiya sahip oldugunu tespit edip
; sonrasinda bu koda ait agac listesi cikarilabilmesi icin slave fonksiyonuna gönderilir.
; sonuc olan agac listesini return etmektedir.
(defun basic(token &optional (agac '("EXPI")))
	(setq bound (getir token 0))
	
	(if (and (string= (car agac) "EXPLISTI") (string= (car (cdr (car token))) "'"))
		(progn
			(setq agac (append agac '(("operator" "'"))))
			(setq agac (append agac '(("operator" "("))))			
			(setq setexp '("VALUES"))
			(setq agac (append agac (slave (subseq token 0 bound) setexp)))
			(setq agac (append agac '(("operator" ")"))))
		)
		(if (string= (car agac) "VALUES")
			(progn
				(setq setexp '("VALUES"))
				; eger token lenght 2 ye esit ve büyükse
				(setq agac (append agac (slave (subseq token 2 (- bound 1)) setexp)))
				
			)
			(progn
				(setq agac (append agac (slave (subseq token 0 bound) (match-xp (subseq token 1 (- bound 1)) expi))))
			)
		)
	)
)

; bos dosya olusturup icerisine gerekli yorum satirini eklemektedir.
(defun create-file()
	(with-open-file (str "141044084.tree"
		             :direction :output
		             :if-exists :supersede
		             :if-does-not-exist :create)
	(format str "; DIRECTIVE: parse tree"))
)

; İnput olarak alinan stringi ilgili dosyaya ekleme islemini gerceklestirir.
(defun write-to-file(outputString)
	(with-open-file (str "141044084.tree"
		             :direction :output
		             :if-exists :append
		             :if-does-not-exist :create)
	(format str outputString))
)

; Agaci dosyaya bastiran fonksiyondur.
; Gerekli girinti , alt satir , ciktida bulunmamasi gereken keywordlerin gizlenmesi
; islemini gerceklestiren fonksiyondur.
(defun print-tree (tree &optional (stringx ""))
	(if (null tree)
		nil
		(progn
			
			(if (listp (car tree))
				(progn
					(if (string= (car (car tree)) "operator")
						(if (or (string= (car (cdr (car tree))) "(") (string= (car (cdr (car tree))) ")"))
							(print-tree (cdr (car tree)) stringx)
						)
						(if (string= (car (car tree)) "keyword")
							(print-tree (cdr (car tree)) stringx)
							(print-tree (car tree) stringx)
						)
					)
				)
				(progn
					(write-to-file "~%")
					;(write-line "")
					;(write-string stringx)
					(write-to-file stringx)
					(if (string= "identifier" (car tree))
						(write-to-file "ID")
						(if (string= "integer" (car tree))
							(write-to-file "integerValue")
							(write-to-file (car tree))
						)
					)

					;(write-string (car tree))
					(setq stringx (concatenate 'string stringx "  "))
				)
			)
			(print-tree (cdr tree) stringx)
		)
	)
)

; Agaci olusturan temel fonksiyonu cagirip, alinan ciktiyi 
; dosyaya yazan fonksiyondur. Bu fonksiyonun görevi yukarida bulunan fonksiyonlari 
; dogru sekilde cagirmaktir.
(defun parser(inputList)
	(defvar outputList '("START" "INPUT" "EXPI"))
	(defvar output (basic inputList outputList))
	(create-file)
	(print-tree output)
)

; parserin cagrilmasi
(parser tokens3)







