% Creates the test data, depending on whetehr it will be ints letters or
% both. Idea is based on generating a numberline based on the users input
% letter(numerically convertable) & number ranges. The function then will
% randomly sample from the numberline with respect to the Items per memory
% load and trails per memory load. 
%uses Randperm function to avoid repeating vector sequences. but randperm
%does not support negative numbers, thus have a nested numberline generator
%that creates both neg and pos sample number lines.

function [trial_data] = RandomDataGeni(matrix_width, Reduced_SettingsTable, c) %number_range, letter_range, ratio, randomise

if ~exist('matrix_width', 'var')
    disp('ERROR: No Input MATRIX EXISTS')   
end  
ItemsPerMem = Reduced_SettingsTable{1,1};
data_type = Reduced_SettingsTable{1,2};
NumberRange = Reduced_SettingsTable{1,3};
LetterRangeNumericForm = Reduced_SettingsTable{1,4};
%======================DATA FLOW CONTROL===================================
switch data_type
    case 'Integers'
        trial_data = IntGeni(matrix_width, NumberRange);
    
    case 'Letters'
        trial_data = LetterGeni(matrix_width, LetterRangeNumericForm);

    case 'Mixed'
        MixedRatioSplit = Reduced_SettingsTable{1,5};
        oricle_input_check4(ItemsPerMem, MixedRatioSplit)
        MixedRandom = Reduced_SettingsTable{1,6};
    
          A = matrix_width;
          B = MixedRatioSplit(c);
          
          C = A - B;
          trial_data = MixedGeni(NumberRange,LetterRangeNumericForm,B,C,MixedRandom);

        
end 
        
%==========================================================================
%====================GENERATE TRIAL DATA: INTEGERS=========================
%create Correct Numberline to sample from users input range.

%================NUMBERLINE GENERATOR VARIABLES============================
function [numberline, differ] = NumberLineGeni(input_range)
try
    
range_min = input_range(1);
range_max = input_range(2); 
%difference betwen min and max
differ = abs(range_max - range_min);
numberline = zeros(1,differ);
%number line Loop
numberline(1) = range_min;
for n3 = 2:(differ + 1)
 numberline(n3) = numberline(n3 -1) + 1;                  
end 

catch NumberlineGeni
    
end 
end 

%========================RANDOM INTEGER GENERATOR===========================
%Samples randomly from a the spesified numberline. 

%%================RANDOM INTEGER GENERATOR VARIABLES============================
function [integer_vector] = IntGeni (matrix_width, NumberRange)

%returns number line to sampe from
[numberline, differ] = NumberLineGeni(NumberRange);
   
ranNumberlinePos = randperm(differ, matrix_width);
vector = zeros(1, matrix_width);
for r = 1:length(ranNumberlinePos)
    
    vector(r) = numberline(ranNumberlinePos(r));
    
end 

%double check no repeates occured
integer_vector = zeros(size(vector));
repeat_checker = unique(vector, 'stable');
equility_checker = isequal(vector, repeat_checker);

if equility_checker == 1
integer_vector = vector;
else 
  %Error repeated digits detected
   msgbox("Error Code 1.1: Repeated Digits Detected in Trial data Geni ")
  error("Error Code 1.1: Repeated Digits Detected in Trial data Geni ")

  %error_flag(?) = error_flag(?) + 1; ADD THIS IN LATER. 
  
end 

end 


%====================GENERATE TRIAL DATA: LETTERS=========================
%may add in where user can specify there own characters but for now it will
%be predefined 
function [letter_vector] = LetterGeni(matrix_width, LetterRangeNumericForm)%matrix_width, letter_range)

%Default paramters 
try
    
LetterRangeNumericForm = lower(LetterRangeNumericForm);

[~, differ] = NumberLineGeni(LetterRangeNumericForm);

ranNumberlinePos = randperm(differ, matrix_width);
 
 new_vector = strings(1, matrix_width);
 for r = 1:length(ranNumberlinePos)
    
     vector = AlphabetListGeni(ranNumberlinePos(r),'Letters');   
     new_vector(r) = vector;
 end 
     letter_vector = new_vector;
     
catch LetterExcep
    
    if strcmpi(LetterExcep.identifier, 'MATLAB:Containers:Map:NoKey') == 1
        msg = 'The specified key in "Letter Range " is not present in the systems character list.Please eneter an alphabetical character between A & Z';
        msgbox(msg)
        error(msg)
    end 
    
end 
end 



%=========GENERATE TRIAL DATA: MIX & MATCH: NUMBERS - LETTERS==============
%may add in where user can specify there own characters but for now it will
%be predefined 

function [mixed_vector] = MixedGeni( NumberRange,letterRange,B,C,MixedRandom)

% checker = isequal(sum(abs([B,C])), matrix_width);
% if checker == 0
%     disp("ERROR: Location - Mix and Match Function: Descrip - Dimension mismatch; matrix width and ratio")
% end 

int_list = IntGeni(B,NumberRange);
letter_list = LetterGeni(C,letterRange);
int_list = string(int_list);

for l = 1:length(int_list)
    letter_list(length(letter_list) + 1) = int_list(l);
end 

if strcmpi(MixedRandom,"yes" ) == 1
    randomisePos = letter_list(randperm(length(letter_list)));
    mixed_vector = randomisePos;
else 
    mixed_vector = letter_list; 
end 

% %Concatenate strings horizontally
% number_letter_vector = strcat(int_list, letter_list); 
end 



%Function end 
end 