(define (listfromTo a b)
  (listfromToHelper a b '()))

(define (listfromToHelper a b L)
  (cond ((> a b) '())
        ((= a b) (cons a L))
        (else (listfromToHelper a (- b 1) (cons b L)))
        ))

(define (listfromTo2 a b)
  (cond ((> a b) '())
        (else (cons a (listfromTo2 (+ a 1) b)))
        ))

(define (removeMult a L)
  (cond ((null? L) '())
        (else
          (if (= (modulo (car L) a) 0)
            (removeMult a (cdr L))
            (cons (car L) (removeMult a (cdr L)))
            ))))
