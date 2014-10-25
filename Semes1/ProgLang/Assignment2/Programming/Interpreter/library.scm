;;2nd element
(define (cadr L)
    (car (cdr L)))

;; All but first 2
(define (cddr L)
    (cdr (cdr L)))

;; All but first 3
(define (cdddr L)
    (cdr (cdr (cdr L))))

;; Third element
(define (caddr L)
    (car (cdr (cdr L))))

;; Forth Element
(define (cadddr L)
    (car (cdr (cdr (cdr L)))))

;; Newline
(define (newline)
  (display "\n"))

;; Append
(define (append L1 L2)
  (cond ((null? L1) L2)
	(else (cons (car L1) (append (cdr L1) L2)))
	))

;; Map
(define (map func L)
  (cond ((null? L) '())
	(else (cons (func (car L)) (map func (cdr L))))
	))

;; Test Func - Remove me
(define (f x)
  (+ x 2))

;; And
(define (and x y)
  (if x y #f))

;; Or
(define (or x y)
  (if x #t y))

;; Not
(define (not x)
  (if x #f #t))

;; Equal?
(define (equal? x y)
  (if (if x y #f) #t (if (not x) (not y) #f)))

