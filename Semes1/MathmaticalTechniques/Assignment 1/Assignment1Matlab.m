%Keeyon Ebrahimi
%kme322
%N14193968

function Assignment1Matlab()
fprintf('There are funtions named: Problem2  Problem4   evaluate   evaluate2\n'); 
fprintf('You can run each one individually to get results\n');
end

%***************Problem 2********%
function Problem2()
v1 = [1, -3, 0];
v2 = [2, 1, 3];

dotV = dot(v1, v2);
magV1 = norm(v1);
magV2 = norm(v2);

Problem2Solution = acos(dotV / (magV1 * magV2)) 
end

%***************Problem 4********%
function Problem4()
M1 = [2, -1, 1, 0; 1, 3, 1, -1; -2, 1, 0, 1];
M2 = [1, 2; -2, 0; 2, -1; 1, 1];

Problem4Solution = M1 * M2
end


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


