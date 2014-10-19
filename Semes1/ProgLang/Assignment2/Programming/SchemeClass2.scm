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

'done)