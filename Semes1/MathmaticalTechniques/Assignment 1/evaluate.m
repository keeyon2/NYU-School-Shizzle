%Keeyon Ebrahimi
%kme322
%N14193968

%***************Problem 5 - Part A - evaluate********%

function [accuracy, precision, recall] = evaluate(D,L,W,T)
    %First find Classifier
    %DdotW = D * W;
    if iscolumn(W) == false
        W = transpose(W);
    end
    if iscolumn(L) == false
        L = transpose(L);
    end
    
    DdotW = D * W;
    R = zeros(1, length(L));
    MagQ = 0;
    R1s = 0;
    L1s = 0;
    R1sCorrect = 0;
    L1sCorrect = 0;

    for i=1:length(L)
        
        % Creates classification
        if DdotW(i) >= T
           R(i) = 1;
           R1s = R1s + 1;
           
           % Needed for Precision
           if L(i) == R(i)
               R1sCorrect = R1sCorrect + 1;
           end
        end
        
        % Needed for Recall
        if L(i) == 1
            L1s = L1s + 1;
            if L(i) == R(i)
                L1sCorrect = L1sCorrect + 1;
            end
        end
                
        % Needed for Precision
        if L(i) == R(i)
            MagQ = MagQ + 1;
        end
    end
    
    accuracy = MagQ/length(L);
    precision = R1sCorrect/R1s;
    recall = L1sCorrect/L1s;
    end

    