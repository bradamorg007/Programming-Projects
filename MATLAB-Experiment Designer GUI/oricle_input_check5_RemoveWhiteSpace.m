% Clears Any Outside WhiteSpace
 function [Output] = oricle_input_check5_RemoveWhiteSpace(x)
 

    if isstring(x) == 1
        x = convertStringsToChars(x);
        DataType = "String";
        
    elseif ischar(x) == 1
        DataType = "Char";
    end 
   
x = double(x);
if isempty(x)
    msg =  'ERROR: One or More User Input Fields Remain Empty';
    msgbox(msg);
    error(msg);
end 
a = 0;
b = 0;
while (a == 0) || (b ==0)
    
    scope_start = x(1,1);
    scope_end = x(1, length(x)); 
   
    if isequal(scope_start, 32)
        x(1) = [];
    else 
        a = 1;
    end 
    if isequal(scope_end, 32)
        x(length(x)) = [];
    else 
        b = 1;
    end  
    if isempty(x)
    msg =  'ERROR: One or More User Input Fields Remain Empty';
    msgbox(msg);
    error(msg);
    end
end 
    
switch DataType
    case "String"
      Output = convertCharsToStrings(char(x));
    case "Char"
      Output = char(x);
end 
end 
    
