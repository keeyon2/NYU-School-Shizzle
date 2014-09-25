%Keeyon Ebrahimi
%kme322
%N14193968


%***************Problem 2********%
v1 = [1, -3, 0];
v2 = [2, 1, 3];

dotV = dot(v1, v2);
magV1 = norm(v1);
magV2 = norm(v2);

Problem2Solution = acos(dotV / (magV1 * magV2)) 


%***************Problem 4********%

M1 = [2, -1, 1, 0; 1, 3, 1, -1; -2, 1, 0, 1];
M2 = [1, 2; -2, 0; 2, -1; 1, 1];

Problem4Solution = M1 * M2

% DELETE ME PROB 5 Set UP% 
%evaluate(M1, v1, M2, Problem4Solution)
%D = [1, 1, 0, 4; 2, 0, 1, 1; 2, 3, 0, 0; 0, 2, 3, 1; 4, 0, 2, 0; 3, 0, 1, 3];
%L = [1, 1, 0, 0, 0, 0];
%W = [1, 2, 1, 2; 0, 0, 0, 1];
%W = transpose(W);
%T = [9, 2];

%[acc, prec, rec] = evaluate(D, L, W, T)
%E = evaluate2(D, L, W, T)


