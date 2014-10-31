function X = AnalyzeCircuit(C,R,V)

% Find Input Dimensions
CRows = size(C, 1);
CCols = size(C, 2);

RRows = size(R, 1);
RCols = size(R, 2);

VRows = size(V, 1);
VCols = size(V, 2);

% Test Input Dims
assert(CRows == 2);
assert(RRows == 1);
assert(VRows == 1);
assert(CCols == RCols);
assert(CCols == VCols);

% get n, b, q
q = length(R) * 2 - 1
n = length(R) - 1;
b = length(R);

% Will later do M \ c to get solution
M = zeros(q + 1, q);
c = zeros(q + 1, 1);

% Source Indecies are where V != 0
% Use the ~ to find all the greater than 0's
%Num Source is the number of sources, numel does this
source_indecies = find(V~=0);
number_of_sources = numel(source_indecies);

% Same Logic as V.  Now we find where the resisters are
% and find the number of Resisters
resister_indecies = find(R~=0);
number_of_resisters = numel(resister_indecies);

% Make first Col of C Excluding 
% first element be V Elements that are
% Not 0
c((1:number_of_sources)+1) = V(source_indecies);

% Ground M
M(1,1) = 1;

% Voltage source
for k = 1:number_of_sources
% All rows excluding first
row = k + 1;

%Cols with Sourc Ind
col = C(:, source_indecies(k));

%Set M
M(row, col) = [-1, 1];
end

%Resistor
for k = 1:number_of_resisters
% Indecies of sources adjusted by k + 1
row = k + 1 + source_indecies;
% Col is The Column where there are resisters,
% And the resister Indecies adjusted by N
col = [C(:, resister_indecies(k)); resister_indecies(k) + n];
%Set M 
M(row, col) = [1, -1, -R(resister_indecies(k))];
end

% (d) Kirchoff's current law
C_Row1 = C(1, :);
C_Row2 = C(2, :);
for k = 1:n
row = k + 1 + b;
% Find col input
col_in = find(C_Row1==k) + n;

%Find Col Output
col_out = find(C_Row2==k) + n;
%Adjust M based on the Input/Output
M(row, col_in) = -1;
M(row, col_out) = 1;
end

% compute Final Result
X = M \ c;
end