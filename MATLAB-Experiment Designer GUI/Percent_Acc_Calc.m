% Precentage accuracy calc per partipant for each condition;
%This data can then be used to calculate descriptives
function [Output_Vect] = Percent_Acc_Calc(data)
LenData = length(data);
% Output_Vect = zeros(LenData, 1);
TrailsPerMem = data{2, 1}{1, 3};

for j = 1:LenData
    A = data{1,j}(:, 3);
    [LenA, ~] = size(A);
        
        percentage_Acc = zeros(1, 1);
        NumParticpants = LenA / TrailsPerMem(j);
        startstep = 1;
        for n = 1:NumParticpants
            correct_count = 0;
            for i = 1:TrailsPerMem(j)
                scope = A(startstep);
                if scope == 1
                    correct_count = correct_count + 1;
                end 
                if ~isequal(startstep, LenA)
                startstep = startstep + 1;   
                else
                end 
            end 
            percentage_Acc(1, end + 1) = (correct_count/i) * 100;
        end
    percentage_Acc(:, 1) = [];
    Output_Vect(j, :) = percentage_Acc;
end 
