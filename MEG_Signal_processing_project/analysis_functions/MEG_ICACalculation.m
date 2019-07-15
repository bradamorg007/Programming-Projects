
function [session] = MEG_ICACalculation(session)

% Down sample Data to 300Hz
cfg=[]; 
cfg.resamplefs = 300;   
data_all_resamp = ft_resampledata(cfg, session.data.outliers);

% Perform ICA. ICA will selft terminate after 100steps
cfg = [];  
cfg.method = 'runica';  
cfg.runica.maxsteps = 100;  
comp = ft_componentanalysis(cfg,data_all_resamp);   

ICAData.comp = comp;
ICAData.data_all_resamp = data_all_resamp;

session.data.ICACalculated = ICAData ;
end 