function [Probe_Matrix] = ProbeSequenceGeni(SettingsTable)

TrialPerMem = SettingsTable{1,3};
percentage = SettingsTable{1, 9}; %The percentage represents the percentage of 1s to be generated
randomise = SettingsTable{1, 10};

%create cell storage

probe_store = cell(1, length(TrialPerMem));

decimaldetection = 0;

one_count = 0;
zero_count = 0;

%split_ratio = [15, 15];

for j = 1:length(TrialPerMem)
    trial = TrialPerMem(j);
    probability = percentage(j) / 100;
    
    Adec = trial * probability;
    
    A = round(trial * probability);
    B = round(trial - A);
    
    if isequal(Adec, A) == 0
        decimaldetection = 1;
    end 
    
    G = zeros(trial, 1);

for i = 1:trial
    
    if i <= A
        G(i) = 1;
        one_count = one_count + 1;    
    else 
        G(i) = 0;  
        zero_count = zero_count + 1;
    end 
         
end

if strcmpi(randomise, "Randomise") == 1
    matrix = G(randperm(length(G)));
    probe_store{1, j} = matrix;
else 
    probe_store{1, j} = G;
end 

end 
if decimaldetection == 1
  msgbox('Warning: Everything is Okay but the ratio of correct vs incorrect probes has been rounded to the nearest integer');
end 
Probe_Matrix = probe_store;
end 


%Check the Randperm Shuffled correctly 
%DataType_Counter(matrix, value_type, strict_count, commandline_disp)
