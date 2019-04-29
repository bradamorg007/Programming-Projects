
function oricle_input_check3(Range, ItemsPerMem, RepCheck)
LenRange = length(Range);

if LenRange ~=2
    msg = 'ERROR: Number or Letter Range vector exceeds the 1x2 dimensions limit';
    msgbox(msg)
    error(msg)
end 

if strcmpi("yes repeat check", RepCheck) == 1
difference = abs(Range(2) - Range(1));
for i = 1:length(ItemsPerMem)
    
    if ItemsPerMem(i) > (difference + 1)
        msg = 'ERROR: Non-repeating Random data cannot be generated if a sub-section of "Items per memory load" exceeds the difference between the inputted max and min values of the number or letter range';
        msgbox(msg)
        error(msg)
    end 

end
end 
end

