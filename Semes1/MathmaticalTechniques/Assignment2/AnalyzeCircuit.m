function [x] = AnalyzeCircuit(C, R, V)
    %Set up the Array to multiply
    q = length(R) * 2 - 1
    n = length(R) - 1;
    b = length(R);
    Equations = zeros(q + 1, q);
    VSourceVector = zeros(q, 1)
    Equations(1,1) = 1;
    VSourceVector = VSourceVector
    
    
end

      