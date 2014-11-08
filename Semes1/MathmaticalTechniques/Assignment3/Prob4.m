%Original Problem
A = [1 1 1 4; 4 8 -3 35; 0 2 3 3]

% (-4 * row1) + Row2
Temp = (-4 * A(1, :)) + A(2, :);
A(2, :) = Temp

% Switch Row 2 and Row 3
TempOldRow2 = A(2,:);
TempOldRow3 = A(3,:);
A(2,:) = TempOldRow3;
A(3,:) = TempOldRow2

% (-2 * Row2) + row 3
Temp = (-2 * A(2,:)) + A(3,:);
A(3,:) = Temp

% Solve for X, Y, Z
z = A(3,4) / A(3,3)
y = (A(2,4) - (z*A(2,3))) / A(2,2)
z = A(1,4) - y - z