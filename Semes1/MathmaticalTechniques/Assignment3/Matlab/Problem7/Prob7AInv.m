function MInv = Prob7AInv(M)

%Get the L S R from the svd(M)
[U,S,V] = svd(M);

%We want to eliminate the values From S and L that don't matter
% We care about The collums of L that go to the rank of S
% We care about the Rows of S that only go to its rank.
S_rank = rank(S);
L_elim = U(:,1:S_rank);
S_elim = S(1:S_rank,:);

% At this point, we want to take the S_elim and replace
% All non-zero elements by 1/self, as shown in line 90 of the 
% SVD handout
S_small_NonZero_Inv = S_elim^(-1)

MInv = V*S_small_NonZero_Inv'*L_elim'