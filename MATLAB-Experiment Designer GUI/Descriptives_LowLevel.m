%Rather than use a long if statement branch, this function stores the
%descriptive stats functions as chars. The input vector x is a binary
%sequence of 1 or 0 = yes or no. Each number in sequence positionlly
%corrisponds to the stored function char vector. the vector is feed through
% a small if statement and the eval()function converts the char functions
% it actionable matlab expressions
%values are then retured as a matrix

function [T] = Descriptives_LowLevel(x, state)
omit = 'omitnan';
StatFunc = {'mean(x,omit)','median(x, omit)','mode(x)',...
                               'sum(x, omit)','skewness(x, flag)',...
                               'kurtosis(x, flag)','std(x, omit)',...
                               'var(x, omit)','min(x,[],omit)',...
                               'max(x,[], omit)'};
                           
T = zeros(1, length(state));
%T.Properties.VariableNames = {'Mean','Median','Mode','Sum','Skewness',...
                                % 'Kurtosis','Std','Variance','Min','Max'};
for i = 1:length(state)
    if state(i) == 1
    output1 = eval(StatFunc{i});
    else
    output1 = NaN;
    end 
    T(1, i) = output1;
end 
end 
    