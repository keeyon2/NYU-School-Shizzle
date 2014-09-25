%Keeyon Ebrahimi
%kme322
%N14193968

%***************Problem 5 - Part B - evaluate2********%

function [E] = evaluate2(D,L,W,T)
    
    %Set up
    E = zeros(3, length(T));
    if iscolumn(L) == false
        L = transpose(L);
    end
    [m, n] = size(W);

    for j=1:length(T)
        DdotW = D * W(1:m, j);
        R = zeros(1, length(L));
        MagQ = 0;
        R1s = 0;
        L1s = 0;
        R1sCorrect = 0;
        L1sCorrect = 0;

        for i=1:length(L)

            % Creates classification
            if DdotW(i) >= T(j)
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
        
        % Placed in second column
        E(1,j) = accuracy;
        E(2,j) = precision;
        E(3,j) = recall;
        
    end
        
    
end

    