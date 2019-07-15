%% This MATLAB script is responsible for running the MEG experiment. 
%% The code ensures synchronous time accurate communication with the MEG device via binary time locked trigger signals. 
%% The code also ensures sinusoidal time locked modulation of pixel luminance values for stimuli. 
%% This facilitates frequency tagged electrophysiological oscillations in the human visual cortex in response to 
%% the presentation of conditional stimuli.


%% ======================= NOTES ==========================================

% TURN ON SCRIPT AND LEAVE TO WAIT
% PRESS GO ON THE MEG AQUISITION SOFTWARE
% PRESS RECORD WRAW CHECK BOX - C TO CONTINUE ON EXPERqIMENT.
% STOP AND SAVE AT THE END OF EACH BLOCK
% REPEAT FOR ALL BLOCKS

%If the program doesnt handle user responses and times or accuracy. what if
%a particpant fails to make a response in the given response window but
%instead makes a response after/at the start of a new trial. is their no
%way to automatically handle this in one program? or will have to analyse
%the data after to determine this?q

% Freq should flip 50% of trials per block. But the order in which they
% flip needs to be randomised
%make baseline grey aswell by freq tagging baseline and response period

% make fixation cross flicker from black to white quickly
% EUG eye tracking

% 20% of trials subject responds and is asked whether they are same or
% different. 

% arrow symbols change need to match the direction of the stimuli. 
% particpant responds whilst thje stimuli is moving. 
% Make the stimuli move up together down together. stim 1 up stim 2 down,,
% stim 1 down, stim 2 up.

%encode the motion direction of stimuli

% ADD DETERMINE WHAT THE CORRECT RESPONSE SHOULD BE FOR PARTICPANTS SO THAT
% IS CAN BE COMPARED LATER EASILY !!!

%% ===================== SCREEN & STIMULI PARAMTERS =======================
clear all;
close all;
sca;
fclose('all');
Screen('Preference', 'SkipSyncTests', 0);
AssertOpenGL;
KbName('UnifyKeyNames');

% To change size of circle apeture leave rectApeture and modulate circle apeture size

Freqs = [65, 72];
spatialFreq = 0.0228; %default 1/160

rectApetureSize = [200, 200];  %300
circleApetureSize = rectApetureSize(1)*0.39; %Def=0.25 Set to 0 if you want a rectangle apeture instead;

Xaxis = 80; % The x y locations of the stimuli on the screen
Yaxis = 80;

gratingBackgroundColor = [0.7, 0.7, 0.7, 0];
shapeAngle = [90 270; 90, 90]; %[270, 90; 90 90] [90 269.4; 90, 89.6] Condition 1 (shape 1, shape 2) then condition 2 (shape 1, shape2shapeAngle) 215 degrees to move in opposite directions
RotateInternalSine = 1;
phaseCyclesPerSec = 1;
amplitude = 1; % Contrast
%% ========================================================================

%% ======================== EXPERIMENT PARAMTERS ======================= %%

% --Debug Paramters--------------------------------------------------------
showFreqPlot = 1;
drawStimToCenter =0;
debugMode = []; %[] = debug Mode OFF - [1 1 200 200] debug mode ON with screen size set
DataPixxActive = 1; %datapixx is active and 0 is OFF;
showPadFrame = 1; %Show the flickering squares for measuring frequency ossilations
%--------------------------------------------------------------------------

% -- Blocks & Block Time length -------------------------------------------
% numblocks + 1 = adds additional practise block
numBlocks = 5;
trialBlockTimeLength =  300; % seconds
%--------------------------------------------------------------------------

% -- Percentage of Experiment Event occurances ----------------------------
practiceBlockSizePercentage = 20;
participantResponsePercentage = 40; %
freqTagChangePercentage = 50;
directChangePercentage = 50;
changeDirectionPercentage = 50;
ConditionChangePercentage = 50;
QuestionChangePercentage = 50;
%--------------------------------------------------------------------------

% -- Time Lengths of experiment events-------------------------------------
breakTimeLength = 30;            % seconds
practiseBreakTimeLength = 20;
% all 4 below are measured in seconds
fixationFlashTime = 1; 
responseCueFlickerTime = 0.3;

baselinePeriod = 1;  
stimulusPeriod = 5; %   DECREASE THIS TO 3 SECONDS
responseperiod = 1; 
%--------------------------------------------------------------------------

% -- Globally set Trigger codes--------------------------------------------
baselinePeriodCode = 1;
stimuliPeriodCode = 2;
responsePeriodCode = 4;
%breakperiodCode = 8;
%--------------------------------------------------------------------------

% SAME DIRECTION 90 DEGREES = 5;
% SAME DIRECTION 270 DEGREES = 6;
% OPPOSING DIRECTION stim1 90 - stim2 270 DEGREES = 7;
% OPPOSSING DIRECTION stim1 270 - stim2 90 DEGREES = 8;

% Freq 65 Hz Left or Stim 1 & 72 Hz right stim2 = 9
% Freq 72 Hz left or Stim 1 && 65 Hz right stim2 = 10


%% =========================== PREPROCESSING ================================== %%

% Seconds
practiceBlockTimeLength = round(trialBlockTimeLength*(practiceBlockSizePercentage/100));
totalTrialLength = sum([baselinePeriod, stimulusPeriod, responseperiod]); 

numTrialsPerBlock(1, 1:numBlocks-1) = round(trialBlockTimeLength/totalTrialLength);
practiceTrials = round(practiceBlockTimeLength/totalTrialLength);
practiceBlockSplit = [round(practiceTrials/2), round(practiceTrials/2)];

trialSequence = [practiceBlockSplit, numTrialsPerBlock];
blockTimeCheck = numTrialsPerBlock(1)*totalTrialLength;

% order of conditions goes grating move in same direction then  gratings move in
% different directions
v = 1;
for i = 1:length(trialSequence)
%     conditionSequence(1:2, i) = shapeAngle(:,v);
%     if isequal(v, 1)
%         v = 2;
%     elseif isequal(v, 2)
%         v = 1;
%     end 
    
    if ~(trialSequence(i) <= 1) 
        %calculate the percentage of trials that will change fixation cross
        numFixChangesPerBlock(i) = round(trialSequence(i) * (participantResponsePercentage/100));
        numFreqChangesPerBlock(i) = round(trialSequence(i) * (freqTagChangePercentage/100));
        numDirectChangesPerBlock(i) =round(trialSequence(i) * (directChangePercentage/100));
        numConditionChangesPerBlock(i) = round(trialSequence(i) * (ConditionChangePercentage/100));

        actualFixPercentage(i) = (numFixChangesPerBlock(i) / trialSequence(i))*100; 
        actualFreqPercentage(i) = (numFreqChangesPerBlock(i) / trialSequence(i))*100; 
        actualDirectPercentage(i) = (numDirectChangesPerBlock(i) / trialSequence(i))*100;
        actualConditionPercentage(i) = (numConditionChangesPerBlock(i)/ trialSequence(i)) * 100;
    else
        numFixChangesPerBlock(i) = 0;
        numFreqChangesPerBlock(i) = 0;
        numDirectChangesPerBlock(i) = 0;
        numConditionChangesPerBlock(i) = 0;
        
        actualFixPercentage(i) = 0;
        actualFreqPercentage(i) =0;
        actualDirectPercentage(i) = 0;
        actualConditionPercentage(i) = 0;
    end 
    
    iterationToChangeFix = sort(randperm(trialSequence(i), numFixChangesPerBlock(i)));
    iterationToChangeFreqs = sort(randperm(trialSequence(i), numFreqChangesPerBlock(i)));
    iterationToChangeDirection = sort(randperm(trialSequence(i), numDirectChangesPerBlock(i)));
    iterationToChangeCondition = sort(randperm(trialSequence(i), numConditionChangesPerBlock(i)));
    
    fixChangeSeq = zeros(1, trialSequence(i));
    freqChangeSeq = zeros(1, trialSequence(i));
    directChangeSeq = zeros(1, trialSequence(i));
    conditionChangeSeq = zeros(1, trialSequence(i));
    
    for j = 1:length(iterationToChangeFix)
        fixChangeSeq(iterationToChangeFix(j)) = 1;
    end 
    
    for j = 1: length(iterationToChangeFreqs)
        freqChangeSeq(iterationToChangeFreqs(j)) = 1;
    end 
    
    for j = 1: length(iterationToChangeDirection)
        directChangeSeq(iterationToChangeDirection(j)) = 1;
    end 
    
    for j = 1: length(iterationToChangeCondition)
        conditionChangeSeq(iterationToChangeCondition(j)) = 1;
    end 
     fixChangeSeqStore{i} = fixChangeSeq; 
     freqChangeSeqStore{i} = freqChangeSeq; 
     directChangeSeqStore{i} = directChangeSeq;
     conditionChangeSeqStore{i} = conditionChangeSeq;
end 

if RotateInternalSine
    rotationType = kPsychUseTextureMatrixForRotation;
else
    rotationType = [];
end 

if ~circleApetureSize
    circleApetureSize = [];
end 
    
screens = Screen('Screens');

if length(screens) > 1, kk = 1;else,kk = 0;end 

screenSelect = max(screens-kk);

white = WhiteIndex(screenSelect);
black = BlackIndex(screenSelect);
grey = white/2;

try
    [window, windowInfo]= Screen('OpenWindow', screenSelect, grey, debugMode);
    
    AssertGLSL;
    %Screen(window,'BlendFunction',GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    [xPixels, yPixels] = Screen('WindowSize', window);
    [xCenter, yCenter] = RectCenter(windowInfo);
    
    MeasuredInterFrameRate = Screen('GetFlipInterval', window);
    %max_trialframes = round(stimulusPeriod/MeasuredInterFrameRate);
    
    TrialTimePeriods = [baselinePeriod, stimulusPeriod, responseperiod];
    for k = 1:3
        max_trialframes(k) = round(TrialTimePeriods(k)/MeasuredInterFrameRate);
    end
    max_fixationFlashFrames = round(fixationFlashTime/MeasuredInterFrameRate);
    max_responseCueFlickerTime = round(responseCueFlickerTime/MeasuredInterFrameRate);
    %% Find Screen Sub-Region positions for stimuli
    
    baseRect = [0 0 rectApetureSize(1) rectApetureSize(2)];
    padFrameBaseRect = [0 0 (rectApetureSize(1)*0.2),  (rectApetureSize(2)*0.2)];

    if isequal(drawStimToCenter, 0)
        xPos=xPixels/4;
        yPos=yPixels/4;
        
        %topleft            %topright                %bottomleft                 %bottomright
        centers=[round([xPos yPos]);round([3*(xPos) yPos]); round([xPos 3*(yPos)]); round([3*(xPos) 3*(yPos)])];
        
        for q = 1:4
            subcenters{q}=[centers(q,1) - Xaxis,  centers(q,2)+Yaxis ; centers(q,1)+Xaxis centers(q,2)+ Yaxis];
        end
        
        % PADFRAME DEST RECT
  
         for q = 1:4
            padFrameSubcenters{q}=[centers(q,1) - Xaxis + 470 ,  centers(q,2)+-Yaxis - 130; centers(q,1)+Xaxis+2000 centers(q,2)+-Yaxis];
        end
%         
%         for q = 1:4
%             padFrameSubcenters{q}=[centers(q,1) * 0,  centers(q,2)*0 ; centers(q,1)* 0 centers(q,2)* 0];
%         end
        dstRects = cell(4, 2);
        padDstRects = cell(4, 2);
        drawQuadrantLimit = 4;
        
        for q = 1:drawQuadrantLimit
            for s = 1:2
                dstRects{q, s} = floor(CenterRectOnPoint(baseRect, subcenters{q}(s,1),subcenters{q}(s,2)));
            end
        end
        
        for q = 1:drawQuadrantLimit
            for s = 1:2
                padDstRects{q, s} = floor(CenterRectOnPoint(padFrameBaseRect, padFrameSubcenters{q}(s,1),padFrameSubcenters{q}(s,2)));
            end
        end
        
    elseif isequal(drawStimToCenter, 1)
        
        for q = 1:4
            subcenters{q}=[xCenter - Xaxis,  yCenter+Yaxis ; xCenter+Xaxis yCenter+Yaxis];
        end
        
        centers = [xCenter, yCenter];
        
        dstRects = cell(1, 2);
        drawQuadrantLimit = 1;
        
        for q = 1:drawQuadrantLimit
            for s = 1:2
                dstRects{q, s} = floor(CenterRectOnPoint(baseRect, subcenters{q}(s,1),subcenters{q}(s,2)));
            end
        end
        
    end
     
    %%
    padFrame = uint8(ones(40, 40) * 255);
    padFrame = Screen('MakeTexture', window, padFrame);
    
    %% Make Sinewave Textures 
    phaseStep = (phaseCyclesPerSec * 360) * MeasuredInterFrameRate;
    
    phase = 0 - phaseStep;
    
    phaseVals = NaN(1, max_trialframes(2));
    
    for c = 1:length(phaseVals)
        phase = phase + phaseStep;
        phaseVals(c) = phase;
    end
    
    phaseVals(2, :) = phaseVals(1, :);
    
    sinewave = CreateProceduralSineGrating(window, rectApetureSize(1), rectApetureSize(2), gratingBackgroundColor, circleApetureSize);
    
    %% Make Fixation cross
    
    FixSize = 15; %pixels
    fixCoords = [[-FixSize, FixSize, 0 0];[0 0 -FixSize, FixSize]];
    lineWidth = 4;
    fixColor = black;
    
    %% Define Freq Tagging Table - For RGB modulation
    
    frame_mult=12; %every refresh is 12 frames
    
    %Frequency timecourse parameters
    cfg.patch_amplitude = 0.5;
    cfg.patch_startPhase = 0;
    cfg.f_offset = 0;
    
    %Populate the frequency table
    for j = 1:length(max_trialframes)
        freqTable=NaN(length(Freqs),(max_trialframes(j)*frame_mult));
        frametime=NaN(1,frame_mult*max_trialframes(j));
        
        for f=1:length(Freqs)
            patch_frequency = Freqs(f);
            patch_angFreq = 2 * pi * patch_frequency;
            cur_time=0;
            frame_cnt=1;
            for t=1:max_trialframes(j)
                
                for i=1:frame_mult
                    freqTable(f,frame_cnt)= cfg.patch_amplitude * sin(patch_angFreq * (cur_time+((i-1)/frame_mult)*MeasuredInterFrameRate) + cfg.patch_startPhase) + cfg.patch_amplitude + cfg.f_offset;
                    frametime(frame_cnt)=cur_time+((i-1)/frame_mult)*MeasuredInterFrameRate;
                    frame_cnt=frame_cnt+1;
                    
                end
                cur_time=cur_time+MeasuredInterFrameRate;
            end
        end
        freqTableStore{j} = freqTable;
    end
    
    for w = 1: length(max_trialframes)
        for r = 1:length(Freqs)
            a = freqTableStore{w}(r, :);
            freqTableStore{w}(r, :) = abs(255.*a-4.403e-14);
        end
    end
    
    %% ===================== EXPERIMENTAL LOOP ============================
    %Setup Propixx 1440 Hz
    if isequal(DataPixxActive, 1)
        Datapixx('Open');
        Datapixx('SetPropixxDlpSequenceProgram', 5); % 2 for 480, 5 for 1440 Hz, 0 for normal
        Datapixx('RegWrRd');
    end
    
        keyTrig = 0;
        while isequal(keyTrig, 0)
            Screen('TextSize', window, 15);
            Screen('DrawText', window, double('Researcher Response Only: Press the c key to continue the experiment or the q key to quit'), centers(q, 1)-centers(q, 1)+5, centers(q, 2)-centers(q, 2)+5, black);
            Screen('Flip', window);
             [ keyIsDown, ~, keyCode ] = KbCheck;
             
             if keyIsDown && keyCode(KbName('c'))
                 keyTrig = 1;
             elseif keyIsDown && keyCode(KbName('q'))
                  if isequal(DataPixxActive, 1)
                      Datapixx('SetPropixxDlpSequenceProgram', 0);
                      Datapixx('RegWrRd');
                      Datapixx('close');
                  end
                 Priority(0);
                 sca;
             end
                
        end 
    
    %Init the MEG trigger pulses
    [outTrig, outAddr] = tag_init_trigger_send();
    
    topPriorityLevel = MaxPriority(window);
    Priority(topPriorityLevel);

    if ~isequal(blockTimeCheck,trialBlockTimeLength )
        
        disp("======================PRE-PROCESSING INFO=======================")
        warning('The total trial length in seconds is not evenly divisible by the overall trial block length')
        disp('THE NUMBER OF TRIALS PER BLOCK HAS BEEN ROUNDED TO THE NEAREST INTEGER')
        disp('-----------------------------------------------------------------')
        disp(strcat('ORIGINAL BLOCK TIME LENGTH = ',string(trialBlockTimeLength), ' Seconds'));
        disp(strcat('NEW BLOCK TIME LENGTH = ',string(blockTimeCheck), ' Seconds'));
        
    end
    disp('=====================================================================')
    disp('EXPERIMENT START: PRACTISE PHASE START')
     
    for m = 1:length(trialSequence)
        for q = 1:drawQuadrantLimit
            Screen('DrawLines', window, fixCoords, lineWidth, black, centers(q, :));
        end
        vbl = Screen('Flip', window); % Processing time Between switching between image frames
        WaitSecs(2);
        
        startBlock = tic;
        %------------------------------------------------------------------
        %                         TRIGGER 1
        %------------------------------------------------------------------
        
        for f = 1:trialSequence(m)
            %CHANGE THE LUMINACE FREQ TAG OF THE STIMS
            if isequal(freqChangeSeqStore{m}(1, f), 1)
                        for p = 1:length(max_trialframes)
                            luminanceVals{p} = [freqTableStore{p}(2,:); freqTableStore{p}(1,:)];
                            freqTagTriggerCode = 8;
                        end
                        disp(strcat('Freq Psoition Change = ', string(freqChangeSeqStore{m}(1, f))));
                        
                        disp('[72, 65]')
                        disp('Route 1')
                   
            elseif isequal(freqChangeSeqStore{m}(1, f), 0)                    
                            luminanceVals = freqTableStore;
                            freqTagTriggerCode = 0;
        
                        disp(strcat('Freq Psoition Change = ', string(freqChangeSeqStore{m}(1, f))));
                        disp('[65, 72]');
                        disp('Route 2');
                   
            end
            
            % CHANGE STIMULUS MOTION DIRECTION 
            phaseSet = phaseVals;
           conditionSequence = [90, 90];
                if isequal(conditionChangeSeqStore{m}(1, f), 0)
                    
                    if isequal(directChangeSeqStore{m}(1, f), 0)
                        %conditionSequence = [90, 90];
                        motionDirectionTriggerCode = 0;
                        phaseSet = phaseVals;
                        
                    elseif isequal(directChangeSeqStore{m}(1, f), 1)
                        %conditionSequence = [270, 270];
                        motionDirectionTriggerCode = 16;
                        phaseSet = -(phaseVals);
                    end 
                    
                elseif isequal(conditionChangeSeqStore{m}(1, f), 1) 
                    if isequal(directChangeSeqStore{m}(1, f), 0)
                        %conditionSequence = [270, 90];
                        motionDirectionTriggerCode = 16+ 32;
                        phaseSet(1, :) = -(phaseVals(1, :));
                        
                        
                    elseif isequal(directChangeSeqStore{m}(1, f), 1)
                        %conditionSequence = [90, 270];
                        motionDirectionTriggerCode = 32;
                        phaseSet(2, :) = -(phaseVals(2, :));
                    end   
                end 
                
            % Combine Triggers 
            
            startTrial = tic;
            if isequal(fixChangeSeqStore{m}(1, f), 1)
                isQuestionTrigCode = 64;
                
                % Decide what the respons e question will be
                if rand >=0.5
                    % CREATES THE QUESTION = DIFFERENT?
                    resTrig = 1;
                    questionTypeTriggerCode = 128;
                else
                    % CREATES THE QUESTION = SAME?
                    resTrig = 0;
                    questionTypeTriggerCode = 0;
                end
            
            else
               isQuestionTrigCode = 0; 
               questionTypeTriggerCode = 0;
            end 
            
            syncBaselineFrames = size(luminanceVals{1}, 2)/12;
            
             
            % TURN BASELINE TRIGGERS OhhhN
           tag_trigger_send(outTrig, outAddr, baselinePeriodCode + freqTagTriggerCode + motionDirectionTriggerCode + isQuestionTrigCode + questionTypeTriggerCode);
            
            for g = 1:syncBaselineFrames
                for q = 1:drawQuadrantLimit
                    for s = 1:2
                        Screen('DrawTexture', window, sinewave, [], dstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{1}(s,(((g-1)*12)+q)), luminanceVals{1}(s,(((g-1)*12)+q+4)), luminanceVals{1}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, 1), spatialFreq, amplitude, 0]);
                        if isequal(showPadFrame, 1)
                        Screen('DrawTexture', window, padFrame, [], padDstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{1}(s,(((g-1)*12)+q)), luminanceVals{1}(s,(((g-1)*12)+q+4)), luminanceVals{1}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, 1), spatialFreq, amplitude, 0]);
                        end 
                    end
                    Screen('DrawLines', window, fixCoords, lineWidth, fixColor, centers(q, :));
                end
                vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
                
                %TURN BASELINE TRIGGER OFF
                if isequal(floor(syncBaselineFrames * 0.5), g)
                    tag_trigger_send(outTrig, outAddr, 0);
                end 
            end
            
            % tag_trigger_send(outTrig, outAddr, 0);
            
            %--------------------------------------------------------------
            %                       TRIGGER 2
            %--------------------------------------------------------------
            syncStimulusFrames = size(luminanceVals{2}, 2)/12;
                        
            startStimIterationTime = tic;
            
            %TURN STIMULUS ONSET ON 
            tag_trigger_send(outTrig, outAddr, stimuliPeriodCode);
            
            for g = 1:syncStimulusFrames
                %phaseCount(j) = phase;
                for q = 1:drawQuadrantLimit
                    for s = 1:2
                        Screen('DrawTexture', window, sinewave, [], dstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{2}(s,(((g-1)*12)+q)), luminanceVals{2}(s,(((g-1)*12)+q+4)), luminanceVals{2}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, g), spatialFreq, amplitude, 0]);
                        if isequal(showPadFrame, 1)
                        Screen('DrawTexture', window, padFrame, [], padDstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{2}(s,(((g-1)*12)+q)), luminanceVals{2}(s,(((g-1)*12)+q+4)), luminanceVals{2}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, g), spatialFreq, amplitude, 0]);
                        end %Screen('DrawTexture', window, padFrame, [], dstRects{q, s}, conditionSequence(s, m), [], [], [freqTable(s,(((g-1)*12)+q)), freqTable(s,(((g-1)*12)+q+4)), freqTable(s,(((g-1)*12)+q+8))], [], rotationType, [phaseVals(g), spatialFreq, amplitude, 0]);
                    end
                    Screen('DrawLines', window, fixCoords, lineWidth, fixColor, centers(q, :));
                end
                
                %DrawRectFrame(windowPtr, rect, [color])
                vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
                
                if KbCheck
                    sca;
                    Priority(0);
                    return;
                end
                
                % TURN STIMULUS ONSET OFF
                 if isequal(floor(syncStimulusFrames * 1), g)
                    tag_trigger_send(outTrig, outAddr, 0);
                end 
            end
                
          
            endStimIterationTime = toc(startStimIterationTime);
            % tag_trigger_send(outTrig, outAddr, 0);
            
            %-------------------------------------------------------------
            %                       TRIGGER 3
            %-------------------------------------------------------------
            
            syncResponseFrames = size(luminanceVals{3}, 2)/12;
            
            %TURN RESPONSE TRIGGER ON
            
            tag_trigger_send(outTrig, outAddr, responsePeriodCode);
            
            for g = 1:syncResponseFrames
                
                for q = 1:drawQuadrantLimit
                    for s = 1:2
                        Screen('DrawTexture', window, sinewave, [], dstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{3}(s,(((g-1)*12)+q)), luminanceVals{3}(s,(((g-1)*12)+q+4)), luminanceVals{3}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, end), spatialFreq, amplitude, 0]);
                        if isequal(showPadFrame, 1)
                        Screen('DrawTexture', window, padFrame, [], padDstRects{q, s}, conditionSequence(s), [], [], [luminanceVals{3}(s,(((g-1)*12)+q)), luminanceVals{3}(s,(((g-1)*12)+q+4)), luminanceVals{3}(s,(((g-1)*12)+q+8))], [], rotationType, [phaseSet(s, end), spatialFreq, amplitude, 0]);
                        end 
                    end
                    if isequal(fixChangeSeqStore{m}(1, f), 1)
                            
                        if g <= max_fixationFlashFrames
                            
                            if isequal(resTrig, 0)
                                responseText = 'SAME?';
                                nudge = 30;
                            elseif isequal(resTrig, 1)
                                responseText = 'DIFFERENT?';
                                nudge = 45;
                            end 
                            
                            if isequal(motionDirectionTriggerCode, 0) || isequal(motionDirectionTriggerCode, 32) && strcmpi(responseText, 'SAME?')
                                correctResponseStore{m}(1, f) = 1;
                                
                            elseif isequal(motionDirectionTriggerCode, 0) || isequal(motionDirectionTriggerCode, 32) && strcmpi(responseText, 'DIFFERENT?')
                                correctResponseStore{m}(1, f) = 0;
                                
                            elseif isequal(motionDirectionTriggerCode, 64+32) || isequal(motionDirectionTriggerCode, 64) && strcmpi(responseText, 'SAME?')
                                correctResponseStore{m}(1, f) = 0;
                                
                            elseif isequal(motionDirectionTriggerCode, 64+32) || isequal(motionDirectionTriggerCode, 64) && strcmpi(responseText, 'DIFFERENT?')
                                correctResponseStore{m}(1, f) = 1;
                            end
                            
                            Screen('TextSize', window, 15);
                            Screen('DrawText', window, responseText, centers(q, 1)-nudge, centers(q, 2), black);
                        else
                            Screen('DrawLines', window, fixCoords, lineWidth, fixColor, centers(q, :));
                        end 
                    else 
                        Screen('DrawLines', window, fixCoords, lineWidth, fixColor, centers(q, :));
              
                        correctResponseStore{m}(1, f) = NaN;
                        
                    end 
                end
                vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
                
                %Calculate the correct response that the particpant should
                %have made.
               
                
                % TURN RESPONSE TRIGGER OFF
                 if isequal(floor(syncResponseFrames * 0.5), g)
                    tag_trigger_send(outTrig, outAddr, 0);
                end 
            end
            
            endTrial(f) = toc(startTrial);
            
            tag_trigger_send(outTrig, outAddr, 0);
            
        end
        
        %------------------------------------------------------------------
        %                        TRIGGER 4
        %------------------------------------------------------------------
        
        %TURN BREAK TRIGGER ON
        %tag_trigger_send(outTrig, outAddr, breakperiodCode);
        
        factChangeLim = 10;
        wordLimit = 120;
        percentCompleted = 0:100/(numBlocks+1):100;
        
        Screen('TextSize', window, 15);
        text1 = 'BreakTime! Count Down: ';
        vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
        [txt] = fact_box(wordLimit);  
        text4 = double(strcat('FUN FACT:', '',txt{randi(length(txt), 1)}));
        
        if isequal(m, 1) || isequal(m, 2)
            count = practiseBreakTimeLength;
        elseif m > 2
            count = breakTimeLength;
        end
        factTimer = 0;
        
        while ~isequal(count, 0)
            
            text2 = double(strcat(text1, '', char(string(count))));
            text3 = double(strcat('Experiment Completion:', '', char(string(percentCompleted(m+1))), '%'));
            
            if isequal(factTimer,factChangeLim)
                [txt] = fact_box(wordLimit);
                text4 = double(strcat('FUN FACT:', '', txt{randi(length(txt), 1)}));
                
                factTimer = 0;
            end
            
                Screen('DrawText', window, text2, centers(q, 1)-centers(q, 1)+5, centers(q, 2)-centers(q, 2)+5, black);
                Screen('DrawText', window, text3, centers(q, 1)-centers(q, 1)+5, centers(q, 2)-centers(q, 2)+55, black);
                Screen('DrawText', window, text4, centers(q, 1)-centers(q, 1)+5, centers(q, 2)-centers(q, 2)+105, black);
            
            vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
            pause(1)
            count = count - 1;
            factTimer = factTimer + 1;
            
            % TURN BREAK TRIGGER OFF
            
             if isequal(floor(count * 0.5), count)
                    tag_trigger_send(outTrig, outAddr, 0);
             end 
            
        end
        
        keyTrig = 0;
        while isequal(keyTrig, 0)
            Screen('DrawText', window, double('Press the c key to continue the experiment or the q key to quit'), centers(q, 1)-centers(q, 1)+5, centers(q, 2)-centers(q, 2)+5, black);
            vbl = Screen('Flip', window, vbl + 0.5 * MeasuredInterFrameRate);
             [ keyIsDown, ~, keyCode ] = KbCheck;
             
             if keyIsDown && keyCode(KbName('c'))
                 keyTrig = 1;
             elseif keyIsDown && keyCode(KbName('q'))
                  if isequal(DataPixxActive, 1)
                      Datapixx('SetPropixxDlpSequenceProgram', 0);
                      Datapixx('RegWrRd');
                      Datapixx('close');
                  end
                 Priority(0);
                 sca;
             end
                
        end 
       
        
        endblock(m) = toc(startBlock) - breakTimeLength;
    end
   
    %% ======================== ADDITIONAL FEATURES =======================           
    if isequal(showFreqPlot, 1)
        subplot(3, 2, 1);
        plot(freqTableStore{1}(1, 1:100), 'r');
        ylabel('Freq 65');
        title('Baseline period Tagging Freqs');
        ylim([0, 255])
        
        grid on
        
        subplot(3, 2, 2);
        plot(freqTableStore{1}(2, 1:100), 'r');
        ylabel('Freq 72');
        title('Baseline period Tagging Freqs');
        ylim([0, 255])
        
        grid on
        
        subplot(3, 2, 3);
        plot(freqTableStore{2}(1, 1:100), 'g');
        ylabel('Freq 65');
        title('Stimuli Period Tagging Freqs');
        ylim([0, 255])
        
        grid on
        
        subplot(3, 2, 4);
        plot(freqTableStore{2}(2, 1:100), 'g');
        ylabel('Freq 72');
        title('Stimuli Period Tagging Freqs');
        ylim([0, 255])
        
        grid on
        
        subplot(3, 2, 5);
        plot(freqTableStore{3}(1, 1:100), 'b');
        ylabel('Freq 65');
        title('Response Period Tagging Freqs');
        ylim([0, 255])
        grid on
        
        subplot(3, 2, 6);
        plot(freqTableStore{3}(2, 1:100), 'b');
        ylabel('Freq 72');
        title('Response Period Tagging Freqs');
        ylim([0, 255])
        
        grid on
    end
    
     

catch Error
   
    if isequal(DataPixxActive, 1)
        propixx to normal state
        Datapixx('SetPropixxDlpSequenceProgram', 0);
        Datapixx('RegWrRd');
        Datapixx('close');
    end
    Priority(0);
    sca;
    
    disp('====================== ERROR DETECTED =========================')
    disp(strcat('Error ID: ', Error.identifier))
    disp('----------------------------------------------------------------')
    disp(strcat('Error Message: ',Error.message))
    disp('----------------------------------------------------------------')
    disp(strcat('Error Line: ',string(Error.stack.line)))
    disp('================================================================')
    
end 
if isequal(DataPixxActive, 1)
    propixx to normal state
    Datapixx('SetPropixxDlpSequenceProgram', 0);
    Datapixx('RegWrRd');
    Datapixx('close');
end
Priority(0);
sca;

%-------------------------------------------------------------------------------
% Trigger Function Functions
%-------------------------------------------------------------------------------
function [handle, address] = tag_init_trigger_send()


address = hex2dec('BFF8'); % check this port address 
handle = io64;
status = io64(handle);
io64(handle, address, 0); % reset trigger

  
end % end

%-------------------------------------------------------------------------------
% Function
%-------------------------------------------------------------------------------
function tag_trigger_send(handle, address, code)

io64(handle, address, code); % send trigger code, e.g., 16 (pin 5)
  % set back to zero 

end % end

function [txt] = fact_box(wordLimit)
[~, txt, ~] = xlsread('Funny_Facts_Ting.xlsx');

[rows, ~]  = size(txt);

a = 1;
for i = 1:rows
    if length(txt{i}) > wordLimit
        mark(a) = i;
        a = a + 1;
    end 
end 

txt(mark) = [];
txt(strcmp(txt(:), '')) = [];

end 



