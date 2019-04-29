%mixed Data Seperater - Ints & Letters 

function [LetterVector, NumberVector] = MixedVectorSplitter(SampleVector)

%Remove letters 

lc = 1;
nc = 1;
for i = 1:length(SampleVector)
    
    scope = SampleVector(i);
    checker = str2double(scope);
    checker1 = isequal(checker, (0.0000 + 1.0000i));
    if isnan(checker) == 1 || checker1 == 1
        LetterVector(lc) = scope;
        lc = lc + 1;
    else
        NumberVector(nc) = scope;
        nc = nc + 1;
    end 
    
end 
if lc == 1
    LetterVector = 0;
elseif nc == 1
    NumberVector = 0;   
end
end 
