
function [response, responseMade] = KeyPressResponse(value) 

            if isequal(value, 121) == 1
                response = 1;
                responseMade = 1;
            elseif isequal(value, 110) == 1
                response = 0;
                responseMade = 1;
            else
                response = 2;
                responseMade = 0;
            end             
end 

