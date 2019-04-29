a = [1 1 -1; 1 -1 0.5]

plotv(a)
title('Vector Plot')
legend('Blue = -1 Vectors', 'Red = +1 Vectors', 'Yellow = 0.5 Vector ')

%Rotation 

R = [cosd(90), -sind(90); sind(90), cosd(90)];

P = R*a;

plotv(P)
title('Vector Plot - 90 Degree Rotation')
legend('Blue = -1 Vectors', 'Red = +1 Vectors', 'Yellow = 0.5 Vector ')