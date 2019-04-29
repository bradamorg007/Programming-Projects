
function oricle_input_check4(A, B)
%check sizes agree

lenA = length(A);
lenB = length(B);

if isequal(lenA, lenB) == 0
    msg = 'ERROR: The Size of "Items per Memory Load" is not the same as "Mixed Data Ratio Split"';
    msgbox(msg);
    error(msg);
end

for i = 1:length(A)
    if B(i) > A(i) 
        msg = 'ERROR: Unable to created trial data with mixed ints and letter data types. An element in the Mixed data ratio split vector exceeds the value of its corrisponding "Items per Memory Load" element';
        msgbox(msg)
        error(msg)
    end
end  
end 