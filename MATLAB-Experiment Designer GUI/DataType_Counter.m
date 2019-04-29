function [output] = DataType_Counter(matrix, value_type, strict_count, commandline_disp)
%Data set up 
value_count_ratio = zeros(length(value_type));
[R, C] = size(matrix);
matrix_size = R * C; 

string_check = isstring(strict_count);

%Module Status 
error_flag = 0;
strict_flag = 0;
success_flag = 0;

%Main Module Loop
if string_check == 1
    strict_count = lower(strict_count);
    if sum(abs(sum(abs(matrix)))) > 0 
        
        for i = 1:R
            for j = 1:C
               scope = matrix(i, j);
                    for h = 1:length(value_type)
                        if scope == value_type(h)
                            value_count_ratio(h, :) = value_count_ratio(h) + 1;
                         end
                    end 
               
             end 
                
        end    
       output = value_count_ratio(:, 1);
       
       if strict_count == "yes" || strict_count == "y"
           output_sum = sum(output);
           unknown_data = output_sum - matrix_size;
           if unknown_data > 0
               strict_flag = 1;
           elseif unknown_data < 0
               strict_flag = 1;  
           elseif unknown_data == 0
               
                success_flag = 1;  
           end 
       end     
           
       
    else 
     
         disp('==============================================================')
        disp('ERROR: THERE IS NO DATA IN THE MATRIX TO COUNT')
        disp('==============================================================')
        error_flag = 1;
    end 

else
    disp('==============================================================')
    disp('ERROR: THE PARAMETER STRICT_COUNT IS NOT A STRING - ENTER  YES or Y')
    disp('==============================================================')
    error_flag = 1;
end 
%COMMAND LINE DISPLAY
if commandline_disp == 'yes'
    disp("=============================STATUS=================================")
    disp("VALUE TYPES TO COUNT = ")
    disp(value_type)
    disp("MATRIX COUNTER OUTPUT = ")
    disp(output')
    disp("ERROR FLAG = ")
    disp(error_flag)
    if strict_count == "yes"
    disp("=============================ATTENTION: STRICT COUNT!=================================")
    disp("STRICT COUNT FLAG = ")
    disp(strict_flag)
    disp("UNKNOWN DATA = ")
    disp(abs(unknown_data))
    disp("=====================================================================================")
    end
    disp("SUCCESS FLAG = ")
    disp(success_flag)
end 

%Send STATUS OUTPUTS TO ORICLE ADMIN MODULE
end 