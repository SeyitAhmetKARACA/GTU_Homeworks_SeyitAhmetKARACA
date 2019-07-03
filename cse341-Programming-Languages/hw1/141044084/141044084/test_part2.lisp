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

(defun hw2Helper(liste11 liste22)	
	(if (not liste22)
		liste11
		(hw2Helper (cons (car liste22) liste11) (cdr liste22))
	)
)

(defun Merge-list(liste1 liste2)
	(reverse (hw2Helper (reverse liste1) liste2))
)


;; MAIN FUNCTION
(defun main ()
  (with-open-file (stream #p"input_part2.csv")
    (loop :for line := (read-csv-line stream) :while line :collect
      (format t "~a~%"
      ;; CALL YOUR (MAIN) FUNCTION HERE
		(Merge-list (read-from-string (nth 0 line)) (read-from-string (nth 1 line)))

      )
    )
  )
)

;; CALL MAIN FUNCTION
(main)
