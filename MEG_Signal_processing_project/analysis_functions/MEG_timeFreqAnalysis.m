
function [session] = MEG_timeFreqAnalysis(session, processAll)

if ~islogical(processAll)
    processAll = true;
    disp(" ");
    disp("*** WARNING ***")
    disp('ProcessAll argument in MEG_timeFrequencyAnalysis was not spesfied. System will default to processAll = true');
end 

if (isfield(session.data, 'filteredData'))
    filLens = length(session.data.filteredData);
    isEmptyData = zeros(1, filLens);
    deduct = 0;
    
    for (i = 1: filLens)
        if (isempty(session.data.filteredData{i}.filterIndexes))
            deduct = deduct + 1;
            isEmptyData(i) = true;
            disp("");
            disp("*** WARNING ***");
            disp(strcat("The filtered data: ", session.data.filteredData{i}.label, " is empty and will not be processed"));
        end
    end
    
    isDataNotEmpty = sum(isEmptyData) < length(isEmptyData);    
else
    disp(" ");
    disp("*** WARNING ***")
    disp('session.data.filteredData in MEG_timeFrequencyAnalysis was not spesfied. System will default to processAll = true using ICADataClean');
    processAll = true;
    isDataNotEmpty = false;
    
end

if (isDataNotEmpty == true && processAll == false)
    disp(" ")
    disp("====================================================================")
    disp(" ******* MEG_TIMEFREQANALYSIS ACTIVE *******")
    disp(" ")
    disp(strcat(" *** TOTAL BATCHES FOR MEG_TFA = ",string(filLens-deduct)));
    disp(" ");
    for(i = 1: length(isEmptyData))
        
        if (isEmptyData(i) == false)
            cfg = [];
            cfg.output = 'pow';
            cfg.channel= 'MEGGRAD';
            cfg.taper  = 'hanning';
            cfg.method = 'mtmconvol';
            cfg.foi    = 1:1:100; % freq of interest
            numfoi     = length(cfg.foi);
            cfg.t_ftimwin = ones(length(cfg.foi), 1) .* 1;
            cfg.toi = [-0.5:0.05:3.5];
            cfg.keeptrials = 'no';
            timeFreqAnalysisData = ft_freqanalysis(cfg, session.data.filteredData{i}.data);
            session.data.filteredData{i}.timeFreqAnalysisData = timeFreqAnalysisData;
            
            % combine power of the planer gradiometers for each sensor
            
            cfg = [];
            cfg.method = 'sum';
            timeFreqAnalysisDataCombined = ft_combineplanar(cfg, session.data.filteredData{i}.timeFreqAnalysisData);
            session.data.filteredData{i}.timeFreqAnalysisDataCombined = timeFreqAnalysisDataCombined;
            disp(" ")
            disp(strcat("*** MEG_TFA BATCH ", string(i-deduct), " of ", string(filLens-deduct), " SUCCESSFULLY PROCESSED ***"))
            disp(" ");
        end   
        

    end
    
    disp(" ")
    disp("====================================================================")
    
else
    
    proceed = true;
    if (isDataNotEmpty == false)
        disp(" ");
        disp("*** WARNING ***");
        disp(" All variations of filtered data is empty. Using session.data.ICADataClean as source data for MEG_timeFreqAnalysis");
        disp(" ");
        q1 = input('Do you wish to proceed? Y/N: ', 's');
        if strcmpi(q1, 'N') || strcmpi(q1, 'N')
            proceed = false;
        elseif strcmpi(q1, 'Y') || strcmpi(q1, 'yes')
            proceed = true;
        else
            proceed = false;
        end
    end
        disp(" ")
        disp("====================================================================")
        disp(" ******* MEG_TIMEFREQANALYSIS ACTIVE *******");
        disp(" ");
        disp(" MEG_TFA IS PROCESSING CUMULATIVE DATA: source = session.data.ICADataClean");
        disp(" ");
    
    if(isfield(session.data, 'ICADataClean') && proceed == true)
        cfg = [];
        cfg.output = 'pow';
        cfg.channel= 'MEGGRAD';
        cfg.taper  = 'hanning';
        cfg.method = 'mtmconvol';
        cfg.foi    = 1:1:100; % freq of interest
        numfoi     = length(cfg.foi);
        cfg.t_ftimwin = ones(length(cfg.foi), 1) .* 1;
        cfg.toi = [0:0.05:4];
        cfg.keeptrials = 'no';
        timeFreqAnalysisData = ft_freqanalysis(cfg, session.data.ICADataClean);
        session.data.timeFreqAnalysisData = timeFreqAnalysisData;
        
        % combine power of the planer gradiometers for each sensor
        
        cfg = [];
        cfg.method = 'sum';
        timeFreqAnalysisDataCombined = ft_combineplanar(cfg, session.data.timeFreqAnalysisData);
        
        session.data.timeFreqAnalysisDataCombined = timeFreqAnalysisDataCombined;
        
        disp(" ");
        disp(" *** MEG_TFA POCESSING SUCCESSFULLY COMPLETED");
        disp(" ");
        disp(" ");
        disp("====================================================================")
        
    else
        disp('*** ERROR ***')
        error('BRAD ISSUED ERROR: session.data.IcaDataClean could not be found')
    end
    
    
end










end 