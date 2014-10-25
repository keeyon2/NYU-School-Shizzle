(define (repl)     ;;; the read-eval-print loop.
  (display "--> ") 
  (let ((exp (read)))
    (cond ((equal? exp '(exit))      ; (exit) only allowed at top level
       'done)
      (else  (display (top-eval exp))
         (newline)
         (repl))
      )))

(define (repl2)     ;;; the read-eval-print loop.
  (display "--> ") 
  (let ((exp (read)))
    (cond ((equal? exp '(exit))      ; (exit) only allowed at top level
       'done)
      (else  (top-eval2 exp)
         (newline)
         (repl))
      )))


(define (top-eval exp)
  (cond ((not (pair? exp)) (my-eval exp *global-env*))
    ((eq? (car exp) 'define)   
     (insert! (list (cadr exp) (my-eval (caddr exp) *global-env*)) *global-env*)
     (cadr exp)) ; just return the symbol being defined
    (else (my-eval exp *global-env*))
    ))

(define (top-eval2 exp)
  (cond ((not (pair? exp)) (my-eval exp *global-env*))
    ((eq? (car exp) 'define)
     (display (caddr exp))   
     ;(insert! (list (cadr exp) (my-eval (caddr exp) *global-env*)) *global-env*)
     (cadr exp)) ; just return the symbol being defined
    (else (my-eval exp *global-env*))
    ))

(define (my-load filename)       ;; don't want to redefine the Scheme LOAD
  (load-repl (open-input-file filename)))

(define (my-Newline)
    (display "\n"))

(define (print-NewLines)
    (display "1")
    (display "\n")
    (display "2")
    )

;;-------------------- Here is the initial global environment --------

(define *global-env*
  (list (list 'car (list 'primitive-function car))
    (list 'cdr (list 'primitive-function cdr))
    (list 'set-car! (list 'primitive-function set-car!))
    (list 'set-cdr! (list 'primitive-function set-cdr!))
    (list 'cons (list 'primitive-function cons))
    (list 'list (list 'primitive-function list))
    (list '+ (list 'primitive-function +))
    (list '- (list 'primitive-function -))
    (list '* (list 'primitive-function *))
    (list '= (list 'primitive-function =))
    (list '< (list 'primitive-function <))
    (list '> (list 'primitive-function >))
    (list '<= (list 'primitive-function  <=))
    (list '>= (list 'primitive-function >=))
    (list 'eq? (list 'primitive-function eq?))
    (list 'pair? (list 'primitive-function pair?))
    (list 'symbol? (list 'primitive-function symbol?))
    (list 'null? (list 'primitive-function null?))
    (list 'read (list 'primitive-function read))
    (list 'display (list 'primitive-function  display))
    (list 'open-input-file (list 'primitive-function open-input-file))
    (list 'close-input-port (list 'primitive-function close-input-port))
    (list 'eof-object? (list 'primitive-function eof-object?))
    (list 'load (list 'primitive-function my-load))
    (list 'newline2 (list 'primitive-function my-Newline))
      ;;defined above
    ))