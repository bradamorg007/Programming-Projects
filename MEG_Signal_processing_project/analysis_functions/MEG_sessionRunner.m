
function [] = MEG_sessionRunner()

    % session Runner controls the the order in which MEG analysis functions
    % are called.
    restoredefaultpath
    addpath /Users/bradamorg/Documents/fieldtrip-20181003

    session = [];
    session.paths.rawData = '/Users/bradamorg/Documents/secondaryProject/rawData/p1b1.fif';
    session.paths.sessionFolderPath = '/Users/bradamorg/Documents/secondaryProject/sessions/';
    session.paths.sessionName = 'test_particpant1';
    session.paths.sessionFileName = 'sessionP1B1';
    session.PCfg = getParameterConfigScript();
    %session.paths.loadSession = '/Users/bradamorg/Documents/secondaryProject/sessions/test_particpant1/sessionP1B1.mat';
    
    session.label = 'Main Data Source';
    session = MEG_sessionInit(session);
    session = MEG_preProcessing(session);
    session = MEG_outlierRejection(session);
    session = MEG_ICACalculation(session);
    session = MEG_ICARejectComponents(session);
    
    % seperate trials by conditions - leave session to represent all data
    % same direction and different direction
    sessionSameDiff = MEG_filterDataByEvents(session, {'[same direction]', '[different direction]'});
    sessionSameDiff.label = 'same vs differnt';
    
    % [65 72Hz] and [72 65Hz]
    sessionFreqTags = MEG_filterDataByEvents(session, {'[65 72Hz]', '[72 65Hz]'});
    sessionFreqTags.label = '65 72Hz vs 72 65Hz';
    
    % time Freq analysis all the trials cumulative
    session = MEG_timeFreqAnalysis(session, true);
    
    %time freq analysis for the trials seperated by conditions
    sessionSameDiff = MEG_timeFreqAnalysis(sessionSameDiff, false);
    sessionFreqTags = MEG_timeFreqAnalysis(sessionFreqTags, false);
    
    % add the condition session to the main session as attachments
    %session.attachments = {sessionSameDiff, sessionFreqTags};
    
    % change the filename of branch sessions uniquely saved
    sessionSameDiff.paths.sessionFileName = 'sessionP1B1_SameDifferent';
    sessionFreqTags.paths.sessionFileName = 'sessionP1B1_FreqTags';
    
    % save main session
    MEG_sessionEnd(session);
    MEG_sessionEnd(sessionSameDiff);
    MEG_sessionEnd(sessionFreqTags);
    
      
% %      MEG_sessionEnd(session);
%     MEG_multiplotTFR(session, 1);
% 
% %    p1Block1 = session;

 end 

    