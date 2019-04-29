%Creates Appriopriate Probe data based on whether it will be correct or not
%which is 1 or 0
function [ProbeDataCell] = HighLevelProbeGeni(StimulusData, ProbeSequence, SettingsTable)

ProbeDataCell = cell(1, length(ProbeSequence));
DataType = SettingsTable{1, 4};
NumberRange = SettingsTable{1, 5};

for i = 1:length(StimulusData)
    
    StimData_Scope = StimulusData{1, i};
    [rows, cols] = size(StimData_Scope);
    ProbeData_Scope = ProbeSequence{1, i};
    ProbeDataMatrix = strings(rows, 1);
    
    for j = 1:rows
       
        StimMatrix_scope = StimData_Scope(j, :);
        ProbeMatrix_scope = ProbeData_Scope(j);
        
            switch ProbeMatrix_scope
            
                case 1
                    %Generate Correct Probe Data
                    A = length(StimMatrix_scope);
                    B = randi([1, A], 1, 1);
                    C = StimMatrix_scope(1, B);
               
                    ProbeDataMatrix(j) = C;
           
                case 0 
                  %Calls low level Helper Function for incorrect probe data
                  ProbeDataMatrix(j) = IncorrectProbeDataGeni(StimMatrix_scope, DataType, NumberRange); 
            end          
     end  
   ProbeDataCell{1, i} =  ProbeDataMatrix;
end 
end 
