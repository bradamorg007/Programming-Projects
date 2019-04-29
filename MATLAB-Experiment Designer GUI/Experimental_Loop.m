%This Function Runs the Main Experiment, that the user iteracts with. 
%It pre-draws all the required objects onto the figure window, but makes
%them invisible. At which point the rest of the process is case of flicking
%each objects visibility on and off in the correct sequence with the
%appropiate iterval times 

%This was done to aid in reducing processing time allowing the interval and
%reaction times to be recorded as tight as possible. Current interval time
%is 1.0000

%==========================================================================
                    %Load Experiment Data 
%==========================================================================
function [ParticpantGlobalDataCell, terminated] = Experimental_Loop(StimulusData,ProbeData,ProbeSequence,ExperimentSettingsTable)
%==========================================================================
                    % Set Variables 
%==========================================================================

%Extract Data From Settings
GlobalSize = ExperimentSettingsTable{1, 12};

GlobalCellRows = GlobalSize(1);
GlobalCellCols = GlobalSize(2);

ItemsPerMem = ExperimentSettingsTable{1, 2};
TrailsPerMem = ExperimentSettingsTable{1, 3};

LenItemsPerMem = length(ItemsPerMem);

%==========================================================================
                       %Create Partcipant Data Stores;
%==========================================================================

ParticpantGlobalDataCell = cell(1,1);
ParticpantDataCellPerItem = cell(1, LenItemsPerMem);

try
    for Q = 1:LenItemsPerMem
            %Col 1 = Response, Col2 = Accuracy, Col3 = Reaction Time
            ParticpantDataCellPerItem{Q} = zeros(TrailsPerMem(Q), 3); 
    end 

    %==========================================================================  
                            %Display Variables
    %==========================================================================
    interval = "+";
    FontSize = 100;
    PositionVector = [0.05, 0.4, 0.9, 0.3];

    %==========================================================================
                              %DEFINE Figure
    %==========================================================================

    fig = figure('units','normalized','outerposition',[0 0 1 1]);

    set(fig, 'MenuBar', 'none');
    set(fig, 'ToolBar', 'none');
    %set(fig,  'Resize',  'off');
    
    questdlg('Welcome!!! The Task: You will be shown a serial sequence of digits or letters. Once the sequence ends you will be shown a probe digit or letter that will MATCH or will NOT MATCH one of the elements in the sequence just shown to you in the previous sequence. Press the Y key if you think the probe and an element in the previous sequence MATCH or the N Key if you think that they DONT MATCH. Enoy :D!', 'I am Ready', 'I am Ready', 'I am Ready');

    %==========================================================================
    %                   PREDEFINE OBJECTS BEFORE LOOP
    %==========================================================================
    stim_txt = uicontrol(fig,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',PositionVector ,...
                        'FontSize', FontSize, ...
                        'FontWeight', 'Bold',...
                        'String', '',...
                        'Visible', 'off');

     interval_txt = uicontrol(fig,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',PositionVector,...
                        'FontSize', FontSize, ...
                        'FontWeight', 'Bold',...
                        'String', interval,...
                        'Visible', 'off');

     probe_txt = uicontrol(fig,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',PositionVector,...
                        'FontSize', FontSize, ...
                        'FontWeight', 'Bold',...
                        'String', '',...
                        'Visible', 'off');

     static_txt1 = uicontrol(fig,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',PositionVector,...
                        'FontSize', 40, ...
                        'FontWeight', 'Bold',...
                        'String', 'The Memory Load has Ended. The next test begins in 30 Seconds',...
                        'Visible', 'off');
     static_txt2 = uicontrol(fig,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.05, 0.6, 0.9, 0.2],...
                        'FontSize', 50, ...
                        'FontWeight', 'Bold',...
                        'Visible', 'off');
         
    set(static_txt1, 'Visible', 'on');
    set(static_txt1, 'String', 'The Test Will Begin in 10 Seconds! Get Ready!!!');
              for t = 1:10
                  set(static_txt2, 'String',sprintf('CountDown = %s\n', num2str(abs(-10 + t))));
                  set(static_txt2, 'Visible', 'on');
                  pause(1)
                  set(static_txt2, 'Visible', 'off');
              end 
              set(static_txt1, 'Visible', 'off');
              set(static_txt1, 'Visible', 'on');
              set(static_txt1, 'String', 'Begin');
              pause(2)
              set(static_txt1, 'Visible', 'off');
              set(static_txt1, 'String', 'The Memory Load has Ended. The next test begins in 20 Seconds');
    %==========================================================================
                            %EXPERIMENTAL LOOP
    %==========================================================================
    %Because The Datasets shapes and sizes can vary alot it is required
    %that each loop determines the iteration length of the next inner loop
    for k = 1:GlobalCellCols
        Stim_matrix = StimulusData{k};
        ProbeData_matrix = ProbeData{k};
        ProbeSequenceMatrix = ProbeSequence{k};
        SaveParticipantData_Matrix = ParticpantDataCellPerItem{k};
        [StimMatrixRows, ~] = size(Stim_matrix);

        for j = 1:StimMatrixRows
            StimMatrix_scope = Stim_matrix(j, :);
            ProbeMatrix_scope = ProbeData_matrix(j);
            ProbeSequence_scope = ProbeSequenceMatrix(j);

            [~, StimMatrixScopeCols] = size(StimMatrix_scope);

            for i = 1:StimMatrixScopeCols

                set(stim_txt, 'Visible', 'on');
                set(stim_txt, 'String', StimMatrix_scope(i));

                pause(1)
                set(stim_txt, 'Visible', 'off');

                %Determines whether to activate the probe string or cross
                %string
                if isequal(i,StimMatrixScopeCols) == 0
                     set(interval_txt, 'Visible', 'on');

                     pause(1)

                     set(interval_txt, 'Visible', 'off');

                    %Key Response 
                else 
                     set(interval_txt, 'Visible', 'on');
                     set(interval_txt, 'String', 'Probe');
                     pause(1)
                     set(interval_txt, 'Visible', 'off');


                     set(probe_txt, 'String', ProbeMatrix_scope);

                     set(probe_txt, 'Visible', 'on');
                     %below is the inner user response loop. Attempted to
                     %make the centre loop
                     % processing as sparce as possible to
                     %capture accurate RT data
                     responseMade = 0;
                     while responseMade == 0 %waits until user hits a valid entry
                         tic;
                            KeyPress = waitforbuttonpress;
                            %E.g
                            % 28 leftarrow
                            % 29 rightarrow
                         value = double(get(gcf,'CurrentCharacter'));%gets key data and converts it to a char
                         [response, responseMade] = KeyPressResponse(value);%determined if user response is valid
                         ReactionTime = toc;
                         %Reset current Keys cache
                         set(gcf,'currentch',char(1))
                     end
                     disp('RESPONSE BROKE WHILE LOOP') %Use in cmd to verify centre while loop Breaks correctly
                     set(probe_txt, 'Visible', 'off');
                     set(interval_txt, 'String', interval);

                     %Store Trial Data
                     SaveParticipantData_Matrix(j, 1) = response;
                     SaveParticipantData_Matrix(j, 2) = ReactionTime;

                     if isequal(response, ProbeSequence_scope) == 1
                        SaveParticipantData_Matrix(j, 3) = 1;
                     else
                        SaveParticipantData_Matrix(j, 3) = 0;   
                     end                  
                end 

            end 
        end
      ParticpantDataCellPerItem{k} = SaveParticipantData_Matrix;
      if k < GlobalCellCols
              set(static_txt1, 'Visible', 'on');
              for t = 1:20
                  set(static_txt2, 'String',sprintf('CountDown = %s\n', num2str(abs(-20 + t))));
                  set(static_txt2, 'Visible', 'on');
                  pause(1)
                  set(static_txt2, 'Visible', 'off');
              end 
              set(static_txt1, 'Visible', 'off');
              set(static_txt1, 'Visible', 'on');
              set(static_txt1, 'String', 'Begin');
              pause(2)
              set(static_txt1, 'Visible', 'off');
              set(static_txt1, 'String', 'The Memory Load has Ended. The next test begins in 20 Seconds');
              
      elseif isequal(k, GlobalCellCols) == 1
              set(static_txt1, 'Visible', 'on');
              set(static_txt1, 'String', 'Thank you! The Experiment has Ended :D');
              pause(2);
              close(fig);
      end  
    end
    %particpant data is stored as a multi-Dimensional cell Array.as its
    %outer dimension is a singleton it can be added to another session
    %array that contain many particpants each with their own multi dim cell
    %singelton
    ParticpantGlobalDataCell{1, 1} = ParticpantDataCellPerItem;
    terminated = 1;
    
catch Exception
    
    if  strcmp(Exception.identifier,'MATLAB:UI:CancelWaitForKeyOrButtonPress') == 1 ||  strcmp(Exception.identifier, 'MATLAB:class:InvalidHandle') == 1
        msg = questdlg('The Experimental Session has been terminated. Please note that current session data has not been saved', 'Session Termination', 'OK', 'OK');
        switch msg
            case 'OK'
                terminated = 3;
                %LINK BACK TO EXPERIMENTAL START PAGE
                experimental_settings()
        end 
    end 
    
end 
end 