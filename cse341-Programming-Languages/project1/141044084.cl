; verilmiş stringi istenilen karaktöre göre ayırıp liste return ediyor.
(defun sakSplit(stringValue identifier bosliste)
	(if (not (position (char identifier 0) stringValue ))
		(cons stringValue bosliste)
		(if (string= identifier (subseq stringValue 0 1))
			(sakSplit (subseq stringValue 1) identifier (cons identifier bosliste))	
			(sakSplit (subseq stringValue (+ 1 (position (char identifier 0) stringValue ))) identifier (cons identifier (cons (subseq stringValue 0 (position (char identifier 0) stringValue )) bosliste)))				
		)
	)
)
; operatorlerin bulundugu liste
(setq operator '("+" "-" "/" "*" "(" ")" "**"))
; keyword lerin bulunduğu liste
(setq keyword '("and" "or" "not" "equal" "append" "concat" "set" "deffun" "for" "while" "if" "exit"))

(defun isKeyword(paramKeyword keywordListe)
	(if (not (car keywordListe))
		nil
		(if (string= (car keywordListe) paramKeyword)
			t
			(isKeyword paramKeyword (cdr keywordListe))
		)
	)
)



(defun isOperator(paramOperator operatorListe)
	(if (not (car operatorListe))
		nil
		(if (string= (car operatorListe) paramOperator)
			t
			(isOperator paramOperator (cdr operatorListe))
		)
	)
)

(defun isBinaryValue(paramBinaryValue)
	(if (string= "TRUE" (string-upcase paramBinaryValue))
		t
		(if (string= "FALSE" (string-upcase paramBinaryValue))
			t
			nil
		)
	)
)

(defun isIntegerValue(parametre &optional (index 0) (negative 0))
	(if (and (= (length parametre) 1))
		(if (and (string<= parametre "9") (string>= parametre "0") (>= index 0) (= negative 0))
			t
			(if (and (string<= parametre "9") (string>= parametre "0") (> index 1) (= negative 1))
				t
				(if (and (string<= parametre "9") (string>= parametre "1"))
					t
					nil
				)
			)
		)
		(if (> (length parametre) 1)
			(if (and (string= (subseq parametre 0 1) "-") (= index 0))
				(isIntegerValue (subseq parametre 1 (length parametre)) (+ index 1) 1)
				(if (and (string<= (subseq parametre 0 1) "9") (string>= (subseq parametre 0 1) "1") (= index 1) (= negative 1))
					(isIntegerValue (subseq parametre 1 (length parametre)) (+ index 1) 1)
					(if (and (string<= (subseq parametre 0 1) "9") (string>= (subseq parametre 0 1) "0") (> index 1) (= negative 1))
						(isIntegerValue (subseq parametre 1 (length parametre)) (+ index 1) 1)
						(if (and (string<= (subseq parametre 0 1) "9") (string>= (subseq parametre 0 1) "1") (= index 0) (= negative 0))
							(isIntegerValue (subseq parametre 1 (length parametre)) (+ index 1) 0)
							(if (and (string<= (subseq parametre 0 1) "9") (string>= (subseq parametre 0 1) "0") (> index 0) (= negative 0))
								(isIntegerValue (subseq parametre 1 (length parametre)) (+ index 1) 0)
								nil
							)
						)
					)	
				)
			)
		)
	)
)

(defun isID(parametre)
	(if (> (length parametre) 1)
		(if (or (and (string<= (subseq parametre 0 1) "z")(string>= (subseq parametre 0 1) "a")) 
			(and (string<= (subseq parametre 0 1) "Z")(string>= (subseq parametre 0 1) "A")))
			(isID (subseq parametre 1 (length parametre)))
			nil
		)
		(if (= (length parametre) 1)
			(if (or (and (string<= (subseq parametre 0 1) "z")(string>= (subseq parametre 0 1) "a"))
				(and (string<= (subseq parametre 0 1) "Z")(string>= (subseq parametre 0 1) "A")))
				t
				nil
			)
			nil	
		)		
	)
)

; dosyadan satır satır veri okuma
(defun readFromFile(fileName)
	(defvar tempListe '())
	(defvar splitList '())
	(let ((in (open fileName :if-does-not-exist nil)))
	   (when in
	      (loop for line = (read-line in nil)
	      
	      	while line do (setq tempListe (append  (sakSplit line " " splitList) tempListe))
	      )
	      (close in)
	   )
	)
	tempListe
)
; verilen listeyi gezip belirtilen karaktere göre bölüp liste return ediyor
(defun bizimListe(paramliste1 x)
	(defvar p2 '())
	(if (not (car paramliste1))
		p2
		(append (reverse (sakSplit (car paramliste1) x p2)) (bizimListe (cdr paramliste1) x))
	)
)

(defun lexer(fileName)
	(defvar lexerList (cleanEmptyString (bizimListe (bizimListe (reverse (readFromFile fileName)) "(" ) ")" )))
	(lexerHelper lexerList)
)

; 
(defun lexerHelper(finalList)
	(defvar returnList '())
	(defvar p2 '())
	(if (not (car finalList))
		p2
		(cond
		      ((isKeyword (car finalList) keyword) (append (cons (cons "keyword" (cons (car finalList) p2)) returnList) (lexerHelper (cdr finalList))))
		      ((isOperator (car finalList) operator) (append (cons (cons "operator" (cons (car finalList) p2)) returnList) (lexerHelper (cdr finalList))))
		      ((isBinaryValue (car finalList)) (append (cons (cons "binary" (cons (car finalList) p2)) returnList) (lexerHelper (cdr finalList))))
		      ((isIntegerValue (car finalList)) (append (cons (cons "integer" (cons (car finalList) p2)) returnList) (lexerHelper (cdr finalList))))
		      ((isID (car finalList)) (append (cons (cons "identifier" (cons (car finalList) p2)) returnList) (lexerHelper (cdr finalList))))
		      (t (append (cons (cons (car finalList) (cons "ERROR" p2)) returnList) (lexerHelper (cdr finalList))))
		)
	)
)

(defun cleanEmptyString(maySpaceList)
	(defvar bosList '())
	(if (not (car maySpaceList))
		bosList
		(if (or (string= "" (string-trim " " (car maySpaceList))) (string= "" (string-trim "	" (car maySpaceList)))  (string= "	" (string-trim "	" (car maySpaceList))))
			(cleanEmptyString (cdr maySpaceList))
			(append (cons (string-trim "	" (car maySpaceList)) bosList) (cleanEmptyString (cdr maySpaceList)))
		)
	)
	
)

(defun yazdir(listeX)
	(if (not (car listeX))
		nil
		(progn
			(write (car listeX))
			(write-line "")
			(yazdir (cdr listeX))
		)
	)
)



;(write (lexer "s2.lisp"))














