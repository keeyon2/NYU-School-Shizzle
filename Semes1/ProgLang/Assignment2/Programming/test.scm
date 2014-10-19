(begin 
(newline)
(newline)
(newline)
(newline)
(display "********************************")
(newline)
(display "********************************")
(newline)
(display "*******     START        *******")
(newline)
(display "********************************")
(newline)
(display "********************************")
(newline)

(display 5)
(newline)
(define (f x y)
    (+ x y))

(f 3 4)

(define x 5)
(display "x is ")
(display 5)
(newline) 

(define (same i j)
    (newline)
    (if (= i j) 'yes 'no))

(define (drawLine)
    (newline)
    (newline)
    (display "---------------------")
    (newline)
    (newline))

(define (KeeyonPrint x)
    (newline)
    (display x)
    (newline)
    (newline))

(drawLine)
(display "Same")
(display (same 4 5))
(display (same 5 5))
(newline)
(display "X is: ")
(display x)
(newline)

(drawLine)
(display "Cond1")
(cond ((= x 3) "x is five" )
        ((= x (- 6 1)) (display "we made it in"))
        (else 'I_DUNNO))
(drawLine)
(display "School Cond")
(define x 3)
(display "School Example")
(newline)
(cond ((= x 3) (display "x is three"))
        ((= x (- 6 1)) 5)
        (else 'I_DUNNO))

(drawLine)
(display "Remaining")

'(f 3 4)

;; List Operations
(drawLine)
(display "List Operations")
(newline)
(newline)
(define foo (list 1 2 3 4))
(display foo)
(newline)
(KeeyonPrint foo)

(KeeyonPrint (list '(+ 1 2) (+ 1 2)))

(drawLine)
(newline)
(KeeyonPrint "Cons Info")
(KeeyonPrint "Cons creates new list by appending the first element and to the second element of type list")
;; Cons
(define x (cons 3 '(1 2 3)))
(define y (cons 'a (list 1 2 3)))
(KeeyonPrint x)
(KeeyonPrint y)

(KeeyonPrint '())
(KeeyonPrint (cons 1 '()))
(drawLine)
(KeeyonPrint "Cons Recursion")

(define (NumList start end)
  (cond ((= start end) (list end))
        (else (cons start (NumList (+ 1 start) end)))
        ))

(define (nums start end)
  (cond ((= start end) (list start))
        (else (cons start (nums (+ start 1) end)))
        ))



(define x (NumList 1 9))
(KeeyonPrint x)
(drawLine)
(KeeyonPrint "Car Time")
(newline)
(KeeyonPrint "Car returns the first element in a list")
(define x (car (list 1 2 3 4)))
(KeeyonPrint x)
(KeeyonPrint "REMINDER: Cons and Car do not change paramaters, this is a functional language! Da silly silly goose")

(define L '(1 2 3 4) )
(KeeyonPrint L)
(KeeyonPrint (car L))

(drawLine)
(KeeyonPrint "CDR")
(KeeyonPrint "CDR returns the rest of the list")
(KeeyonPrint "Seems to be similar to saying the opposite of Car")

(KeeyonPrint (cdr L))

(drawLine)
(KeeyonPrint "Recursion for nth Numb")

(define (nth elem List)
    (KeeyonPrint elem)
    (KeeyonPrint List)
    (cond ((= elem 1) (car List))
    (else (nth (- elem 1) (cdr List)))
    ))

(KeeyonPrint (nth 4 (nums 1 49)))
;(KeeyonPrint (nums 1 10))

'done ) 





;(display (+ 3 5))
;(display (+ 5 x))

;(display x)
;(display "Hi Alaina")