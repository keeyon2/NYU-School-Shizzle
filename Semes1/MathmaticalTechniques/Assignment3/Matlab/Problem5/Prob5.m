A = [1 2; 3 4]
B = [-5 -7; -6 -8]
P = [6 -5]

%(B * A^-1)
InvMult = B*inv(A)

%(B * A^-1) * P
Solution = InvMult * P'
