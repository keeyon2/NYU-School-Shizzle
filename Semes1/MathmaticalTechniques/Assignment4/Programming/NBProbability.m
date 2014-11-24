function P = NBProbability(S, D, N)
ColSizeD = size(D, 1);
SumD = sum(D);
ProbD = SumD/ColSizeD;

RepeatingN = repmat(N, ColSizeD, 1);
SameNSmatrix = ~xor(S, RepeatingN);
SameWithFluMatrix = transpose(D) * SameNSmatrix;
TotalSymptomProb = prod(SameWithFluMatrix) / (SumD ^ size(N, 2));
P = TotalSymptomProb * ProbD;