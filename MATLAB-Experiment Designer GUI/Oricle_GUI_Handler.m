
%TO DO LIST:
% ADD USE THE Check Function Inputs with validateattributes for validating 
%inputs this is quicker than the function I created, Convert each add
%paramter to a number in the brackets.


%ORICLE FRONT END HANDLING

function [ExperimentSettingsTable] = Oricle_GUI_Handler(varargin)
%==========================DEFUALT PARAMTERS===============================
%memory load defaults 
defaultMemoryLoad = "5";
defaultItemsPerMem = "1 2 3 4 5";
defaultTrialsPerMem = "30 30 30 30 30";

%Data type Defaults 

defaultDataType = "Integers";

%Data Configuration Setting Defualts

defaultNumberRange = '0 9';
defaultLetterRange = 'a z';
defaultMixedRatioSplit = '15 15';
defaultMixedRandom = 'yes';

%Probe setting defaults
defaultPercentage = '50';
defaultProbeRandom = 'yes';
defaultCapitalise = 'yes';

%==========================================================================
%===============================INPUT PARSER===============================
%input parser allows for varying numbers of function inputs
 p = inputParser;
 %memory load passed params
 addParameter(p,'MemoryLoad',defaultMemoryLoad);
 addParameter(p,'ItemsPerMem',defaultItemsPerMem);
 addParameter(p,'TrialsPerMem',defaultTrialsPerMem);
 
  
 %data type 
 addParameter(p,'DataType',defaultDataType);
 
 %Data Configuration Setting passed params
 addParameter(p,'NumberRange',defaultNumberRange);
 addParameter(p,'LetterRange',defaultLetterRange);
 addParameter(p,'MixedRatioSplit',defaultMixedRatioSplit);
 addParameter(p,'MixedRandom',defaultMixedRandom);
   
%Probe setting passed params
 addParameter(p,'Percentage',defaultPercentage);
 addParameter(p,'ProbeRandom',defaultProbeRandom);
 addParameter(p,'Capitalise', defaultCapitalise);
 
 parse(p,varargin{:});
 
 %=========================================================================
 %                   Define & Check Variable Inputs
 %=========================================================================
 % Main Memory Load Variables Define and Check 1 & 2
 MemoryLoad = oricle_input_check1(p.Results.MemoryLoad, 'y', 'mixed=n');
 ItemsPerMem = oricle_input_check1(p.Results.ItemsPerMem, 'y', 'mixed=n');
 TrialsPerMem = oricle_input_check1(p.Results.TrialsPerMem, 'y', 'mixed=n');
 DataType = convertCharsToStrings(p.Results.DataType);
 oricle_input_check2('x',MemoryLoad, 'y',ItemsPerMem,'z',TrialsPerMem);
 
 %Number Range Define and initial Check
 NumberRange = oricle_input_check1(p.Results.NumberRange,'n', 'mixed=n');
 %Letters Range Define and inital Check
 LetterRange = strsplit(oricle_input_check5_RemoveWhiteSpace(p.Results.LetterRange));
 LetterRangeNumericForm = AlphabetListGeni(LetterRange,"numerical");
 oricle_input_check3(LetterRangeNumericForm, ItemsPerMem,"yes repeat check");
 %Mixed Range Define and initial Check
 MixedRatioSplit = oricle_input_check1(p.Results.MixedRatioSplit, 'y','mixed=y');
 MixedRandom = convertCharsToStrings(p.Results.MixedRandom);
 %percentage Range Define and Initial Check
 Percentage = oricle_input_check1(p.Results.Percentage, 'n', 'mixed=n');
 ProbeRandom = convertCharsToStrings(p.Results.ProbeRandom);
 Capitalise = p.Results.Capitalise;
 
 %Additional Checks That only need to be performed depending on the
 %DataType selected by user
switch DataType
    case 'Integers'
        oricle_input_check3(NumberRange, ItemsPerMem, "yes repeat check");
        
    case 'Letters'
        oricle_input_check3(LetterRangeNumericForm, ItemsPerMem, "yes repeat check");
    case 'Mixed'
        oricle_input_check3(NumberRange, ItemsPerMem, "yes repeat check");
        oricle_input_check3(LetterRangeNumericForm, ItemsPerMem, "yes repeat check");
        oricle_input_check4(ItemsPerMem,MixedRatioSplit);
end 
 
 oricle_input_check2('x',Percentage, 'y',TrialsPerMem);
%Check Variables
 
 
%User Defines A directory to save Data to
[filename,path] = uiputfile('StimulusData.mat','Save Workspace As');
SavePath = convertCharsToStrings(strcat(path, filename));

if isequal(filename, 0) == 1 || isequal(path, 0) == 1
    msg = 'ERROR: Please Specify a location to save the Stimulus and Probe Data';
    msgbox(msg);
    error(msg);
end 
%This contains all the spesfications to generate users required dataset
ExperimentSettingsTable = table(MemoryLoad,ItemsPerMem,TrialsPerMem,DataType,NumberRange, LetterRangeNumericForm ,MixedRatioSplit,MixedRandom,Percentage,ProbeRandom,SavePath);

%Stimulus Data generator returns Cell Array, one for each memory load
StimulusData = TestDataGeni(ExperimentSettingsTable);
%Defines the sequence of probe Match to Not Matched, returns vector
ProbeSequence = ProbeSequenceGeni(ExperimentSettingsTable);
%Produces the probe data to be displayed relative to its state detemined by
%the probe sequence vector and its display determined by what stimulus
%element it will match or not match
ProbeData = HighLevelProbeGeni(StimulusData, ProbeSequence, ExperimentSettingsTable);

%Check Cell Sizes Are all equal before saving

StimulusDataSize = size(StimulusData);
ProbeSequenceSize = size(ProbeSequence);
ProbeDataSize = size(ProbeData);

if isequal(StimulusDataSize,ProbeSequenceSize,ProbeDataSize) == 0
    msg = 'FATAL ERROR: The System Has Generated Stimulus & Probe Data with Non-Matching Sizes. Inspect the Oricle_GUI_Handler Function - Line 110';
    msgbox(msg);
    error(msg);
else
    ExperimentSettingsTable{1, 12} = StimulusDataSize;
    ExperimentSettingsTable.Properties.VariableNames(1, 12) = {'GlobalDataSize'}; 
end 


save(SavePath, 'StimulusData', 'ProbeData', 'ProbeSequence', 'ExperimentSettingsTable');

%save the Test data and probe sequence data to a folder to be used by later
%function 

end 