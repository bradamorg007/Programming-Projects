
function Pcfg = getParameterConfigScript()

%==========================================================================
%                           SESSION PARAMETERS
%==========================================================================
%   get Param config setup controls the paramters values for all session
%   runner functions
%========================= Trial definition setup =========================
Pcfg1.dataset = session.paths.rawData;
Pcfg1.trialfun = 'ft_trialfun_general';
Pcfg1.trialdef.eventtype = 'STI002';
Pcfg1.trialdef.eventvalue = 5;
Pcfg1.trialdef.prestim = 1;
Pcfg1.trialdef.poststim = 4;

Pcfg1.bsfilter = 'yes';
Pcfg1.bsfreq = [48 52];      % Apply a 50Hz notch filter
Pcfg1.lpfilter = 'yes';
Pcfg1.lpfreq = 80;
Pcfg1.padding   = 8;
Pcfg1.padtype  = 'data';
Pcfg1.channel = {'MEG'};
Pcfg1.detrend = 'yes';

% ======================= Remove Outliers setup ===========================
%Grab planar gradiometers
Pcfg2.channel='MEGGRAD';

% data browser window
Pcfg2.viewmode='vertical';
Pcfg2.continuous='no';

% summary stats window for removing outliers
Pcfg2.method  = 'summary';
Pcfg2.layout  = 'neuromag306planar.lay';

% =======================      ICA setup        ===========================

% Down sample Data to 300Hz
Pcfg3.resamplefs = 300;   

% Perform ICA. ICA will selft terminate after 100steps  
Pcfg3.method = 'runica';  
Pcfg3.runica.maxsteps = 100;  

% ICA Reject components Data browser setup
Pcfg3.channel = [1:10]; 
Pcfg3.continuous='no';
Pcfg3.viewmode = 'component'; 
Pcfg3.layout = 'neuromag306planar.lay';

% =======================     Time FreqAnalysis setup    ==================

Pcfg4.output = 'pow';
Pcfg4.channel= 'MEGGRAD';
Pcfg4.taper  = 'hanning';
Pcfg4.method = 'mtmconvol';
Pcfg4.foi    = 1:1:100; % freq of interest
%numfoi     = length(cfg.foi);
Pcfg4.t_ftimwin = ones(length(cfg.foi), 1) .* 1;
Pcfg4.toi = [-0.5:0.05:4];
Pcfg4.keeptrials = 'no';

% =======================     MultiPlot  setup     ========================

Pcfg5.baseline = [3.0 4.0];
Pcfg5.baselinetype = 'relative';
Pcfg5.showlabels = 'no';
Pcfg5.layout = 'neuromag306cmb.lay';


Pcfg.preP = Pcfg1;
Pcfg.outL = Pcfg2;
Pcfg.ica = Pcfg3;
Pcfg.tfa = Pcfg4;
Pcfg.mplot = Pcfg5;

end 