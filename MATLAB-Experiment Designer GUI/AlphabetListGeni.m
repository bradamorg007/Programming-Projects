
%This function builds a key map between alphabet and their numerical letter
%positions when represented as a number line. this function can take in
%numbers and output corrisponding letters or Visa versa

function [OutputVector] = AlphabetListGeni(SampleVector, Output_Type)

Output_Type = lower(Output_Type);
        alpha_rows = 1;
        alphabet_list = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        [L, W] = size(alphabet_list);
        alpha_size = L * W;
        alphabet_values = zeros(alpha_rows, length(alphabet_list));
        for a = 1:length(alphabet_list)
        alphabet_values(a) = a;   
        end 
        alphabetObj = containers.Map(alphabet_list,alphabet_values);
        AlphaKeySet = keys(alphabetObj);
        
        LenSampVect = length(SampleVector);
        
        
        
        switch Output_Type
        
            case 'letters'  
               
                Vector = zeros(1, LenSampVect);
                for i = 1:LenSampVect
                    A = SampleVector(1, i);
                    B = AlphaKeySet{A};
                    Vector(i) = B;    
                end
                
                OutputVector = sort(char(Vector));
                
            case 'numerical'
                SampleVector = lower(SampleVector);
                Vector = zeros(1, LenSampVect);
                for j = 1:LenSampVect
                    A = SampleVector{1, j};
                    B = alphabetObj(A);
                    Vector(j) = B;  
                end
                
                OutputVector = sort(Vector);
        end 
end 