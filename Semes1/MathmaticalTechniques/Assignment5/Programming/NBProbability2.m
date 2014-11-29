function [P, I] = NBProbability2(S, D, N)
% Elements in D
ColSizeD = size(D, 1);
DiseasesCheckedArrayI = [];
DiseaseProbabilityArray = [];
%For 
for i = 1:ColSizeD
    CurrentlyCheckingDisease = D(i, 1);
    HaveCheckedThisDisease = any(CurrentlyCheckingDisease == DiseasesCheckedArrayI);
    if (HaveCheckedThisDisease)
        continue
    end
    DiseasesCheckedArrayI(end+1) = CurrentlyCheckingDisease;
    
    % Find out how many of this disease exist
    CurrentlyCheckingDRepeatingMatrix = ones(1, ColSizeD);
    for j = 1:ColSizeD
        CurrentlyCheckingDRepeatingMatrix(1, j) = CurrentlyCheckingDisease;
    end
    NewD = ~xor(D, transpose(CurrentlyCheckingDRepeatingMatrix));
    NewD = double(NewD);
    SumNewD = sum(NewD);
    ProbNewD = SumNewD/ColSizeD;
    RepeatingN = repmat(N, ColSizeD, 1);
    SameNSmatrix = ~xor(S, RepeatingN);
    TransposedD = transpose(NewD);
    SameWithFluMatrix = mtimes(TransposedD,SameNSmatrix);
    TotalSymptomProb = prod(SameWithFluMatrix) / (SumNewD ^ size(N, 2));
    FinalProb = TotalSymptomProb * ProbNewD;
    DiseaseProbabilityArray(end + 1) = FinalProb;
end
I = DiseasesCheckedArrayI;
P = DiseaseProbabilityArray/norm(DiseaseProbabilityArray);