%Function Converts String Data to Ints
%Checks for non_numeric data enetered in numeric only input fields
%Checks for negtive ints if input field disallows it
%Rounds any decimal values entered

function [x_new] = oricle_input_check1(x, isposrange, isMixedRange)
if isempty(isposrange) == 1
    isposrange = 'n';
end 
%error Message store 

Error_store = {'ERROR: You have Entered non-numeric data into a user field that only inputs numeric data',...
               'ERROR: Items per Memory Load or Trials Per Memory load are not Equal to the Number of Memory loads',...
               'ERROR: Size of Items per memory load vector is not equal to the size of Trial per memory load vector',...
               'ERROR: A Entry from the Memory Load Subsection Contains an element that is equal to or less than zero',...
               'ERROR: Function could not provide an output due to the input data being invalid'};

% %Delete undected white Spaces 
x = oricle_input_check5_RemoveWhiteSpace(x);

%Convert text field string parameters to numbers;
x = strsplit(x);
x = str2double(x);
% Error CONTROL check 1: Numeric Check memory load
checker1 = isequal(x, (0.0000 + 1.0000i));
numeric_check1 = sum(isnan(x));

%ADD RESTART SETTING FUNCTION
if numeric_check1 == 1 || checker1 == 1
    msgbox(Error_store{1,1})
    error(Error_store{1,1});
    error_numeric = 1;
end 

%Round Decimal Entries warner usr values have been rounded
xdec = x;
x = round(x);

if isequal(x, xdec) == 0
    msgbox('WARNING: Decimal Values entered, Results have been rounded to the nearest Integer')
end 
    
%Check to see if the entry is a number_range if true, do nt do negative
%check 
%Check for negative or zeros values
if strcmp(isposrange, 'y') == 1
    for i = 1:length(x)
        if strcmpi(isMixedRange, 'mixed=y')
            if x(i) < 0
             msgbox(Error_store{1,4});
             error(Error_store{1,4});                
            end
        else
            if x(i) <= 0
             msgbox(Error_store{1,4});
             error(Error_store{1,4});    
            end
        end 
    end 
end 
x_new = x;
end 


