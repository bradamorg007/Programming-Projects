
function [] = MEG_multiplotTFR(session, dataToPlot)

if isfield(session.data, 'timeFreqAnalysisDataCombined')
    
cfg = [];
cfg.baseline = [3.0, 4.0];
cfg.baselinetype = 'relative';
cfg.showlabels = 'no';
cfg.layout = 'neuromag306cmb.lay';
ft_multiplotTFR(cfg, session.data.timeFreqAnalysisDataCombined);

elseif isfield(session.data, 'filteredData')
    
    cfg = [];
    cfg.baseline = [3.0 4.0];
    cfg.baselinetype = 'relative';
    cfg.showlabels = 'no';
    cfg.layout = 'neuromag306cmb.lay';
    ft_multiplotTFR(cfg, session.data.filteredData{dataToPlot}.timeFreqAnalysisDataCombined);
    
else
    error();
end 

end 