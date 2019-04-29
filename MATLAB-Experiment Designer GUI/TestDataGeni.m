
% DATA GENERATER 
%· Lists with memory load N = 1, 2, 3, 4 or 5 digits (use ‘0’ to ‘9’ randomly) should be
% presented serially. About 1 s per item.
% · Present 30 trials per memory load. Total of 5 memory loads thus, 150
% trials per particpant in total. 
%The generated sequence can not repeat.
%Also Generate letters 

%simulate function parameters 

% %Error Check - The number_memory_loads must be equal to the length of the
% %memory load Vector
% number_memory_loads = 5;
% %digit count per memory load
% memory_load = [1 2 3 4 5]; 
% %trials per memory load 
% trials = [30, 30, 60, 30, 30]; 

function [Test_Data_Matrix] = TestDataGeni(SettingsTable)

%Unpack Variables from Settings Table 
MemoryLoad = SettingsTable{1, 1};
ItemsPerMem = SettingsTable{1, 2};
TrialsPerMem = SettingsTable{1, 3};
Reduced_SettingsTable = table(SettingsTable.ItemsPerMem, SettingsTable.DataType, SettingsTable.NumberRange, SettingsTable.LetterRangeNumericForm,...
                      SettingsTable.MixedRatioSplit, SettingsTable.MixedRandom);

load_settings = zeros(3, MemoryLoad);
[L,W] = size(load_settings);
memory_load_store = cell(1, W);

%==================INITIAL ERROR1 CHECK PITSTOP=====================================

%To be added

%======================GENERATE MATRIX SKELETONS FOR STORAGE===============
%   %The below forloop needs to be sent to Oricle 
for i = 1:MemoryLoad
    load_settings(1, i) = 1; %1 means an active memory load
    load_settings(2, i) = ItemsPerMem(i);
    load_settings(3, i) = TrialsPerMem(i);   
end 
  
%List setting reader and generator
%create a multi-dimensional array to store trail matrices 

for w = 1:W    
        reader1 = load_settings(L-1, w);
        reader2 = load_settings(L, w); 
        matrix_outline = zeros(reader2, reader1);
        memory_load_store{w} = matrix_outline; % CAN THIS BE PRE-ALLOCATED FOR SPEED
end 

%==================ERROR2 CHECK PITSTOP=====================================

%To be added
%==================POPULATE SKELETON MATRICES==============================

%Population options - random Intergers(range and be spesfied by user ), Random letters , Both


%Nested Function 

%stops every L and runs error checker function, that assess whether trails
%are the same or not

for c = 1:length(memory_load_store)
    %cell_scope = cell(1, 1);
    cell_scope = string(memory_load_store{1, c});
    [IML, IMW] = size(cell_scope); %IML = Imbedded Matrix Length or Width
    
        for n = 1:IML
            
         cell_scope(n, :) = RandomDataGeni(IMW, Reduced_SettingsTable, c); %letter_range, ratio, randomise); 
            
        end 
    
     memory_load_store{1, c} = cell_scope;
end 

Test_Data_Matrix = memory_load_store;
end 
%==================FINAL ERROR3 CHECK PITSTOP=====================================

%To be added


