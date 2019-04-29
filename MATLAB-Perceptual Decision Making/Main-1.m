
%Should The CRITERION BOUNDIRES BE +1 or -1 OR CAN THEY BE ANYTHING EITHER
%POS OR NEG
%% Task 2 part 1 - Generate Data Log Likehood Ratios

% Uses the first value from vector or matrix when it is held constant 
try
sigma = [2 4 8 16];
priors = [0.5, 0.5; 0.7 0.3;0.8 0.2;0.9 0.1];
priors1 = priors(:, 1);
priors2 = priors(:, 2);
priors1 = priors1';
priors2 = priors2';
DataSplit = 100; %percentage 
sample = 300;
State = [-1 1];
a = 0;


Qstr = input('Do You want to change a variable?', 's');

if strcmpi(Qstr, 'show me') || strcmpi(Qstr, 'show') || strcmpi(Qstr, 'show vars') || strcmpi(Qstr, 'show settings') || strcmpi(Qstr, 's')
    DataTable = table(sigma, priors1, priors2, DataSplit, sample, State)   
    Qstr = input('Do You want to change a variable?', 's');
end 
if strcmpi(Qstr, 'yes') || strcmpi(Qstr, 'y')
    done = 0;
    while done == 0
        Qstr2 = input('Change variable: ', 's');
        if strcmpi(Qstr2, 'done') || strcmpi(Qstr2, 'd')
            done = 1;
        elseif strcmpi(Qstr2, 'show me') || strcmpi(Qstr2, 'show') || strcmpi(Qstr2, 'show vars') || strcmpi(Qstr2, 'show settings') || strcmpi(Qstr2, 's')
          DataTable = table(sigma, priors1, priors2, DataSplit, sample, State)
        else 
          eval(Qstr2);
        end 
    end
elseif strcmpi(Qstr, 'No') || strcmpi(Qstr, 'n')
else 
    error('Exception');
end 

disp('FINAL SETTING CHANGES:')
DataTable = table(sigma, priors1, priors2, DataSplit, sample, State)

QQ = questdlg('Which variable would you like to hold constant', 'Condition Contriol', 'Sigma', 'Priors', 'None', 'Cancel');
if strcmp(QQ, "Priors")
    LoopLen = length(sigma);
    disp('Priors have been held constant at...')
    priors(:, 1) = priors(1, 1);
    priors(:, 2) = priors(1, 2)
elseif strcmp(QQ, "Sigma")
    [LoopLen, ~] = size(priors);
    sig = sigma(1);
    sigma = zeros(1, LoopLen);
    disp('Sigma has been held Constant at...')
    sigma(:) = sig
elseif strcmpi(QQ, "None")
else 
    error('Exception')
end 
    
Data_store = cell(1, LoopLen);
liklihood_store = cell(1, LoopLen);
Log_LKH_store = cell(1,  LoopLen);
Sum_LogLKH_store = cell(1, LoopLen);
FullOutput_store = cell(1, LoopLen);
Barrier_store = cell(1, LoopLen)


for p = 1:LoopLen
[Data, liklihood, Log_LKH, Sum_LogLKH, FullOutput, Barrier, a]= Log_LKH_Ratios(a,State, sample, sigma(p), priors(p, :), DataSplit); 
Data_store{p} = Data;
liklihood_store{p} = liklihood;
Log_LKH_store{p} = Log_LKH;
Sum_LogLKH_store{p} = Sum_LogLKH;
FullOutput_store{p} = FullOutput;
Barrier_store{p} = Barrier;


end
 disp('===================================================================')
 disp('Processing Complete...')
 disp('===================================================================')
plotvalue = {'Histogram', 'Plot', 'Histogram', 'Plot'};
infotable = table({'Histogram'}, {'Plot'}, {'Histogram'}, {'Plot'});
infotable.Properties.VariableNames = {'Data_store', 'FullOutput_store', 'Log_LKH_store', 'Sum_LogLKH_store'}
disp('Availble Graph functions: overlayplot & graphplot [ Args: plottype, data, title, xlabel, ylabel, legend])')
catch Exception
    disp('Invalid Entry: System will reset')
    clear all
    close all
    run Main
end 
%% ========================================================================
%                               FUNCTIONS
%  ========================================================================

function [Data, LKH, Log_LKH_store, Sum_Log_LKH_store,Log_FullOutput_store,Barrier_store, a] = Log_LKH_Ratios(a,S, sample, sigma, priors, DataSplit)

Q = questdlg('Should The System Use Priors?', 'Yes', 'No');
BarrierS1 = 5;
BarrierS2 = -5;
Barrier_store = zeros(sample, 1);
%Data split convert from percentage to number split of samples;
DataSplit = sample * (DataSplit / 100);
Data = zeros(sample, length(sigma));
LKH = zeros(sample, length(S));
Log_LKH_store = zeros(sample,1);
Sum_Log_LKH_store = zeros(sample,1);
Log_FullOutput_store = zeros(sample, 1);
Previos_Log = 0;

for e = 1:length(sigma)
    for i = 1:sample
    if i > DataSplit
    Data(i, e) = normrnd(S(1), sigma(e)); 
    elseif i <= DataSplit
    Data(i, e) = normrnd(S(2), sigma(e));  
    end 
    [liklihood, Log_LKH, Sum_LogLKH,Log_FullOutput] = gauss(Data(i, e),Previos_Log, S, sigma(e), priors, Q); 
   if Log_FullOutput >= BarrierS1 || Log_FullOutput <=BarrierS2
       a = a + 1;
       if a == 1
       Barrier_store(i) = Log_FullOutput;
       else 
       Barrier_store(i) = [];
       end 
   else 
       a = 0;
   end 
    LKH(i, :) = liklihood;
    Log_LKH_store(i) = Log_LKH;
    Sum_Log_LKH_store(i) = Sum_LogLKH;
    Log_FullOutput_store(i) = Log_FullOutput;
    Previos_Log = Sum_Log_LKH_store(i);
    
    %Previos_Log = Previos_Log + Sum_Log_LKH_store(i);
    end 
end 


end 


function [liklihood, Log_LKH, Sum_LogLKH,Log_FullOutput] = gauss(x,prevx,sj, s, priors, Q)

previous = 0;
for c = 1:length(sj)
    p1 = -.5 * ((x - sj(c))/s) .^ 2;
    p2 = (s * sqrt(2*pi));
    liklihood(c) = exp(p1) ./ p2; 
end 
switch Q
    case "Yes"
        log_prior = log(priors(1)/ priors(2));
        Log_LKH = log(liklihood(1)/liklihood(2));
        Sum_LogLKH = prevx + Log_LKH;
        Log_FullOutput = (prevx +(log(liklihood(1)/liklihood(2)))) + (log(priors(1)/ priors(2)));
        
    case "No"
         Log_LKH = log(liklihood(1)/liklihood(2));
         Sum_LogLKH = prevx + Log_LKH;
         Log_FullOutput = 0;
end      
end 

