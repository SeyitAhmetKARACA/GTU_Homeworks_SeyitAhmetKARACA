(load "csv-parser.lisp")
(in-package :csv-parser)

;; (read-from-string STRING)
;; This function converts the input STRING to a lisp object.
;; In this code, I use this function to convert lists (in string format) from csv file to real lists.

;; (nth INDEX LIST)
;; This function allows us to access value at INDEX of LIST.
;; Example: (nth 0 '(a b c)) => a

;; !!! VERY VERY VERY IMPORTANT NOTE !!!
;; FOR EACH ARGUMENT IN CSV FILE
;; USE THE CODE (read-from-string (nth ARGUMENT-INDEX line))
;; Example: (mypart1-funct (read-from-string (nth 0 line)) (read-from-string (nth 1 line)))

;; DEFINE YOUR FUNCTION(S) HERE

(defun helper(liste listeD number index ith)
	(setq check (car liste))
	(if (not check)
		listeD
		(if (= ith index)
			(helper liste (cons number listeD) number index (incf ith 1))
			(helper (cdr liste) (cons check listeD) number index (incf ith 1))
		)
	)
)

(defun insert-n(liste number index)
	(setq ith 0)
	(setq dummy '())
	(setq retVal (helper liste dummy number index ith))
	(reverse retVal)
)


;; MAIN FUNCTION
(defun main ()
  (with-open-file (stream #p"input_part3.csv")
    (loop :for line := (read-csv-line stream) :while line :collect
      (format t "~a~%"
      ;; CALL YOUR (MAIN) FUNCTION HERE
	(insert-n (read-from-string (nth 0 line)) (read-from-string (nth 1 line)) (read-from-string (nth 2 line)))


      )
    )
  )
)

;; CALL MAIN FUNCTION
(main)
