
function session = MEG_preProcessingOLD(session)

cfg.dataset = session.paths.rawData;
cfg.trialfun = 'ft_trialfun_general';
cfg.trialdef.eventtype = 'STI002';
cfg.trialdef.eventvalue = 5;
cfg.trialdef.prestim = 1;
cfg.trialdef.poststim = 4;
cfg = ft_definetrial(cfg);

cfg.bsfilter = 'yes';  
cfg.bsfreq = [48 52];      % Apply a 50Hz notch filter  
cfg.lpfilter = 'yes';
cfg.lpfreq = 80; 
cfg.padding   = 8;          
cfg.padtype  = 'data'; 
cfg.channel = {'MEG'};  
cfg.detrend = 'yes';     % Remove slow drifts 
data_all  = ft_preprocessing(cfg);

session.data.preProcessed = data_all;

end 