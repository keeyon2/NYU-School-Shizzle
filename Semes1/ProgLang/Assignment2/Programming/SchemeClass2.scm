(begin 
(newline)
(display (null? `(1 2 4)))

;; Clean Prints
(define (PrintsWithNewLines x)
    (newline)
    (display x)
    (newline)
    (newline))

;; Length of a list
(define (len L)
    (cond ((null? L) 0)
    (else (+ 1 (len (cdr L))))
    ))

;; Clean Prints
(define (PrintsWithNewLines x)
    (newline)
    (display x)
    (newline)
    (newline))

;; Length of Lists With nested lists
(define (nestedListsLength L)
    (cond ((null? L) 0)
        ((pair? (car L)) (+ (nestedListsLength (car L)) (nestedListsLength (cdr L))))
        (else (+ 1 (nestedListsLength (cdr L))))
    ))

;; Professors version
(define (nested-length L)
  (cond ((null? L) 0)
        ((pair? (car L)) (+ (nested-length (car L)) (nested-length (cdr L))))
        (else (+ 1 (nested-length (cdr L))))
        ))

;; Professor Append
(define (app L1 L2)
  (cond ((null? L1) L2)
        (else (cons (car L1)  (app (cdr L1) L2)))
        ))

(define (f x)
    (+ x 3))

(define (my-cons a b)
    (lambda (key)
        (cond ((eq? key 'car) a)
              ((eq? key 'cdr) b)
              (else (error...))
           )))

(define (my-car L) (L 'car))
(define (my-cdr L) (L 'cdr))


;; Testing Lengths
(newline)
(define NestedList '('(1 2 3) 4 5 6))

(PrintsWithNewLines (len (quote (1 2 3 4))))
(PrintsWithNewLines NestedList)
(PrintsWithNewLines (len NestedList))
(PrintsWithNewLines (cdr (car NestedList)))
(PrintsWithNewLines (cdr (car NestedList)))

(PrintsWithNewLines (nestedListsLength NestedList))
(PrintsWithNewLines (nested-length NestedList))
(PrintsWithNewLines (app (list 1 2 3) (list 4 5 6 7)))

(PrintsWithNewLines "Time for the good stuff")
(PrintsWithNewLines (my-cons 3 '()))




'done)