x =[-1:0.1:1];
%HypeT = (exp(2*x) - exp(-2*x))/(exp(2*x) + exp(-2*x))
% tanh = tansig(2*x);
y = tanh(2*x);
P = legendre(5, y);

%plot(x, P(6,:), x, y);
plot(x,y, x, P)
%P = legendre(1, -1:0.1:1);


%plot(x, P,x,tanh(x))