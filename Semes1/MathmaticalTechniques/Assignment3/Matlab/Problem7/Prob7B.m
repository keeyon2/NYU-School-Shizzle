x =[-1:0.1:1];
y = tanh(2*x);
P = legendre(5, y);

plot(x,y, x, P)
